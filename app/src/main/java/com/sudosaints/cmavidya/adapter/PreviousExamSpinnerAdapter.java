package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.PreviousExam;

import java.util.List;

/**
 * Created by inni on 4/2/15.
 */
public class PreviousExamSpinnerAdapter extends ArrayAdapter {
	private Activity activity;
	private List<PreviousExam> previousExams;
	private LayoutInflater inflater;

	public PreviousExamSpinnerAdapter(Activity activity, List<PreviousExam> previousExams) {
		super(activity, R.layout.spinner_row, previousExams);
		this.activity = activity;
		this.previousExams = previousExams;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public PreviousExam getItem(int position) {
		return (PreviousExam) super.getItem(position);
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
		PreviousExam previousExam = getItem(position);
		TextView label = (TextView) row.findViewById(R.id.spinnerTextView);
		// Set values for spinner each row
		label.setText(previousExam.getExamYear() + "");
		if (isDropdown) label.setPadding(12, 16, 12, 16);
		return row;
	}
}
