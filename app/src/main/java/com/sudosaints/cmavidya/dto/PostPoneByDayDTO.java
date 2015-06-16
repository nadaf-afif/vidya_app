package com.sudosaints.cmavidya.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 18/12/14.
 */
public class PostPoneByDayDTO implements Serializable {
    @JsonProperty("UserEmailId")
    private String userEmailId;
    @JsonProperty("idPlannerOutputDateWiseTime")
    private long idPlannerOutputDateWiseTime;

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public long getIdPlannerOutputDateWiseTime() {
        return idPlannerOutputDateWiseTime;
    }

    public void setIdPlannerOutputDateWiseTime(long idPlannerOutputDateWiseTime) {
        this.idPlannerOutputDateWiseTime = idPlannerOutputDateWiseTime;
    }
}
