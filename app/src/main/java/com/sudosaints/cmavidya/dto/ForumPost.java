package com.sudosaints.cmavidya.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 16/1/15.
 */
public class ForumPost implements Serializable {
	@JsonProperty("ForumID")
	private int forumID;

	@JsonProperty("ParentPostID")
	private int parentPostID;

	@JsonProperty("Title")
	private String title;

	@JsonProperty("Body")
	private String body;

	@JsonProperty("Closed")
	private boolean closed;

	@JsonProperty("UserName")
	private String userName;

	public int getForumID() {
		return forumID;
	}

	public void setForumID(int forumID) {
		this.forumID = forumID;
	}

	public int getParentPostID() {
		return parentPostID;
	}

	public void setParentPostID(int parentPostID) {
		this.parentPostID = parentPostID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
