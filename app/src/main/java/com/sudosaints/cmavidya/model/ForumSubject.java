package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by inni on 25/2/15.
 */
@DatabaseTable(tableName = "ForumSubjectTbl")
public class ForumSubject implements Serializable {

	@DatabaseField(id = true)
	private long id;
	@DatabaseField
	private long ForumMasterID;
	@DatabaseField
	private int Importance;
	@DatabaseField
	private boolean Moderated;
	@DatabaseField
	private String AddedBy;
	@DatabaseField
	private String AddedDate;
	@DatabaseField
	private String Description;
	@DatabaseField
	private String ImageUrl;
	@DatabaseField
	private String StrAddedDate;
	@DatabaseField
	private String Title;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getForumMasterID() {
		return ForumMasterID;
	}

	public void setForumMasterID(long forumMasterID) {
		ForumMasterID = forumMasterID;
	}

	public int getImportance() {
		return Importance;
	}

	public void setImportance(int importance) {
		Importance = importance;
	}

	public boolean isModerated() {
		return Moderated;
	}

	public void setModerated(boolean moderated) {
		Moderated = moderated;
	}

	public String getAddedBy() {
		return AddedBy;
	}

	public void setAddedBy(String addedBy) {
		AddedBy = addedBy;
	}

	public String getAddedDate() {
		return AddedDate;
	}

	public void setAddedDate(String addedDate) {
		AddedDate = addedDate;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	public String getStrAddedDate() {
		return StrAddedDate;
	}

	public void setStrAddedDate(String strAddedDate) {
		StrAddedDate = strAddedDate;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}
}
