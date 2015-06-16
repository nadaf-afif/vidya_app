package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.ForumThread;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by inni on 24/2/15.
 */
public class ForumThreadListAdapter extends BaseAdapter {

	private List<ForumThread> forumThreads;
	private Activity activity;
	private LayoutInflater inflater;


	public ForumThreadListAdapter(Activity activity, List<ForumThread> forumThreads) {
		this.activity = activity;
		this.forumThreads = forumThreads;
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return forumThreads.size();
	}

	@Override
	public ForumThread getItem(int position) {
		return forumThreads.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.row_forum_thread_list, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.title = ButterKnife.findById(convertView, R.id.threadTitleTV);
			viewHolder.addedBy = ButterKnife.findById(convertView, R.id.threadAddedBy);
			viewHolder.addedOn = ButterKnife.findById(convertView, R.id.threadAddedOn);
			viewHolder.lastPostBy = ButterKnife.findById(convertView, R.id.threadLastPostBy);
			viewHolder.lastPostOn = ButterKnife.findById(convertView, R.id.threadLastPostOn);
			viewHolder.replies = ButterKnife.findById(convertView, R.id.threadRepliesCount);
			viewHolder.views = ButterKnife.findById(convertView, R.id.threadViewsCount);
			convertView.setTag(viewHolder);
		}

		ViewHolder viewHolder = (ViewHolder) convertView.getTag();

		viewHolder.title.setText(getItem(position).getTitle());
		viewHolder.title.setSelected(true);

		viewHolder.addedBy.setText(getItem(position).getAddedBy());
		viewHolder.addedOn.setText(getItem(position).getStrAddedDate());
		viewHolder.lastPostBy.setText(getItem(position).getLastPostBy());
		viewHolder.lastPostOn.setText(getItem(position).getStrLastPostDate());
		viewHolder.replies.setText(getItem(position).getReplyCount() + "");
		viewHolder.views.setText(getItem(position).getViewCount() + "");


		return convertView;
	}

	private static class ViewHolder {
		TextView title, addedBy, addedOn, lastPostBy, lastPostOn, replies, views;
	}
}
