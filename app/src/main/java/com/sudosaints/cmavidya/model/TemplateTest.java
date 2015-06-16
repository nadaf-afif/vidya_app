package com.sudosaints.cmavidya.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by inni on 31/1/15.
 */
@DatabaseTable(tableName = "templateTestTbl")
public class TemplateTest implements Serializable {
	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField
	private String TemplateName;
	@DatabaseField
	private int IdTemplateMaster;

	public long getId() {
		return id;
	}

	public String getTemplateName() {
		return TemplateName;
	}

	public void setTemplateName(String templateName) {
		TemplateName = templateName;
	}

	public int getIdTemplateMaster() {
		return IdTemplateMaster;
	}

	public void setIdTemplateMaster(int idTemplateMaster) {
		IdTemplateMaster = idTemplateMaster;
	}
}
