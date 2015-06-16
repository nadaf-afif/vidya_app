package com.sudosaints.cmavidya.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by inni on 19/11/14.
 */
public class SubjectLevelDetails implements Serializable {
    private List<Subject> subjectList;
    private List<String> difficultLevesList;
    private List<Integer> noOfQuestionList;
    private List<Integer> diffLevelSelected, noOfQuestionSelected;

    public SubjectLevelDetails(List<Subject> subjectList, List<String> difficultLevesList, List<Integer> noOfQuestionList) {
        this.subjectList = subjectList;
        this.difficultLevesList = difficultLevesList;
        this.noOfQuestionList = noOfQuestionList;
        this.diffLevelSelected = new ArrayList<Integer>();
        this.noOfQuestionSelected = new ArrayList<Integer>();
        for (int i = 0; i < subjectList.size(); i++) {
            diffLevelSelected.add(0);
            noOfQuestionSelected.add(1);
        }
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
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
