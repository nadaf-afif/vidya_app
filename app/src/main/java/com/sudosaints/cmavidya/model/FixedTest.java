package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by inni on 31/1/15.
 */
@DatabaseTable(tableName = "fixedTestTbl")
public class FixedTest implements Serializable {
	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField
	private String CreatedDate;
	@DatabaseField
	private int IdFixedTestMaster;
	@DatabaseField
	private int IdTestType;
	@DatabaseField
	private String TestId;

	public long getId() {
		return id;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public int getIdFixedTestMaster() {
		return IdFixedTestMaster;
	}

	public void setIdFixedTestMaster(int idFixedTestMaster) {
		IdFixedTestMaster = idFixedTestMaster;
	}

	public int getIdTestType() {
		return IdTestType;
	}

	public void setIdTestType(int idTestType) {
		IdTestType = idTestType;
	}

	public String getTestId() {
		return TestId;
	}

	public void setTestId(String testId) {
		TestId = testId;
	}
}
