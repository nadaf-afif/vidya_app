package com.sudosaints.cmavidya.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by inni on 9/2/15.
 */
public class MyplanUnderstandingDetails implements Serializable {
	private List<PlanEvents> planEvents;
	private List<Integer> understandingsList;
	private List<Boolean> isSelectedList;
	private List<Integer> understandingSelectedList;

	public MyplanUnderstandingDetails(List<PlanEvents> planEvents, List<Integer> understandingsList) {
		this.planEvents = planEvents;
		this.understandingsList = understandingsList;
		this.isSelectedList = new ArrayList<Boolean>();
		this.understandingSelectedList = new ArrayList<Integer>();
		for (int i = 0; i < planEvents.size(); i++) {
			isSelectedList.add(new Boolean(false));
			understandingSelectedList.add(10);
		}
	}

	public List<PlanEvents> getPlanEvents() {
		return planEvents;
	}

	public void setPlanEvents(List<PlanEvents> planEvents) {
		this.planEvents = planEvents;
	}

	public List<Integer> getUnderstandingsList() {
		return understandingsList;
	}

	public void setUnderstandingsList(List<Integer> understandingsList) {
		this.understandingsList = understandingsList;
	}

	public List<Boolean> getIsSelectedList() {
		return isSelectedList;
	}

	public void setIsSelectedList(List<Boolean> isSelectedList) {
		this.isSelectedList = isSelectedList;
	}

	public List<Integer> getUnderstandingSelectedList() {
		return understandingSelectedList;
	}

	public void setUnderstandingSelectedList(List<Integer> understandingSelectedList) {
		this.understandingSelectedList = understandingSelectedList;
	}
}
