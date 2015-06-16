package com.sudosaints.cmavidya.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by inni on 31/1/15.
 */
@DatabaseTable(tableName = "PeriviousTestTbl")
public class PreviousTest implements Serializable {

	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField
	private String ExamName;
	@DatabaseField
	private int IdExam;
	@DatabaseField
	private int IdTestType;

	@ForeignCollectionField(eager = false)
	private ForeignCollection<PreviousExam> previousExams;

	public long getId() {
		return id;
	}

	public String getExamName() {
		return ExamName;
	}

	public void setExamName(String examName) {
		ExamName = examName;
	}

	public int getIdExam() {
		return IdExam;
	}

	public void setIdExam(int idExam) {
		IdExam = idExam;
	}

	public int getIdTestType() {
		return IdTestType;
	}

	public void setIdTestType(int idTestType) {
		IdTestType = idTestType;
	}

	public ForeignCollection<PreviousExam> getPreviousExams() {
		return previousExams;
	}

	public void setPreviousExams(ForeignCollection<PreviousExam> previousExams) {
		this.previousExams = previousExams;
	}
}
