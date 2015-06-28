package com.sudosaints.cmavidya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.sudosaints.cmavidya.adapter.CalendarGridAdapter;
import com.sudosaints.cmavidya.adapter.CalendarListAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.CalendarButtonData;
import com.sudosaints.cmavidya.model.CalendarMonthData;
import com.sudosaints.cmavidya.model.PlanEvents;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.DateHelper;
import com.sudosaints.cmavidya.util.UIHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by inni on 25/7/14.
 */
public class CalendarViewActivity extends Activity {

    CMAVidyaApp cmaVidyaApp;
    DBUtils dbUtils;

    UIHelper uiHelper;
    @Bind(R.id.plannerClanderListView)
    ListView calendarYearListView;


    Calendar calendar = null;
    Date startDate, endDate;

    int year = 0;
    int month = 0;
    int day = 0;
    CalendarListAdapter calendarListAdapter;

    View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_calender);
        cmaVidyaApp = new CMAVidyaApp();
        ButterKnife.bind(this);
        dbUtils = new DBUtils(this);


        uiHelper = new UIHelper(this);

        uiHelper.setActionBar(Constants.ActivityABarAction.PLANNER_CALENDER, actionBarLeftOnClickListener, null);


        try {
            QueryBuilder<PlanEvents, Long> eventsQueryBuilder = dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().queryBuilder();
            eventsQueryBuilder.orderBy("topicDate", true);
            eventsQueryBuilder.limit(1l);
            List<PlanEvents> events = dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().query(eventsQueryBuilder.prepare());
            startDate = new Date(events.get(0).getTopicDate());
            eventsQueryBuilder = null;
            events.clear();
            QueryBuilder<PlanEvents, Long> eventsQueryBuilderEnd = dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().queryBuilder();
            eventsQueryBuilderEnd.orderBy("topicDate", false);
            eventsQueryBuilderEnd.limit(1l);
            events = dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().query(eventsQueryBuilderEnd.prepare());
            endDate = new Date(events.get(0).getTopicDate());

        } catch (Exception e) {
        }


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        if (null != startDate && null != endDate)
            showCalendar();
    }

    public void showCalendar() {
        Calendar calendarStartDateTemp = Calendar.getInstance();
        calendarStartDateTemp.setTime(startDate);
        int startMonth = calendarStartDateTemp.get(Calendar.MONTH);
		year = calendarStartDateTemp.get(Calendar.YEAR);
        Calendar calendarEndDateTemp = Calendar.getInstance();
        calendarEndDateTemp.setTime(endDate);
        int lastMonth = calendarEndDateTemp.get(Calendar.MONTH);
        List<CalendarMonthData> calendarMonthDatas = new ArrayList<CalendarMonthData>();

        int monthNo;
        if (lastMonth > startMonth) {
            for (monthNo = startMonth; monthNo <= lastMonth; monthNo++) {

                List<CalendarButtonData> buttonLabelList = new ArrayList<CalendarButtonData>();
                //Initialize days in calendar
                String[] dList = {"S", "M", "T", "W", "T", "F", "S"};
                for (String s : dList) {
                    buttonLabelList.add(new CalendarButtonData(s, 0, "", false, true, false));
                }

                //Initialize calendar to current month in view
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.MONTH, monthNo);
                calendar.set(Calendar.YEAR, year);

                //Get day for 1st date
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                //Get total days in current selected month
                int currentMonthTotalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                //Get total days in previous selected month
                if ((monthNo - 1) < 0)
                    calendar.set(Calendar.MONTH, 11);
                else
                    calendar.set(Calendar.MONTH, monthNo - 1);
                int prevMonthTotalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                int index = 0;

                //Show previous month days
                int j = prevMonthTotalDays;
                if (currentMonthTotalDays > 1) {
                    j = prevMonthTotalDays - (dayOfWeek - 2);
                    while (j <= prevMonthTotalDays) {
                        buttonLabelList.add(new CalendarButtonData(String.valueOf(j), -1, "", true, false, false));
                        j++;
                        index++;
                    }
                }

                //Show current month days
                for (int i = 0; i < currentMonthTotalDays; i++) {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.YEAR, year);
                    calendar1.set(Calendar.MONTH, monthNo);
                    calendar1.set(Calendar.DAY_OF_MONTH, i + 1);
                    calendar1.set(Calendar.HOUR_OF_DAY, 0);
                    calendar1.set(Calendar.MINUTE, 0);
                    long stMills = calendar1.getTimeInMillis();
                    calendar1.set(Calendar.HOUR_OF_DAY, 23);
                    calendar1.set(Calendar.MINUTE, 59);
                    calendar1.set(Calendar.SECOND, 59);
                    long edMills = calendar1.getTimeInMillis();
                    CalendarButtonData calendarButtonData = null;
                    try {
                        QueryBuilder<PlanEvents, Long> eventsQueryBuilder = dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().queryBuilder();
                        eventsQueryBuilder.where().between("topicDate", (stMills - 60000l), edMills);
                        PreparedQuery<PlanEvents> preparedQuery = eventsQueryBuilder.prepare();
                        if (dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().query(preparedQuery).size() > 0) {
                            calendarButtonData = new CalendarButtonData(String.valueOf(i + 1), i + 1, "", true, true, true);
                            calendarButtonData.setMills(stMills);
                        } else {
                            calendarButtonData = new CalendarButtonData(String.valueOf(i + 1), i + 1, "", true, true, false);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // CalendarButtonData calendarButtonData = new CalendarButtonData(String.valueOf(i + 1), i + 1, "", true, true, false);
                    if ((i + 1) == day && monthNo == month) {
                        calendarButtonData.setTag(Constants.SELECTED_CALENDAR_BUTTON_TAG);
                    }
                    buttonLabelList.add(calendarButtonData);
                    index++;
                }

                //Show next month days
                int k = 1;
                while ((index % 7) != 0) {
                    buttonLabelList.add(new CalendarButtonData(String.valueOf(k), -2, "", true, false, false));
                    k++;
                    index++;
                }
                CalendarMonthData calendarMonthData = new CalendarMonthData();
                calendarMonthData.setMonth(DateHelper.getMonthForInt(monthNo));
                calendarMonthData.setYear(year + "");
                calendarMonthData.setCalenderButtonDataList(buttonLabelList);
                calendarMonthDatas.add(calendarMonthData);

            }
        } else {
            for (monthNo = startMonth; monthNo < 12; monthNo++) {

                List<CalendarButtonData> buttonLabelList = new ArrayList<CalendarButtonData>();
                //Initialize days in calendar
                String[] dList = {"S", "M", "T", "W", "T", "F", "S"};
                for (String s : dList) {
                    buttonLabelList.add(new CalendarButtonData(s, 0, "", false, true, false));
                }

                //Initialize calendar to current month in view
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.MONTH, monthNo);
                calendar.set(Calendar.YEAR, year);

                //Get day for 1st date
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                //Get total days in current selected month
                int currentMonthTotalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                //Get total days in previous selected month
                if ((monthNo - 1) < 0)
                    calendar.set(Calendar.MONTH, 11);
                else
                    calendar.set(Calendar.MONTH, monthNo - 1);
                int prevMonthTotalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                int index = 0;

                //Show previous month days
                int j = prevMonthTotalDays;
                if (currentMonthTotalDays > 1) {
                    j = prevMonthTotalDays - (dayOfWeek - 2);
                    while (j <= prevMonthTotalDays) {
                        buttonLabelList.add(new CalendarButtonData(String.valueOf(j), -1, "", true, false, false));
                        j++;
                        index++;
                    }
                }

                //Show current month days
                for (int i = 0; i < currentMonthTotalDays; i++) {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.YEAR, year);
                    calendar1.set(Calendar.MONTH, monthNo);
                    calendar1.set(Calendar.DAY_OF_MONTH, i + 1);
                    calendar1.set(Calendar.HOUR_OF_DAY, 0);
                    calendar1.set(Calendar.MINUTE, 0);
                    long stMills = calendar1.getTimeInMillis();
                    calendar1.set(Calendar.HOUR_OF_DAY, 23);
                    calendar1.set(Calendar.MINUTE, 59);
                    calendar1.set(Calendar.SECOND, 59);
                    long edMills = calendar1.getTimeInMillis();
                    CalendarButtonData calendarButtonData = null;
                    try {
                        QueryBuilder<PlanEvents, Long> eventsQueryBuilder = dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().queryBuilder();
                        eventsQueryBuilder.where().between("topicDate", (stMills - 60000l), edMills);
                        PreparedQuery<PlanEvents> preparedQuery = eventsQueryBuilder.prepare();
                        if (dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().query(preparedQuery).size() > 0) {
                            calendarButtonData = new CalendarButtonData(String.valueOf(i + 1), i + 1, "", true, true, true);
                            calendarButtonData.setMills(stMills);
                        } else {
                            calendarButtonData = new CalendarButtonData(String.valueOf(i + 1), i + 1, "", true, true, false);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // CalendarButtonData calendarButtonData = new CalendarButtonData(String.valueOf(i + 1), i + 1, "", true, true, false);
                    if ((i + 1) == day && monthNo == month) {
                        calendarButtonData.setTag(Constants.SELECTED_CALENDAR_BUTTON_TAG);
                    }
                    buttonLabelList.add(calendarButtonData);
                    index++;
                }

                //Show next month days
                int k = 1;
                while ((index % 7) != 0) {
                    buttonLabelList.add(new CalendarButtonData(String.valueOf(k), -2, "", true, false, false));
                    k++;
                    index++;
                }
                CalendarMonthData calendarMonthData = new CalendarMonthData();
                calendarMonthData.setMonth(DateHelper.getMonthForInt(monthNo));
                calendarMonthData.setYear(year + "");
                calendarMonthData.setCalenderButtonDataList(buttonLabelList);
                calendarMonthDatas.add(calendarMonthData);

            }
            year++;
            for (monthNo = 0; monthNo <= lastMonth; monthNo++) {

                List<CalendarButtonData> buttonLabelList = new ArrayList<CalendarButtonData>();
                //Initialize days in calendar
                String[] dList = {"S", "M", "T", "W", "T", "F", "S"};
                for (String s : dList) {
                    buttonLabelList.add(new CalendarButtonData(s, 0, "", false, true, false));
                }

                //Initialize calendar to current month in view
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.MONTH, monthNo);
                calendar.set(Calendar.YEAR, year);

                //Get day for 1st date
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                //Get total days in current selected month
                int currentMonthTotalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                //Get total days in previous selected month
                if ((monthNo - 1) < 0)
                    calendar.set(Calendar.MONTH, 11);
                else
                    calendar.set(Calendar.MONTH, monthNo - 1);
                int prevMonthTotalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                int index = 0;

                //Show previous month days
                int j = prevMonthTotalDays;
                if (currentMonthTotalDays > 1) {
                    j = prevMonthTotalDays - (dayOfWeek - 2);
                    while (j <= prevMonthTotalDays) {
                        buttonLabelList.add(new CalendarButtonData(String.valueOf(j), -1, "", true, false, false));
                        j++;
                        index++;
                    }
                }

                //Show current month days
                for (int i = 0; i < currentMonthTotalDays; i++) {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.YEAR, year);
                    calendar1.set(Calendar.MONTH, monthNo);
                    calendar1.set(Calendar.DAY_OF_MONTH, i + 1);
                    calendar1.set(Calendar.HOUR_OF_DAY, 0);
                    calendar1.set(Calendar.MINUTE, 0);
                    long stMills = calendar1.getTimeInMillis();
                    calendar1.set(Calendar.HOUR_OF_DAY, 23);
                    calendar1.set(Calendar.MINUTE, 59);
                    calendar1.set(Calendar.SECOND, 59);
                    long edMills = calendar1.getTimeInMillis();
                    CalendarButtonData calendarButtonData = null;
                    try {
                        QueryBuilder<PlanEvents, Long> eventsQueryBuilder = dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().queryBuilder();
                        eventsQueryBuilder.where().between("topicDate", (stMills - 60000l), edMills);
                        PreparedQuery<PlanEvents> preparedQuery = eventsQueryBuilder.prepare();
                        if (dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().query(preparedQuery).size() > 0) {
                            calendarButtonData = new CalendarButtonData(String.valueOf(i + 1), i + 1, "", true, true, true);
                            calendarButtonData.setMills(stMills);
                        } else {
                            calendarButtonData = new CalendarButtonData(String.valueOf(i + 1), i + 1, "", true, true, false);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // CalendarButtonData calendarButtonData = new CalendarButtonData(String.valueOf(i + 1), i + 1, "", true, true, false);
                    if ((i + 1) == day && monthNo == month) {
                        calendarButtonData.setTag(Constants.SELECTED_CALENDAR_BUTTON_TAG);
                    }
                    buttonLabelList.add(calendarButtonData);
                    index++;
                }

                //Show next month days
                int k = 1;
                while ((index % 7) != 0) {
                    buttonLabelList.add(new CalendarButtonData(String.valueOf(k), -2, "", true, false, false));
                    k++;
                    index++;
                }
                CalendarMonthData calendarMonthData = new CalendarMonthData();
                calendarMonthData.setMonth(DateHelper.getMonthForInt(monthNo));
                calendarMonthData.setYear(year + "");
                calendarMonthData.setCalenderButtonDataList(buttonLabelList);
                calendarMonthDatas.add(calendarMonthData);

            }
            year--;


        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        calendarListAdapter = new CalendarListAdapter(this, calendarMonthDatas, displayMetrics.widthPixels, new CalendarGridAdapter.CalButtonClickListner() {
            @Override
            public void onClick(long mills) {
                if(mills!=0l){
                    Intent intent= new Intent(CalendarViewActivity.this, PlannerActivity.class);
                    intent.putExtra(Constants.INTENT_MILLS,mills);
                    startActivity(intent);
                    finish();
                }
            }
        });

        calendarYearListView.setAdapter(calendarListAdapter);
       /* calendarGridAdapter = new CalendarGridAdapter(SignInActivity.this, this, buttonLabelList, null);
        ((GridView) findViewById(R.id.calenderRowGridView)).setAdapter(calendarGridAdapter);
*/

    }

}
