package com.sudosaints.cmavidya.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 26/12/14.
 */
public class PrepareTestDTO implements Serializable {

	@JsonProperty("UserName")
	private String userName;
	@JsonProperty("Ids")
	private String ids;
	@JsonProperty("NumOfQuestion")
	private String numOfQuestion;
	@JsonProperty("DifficultyLevel")
	private String difficultyLevel;
	@JsonProperty("IdTestType")
	private int idTestType;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getNumOfQuestion() {
		return numOfQuestion;
	}

	public void setNumOfQuestion(String numOfQuestion) {
		this.numOfQuestion = numOfQuestion;
	}

	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public int getIdTestType() {
		return idTestType;
	}

	public void setIdTestType(int idTestType) {
		this.idTestType = idTestType;
	}
}
