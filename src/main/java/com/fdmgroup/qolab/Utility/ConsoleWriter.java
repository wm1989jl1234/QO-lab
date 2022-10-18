package com.fdmgroup.qolab.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.CharBuffer;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fdmgroup.qolab.model.Room;
import com.fdmgroup.qolab.model.Trainee;

/**
* ConsoleWriter is the class responsible for asynchronously publishing the contents on a BufferedReader object to the trainee's 
* consoleOutput topic in the websocket. This class must close the process's input/output streams when process.destroy() is called elsewhere.
* note that there is a hard-coded limit of 500 characters that can be written to the trainee's console before characters will stop being written.
* 
* @author Ian Jensen
* 
*/
public class ConsoleWriter implements Runnable{

	private BufferedReader bufferedReader;
	private SimpMessagingTemplate template;
	private Room currentRoom;
	private String traineeName;
	private Process process;
	
	/**
	 * 
	 * @param bufferedReader the buffer from which the contents are to be sent character-by-character to the 
	 * trainee's consoleOutput websocket topic
	 * @param template the SimpMessagingTemplate used to connect to the websocket
	 * @param currentRoom the Room object associated with the session the trainee belongs to
	 * @param traineeName the (uniquely identifying) name of the trainee
	 * @param process the process from which bufferedReader was generated. the process will have it's streams 
	 * closed in the event that process.destroy is called elsewhere
	 */
	public ConsoleWriter(BufferedReader bufferedReader, SimpMessagingTemplate template, Room currentRoom, String traineeName, Process process){
		this.bufferedReader = bufferedReader;
		this.template = template;
		this.currentRoom = currentRoom;
		this.traineeName = traineeName;
		this.process = process;
	}
	
	@Override
	public void run() {
		for (Trainee traineeIteration : currentRoom.getTrainees()) {
			if (traineeIteration.getName().equals(traineeName)) {
				
				//stream the contents of the bufferedReader to the websocket one character at a time
				String outCharacter;
		        while ((outCharacter = safeRead(bufferedReader)) != null) {
		        	
		        	//wait to give the websocket time to flush its buffer
		        	try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		        	
		        	String currentConsoleOutput = traineeIteration.getConsoleOutput();
		        	if(currentConsoleOutput.length() < 500) {
		        		// the trainee's consoleOutput is within limit
						traineeIteration.setConsoleOutput(currentConsoleOutput + outCharacter);
						template.convertAndSend("/topic/" + currentRoom.getRoomId(), currentRoom);
		        	}else {
		        		// the trainee's consoleOutput has gotten too big
						traineeIteration.setConsoleOutput("SIZE LIMIT EXCEEDED\n");
						template.convertAndSend("/topic/" + currentRoom.getRoomId(), currentRoom);
						try {
							// preemptively close the process's input streams. this may not be necessary
							process.getOutputStream().close();
							process.getInputStream().close();
							process.getErrorStream().close();
							} catch (IOException e) {e.printStackTrace();}
						break;
		        	}
		        }
			}
		}
	}
	
	/**
	 * safeRead allows reading of a BufferedReader and handles any IOException that occurs 
	 * as a result of the underlying process being destroyed
	 * 
	 * @param br the BufferedReader object from which a single character is read
	 * @return returns the next character of the BufferedReader or null if an IOexception has 
	 * occurred when attempting to read
	 */
	private String safeRead(BufferedReader br) {	
		try {
			int outInt = br.read();
			if ( outInt != -1) {
				return "" + (char) outInt;
			}else {
				// -1 means the BufferedReader has reached its end
				return null;
			}
		} catch (IOException e) {
			try {
				// an IOException means that process.destroy() was called somewhere else, so close the input/output streams
				process.getOutputStream().close();
				process.getInputStream().close();
				process.getErrorStream().close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}
}
