package com.sudosaints.cmavidya.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 27/2/15.
 */
public class UpdateThreadDTO implements Serializable {
	@JsonProperty("ID")
	private long id;

	@JsonProperty("Title")
	private String title;

	@JsonProperty("ParentPostID")
	private long parentPostID;

	@JsonProperty("Body")
	private String body;

	@JsonProperty("Closed")
	private boolean closed;

	@JsonProperty("UserName")
	private String userName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getParentPostID() {
		return parentPostID;
	}

	public void setParentPostID(long parentPostID) {
		this.parentPostID = parentPostID;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
