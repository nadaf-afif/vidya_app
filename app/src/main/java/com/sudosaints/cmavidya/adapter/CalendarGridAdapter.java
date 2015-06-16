package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.CalendarButtonData;
import com.sudosaints.cmavidya.util.Constants;

import java.util.List;

//Adapter for calendar grid
public class CalendarGridAdapter extends BaseAdapter {

    public interface  CalButtonClickListner {
        public void onClick(long mills);
    }

	Activity activity = null;
	List<CalendarButtonData> buttonLabelList = null;
    int screenWidth;
    Button calendarButton = null;
    CalButtonClickListner calButtonClickListne=null;


	public CalendarGridAdapter( Activity activity, List<CalendarButtonData> buttonLabelList, CalButtonClickListner onClickListener, int screenWidth) {
		this.activity = activity;
		this.buttonLabelList = buttonLabelList;
		this.calButtonClickListne = onClickListener;

        this.screenWidth=screenWidth;


    }

	@Override
	public int getCount() {
		return buttonLabelList.size();
	}

	@Override
	public Object getItem(int position) {
		return buttonLabelList.get(position).getLabel();
	}

	@Override
	public long getItemId(int position) {
		return buttonLabelList.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// Get the view for calendar button

        if (convertView != null) {

			calendarButton = (Button) convertView;
		} else {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			calendarButton = (Button) inflater.inflate(R.layout.calendar_button_layout, null);
			calendarButton.setLayoutParams(new AbsListView.LayoutParams(screenWidth / 7, screenWidth / 7));
		}

		// set button color
		if (!(buttonLabelList.get(position).isCurrentMonth())) {
			calendarButton.setTextColor(activity.getResources().getColor(R.color.lite_gray));
		}

		// Set label and id
		calendarButton.setId(buttonLabelList.get(position).getId());
		calendarButton.setText(buttonLabelList.get(position).getLabel());

		// Set color of button label
		if (!buttonLabelList.get(position).isEnabled()){
			calendarButton.setTextColor(activity.getResources().getColor(R.color.text_black));
			calendarButton.setTextAppearance(activity, R.style.boldText);
		}
		// Show ring around date has planner
		if (buttonLabelList.get(position).isHasPlan()) {
			calendarButton.setBackgroundResource(R.drawable.calendar_button_has_plan);
			calendarButton.setTextColor(activity.getResources().getColor(R.color.app_color));
		}

		if(buttonLabelList.get(position).getTag().equalsIgnoreCase(Constants.SELECTED_CALENDAR_BUTTON_TAG)){
			calendarButton.setBackgroundResource(R.drawable.calendar_button_current_day);
			calendarButton.setTextColor(activity.getResources().getColor(R.color.text_white));
		}


		// Set click event listener
		calendarButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calButtonClickListne.onClick( buttonLabelList.get(position).getMills());

            }
        });
		convertView = calendarButton;
		return convertView;
	}
}