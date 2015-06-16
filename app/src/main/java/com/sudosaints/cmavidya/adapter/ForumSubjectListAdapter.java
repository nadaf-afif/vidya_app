package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.ForumSubject;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by inni on 24/2/15.
 */
public class ForumSubjectListAdapter extends BaseAdapter {

	private List<ForumSubject> forumSubjects;
	private Activity activity;
	private LayoutInflater inflater;


	public ForumSubjectListAdapter(Activity activity, List<ForumSubject> forumSubjects) {
		this.activity = activity;
		this.forumSubjects = forumSubjects;
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return forumSubjects.size();
	}

	@Override
	public ForumSubject getItem(int position) {
		return forumSubjects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.row_item_forum_topic, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.title = ButterKnife.findById(convertView, R.id.forumTitleTV);
			viewHolder.createdDate = ButterKnife.findById(convertView, R.id.forumDateTV);
			viewHolder.description = ButterKnife.findById(convertView, R.id.forumdescTV);
			viewHolder.owner = ButterKnife.findById(convertView, R.id.forumOwnerTV);
			convertView.setTag(viewHolder);
		}

		ViewHolder viewHolder = (ViewHolder) convertView.getTag();

		viewHolder.owner.setText(getItem(position).getAddedBy());
		viewHolder.description.setText(getItem(position).getDescription());
		viewHolder.description.setSelected(true);
		viewHolder.createdDate.setText(getItem(position).getAddedDate());
		viewHolder.title.setText(getItem(position).getTitle());
		viewHolder.title.setSelected(true);
		return convertView;
	}

	private static class ViewHolder {
		TextView title, description, owner, createdDate;
	}
}
