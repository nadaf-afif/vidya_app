package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by inni on 16/1/15.
 */
@DatabaseTable(tableName = "ForumTopicTbl")
public class ForumTopic implements Serializable {
	@DatabaseField(generatedId = true)
	private int _id;
	@DatabaseField
	private String addedBy;

	@DatabaseField
	private String addedDate;

	@DatabaseField
	private String description;

	@DatabaseField
	private int forumMasterID;

	@DatabaseField
	private int id;


	@DatabaseField
	private String imageUrl;

	@DatabaseField
	private int importance;

	@DatabaseField
	private boolean moderated;

	@DatabaseField
	private String title;


	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getForumMasterID() {
		return forumMasterID;
	}

	public void setForumMasterID(int forumMasterID) {
		this.forumMasterID = forumMasterID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public boolean isModerated() {
		return moderated;
	}

	public void setModerated(boolean moderated) {
		this.moderated = moderated;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
