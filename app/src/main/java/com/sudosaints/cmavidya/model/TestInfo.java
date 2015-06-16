package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by inni on 26/12/14.
 */
@DatabaseTable
public class TestInfo implements Serializable {

	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField
	private long idTestLogMaster;
	@DatabaseField
	private String comments;
	@DatabaseField
	private String bookMarkName;
	@DatabaseField
	private long timeOfCreation;
	@DatabaseField
	private boolean endTest;


	public long getId() {
		return id;
	}

	public long getIdTestLogMaster() {
		return idTestLogMaster;
	}

	public void setIdTestLogMaster(long idTestLogMaster) {
		this.idTestLogMaster = idTestLogMaster;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {

		this.comments = comments;
	}

	public String getBookMarkName() {
		return bookMarkName;
	}

	public void setBookMarkName(String bookMarkName) {
		this.bookMarkName = bookMarkName;
	}

	public long getTimeOfCreation() {
		return timeOfCreation;
	}

	public void setTimeOfCreation(long timeOfCreation) {
		this.timeOfCreation = timeOfCreation;
	}

	public boolean isEndTest() {
		return endTest;
	}

	public void setEndTest(boolean endTest) {
		this.endTest = endTest;
	}
}
