package com.sudosaints.cmavidya.model;

/**
 * Created by Rishi M on 7/14/2015.
 */
public class Notification {
    String description,endDate,startDate,note;
    int notificationId,courseBatchId;
    boolean isActive;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getCourseBatchId() {
        return courseBatchId;
    }

    public void setCourseBatchId(int courseBatchId) {
        this.courseBatchId = courseBatchId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
