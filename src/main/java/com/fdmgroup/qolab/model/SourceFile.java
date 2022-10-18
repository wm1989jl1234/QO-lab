package com.fdmgroup.qolab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;

/**
 * SourceFile POJO. Contains file id, text written by the trainer,
 * title of the source file, trainee and writing the source code.
 * 
 * @since 2020-10-23
 */
@Component
@Entity
public class SourceFile {
	
	/**
	 * Uniqe Id of the source code file. 
	 */
	@Id
	@SequenceGenerator(name = "sourceFileSeq", sequenceName = "SEQ_SOURCE_FILE",  initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sourceFileSeq")
	@Column(name="SOURCE_FILE_ID")
	private int sourceFileId;
	
	/**
	 * The text written by the trainee as a code. (Code)
	 */
	@Column(name="BODY_TEXT")
	private String bodyText;
	
	/** 
	 * The title of the source file. (Class name)
	 */
	@Column(name="TITLE")
	private String title;
	
	/** 
	 * The trainee that wrote the code. 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TRAINEE_ID")
	private Trainee trainee;
	
	/**
	 * Default constructor
	 */
	public SourceFile() {}
	
	/**
	 * Constructor 
	 * 
	 * @param bodyText
	 * @param title
	 * @param trainee
	 * 
	 * @return A source file object with the given attributes from param. 
	 */
	public SourceFile(String bodyText, String title, Trainee trainee) {
		super();
		this.bodyText = bodyText;
		this.title = title;
		this.trainee = trainee;
	}

	public int getSourceFileId() {
		return sourceFileId;
	}

	public void setSourceFileId(int sourceFileId) {
		this.sourceFileId = sourceFileId;
	}

	public Trainee getTrainee() {
		return trainee;
	}

	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "SourceFile [sourceFileId=" + sourceFileId + ", bodyText=" + bodyText + ", title=" + title + ", trainee="
				+ trainee + "]";
	}

}
