package com.fdmgroup.qolab.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

import com.fdmgroup.qolab.Utility.BooleanTFConverter;
import com.google.gson.annotations.Expose;

/**
 * Room POJO. Contains room id, trainer who created the room,
 * the list of trainees on the room, challenge taking place on the room,
 * problem statment and toggelable attributes.
 * 
 * @since 2020-10-23
 */
@Component
@Entity
public class Room implements Cloneable {
	
	/**
	 * The unique id of the room (room key).
	 */
	@Id
	@Column(name="ROOM_ID")
	private String roomId;
	
	/**
	 * The trainer who created the room. 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_TRAINER_ID")
	private Trainer trainer;
	
	/**
	 * ArrayList of trainees on the room. 
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
	private List<Trainee> trainees = new ArrayList<Trainee>();
	
	/**
	 * The challenge being run on the room. 
	 */
	@OneToOne (cascade=CascadeType.ALL)
	@JoinColumn(name = "FK_Challenge_ID", referencedColumnName="CHALLENGE_ID")
	private Challenge challenge;
	
	/**
	 * The title of the problem being run on the room. 
	 */
	@Lob
	@Column(name="PROBLEM_STATEMENT", length=1024)
	private String problemStatement;
	
	//TODO: Phase out the toggleable attributes not in use.
	
	/**
	 * Are trainees allowed to look at other trainees screen on this room?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isScreenPeekMode;
	
	/**
	 * Are trainees allowed to send code to other trainees on this room?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isSendCodeEnabled;
	
	/**
	 * Are trainees allowed to send code to the trainer on this room?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isSendTrainerCodeEnabled;
	
	/**
	 * Are trainees allowed to compile their code on this room?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isCompileCodeEnabled;
	
	/**
	 * Is there is timer restriction imposed on this room?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isTimerEnabled;
	
	/**
	 * Are trainees progressing(working) on this room?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isInProgressEnabled;
	
	/**
	 * Are trainees allowed to ask for help on this room?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isNeedsHelpEnabled;
	
	/**
	 * Is the trainne done completing this room?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isCompletedEnabled;
	
	/**
	 * Constructor
	 */
	public Room() {}
	
	public Room(String roomId, Trainer trainer, String problemStatement, boolean isScreenPeekMode) {
		super();
		this.roomId = roomId;
		this.trainer = trainer;
		this.problemStatement = problemStatement;
		this.isScreenPeekMode = isScreenPeekMode;
	}
	
	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

//	public String getRoomPin() {
//		return roomPin;
//	}
//
//	public void setRoomPin(String roomPin) {
//		this.roomPin = roomPin;
//	}

	public Boolean getIsSendTrainerCodeEnabled() {
		return isSendTrainerCodeEnabled;
	}

	public void setIsSendTrainerCodeEnabled(Boolean isSendTrainerCodeEnabled) {
		this.isSendTrainerCodeEnabled = isSendTrainerCodeEnabled;
	}

	public String getRoomId() {
		return roomId;
	}
	
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public List<Trainee> getTrainees() {
		return trainees;
	}

	public void setTrainees(List<Trainee> trainees) {
		this.trainees = trainees;
	}

	public String getProblemStatement() {
		return problemStatement;
	}

	public void setProblemStatement(String problemStatement) {
		this.problemStatement = problemStatement;
	}

	public Boolean getIsScreenPeekMode() {
		return isScreenPeekMode;
	}

	public void setIsScreenPeekMode(Boolean isScreenPeekMode) {
		this.isScreenPeekMode = isScreenPeekMode;
	}

	public Boolean getIsSendCodeEnabled() {
		return isSendCodeEnabled;
	}

	public void setIsSendCodeEnabled(Boolean isSendCodeEnabled) {
		this.isSendCodeEnabled = isSendCodeEnabled;
	}

	public Boolean getIsCompileCodeEnabled() {
		return isCompileCodeEnabled;
	}

	public void setIsCompileCodeEnabled(Boolean isCompileCodeEnabled) {
		this.isCompileCodeEnabled = isCompileCodeEnabled;
	}

	public Boolean getIsTimerEnabled() {
		return isTimerEnabled;
	}

	public void setIsTimerEnabled(Boolean isTimerEnabled) {
		this.isTimerEnabled = isTimerEnabled;
	}

	public Boolean getIsInProgressEnabled() {
		return isInProgressEnabled;
	}

	public void setIsInProgressEnabled(Boolean isInProgressEnabled) {
		this.isInProgressEnabled = isInProgressEnabled;
	}

	public Boolean getIsNeedsHelpEnabled() {
		return isNeedsHelpEnabled;
	}

	public void setIsNeedsHelpEnabled(Boolean isNeedsHelpEnabled) {
		this.isNeedsHelpEnabled = isNeedsHelpEnabled;
	}

	public Boolean getIsCompletedEnabled() {
		return isCompletedEnabled;
	}

	public void setIsCompletedEnabled(Boolean isCompletedEnabled) {
		this.isCompletedEnabled = isCompletedEnabled;
	}

	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", problemStatement="
				+ problemStatement + ", isScreenPeekMode=" + isScreenPeekMode + ", isSendCodeEnabled="
				+ isSendCodeEnabled + ", isSendTrainerCodeEnabled=" + isSendTrainerCodeEnabled
				+ ", isCompileCodeEnabled=" + isCompileCodeEnabled + ", isTimerEnabled=" + isTimerEnabled
				+ ", isInProgressEnabled=" + isInProgressEnabled + ", isNeedsHelpEnabled=" + isNeedsHelpEnabled
				+ ", isCompletedEnabled=" + isCompletedEnabled + "]";
	}
	
	public Object clone() throws CloneNotSupportedException {
		Room t = (Room) super.clone();
		return t;
	}
	
}
