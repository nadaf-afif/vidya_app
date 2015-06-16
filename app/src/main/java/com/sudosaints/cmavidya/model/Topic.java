package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 13/8/14.
 */
@DatabaseTable
public class Topic implements Serializable {

	@DatabaseField(id = true)
	@JsonProperty("IdTopicMaster")
	private int idTopicMaster;

	@DatabaseField
	@JsonProperty("TopicName")
	private String topicName;

	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Subject subject;

	public Topic() {
		this.subject = new Subject();
	}


	public int getIdTopicMaster() {
		return idTopicMaster;
	}

	public void setIdTopicMaster(int idTopicMaster) {
		this.idTopicMaster = idTopicMaster;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public int getIdSubjectMaster() {
		return this.subject.getIdSubjectMaster();
	}

	public void setIdSubjectMaster(int idSubjectMaster) {
		this.subject.setIdSubjectMaster(idSubjectMaster);
	}

	public String getSubjectName() {
		return this.subject.getSubjectName();
	}

	public void setSubjectName(String subjectName) {
		this.subject.setSubjectName(subjectName);
	}


}
