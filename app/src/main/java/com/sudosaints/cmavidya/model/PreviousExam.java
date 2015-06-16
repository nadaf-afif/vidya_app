package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by inni on 31/1/15.
 */
@DatabaseTable(tableName = "previousExamTbl")
public class PreviousExam implements Serializable {
	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField
	private String ExamYear;
	@DatabaseField
	private int IdExam;
	@DatabaseField
	private int IdPreviousExam;

	@DatabaseField(foreign = true)
	private PreviousTest previousTest;

	public long getId() {
		return id;
	}

	public String getExamYear() {
		return ExamYear;
	}

	public void setExamYear(String examYear) {
		ExamYear = examYear;
	}

	public int getIdExam() {
		return IdExam;
	}

	public void setIdExam(int idExam) {
		IdExam = idExam;
	}

	public int getIdPreviousExam() {
		return IdPreviousExam;
	}

	public void setIdPreviousExam(int idPreviousExam) {
		IdPreviousExam = idPreviousExam;
	}

	public PreviousTest getPreviousTest() {
		return previousTest;
	}

	public void setPreviousTest(PreviousTest previousTest) {
		this.previousTest = previousTest;
	}
}
