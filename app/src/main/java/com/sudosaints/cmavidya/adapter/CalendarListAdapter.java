package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.CalendarButtonData;
import com.sudosaints.cmavidya.model.CalendarMonthData;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.views.CustomGridView;

import java.util.List;

/**
 * Created by inni on 26/7/14.
 */
public class CalendarListAdapter extends BaseAdapter {



    private List<CalendarMonthData> calenderMonthDatas;
    private Activity activity;
    private int screenWidth;
    private CalendarGridAdapter.CalButtonClickListner listener;

    public CalendarListAdapter(Activity activity, List<CalendarMonthData> calenderMonthDatas,int screenWidth, CalendarGridAdapter.CalButtonClickListner listener) {
        this.activity = activity;
        this.calenderMonthDatas = calenderMonthDatas;
        this.screenWidth=screenWidth;
        this.listener = listener;


    }

    @Override
    public int getCount() {
        return calenderMonthDatas.size();
    }

    @Override
    public CalendarMonthData getItem(int i) {
        return calenderMonthDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final CalendarMonthData calenderMonthData = calenderMonthDatas.get(i);

        if (view == null) {
            LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.calender_row_layout, viewGroup, false);
        }

        TextView monthTextView = (TextView) view.findViewById(R.id.calenderRowMonthTextView);
//        GridLayout calenderGridView = (GridLayout) view.findViewById(R.id.calenderRowGridView);
        CustomGridView calenderGridView = (CustomGridView) view.findViewById(R.id.calenderRowGridView);
        monthTextView.setText(calenderMonthData.getMonth() + " " + calenderMonthData.getYear());




        CalendarGridAdapter calendarGridAdapter = new CalendarGridAdapter(activity, calenderMonthData.getCalenderButtonDataList(),listener,screenWidth);
        calenderGridView.setAdapter(calendarGridAdapter);
       /* calenderGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (null != listener) {
                    CalendarButtonData data = (CalendarButtonData) adapterView.getItemAtPosition(position);
                    listener.onClick(data);
                }
            }
        });*/



        /*for (CalendarButtonData buttonData : calenderMonthData.getCalenderButtonDataList()) {
            Button calendarButton = null;
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            calendarButton = (Button) inflater.inflate(R.layout.calendar_button_layout, null);
            // get Screen Width
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int screenWidth = displayMetrics.widthPixels;

            calendarButton.setLayoutParams(new AbsListView.LayoutParams(screenWidth / 7, screenWidth / 7));

            // set button color
            if (!(buttonData.isCurrentMonth())) {
                calendarButton.setTextColor(activity.getResources().getColor(R.color.lite_gray));
            }

            // Set label and id
            calendarButton.setId(buttonData.getId());
            calendarButton.setText(buttonData.getLabel());

            // Set color of button label
            if (!buttonData.isEnabled()) {
                calendarButton.setTextColor(activity.getResources().getColor(R.color.text_black));
                calendarButton.setTextAppearance(activity, R.style.boldText);
            }
            // Show ring around date has planner
            if (buttonData.isHasPlan()) {
                calendarButton.setBackgroundResource(R.drawable.calendar_button_has_plan);
                calendarButton.setTextColor(activity.getResources().getColor(R.color.app_blue));
            }
            if (buttonData.isHasPlan()) {
                calendarButton.setBackgroundResource(R.drawable.calendar_button_has_plan);
                calendarButton.setTextColor(activity.getResources().getColor(R.color.app_blue));
            }
            if (buttonData.getTag().equalsIgnoreCase(Constants.SELECTED_CALENDAR_BUTTON_TAG)) {
                calendarButton.setBackgroundResource(R.drawable.calendar_button_current_day);
                calendarButton.setTextColor(activity.getResources().getColor(R.color.text_white));
            }


            // Set click event listener
            calendarButton.setOnClickListener(null);
            calenderGridView.addView(calendarButton);

        }*/

        return view;
    }


}

