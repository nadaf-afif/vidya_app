package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;

import java.util.List;

/**
 * Created by inni on 4/2/15.
 */
public class UnderstandingSpinnerAdapter extends ArrayAdapter {
	private Activity activity;
	private List<Integer> integers;
	private LayoutInflater inflater;

	public UnderstandingSpinnerAdapter(Activity activity, List<Integer> integers) {
		super(activity, R.layout.spinner_row, integers);
		this.activity = activity;
		this.integers = integers;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Integer getItem(int position) {
		return (Integer) super.getItem(position);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent, true);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent, false);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent, boolean isDropdown) {

/********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
		View row = inflater.inflate(R.layout.spinner_row, parent, false);
		/***** Get each Model object from Arraylist ********/
		TextView label = (TextView) row.findViewById(R.id.spinnerTextView);
		// Set values for spinner each row
		label.setText(getItem(position) + "");
		if (isDropdown) label.setPadding(12, 16, 12, 16);
		return row;
	}
}
