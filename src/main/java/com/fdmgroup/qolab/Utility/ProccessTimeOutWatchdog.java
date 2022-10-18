package com.fdmgroup.qolab.Utility;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fdmgroup.qolab.model.Room;
import com.fdmgroup.qolab.model.Trainee;

/**
 * ProccessTimeOutWatchdog is responsible for asynchronously killing a process if it has not ended within a (hard-coded) time window
 * 
 * @author Ian Jensen
 *
 */
public class ProccessTimeOutWatchdog implements Runnable{
	
	private Process process;
	private Trainee traineeIteration;
	private SimpMessagingTemplate template;
	private Room currentRoom;
	
	/**
	 * 
	 * @param process the Process object to be terminated after 5 seconds
	 * @param traineeIteration the Trainee object which will be receiving the notification that the process has timed out
	 * @param template the SimpMessagingTemplate used for websocket connection
	 * @param currentRoom the Room object to be modified and sent back to the websocket
	 */
	public ProccessTimeOutWatchdog(Process process, Trainee traineeIteration, SimpMessagingTemplate template, Room currentRoom) {
		super();
		this.process = process;
		this.traineeIteration = traineeIteration;
		this.template = template;
		this.currentRoom = currentRoom;
		
	}

	@Override
	public void run() {
		// wait 5 seconds before attempting to kill the process
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(process.isAlive()) {
			System.out.println("process timed out. killing process...");
			
			//publish message to the websocket
			traineeIteration.setConsoleOutput(traineeIteration.getConsoleOutput() + "process timed out. killing process...\n");
			template.convertAndSend("/topic/" + currentRoom.getRoomId(), currentRoom);
			
			//killing process. the method that reads from the process's streams is responsible for closing them.
			process.destroy(); 
		    System.out.println(process.isAlive());

		}
	}
}
