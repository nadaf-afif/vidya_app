package com.sudosaints.cmavidya.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 3/2/15.
 */
public class PreviousTestDTO implements Serializable {
	@JsonProperty("UserName")
	private String userName;
	@JsonProperty("IdTestType")
	private int idTestType;
	@JsonProperty("IdPreviousExam")
	private int idPreviousExam;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIdTestType() {
		return idTestType;
	}

	public void setIdTestType(int idTestType) {
		this.idTestType = idTestType;
	}

	public int getIdPreviousExam() {
		return idPreviousExam;
	}

	public void setIdPreviousExam(int idPreviousExam) {
		this.idPreviousExam = idPreviousExam;
	}
}
