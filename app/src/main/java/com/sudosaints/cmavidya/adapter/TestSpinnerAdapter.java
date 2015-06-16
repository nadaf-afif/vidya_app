package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.Test;

import java.util.List;

/**
 * Created by inni on 19/11/14.
 */
public class TestSpinnerAdapter extends ArrayAdapter<Test> {

	private Activity activity;
	private List<Test> testList;
	private LayoutInflater inflater;

	public TestSpinnerAdapter(Activity activity, List<Test> objects) {
		super(activity, R.layout.spinner_row, objects);
		this.activity = activity;
		this.testList = objects;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Test getItem(int position) {
		return super.getItem(position);
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
		Test value = getItem(position);
		TextView label = (TextView) row.findViewById(R.id.spinnerTextView);
		// Set values for spinner each row
		if (isDropdown) {
			label.setPadding(12, 16, 12, 16);
		}
		label.setText(value.getTestId() + " - " + value.getStrDate());
		return row;
	}


}
