package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.Test;

import java.util.List;

/**
 * Created by inni on 2/1/15.
 */
public class SavedTestAdapter extends BaseAdapter {

	private List<Test> testInfos;
	private Activity activity;
	private LayoutInflater inflater;


	public SavedTestAdapter(Activity activity, List<Test> testInfos) {
		this.testInfos = testInfos;
		this.activity = activity;
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return testInfos.size();
	}

	@Override
	public Test getItem(int position) {
		return testInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (null == convertView) {
			convertView = (View) inflater.inflate(R.layout.item_list_subject_name, null);

		}
		((TextView) convertView).setText(getItem(position).getTestId() + "\nCreated on " + getItem(position).getStrDate());

		return convertView;
	}
}
