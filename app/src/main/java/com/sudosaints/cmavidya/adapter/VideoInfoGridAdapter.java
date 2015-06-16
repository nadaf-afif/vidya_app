package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.VideoInfo;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by inni on 17/3/15.
 */
public class VideoInfoGridAdapter extends BaseAdapter {

	private Activity activity;
	private List<VideoInfo> videoInfos;
	private LayoutInflater inflater;

	public VideoInfoGridAdapter(Activity activity, List<VideoInfo> videoInfos) {
		this.activity = activity;
		this.videoInfos = videoInfos;
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return videoInfos.size();
	}

	@Override
	public VideoInfo getItem(int position) {
		return videoInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.row_item_video_info, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.videoInfo = ButterKnife.findById(convertView, R.id.videoInfoTV);
			viewHolder.videoThumbIV = ButterKnife.findById(convertView, R.id.videoInfoIV);
			convertView.setTag(viewHolder);
		}

		ViewHolder viewHolder = (ViewHolder) convertView.getTag();

		viewHolder.videoInfo.setText(getItem(position).getDescription());

		String id = getItem(position).getFilePath().substring(getItem(position).getFilePath().lastIndexOf("/") + 1, getItem(position).getFilePath().lastIndexOf("?"));
		String thumbUrl = "http://img.youtube.com/vi/" + id + "/default.jpg";

		Picasso.with(activity).load(thumbUrl).placeholder(R.drawable.videos).into(viewHolder.videoThumbIV);
		return convertView;
	}

	class ViewHolder {
		ImageView videoThumbIV;
		TextView videoInfo;
	}
}
