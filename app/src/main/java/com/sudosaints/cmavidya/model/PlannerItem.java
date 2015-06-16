package com.sudosaints.cmavidya.model;

import java.io.Serializable;

/**
 * Created by inni on 25/7/14.
 */
public class PlannerItem implements Serializable {
    private String title, description;
    private String startTime, endTime;

    public PlannerItem(){
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
