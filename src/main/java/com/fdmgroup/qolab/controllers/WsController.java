package com.fdmgroup.qolab.controllers;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.json.*;

import com.fdmgroup.qolab.Utility.ConsoleWriter;
import com.fdmgroup.qolab.Utility.ProccessTimeOutWatchdog;
import com.fdmgroup.qolab.Utility.StringCompiler;
import com.fdmgroup.qolab.model.Room;
import com.fdmgroup.qolab.model.Trainee;
import com.fdmgroup.qolab.model.Trainer;
import com.fdmgroup.qolab.model.SourceFile;

/**
 * WebSocket controller class. 
 * 
 *
 * @since 2020-10-23
 */
@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate template;

	ArrayList<Room> rooms = new ArrayList();
	
	//Trainer creates room
	@MessageMapping("/trainer/createRoom/{roomId}")
	@SendTo("/topic/{roomId}")
	public void createRoom (@Payload String payload, @DestinationVariable String roomId) {
		
		//Check if room already exists (i.e. trainer refreshes page)
		Room currentRoom = findRoom(roomId);
		
		//Create room if one doesn't exist
		if (currentRoom == null) {
			System.out.println("Room created - Room ID: " + roomId + " Trainer: " + payload);
			currentRoom = new Room();
			currentRoom.setRoomId(roomId);
			Trainer newTrainer = new Trainer();
			newTrainer.setUsername(payload);
			currentRoom.setTrainer(newTrainer);
			rooms.add(currentRoom);
		}
		
		//Send the room object to subscribers
		template.convertAndSend("/topic/" + roomId, currentRoom); 
	}
	
	//Trainee joins room
	@MessageMapping("/trainee/register/{roomId}")
	@SendTo("/topic/{roomId}")
	public Room register (@Payload String json, @DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor) {
		
		Trainee currentTrainee = null;
		
		String jsonString = json;
		JSONObject obj = new JSONObject(jsonString);
		String traineeName = obj.getString("name");
		
		//Find room object (returns null if no room found)
		Room currentRoom = findRoom(roomId);
		
		//Check if user is already registered (mainly for disconnected users to continue their session)
		for (Trainee traineeIteration : currentRoom.getTrainees()) {
			if (traineeIteration.getName().equals(traineeName)) {
				System.out.println("Welcome back: " + traineeName);
				currentTrainee = traineeIteration;
				currentTrainee.setSessionId(headerAccessor.getSessionId());
			}
		}
		
		//If no trainee exists, add them to room
		if (currentTrainee == null) {
			System.out.println("Trainee joined - Room ID:" + roomId + " Trainee: " + traineeName + " Session ID: " + headerAccessor.getSessionId());
			currentTrainee = new Trainee();
			currentTrainee.setName(traineeName);
			List<SourceFile> sourceFiles = new ArrayList<SourceFile>();
			sourceFiles.add(new SourceFile("public class Main {\r\n\tpublic static void main(String[] args){\r\n\t\r\n\t}\r\n}", "Main", null));
			currentTrainee.setSourceFiles(sourceFiles);
			currentTrainee.setRoomTag(roomId);
			currentTrainee.setIsNeedHelp(false);
			currentTrainee.setIsDone(false);
			currentTrainee.setIsTyping(false);
			currentTrainee.setSessionId(headerAccessor.getSessionId());
			currentRoom.getTrainees().add(currentTrainee);
		}
		
		return currentRoom;
	}

	//Room updates (e.g. new code written by trainees)
	@MessageMapping("/trainee/send/{roomId}")
	@SendTo("/topic/{roomId}")
	public Room sendMessage(@Payload Trainee trainee, @DestinationVariable String roomId) {
		System.out.println("Code update - Room: " + roomId + " Trainee: " + trainee.getName());
		Room currentRoom = null;
		for (Room roomIteration : rooms) {
			if (roomIteration.getRoomId().equals(trainee.getRoomTag())) {
				for (Trainee traineeIteration : roomIteration.getTrainees()) {
					if (traineeIteration.getName().equals(trainee.getName())) {
						currentRoom = roomIteration;
						traineeIteration.setSourceFiles(trainee.getSourceFiles());
						break;
					}
				}
			}
		}
		return currentRoom;
	}
	
	//Status updates
	@MessageMapping("/trainee/status/{roomId}")
	@SendTo("/topic/{roomId}")
	public Room updateStatus(@Payload Trainee trainee, @DestinationVariable String roomId) {
		System.out.println("Status update - Room: " + roomId + " Trainee: " + trainee.getName());
		Room currentRoom = null;
		for (Room roomIteration : rooms) {
			if (roomIteration.getRoomId().equals(trainee.getRoomTag())) {
				for (Trainee traineeIteration : roomIteration.getTrainees()) {
					if (traineeIteration.getName().equals(trainee.getName())) {
						if (!traineeIteration.getIsDone().equals(trainee.getIsDone())) {
							traineeIteration.setIsDone(trainee.getIsDone());
							if (trainee.getIsDone()) {
								traineeIteration.setIsDoneTimestamp(new Timestamp(System.currentTimeMillis()));
							}
							else {
								traineeIteration.setIsDoneTimestamp(null);
							}
						}
						traineeIteration.setIsNeedHelp(trainee.getIsNeedHelp());
						traineeIteration.setIsTyping(trainee.getIsTyping());
						currentRoom = roomIteration;
						break;
					}
				}
			}
		}
		return currentRoom;
	}
	

	//trainee runs code
	@MessageMapping("/trainee/run/{roomId}")
	public void runCode(@Payload Trainee trainee, @DestinationVariable String roomId) {
		System.out.println("running code in room:" + roomId + " Trainee: " + trainee.getName());
		Room currentRoom = null;
		for (Room roomIteration : rooms) {
			if (roomIteration.getRoomId().equals(trainee.getRoomTag())) {
				
				currentRoom = roomIteration;
				
				
				//reset the console output
				for (Trainee traineeIteration : currentRoom.getTrainees()) {
					if (traineeIteration.getName().equals(trainee.getName())) {
						traineeIteration.setConsoleOutput("");
					
						//run
						StringCompiler stringCompiler = new StringCompiler();
						Process process = stringCompiler.compileAndRun(trainee.getRoomTag()+trainee.getName(), trainee.getSourceFiles());
						String compilationErrors = stringCompiler.getCompilationErrors();
						
						//publish the compilation errors to websocket
						traineeIteration.setConsoleOutput(traineeIteration.getConsoleOutput() + compilationErrors);
						template.convertAndSend("/topic/" + currentRoom.getRoomId(), currentRoom);

						// abort if the code failed to compile
						if(process == null) 
							break;
						
						//start the timeout watchdog
						ProccessTimeOutWatchdog ptoc = new ProccessTimeOutWatchdog(process, traineeIteration, template, currentRoom);
			            Thread processTimer = new Thread(ptoc);
			            processTimer.start();

						//stream the stdout to websocket
						BufferedReader stdInput = new BufferedReader(new 
			            	     InputStreamReader(process.getInputStream()));
						ConsoleWriter stdWriter = new ConsoleWriter(stdInput, template, currentRoom, trainee.getName(), process);
						Thread stdWriterThread = new Thread(stdWriter);
						stdWriterThread.start();
						
						//stream the stderr to websocket
						 BufferedReader errInput = new BufferedReader(new 
			            	     InputStreamReader(process.getErrorStream()));
						ConsoleWriter errWriter = new ConsoleWriter(errInput, template, currentRoom, trainee.getName(), process);
						Thread errWriterThread = new Thread(errWriter);
						errWriterThread.start();
						
						break;
					}
				}
				break;
			}
		}
	}
	
	//trainee creates a new tab
	@MessageMapping("/trainee/createClass/{roomId}/{traineeName}")
	@SendTo("/topic/{roomId}")
	public Room createClass(@Payload String className, @DestinationVariable String roomId, @DestinationVariable String traineeName) {
		System.out.println(className+ roomId+ traineeName);
		Room currentRoom = findRoom(roomId);
		for (Trainee traineeIteration : currentRoom.getTrainees()) {
			if (traineeIteration.getName().equals(traineeName)) {
				for(SourceFile s : traineeIteration.getSourceFiles()) {
					if(s.getTitle().equals(className)) {
						return currentRoom;
					}
				}
				traineeIteration.getSourceFiles().add(
						new SourceFile("public class "+className+" {\r\n"
								+ "	\r\n"
								+ "}", 
								className, 
								null)
						);
			}
		}
		return currentRoom;
	}
		
	
	
	//Handles user disconnection
	@EventListener(SessionDisconnectEvent.class)
	public void handleWebsocketDisconnectListner(SessionDisconnectEvent event) {
		System.out.println("Session disconnecting: " + event.getSessionId());
		Room currentRoom = null;
		for (Room roomIteration : rooms) {
			for (Trainee traineeIteration : roomIteration.getTrainees()) {
				if (traineeIteration.getSessionId().equals(event.getSessionId())) {
					System.out.println("Trainee left - Room ID: " + roomIteration.getRoomId() + " Trainee: " + traineeIteration.getName());
					traineeIteration.setSessionId(null);
					currentRoom = roomIteration;
					break;
				}
			}
		}
		template.convertAndSend("/topic/" + currentRoom.getRoomId(), currentRoom);
	}
	
	//Function to find room by Room ID
	private Room findRoom(String roomId) {
		for (Room roomIteration : rooms) {
			if (roomId.equals(roomIteration.getRoomId())) {
				return roomIteration;
			}
		}
		return null;
	}

	
}
