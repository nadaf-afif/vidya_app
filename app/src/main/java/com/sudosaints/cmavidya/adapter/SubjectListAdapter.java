package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.Subject;

import java.util.List;

/**
 * Created by inni on 25/12/14.
 */
public class SubjectListAdapter extends BaseAdapter {
	private List<Subject> subjects;
	private Activity activity;
	private LayoutInflater inflater;

	public SubjectListAdapter(Activity activity, List<Subject> subjects) {
		this.activity = activity;
		this.subjects = subjects;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return subjects.size();
	}

	@Override
	public Subject getItem(int position) {
		return subjects.get(position);
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
		((TextView) convertView).setText(getItem(position).getSubjectName());

		return convertView;
	}
}
