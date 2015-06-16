package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.TopicReplanData;

import java.util.HashMap;
import java.util.List;

/**
 * Created by inni on 11/12/14.
 */
public class DragNDropTopicListAdapter extends ArrayAdapter<TopicReplanData> {
	private List<TopicReplanData> TopicReplanDataList;
	private Activity activity;
	// private List<String> strings;

	final int INVALID_ID = -1;

	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	public DragNDropTopicListAdapter(Activity context, List<TopicReplanData> objects) {
		super(context, R.layout.text_view, objects);
		TopicReplanDataList = objects;
		this.activity = context;
		for (int i = 0; i < objects.size(); ++i) {
			mIdMap.put(objects.get(i).getTopicname(), i);
		}
	}

	@Override
	public long getItemId(int position) {
		if (position < 0 || position >= mIdMap.size()) {
			return INVALID_ID;
		}
		String item = getItem(position).getTopicname();
		return mIdMap.get(item);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = layoutInflater.inflate(R.layout.row_item_topic_replan_data, parent, false);
		TextView textView = (TextView) convertView.findViewById(R.id.replanRowTitleTV);
		textView.setText(getItem(position).getTopicname());
		CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.isActiveCB);
		checkBox.setChecked(getItem(position).isActive());
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				getItem(position).setActive(b);
			}
		});
		return convertView;
	}
}