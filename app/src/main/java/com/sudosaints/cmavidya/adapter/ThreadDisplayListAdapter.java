package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.ForumThread;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by inni on 28/2/15.
 */
public class ThreadDisplayListAdapter extends BaseAdapter {

	private List<ForumThread> forumThreads;
	private Activity activity;
	private LayoutInflater inflater;

	public ThreadDisplayListAdapter(Activity activity, List<ForumThread> forumThreads) {
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
			convertView = inflater.inflate(R.layout.row_item_thread_display, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.userImage = ButterKnife.findById(convertView, R.id.itemThreadUserIV);
			viewHolder.body = ButterKnife.findById(convertView, R.id.itemThreadBodyWV);
			viewHolder.postDate = ButterKnife.findById(convertView, R.id.itemThreadDateTV);
			viewHolder.userName = ButterKnife.findById(convertView, R.id.itemThreadUserNameTV);
			convertView.setTag(viewHolder);
		}

		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.body.loadData(getItem(position).getBody(), "text/html", "utf-8");
		viewHolder.postDate.setText(getItem(position).getStrAddedDate());
		try {
			Picasso.with(activity).load(getItem(position).getPhoto() + "").placeholder(R.drawable.user_demo).into(viewHolder.userImage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		viewHolder.userName.setText(getItem(position).getAddedBy());

		if ((position % 2) == 1) {
			convertView.setBackgroundColor(activity.getResources().getColor(R.color.planner_row_odd));
			viewHolder.body.setBackgroundColor(activity.getResources().getColor(R.color.planner_row_odd));
		} else {
			convertView.setBackgroundColor(activity.getResources().getColor(R.color.planner_row_even));
			viewHolder.body.setBackgroundColor(activity.getResources().getColor(R.color.planner_row_even));

		}
		return convertView;
	}

	class ViewHolder {
		ImageView userImage;
		TextView userName, postDate;
		WebView body;
	}
}
