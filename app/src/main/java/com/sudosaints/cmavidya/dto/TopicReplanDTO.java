package com.sudosaints.cmavidya.dto;

import com.sudosaints.cmavidya.model.TopicReplanData;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by inni on 15/12/14.
 */
public class TopicReplanDTO implements Serializable {
    @JsonProperty("IdPlanner")
    private int idPlanner;
    @JsonProperty("oList")
    private ArrayList<TopicReplanData> oList;

    public int getIdPlanner() {
        return idPlanner;
    }

    public void setIdPlanner(int idPlanner) {
        this.idPlanner = idPlanner;
    }

    public ArrayList<TopicReplanData> getoList() {
        return oList;
    }

    public void setoList(ArrayList<TopicReplanData> oList) {
        this.oList = oList;
    }
}
