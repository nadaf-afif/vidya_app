package com.sudosaints.cmavidya.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by inni on 1/1/15.
 */
@DatabaseTable
public class Test implements Serializable {


	@DatabaseField(id = true)
	private long idTestLogMaster;

	@DatabaseField
	private boolean isTestEnd;

	@DatabaseField
	private double negativeMarks;

	@DatabaseField
	private int noOfQuestion;

	@DatabaseField
	private String requireTime;

	@DatabaseField
	private String strDate;

	@DatabaseField
	private String takenTime;

	@DatabaseField
	private String testId;

	@DatabaseField
	private int idTestType;

	@DatabaseField
	private String topics;

	@ForeignCollectionField(eager = false)
	ForeignCollection<Question> questions;

	public long getIdTestLogMaster() {
		return idTestLogMaster;
	}

	public void setIdTestLogMaster(long idTestLogMaster) {
		this.idTestLogMaster = idTestLogMaster;
	}

	public boolean isTestEnd() {
		return isTestEnd;
	}

	public void setTestEnd(boolean isTestEnd) {
		this.isTestEnd = isTestEnd;
	}

	public double getNegativeMarks() {
		return negativeMarks;
	}

	public void setNegativeMarks(double negativeMarks) {
		this.negativeMarks = negativeMarks;
	}

	public int getNoOfQuestion() {
		return noOfQuestion;
	}

	public void setNoOfQuestion(int noOfQuestion) {
		this.noOfQuestion = noOfQuestion;
	}

	public String getRequireTime() {
		return requireTime;
	}

	public void setRequireTime(String requireTime) {
		this.requireTime = requireTime;
	}

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	public String getTakenTime() {
		return takenTime;
	}

	public void setTakenTime(String takenTime) {
		this.takenTime = takenTime;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public ForeignCollection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ForeignCollection<Question> questions) {
		this.questions = questions;
	}

	public int getIdTestType() {
		return idTestType;
	}

	public void setIdTestType(int idTestType) {
		this.idTestType = idTestType;
	}

	@Override
	public String toString() {
		return this.getTestId() + " " + this.getStrDate();
	}
}
