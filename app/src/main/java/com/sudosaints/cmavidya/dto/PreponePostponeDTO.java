package com.sudosaints.cmavidya.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 10/9/14.
 */
public class PreponePostponeDTO implements Serializable {
	@JsonProperty("UserEmailId")
	private String userEmailId;
	@JsonProperty("type")
	private String type;

	@JsonProperty("idPlannerOutputDateWiseTime")
	private int idPlannerOutputDateWiseTime;
	@JsonProperty("Idsubjectnamesaketopicmapping")
	private int idsubjectnamesaketopicmapping;
	@JsonProperty("hours")
	private int hours;
	@JsonProperty("revindex")
	private int revindex;

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIdPlannerOutputDateWiseTime() {
		return idPlannerOutputDateWiseTime;
	}

	public void setIdPlannerOutputDateWiseTime(int idPlannerOutputDateWiseTime) {
		this.idPlannerOutputDateWiseTime = idPlannerOutputDateWiseTime;
	}

	public int getIdsubjectnamesaketopicmapping() {
		return idsubjectnamesaketopicmapping;
	}

	public void setIdsubjectnamesaketopicmapping(int idsubjectnamesaketopicmapping) {
		this.idsubjectnamesaketopicmapping = idsubjectnamesaketopicmapping;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getRevindex() {
		return revindex;
	}

	public void setRevindex(int revindex) {
		this.revindex = revindex;
	}
}
