package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by inni on 11/8/14.
 */
@DatabaseTable
public class CalenderPlan {


    @DatabaseField(id = true)
    private int IdTopic;
    @DatabaseField
    private String Subject;
    @DatabaseField
    private String Topicname;
    @DatabaseField
    private int IdPlannerOutPutDateWiseTime;
    @DatabaseField
    private int RevisionNo;
    @DatabaseField
    private int topicTime;
    @DatabaseField
    private String ayTime;
    @DatabaseField
    private String AvavialableDayTime;
    @DatabaseField
    private String TopicDate;
    @DatabaseField
    private String description;

    CalenderPlan(){

    }

    public int getIdTopic() {   return IdTopic; }

    public void setIdTopic(int idTopic) {
        IdTopic = idTopic;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTopicname() {
        return Topicname;
    }

    public void setTopicname(String topicname) {
        Topicname = topicname;
    }

    public int getIdPlannerOutPutDateWiseTime() {
        return IdPlannerOutPutDateWiseTime;
    }

    public void setIdPlannerOutPutDateWiseTime(int idPlannerOutPutDateWiseTime) {
        IdPlannerOutPutDateWiseTime = idPlannerOutPutDateWiseTime;
    }

    public int getRevisionNo() {
        return RevisionNo;
    }

    public void setRevisionNo(int revisionNo) {
        RevisionNo = revisionNo;
    }

    public int getTopicTime() {
        return topicTime;
    }

    public void setTopicTime(int topicTime) {
        this.topicTime = topicTime;
    }

    public String getAyTime() {
        return ayTime;
    }

    public void setAyTime(String ayTime) {
        this.ayTime = ayTime;
    }

    public String getAvavialableDayTime() {
        return AvavialableDayTime;
    }

    public void setAvavialableDayTime(String avavialableDayTime) {
        AvavialableDayTime = avavialableDayTime;
    }

    public String getTopicDate() {
        return TopicDate;
    }

    public void setTopicDate(String topicDate) {
        TopicDate = topicDate;
    }

    public String getEscription() {
        return description;
    }

    public void setEscription(String escription) {
        this.description = escription;
    }
}
