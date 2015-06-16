package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by salt on 7/11/14.
 */
@DatabaseTable(tableName = "KeyNotes")
public class KeyNotes implements Serializable {

	@DatabaseField
    private int AddedBy;
    @DatabaseField
    private String Description;
    @DatabaseField
    private int IdKeyNotes;
    @DatabaseField
    private int IdTopicMaster;
    @DatabaseField
    private boolean IsActive;
    @DatabaseField
    private String ThumbnailUrl;
    @DatabaseField
    private String UploadedDate;
    @DatabaseField
    private String UploadedFileUrl;


    public int getAddedBy() {
        return AddedBy;
    }

    public void setAddedBy(int addedBy) {
        AddedBy = addedBy;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getIdKeyNotes() {
        return IdKeyNotes;
    }

    public void setIdKeyNotes(int idKeyNotes) {
        IdKeyNotes = idKeyNotes;
    }

    public int getIdTopicMaster() {
        return IdTopicMaster;
    }

    public void setIdTopicMaster(int idTopicMaster) {
        IdTopicMaster = idTopicMaster;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean isActive) {
        IsActive = isActive;
    }

    public String getThumbnailUrl() {
        return ThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        ThumbnailUrl = thumbnailUrl;
    }

    public String getUploadedDate() {
        return UploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        UploadedDate = uploadedDate;
    }

    public String getUploadedFileUrl() {
        return UploadedFileUrl;
    }

    public void setUploadedFileUrl(String uploadedFileUrl) {
        UploadedFileUrl = uploadedFileUrl;
    }
}
