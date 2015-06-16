package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 13/8/14.
 */
@DatabaseTable
public class Subject implements Serializable {


	@DatabaseField(id = true)
	@JsonProperty("IdSubjectMaster")
	private int idSubjectMaster;

	@DatabaseField
	@JsonProperty("SubjectName")
	private String subjectName;


	public int getIdSubjectMaster() {
		return idSubjectMaster;
	}

	public void setIdSubjectMaster(int idSubjectMaster) {
		this.idSubjectMaster = idSubjectMaster;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}
