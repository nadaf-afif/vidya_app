package com.sudosaints.cmavidya.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 3/1/15.
 */
public class UpdateAnswer implements Serializable {

	@JsonProperty("UserName")
	private String userName;

	@JsonProperty("IdTestLogMaster")
	private long idTestLogMaster;

	@JsonProperty("IdTestLog")
	private long idTestLog;

	@JsonProperty("OptionSelected")
	private int optionSelected;

	@JsonProperty("TakenTime")
	private String takenTime;

	@JsonProperty("IsTestEnd")
	private boolean isTestEnd;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getIdTestLogMaster() {
		return idTestLogMaster;
	}

	public void setIdTestLogMaster(long idTestLogMaster) {
		this.idTestLogMaster = idTestLogMaster;
	}

	public long getIdTestLog() {
		return idTestLog;
	}

	public void setIdTestLog(long idTestLog) {
		this.idTestLog = idTestLog;
	}

	public int getOptionSelected() {
		return optionSelected;
	}

	public void setOptionSelected(int optionSelected) {
		this.optionSelected = optionSelected;
	}

	public String getTakenTime() {
		return takenTime;
	}

	public void setTakenTime(String takenTime) {
		this.takenTime = takenTime;
	}

	public boolean isTestEnd() {
		return isTestEnd;
	}

	public void setTestEnd(boolean isTestEnd) {
		this.isTestEnd = isTestEnd;
	}
}
