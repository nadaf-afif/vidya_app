package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 9/12/14.
 */
@DatabaseTable(tableName = "SubjectReplanData")
public class SubjectReplanData implements Serializable {
	@DatabaseField
	@JsonProperty("AvailableRevTime")
	private long availableRevTime;

	@DatabaseField
	@JsonProperty("IdSubjectNameSakeMaster")
	private long idSubjectNameSakeMaster;

	@DatabaseField
	@JsonProperty("RevisionNo")
	private long revisionNo;

	@DatabaseField
	@JsonProperty("SubRevTime")
	private long subRevTime;

	@DatabaseField
	@JsonProperty("Subject")
	private String subject;

	@DatabaseField
	@JsonProperty("idIndex")
	private long idIndex;

	@DatabaseField(generatedId = true)
	private int id;

	public int getId() {
		return id;
	}

	public long getAvailableRevTime() {
		return availableRevTime;
	}

	public void setAvailableRevTime(long availableRevTime) {
		this.availableRevTime = availableRevTime;
	}

	public long getIdSubjectNameSakeMaster() {
		return idSubjectNameSakeMaster;
	}

	public void setIdSubjectNameSakeMaster(long idSubjectNameSakeMaster) {
		this.idSubjectNameSakeMaster = idSubjectNameSakeMaster;
	}

	public long getRevisionNo() {
		return revisionNo;
	}

	public void setRevisionNo(long revisionNo) {
		this.revisionNo = revisionNo;
	}

	public long getSubRevTime() {
		return subRevTime;
	}

	public void setSubRevTime(long subRevTime) {
		this.subRevTime = subRevTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public long getIdIndex() {
		return idIndex;
	}

	public void setIdIndex(long idIndex) {
		this.idIndex = idIndex;
	}
}