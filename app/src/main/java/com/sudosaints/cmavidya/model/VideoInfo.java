package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by inni on 16/3/15.
 */
@DatabaseTable(tableName = "VideoInfoTbl")
public class VideoInfo implements Serializable {

	@DatabaseField(id = true)
	private int idVideo;
	@DatabaseField
	private int commandType;
	@DatabaseField
	private int count;
	@DatabaseField
	private String description;
	@DatabaseField
	private String duration;
	@DatabaseField
	private String filePath;
	@DatabaseField
	private int idCustomLevelMaster;
	@DatabaseField
	private int idSubjectMaster;
	@DatabaseField
	private int idTopicMaster;
	@DatabaseField
	private int idVideoLog;
	@DatabaseField
	private boolean isActive;
	@DatabaseField
	private int priority;
	@DatabaseField
	private String size;
	@DatabaseField
	private String subjectName;
	@DatabaseField
	private String topicName;
	@DatabaseField
	private String totalTime;


	public int getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(int idVideo) {
		this.idVideo = idVideo;
	}

	public int getCommandType() {
		return commandType;
	}

	public void setCommandType(int commandType) {
		this.commandType = commandType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getIdCustomLevelMaster() {
		return idCustomLevelMaster;
	}

	public void setIdCustomLevelMaster(int idCustomLevelMaster) {
		this.idCustomLevelMaster = idCustomLevelMaster;
	}

	public int getIdSubjectMaster() {
		return idSubjectMaster;
	}

	public void setIdSubjectMaster(int idSubjectMaster) {
		this.idSubjectMaster = idSubjectMaster;
	}

	public int getIdTopicMaster() {
		return idTopicMaster;
	}

	public void setIdTopicMaster(int idTopicMaster) {
		this.idTopicMaster = idTopicMaster;
	}

	public int getIdVideoLog() {
		return idVideoLog;
	}

	public void setIdVideoLog(int idVideoLog) {
		this.idVideoLog = idVideoLog;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
}
