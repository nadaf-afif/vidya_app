package com.sudosaints.cmavidya.model;

public class CalendarButtonData {

	public boolean isHasPlan() {
		return hasPlan;
	}

	public void setHasPlan(boolean hasPlan) {
		this.hasPlan = hasPlan;
	}

	private String label;
	private int id;
	private String tag;
	private boolean isEnabled;
	private boolean isCurrentMonth;
	private boolean hasPlan;
    private long mills;

	public CalendarButtonData(String label, int id, String tag, boolean isEnabled, boolean isCurrentMonth, boolean hasPlan) {
		this.label = label;
		this.id = id;
		this.tag = tag;
		this.isEnabled = isEnabled;
		this.isCurrentMonth=isCurrentMonth;
		this.hasPlan=hasPlan;
	}

    public long getMills() {
        return mills;
    }

    public void setMills(long mills) {
        this.mills = mills;
    }

    public boolean isCurrentMonth() {
		return isCurrentMonth;
	}

	public void setCurrentMonth(boolean isCurrentMonth) {
		this.isCurrentMonth = isCurrentMonth;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
