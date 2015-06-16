package com.sudosaints.cmavidya.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 4/2/15.
 */
public class FixedTestDTO implements Serializable {
	@JsonProperty("UserName")
	private String userName;
	@JsonProperty("IdFixedTestMaster")
	private int IdFixedTestMaster;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIdFixedTestMaster() {
		return IdFixedTestMaster;
	}

	public void setIdFixedTestMaster(int idFixedTestMaster) {
		IdFixedTestMaster = idFixedTestMaster;
	}
}
