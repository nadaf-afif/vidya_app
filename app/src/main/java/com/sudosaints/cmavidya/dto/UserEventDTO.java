package com.sudosaints.cmavidya.dto;


import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 7/2/15.
 */
public class UserEventDTO implements Serializable {

	@JsonProperty("UserName")
	private String userName;

	@JsonProperty("StrPlanDate")
	private String strPlanDate;

	@JsonProperty("EventName")
	private String eventName;

	@JsonProperty("IsActive")
	private boolean isActive;

	@JsonProperty("IdUserEvents")
	private int idUserEvents;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStrPlanDate() {
		return strPlanDate;
	}

	public void setStrPlanDate(String strPlanDate) {
		this.strPlanDate = strPlanDate;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getIdUserEvents() {
		return idUserEvents;
	}

	public void setIdUserEvents(int idUserEvents) {
		this.idUserEvents = idUserEvents;
	}
}