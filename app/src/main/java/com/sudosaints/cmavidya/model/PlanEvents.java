package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by inni on 2/9/14.
 */
@DatabaseTable(tableName = "PlanEventsTbl")
public class PlanEvents implements Serializable{

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private int avavialableDayTime;
    @DatabaseField
    private int dayTime;
    @DatabaseField
    private String description;
    @DatabaseField
    private int idPlannerOutPutDateWiseTime;
    @DatabaseField
    private int idTopic;
    @DatabaseField
    private boolean isActive;
    @DatabaseField
    private int isRevision;
    @DatabaseField
    private int revisionNumber;
    @DatabaseField
    private String subject;
    @DatabaseField
    private long topicDate;
    @DatabaseField
    private String topicTime;
    @DatabaseField
    private String topicName;
    @DatabaseField
    private float topicTimeFloat;



    public PlanEvents(){
        super();
    }

    public long getId() {
        return id;
    }

    public int getAvavialableDayTime() {
        return avavialableDayTime;
    }

    public void setAvavialableDayTime(int avavialableDayTime) {
        this.avavialableDayTime = avavialableDayTime;
    }

    public int getDayTime() {
        return dayTime;
    }

    public void setDayTime(int dayTime) {
        this.dayTime = dayTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdPlannerOutPutDateWiseTime() {
        return idPlannerOutPutDateWiseTime;
    }

    public void setIdPlannerOutPutDateWiseTime(int idPlannerOutPutDateWiseTime) {
        this.idPlannerOutPutDateWiseTime = idPlannerOutPutDateWiseTime;
    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getIsRevision() {
        return isRevision;
    }

    public void setIsRevision(int isRevision) {
        this.isRevision = isRevision;
    }

    public int getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(int revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public long getTopicDate() {
        return topicDate;
    }

    public void setTopicDate(long topicDate) {
        this.topicDate = topicDate;
    }

    public String getTopicTime() {
        return topicTime;
    }

    public void setTopicTime(String topicTime) {
        this.topicTime = topicTime;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public float getTopicTimeFloat() {
        return topicTimeFloat;
    }

    public void setTopicTimeFloat(float topicTimeFloat) {
        this.topicTimeFloat = topicTimeFloat;
    }
}
