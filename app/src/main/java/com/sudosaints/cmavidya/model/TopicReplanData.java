package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 9/12/14.
 */
@DatabaseTable(tableName = "TopicReplanData")
public class TopicReplanData implements Serializable {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField
	@JsonProperty("AvailableTopicRevTime")
	private long availableTopicRevTime;


	@DatabaseField
	@JsonProperty("IdIndex")
	private long idIndex;

	@DatabaseField
	@JsonProperty("IdSubjectNameSakeMaster")
	private long idSubjectNameSakeMaster;

	@DatabaseField
	@JsonProperty("IdTopic")
	private long idTopic;

	@DatabaseField
	@JsonProperty("RevisionNo")
	private long revisionNo;

	@DatabaseField
	@JsonProperty("Subject")
	private String subject;

	@DatabaseField
	@JsonProperty("Topicname")
	private String topicname;

	@DatabaseField
	@JsonProperty("IsActive")
	private boolean isActive;


	public int getId() {
		return id;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public long getAvailableTopicRevTime() {
		return availableTopicRevTime;
	}

	public void setAvailableTopicRevTime(long availableTopicRevTime) {
		this.availableTopicRevTime = availableTopicRevTime;
	}

	public long getIdIndex() {
		return idIndex;
	}

	public void setIdIndex(long idIndex) {
		this.idIndex = idIndex;
	}

	public long getIdSubjectNameSakeMaster() {
		return idSubjectNameSakeMaster;
	}

	public void setIdSubjectNameSakeMaster(long idSubjectNameSakeMaster) {
		this.idSubjectNameSakeMaster = idSubjectNameSakeMaster;
	}

	public long getIdTopic() {
		return idTopic;
	}

	public void setIdTopic(long idTopic) {
		this.idTopic = idTopic;
	}

	public long getRevisionNo() {
		return revisionNo;
	}

	public void setRevisionNo(long revisionNo) {
		this.revisionNo = revisionNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTopicname() {
		return topicname;
	}

	public void setTopicname(String topicname) {
		this.topicname = topicname;
	}
}
