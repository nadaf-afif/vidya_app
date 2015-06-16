package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by inni on 23/2/15.
 */
@DatabaseTable(tableName = "ForumThreadTbl")
public class ForumThread implements Serializable {
	@DatabaseField(id = true)
	private long id;

	@DatabaseField
	private String AddedBy;

	@DatabaseField
	private String AddedByIP;

	@DatabaseField
	private String AddedDate;

	@DatabaseField
	private boolean Approved;

	@DatabaseField
	private String Body;

	@DatabaseField
	private boolean Closed;

	@DatabaseField
	private long ForumID;

	@DatabaseField
	private long ForumMasterID;

	@DatabaseField
	private String ForumTitle;

	@DatabaseField
	private boolean Important;

	@DatabaseField
	private String LastPostBy;

	@DatabaseField
	private String LastPostDate;

	@DatabaseField
	private String Name;

	@DatabaseField
	private String ParentPost;

	@DatabaseField
	private long ParentPostID;

	@DatabaseField
	private String Photo;

	@DatabaseField
	private long ReplyCount;

	@DatabaseField
	private String StrAddedDate;

	@DatabaseField
	private String StrLastPostDate;

	@DatabaseField
	private String Title;

	@DatabaseField
	private String UserName;

	@DatabaseField
	private long ViewCount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddedBy() {
		return AddedBy;
	}

	public void setAddedBy(String addedBy) {
		AddedBy = addedBy;
	}

	public String getAddedByIP() {
		return AddedByIP;
	}

	public void setAddedByIP(String addedByIP) {
		AddedByIP = addedByIP;
	}

	public String getAddedDate() {
		return AddedDate;
	}

	public void setAddedDate(String addedDate) {
		AddedDate = addedDate;
	}

	public boolean isApproved() {
		return Approved;
	}

	public void setApproved(boolean approved) {
		Approved = approved;
	}

	public String getBody() {
		return Body;
	}

	public void setBody(String body) {
		Body = body;
	}

	public boolean isClosed() {
		return Closed;
	}

	public void setClosed(boolean closed) {
		Closed = closed;
	}

	public long getForumID() {
		return ForumID;
	}

	public void setForumID(long forumID) {
		ForumID = forumID;
	}

	public long getForumMasterID() {
		return ForumMasterID;
	}

	public void setForumMasterID(long forumMasterID) {
		ForumMasterID = forumMasterID;
	}

	public String getForumTitle() {
		return ForumTitle;
	}

	public void setForumTitle(String forumTitle) {
		ForumTitle = forumTitle;
	}

	public boolean isImportant() {
		return Important;
	}

	public void setImportant(boolean important) {
		Important = important;
	}

	public String getLastPostBy() {
		return LastPostBy;
	}

	public void setLastPostBy(String lastPostBy) {
		LastPostBy = lastPostBy;
	}

	public String getLastPostDate() {
		return LastPostDate;
	}

	public void setLastPostDate(String lastPostDate) {
		LastPostDate = lastPostDate;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getParentPost() {
		return ParentPost;
	}

	public void setParentPost(String parentPost) {
		ParentPost = parentPost;
	}

	public long getParentPostID() {
		return ParentPostID;
	}

	public void setParentPostID(long parentPostID) {
		ParentPostID = parentPostID;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public long getReplyCount() {
		return ReplyCount;
	}

	public void setReplyCount(long replyCount) {
		ReplyCount = replyCount;
	}

	public String getStrAddedDate() {
		return StrAddedDate;
	}

	public void setStrAddedDate(String strAddedDate) {
		StrAddedDate = strAddedDate;
	}

	public String getStrLastPostDate() {
		return StrLastPostDate;
	}

	public void setStrLastPostDate(String strLastPostDate) {
		StrLastPostDate = strLastPostDate;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public long getViewCount() {
		return ViewCount;
	}

	public void setViewCount(long viewCount) {
		ViewCount = viewCount;
	}
}
