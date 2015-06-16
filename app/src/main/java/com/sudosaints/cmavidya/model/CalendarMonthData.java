package com.sudosaints.cmavidya.model;

import java.util.List;

/**
 * Created by inni on 26/7/14.
 */
public class CalendarMonthData {
    private String month, year;
    private List<CalendarButtonData> calenderButtonDataList;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<CalendarButtonData> getCalenderButtonDataList() {
        return calenderButtonDataList;
    }

    public void setCalenderButtonDataList(List<CalendarButtonData> calenderButtonDataList) {
        this.calenderButtonDataList = calenderButtonDataList;
    }
}
