package com.sudosaints.cmavidya.dto;

import com.sudosaints.cmavidya.model.SubjectReplanData;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by inni on 12/12/14.
 */
public class SubjectReplanDTO implements Serializable {
	@JsonProperty("IdPlanner")
	private int idPlanner;
	@JsonProperty("oList")
	private ArrayList<SubjectReplanData> oList;

	public int getIdPlanner() {
		return idPlanner;
	}

	public void setIdPlanner(int idPlanner) {
		this.idPlanner = idPlanner;
	}

	public ArrayList<SubjectReplanData> getoList() {
		return oList;
	}

	public void setoList(ArrayList<SubjectReplanData> oList) {
		this.oList = oList;
	}
}
