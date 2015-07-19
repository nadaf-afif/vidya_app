package com.sudosaints.cmavidya.model;

/**
 * Created by Rishi M on 7/19/2015.
 */
public class Downloads {
    String strActivationDate,strEnddate,UploadFileUrl,TopicName,SubjectName,FileName,Description;
    int idDownload,idTopicMaster;
    boolean IsActive;

    public String getStrActivationDate() {
        return strActivationDate;
    }

    public void setStrActivationDate(String strActivationDate) {
        this.strActivationDate = strActivationDate;
    }

    public String getStrEnddate() {
        return strEnddate;
    }

    public void setStrEnddate(String strEnddate) {
        this.strEnddate = strEnddate;
    }

    public String getUploadFileUrl() {
        return UploadFileUrl;
    }

    public void setUploadFileUrl(String uploadFileUrl) {
        UploadFileUrl = uploadFileUrl;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getIdDownload() {
        return idDownload;
    }

    public void setIdDownload(int idDownload) {
        this.idDownload = idDownload;
    }

    public int getIdTopicMaster() {
        return idTopicMaster;
    }

    public void setIdTopicMaster(int idTopicMaster) {
        this.idTopicMaster = idTopicMaster;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean isActive) {
        IsActive = isActive;
    }
}
