package com.fdmgroup.qolab.model;

import java.util.List;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import com.fdmgroup.qolab.Utility.BooleanTFConverter;
import com.fdmgroup.qolab.Utility.CloneClass;
import com.google.gson.annotations.Expose;

/**
 * Trainee POJO. Contains trainee id, source files written by the trainee,
 * console output, room and status attributes.
 * 
 * @since 2020-10-23
 */
@Component
@Entity
public class Trainee implements Cloneable {
	
	/**
	 * Unique id of a trainer generated from a sequence.
	 */
	@Id
	@SequenceGenerator(name = "	traineeSeq", sequenceName = "SEQ_TRAINEE",  initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "traineeSeq")
	@Column(name="TRAINEE_ID")
	private int traineeId;
	
	/**
	 * The name of the trainee. 
	 */
	@Column(name="TRAINEE_NAME", unique=false)
	private String name;
	
	/**
	 * List of the source files created by the trainee.
	 */
	@OneToMany(mappedBy = "trainee")
	private List<SourceFile> sourceFiles;
	
	/**
	 * The output from the trainee's console. 
	 */
	private String consoleOutput;
	
	/**
	 * The room which the trainee is part of. 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ROOM_ID")
	private Room room;
	
	/**
	 * Status Attribute #1
	 * Does the trainee need help?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isNeedHelp;
	

	/**
	 * Status Attribute #2
	 * Is the trainee finished?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isDone;
	
	/**
	 * Status Attribute #3
	 * Is the trainee currently typing?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isTyping;
	
	/**
	 * The id of the room trainee is currently part of. 
	 */
	@Transient
	private String roomTag;
	
	
	@Transient
	private Timestamp isDoneTimestamp;

	@Transient
	private String sessionId;

	/**
	 * Default constructor. 
	 * 
	 * @return Trainee object with the console output of empty string ("").
	 */
	public Trainee() {
		this.consoleOutput = "";
	}
	
	/**
	 * Constructor
	 * 
	 * @param traineeId
	 * @param name
	 * @param sourceFiles
	 * @param room
	 * @param isNeedHelp
	 * @param isDone
	 * @param isTyping
	 * 
	 * @return Trainee object with the attributes set from the parameters. 
	 */
	public Trainee(String name, List<SourceFile> sourceFiles, Room room, Boolean isNeedHelp,
			Boolean isDone, Boolean isTyping) {
		super();
		this.name = name;
		this.sourceFiles = sourceFiles;
		this.room = room;
		this.isNeedHelp = isNeedHelp;
		this.isDone = isDone;
		this.isTyping = isTyping;
		this.consoleOutput = "";
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(int traineeId) {
		this.traineeId = traineeId;
	}

	public String getRoomTag() {
		return roomTag;
	}

	public void setRoomTag(String roomTag) {
		this.roomTag = roomTag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SourceFile> getSourceFiles() {
		return sourceFiles;
	}

	public void setSourceFiles(List<SourceFile> sourceFiles) {
		this.sourceFiles = sourceFiles;
	}
	
	public String getConsoleOutput() {
		return consoleOutput;
	}

	public void setConsoleOutput(String consoleOutput) {
		this.consoleOutput = consoleOutput;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Boolean getIsNeedHelp() {
		return isNeedHelp;
	}

	public void setIsNeedHelp(Boolean isNeedHelp) {
		this.isNeedHelp = isNeedHelp;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}


	public Boolean getIsTyping() {
		return isTyping;
	}

	public void setIsTyping(Boolean isTyping) {
		this.isTyping = isTyping;
	}
	
	public Timestamp getIsDoneTimestamp() {
		return isDoneTimestamp;
	}

	public void setIsDoneTimestamp(Timestamp isDoneTimestamp) {
		this.isDoneTimestamp = isDoneTimestamp;
	}

	@Override
	public String toString() {
		return "Trainee [traineeId=" + traineeId + ", name=" + name + ", sourceFiles=" + sourceFiles + ", room=" + room
				+ ", isNeedHelp=" + isNeedHelp + ", isDone=" + isDone + ", isTyping=" + isTyping + "]";
	}
	
	/**
	 * A method to clone the trainee object. 
	 * 
	 * @return Trainee object that is a exact clone of this trainee object. 
	 * @throws CloneNotSupportedException
	 * 
	 */
	public Object clone() throws CloneNotSupportedException {
		Trainee t = (Trainee) super.clone();
		return t;
	}
	
}
