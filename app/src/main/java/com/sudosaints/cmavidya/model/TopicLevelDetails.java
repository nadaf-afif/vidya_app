package com.sudosaints.cmavidya.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inni on 21/11/14.
 */
public class TopicLevelDetails {
    private List<Topic> topicList;
    private List<String> difficultLevesList;
    private List<Integer> noOfQuestionList;
    private List<Integer> diffLevelSelected, noOfQuestionSelected;

    public TopicLevelDetails(List<Topic> topicList, List<String> difficultLevesList, List<Integer> noOfQuestionList) {
        this.topicList = topicList;
        this.difficultLevesList = difficultLevesList;
        this.noOfQuestionList = noOfQuestionList;
        this.diffLevelSelected = new ArrayList<Integer>();
        this.noOfQuestionSelected = new ArrayList<Integer>();
        for (int i = 0; i < topicList.size(); i++) {
            diffLevelSelected.add(0);
            noOfQuestionSelected.add(1);
        }
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<String> getDifficultLevesList() {
        return difficultLevesList;
    }

    public void setDifficultLevesList(List<String> difficultLevesList) {
        this.difficultLevesList = difficultLevesList;
    }

    public List<Integer> getNoOfQuestionList() {
        return noOfQuestionList;
    }

    public void setNoOfQuestionList(List<Integer> noOfQuestionList) {
        this.noOfQuestionList = noOfQuestionList;
    }

    public List<Integer> getDiffLevelSelected() {
        return diffLevelSelected;
    }

    public void setDiffLevelSelected(List<Integer> diffLevelSelected) {
        this.diffLevelSelected = diffLevelSelected;
    }

    public List<Integer> getNoOfQuestionSelected() {
        return noOfQuestionSelected;
    }

    public void setNoOfQuestionSelected(List<Integer> noOfQuestionSelected) {
        this.noOfQuestionSelected = noOfQuestionSelected;
    }
}
