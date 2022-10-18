package com.fdmgroup.qolab.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

import com.fdmgroup.qolab.Utility.BooleanTFConverter;
import com.google.gson.annotations.Expose;

/**
 * Challenge POJO. Contains challenge id, challenge name, 
 * trainer who created challenge, the 
 * problem statment and toggleable attributes.
 * 
 * @since 2020-10-23
 */
@Component
@Entity
public class Challenge {
	
	/**
	 * The uniqe id of the challenge. 
	 */
	@Id
	@SequenceGenerator(name = "challengeSeq", sequenceName = "SEQ_CHA",  initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challengeSeq")
	@Column(name="CHALLENGE_ID")
	private int challengeId;
	
	/**
	 * The heading(title) of the challenge. 
	 */
	@Column(name="CHALLENGE_NAME")
	private String challengeName;
	
	/**
	 * Trainer who created the challenge. 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TRAINER_ID")
	private Trainer trainer;
	
	//Toggleable Attributes
	//TODO: Phase out the toggleable attributes not in use.
	/**
	 * The problem description. Deatils about the problem. 
	 */
	@Lob
	@Column(name="PROBLEM_STATEMENT", length=1024)
	private String problemStatement;
	
	/**
	 * Are trainees allowed to look at other trainees screen on this challenge?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isScreenPeekMode;
	
	/**
	 * Are trainees allowed to send code to other trainees on this challenge?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isSendCodeEnabled;
	
	/**
	 * Are trainees allowed to send code to the trainer on this challenge?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isSendTrainerCodeEnabled;
	
	/**
	 * Are trainees allowed to compile their code on this challenge?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isCompileCodeEnabled;
	
	/**
	 * Is there is timer restriction imposed on this challenge?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isTimerEnabled;
	
	/**
	 * Are trainees progressing(working) on this challenge?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isInProgressEnabled;
	
	/**
	 * Are trainees allowed to ask for help on this challenge?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isNeedsHelpEnabled;
	
	/**
	 * Is the trainne done completing this challenge?
	 */
	@Convert(converter=BooleanTFConverter.class)
	private Boolean isCompletedEnabled;
	
	/**
	 * Constructor
	 */
	public Challenge() {}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public int getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}

	public String getChallengeName() {
		return challengeName;
	}

	public void setChallengeName(String challengeName) {
		this.challengeName = challengeName;
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

	public Boolean getIsSendTrainerCodeEnabled() {
		return isSendTrainerCodeEnabled;
	}

	public void setIsSendTrainerCodeEnabled(Boolean isSendTrainerCodeEnabled) {
		this.isSendTrainerCodeEnabled = isSendTrainerCodeEnabled;
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
		return "Challenge [challengeId=" + challengeId + ", problemStatement=" + problemStatement
				+ ", isScreenPeekMode=" + isScreenPeekMode + ", isSendCodeEnabled=" + isSendCodeEnabled
				+ ", isSendTrainerCodeEnabled=" + isSendTrainerCodeEnabled + ", isCompileCodeEnabled="
				+ isCompileCodeEnabled + ", isTimerEnabled=" + isTimerEnabled + ", isInProgressEnabled="
				+ isInProgressEnabled + ", isNeedsHelpEnabled=" + isNeedsHelpEnabled + ", isCompletedEnabled="
				+ isCompletedEnabled + "]";
	}
	
	
}
