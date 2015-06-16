package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 1/1/15.
 */
@DatabaseTable(tableName = "question")
public class Question implements Serializable {

	@DatabaseField(generatedId = true)
	private long id;

	@JsonProperty("DifficultyLevel")
	@DatabaseField
	private int difficultyLevel;

	@DatabaseField
	@JsonProperty("Duration")
	private int duration;

	@DatabaseField
	@JsonProperty("Explanation")
	private String explanation;

	@DatabaseField
	@JsonProperty
	private int idIndex;

	@DatabaseField
	@JsonProperty("IdQuestionMaster")
	private int idQuestionMaster;

	@DatabaseField
	@JsonProperty("IdSubjectMaster")
	private int idSubjectMaster;

	@DatabaseField
	@JsonProperty("IdTestLog")
	private int idTestLog;

	@DatabaseField
	@JsonProperty("IdTestLogMaster")
	private int idTestLogMaster;

	@DatabaseField
	@JsonProperty("IdTopicMaster")
	private int idTopicMaster;

	@DatabaseField
	@JsonProperty("Marks")
	private int marks;

	@DatabaseField
	@JsonProperty("Option1")
	private String option1;

	@DatabaseField
	@JsonProperty("Option2")
	private String option2;

	@DatabaseField
	@JsonProperty("Option3")
	private String option3;

	@DatabaseField
	@JsonProperty("Option4")
	private String option4;

	@DatabaseField
	@JsonProperty("Option5")
	private String option5;

	@DatabaseField
	@JsonProperty("OptionSelected")
	private int optionSelected;

	@DatabaseField
	@JsonProperty("OutOf")
	private int outOf;

	@DatabaseField
	@JsonProperty("Questions")
	private String questions;

	@DatabaseField
	@JsonProperty("RightAnswer")
	private int rightAnswer;

	@DatabaseField
	@JsonProperty("SubjectName")
	private String subjectName;

	@DatabaseField
	@JsonProperty("TopicName")
	private String topicName;

	@DatabaseField(foreign = true)
	private Test test;


	public long getId() {
		return id;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public int getIdIndex() {
		return idIndex;
	}

	public void setIdIndex(int idIndex) {
		this.idIndex = idIndex;
	}

	public int getIdQuestionMaster() {
		return idQuestionMaster;
	}

	public void setIdQuestionMaster(int idQuestionMaster) {
		this.idQuestionMaster = idQuestionMaster;
	}

	public int getIdSubjectMaster() {
		return idSubjectMaster;
	}

	public void setIdSubjectMaster(int idSubjectMaster) {
		this.idSubjectMaster = idSubjectMaster;
	}

	public int getIdTestLog() {
		return idTestLog;
	}

	public void setIdTestLog(int idTestLog) {
		this.idTestLog = idTestLog;
	}

	public int getIdTestLogMaster() {
		return idTestLogMaster;
	}

	public void setIdTestLogMaster(int idTestLogMaster) {
		this.idTestLogMaster = idTestLogMaster;
	}

	public int getIdTopicMaster() {
		return idTopicMaster;
	}

	public void setIdTopicMaster(int idTopicMaster) {
		this.idTopicMaster = idTopicMaster;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getOption5() {
		return option5;
	}

	public void setOption5(String option5) {
		this.option5 = option5;
	}

	public int getOptionSelected() {
		return optionSelected;
	}

	public void setOptionSelected(int optionSelected) {
		this.optionSelected = optionSelected;
	}

	public int getOutOf() {
		return outOf;
	}

	public void setOutOf(int outOf) {
		this.outOf = outOf;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public int getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(int rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
}
