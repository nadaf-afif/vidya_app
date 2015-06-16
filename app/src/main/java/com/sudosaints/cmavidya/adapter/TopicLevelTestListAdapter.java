package com.sudosaints.cmavidya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.Topic;
import com.sudosaints.cmavidya.model.TopicLevelDetails;

import java.util.List;

/**
 * Created by inni on 21/11/14.
 */
public class TopicLevelTestListAdapter extends BaseAdapter {

	private List<Topic> topicList;
	private List<String> difficultLevesList;
	private List<Integer> noOfQuestionList, diffLevelSelected, noOfQuestionSelected;
	private LayoutInflater inflater;
	private Activity activity;
	private DifficultySpinnerAdapter difficultySpinnerAdapter;
	private QuestionQuantitySpinnerAdapter questionQuantitySpinnerAdapter;


	public TopicLevelTestListAdapter(Activity activity, TopicLevelDetails topicLevelDetails) {
		this.activity = activity;
		this.topicList = topicLevelDetails.getTopicList();
		this.difficultLevesList = topicLevelDetails.getDifficultLevesList();
		this.noOfQuestionList = topicLevelDetails.getNoOfQuestionList();
		this.diffLevelSelected = topicLevelDetails.getDiffLevelSelected();
		this.noOfQuestionSelected = topicLevelDetails.getNoOfQuestionSelected();
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public Topic getItem(int i) {
		return topicList.get(i);
	}

	@Override
	public int getCount() {
		return topicList.size();
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewGroup) {
		if (null == view) {
			view = (View) inflater.inflate(R.layout.topic_level_test_list_item, null);
			if (null == questionQuantitySpinnerAdapter) {
				questionQuantitySpinnerAdapter = new QuestionQuantitySpinnerAdapter(activity, R.layout.spinner_row, noOfQuestionList);
			}
			if (null == difficultySpinnerAdapter) {
				difficultySpinnerAdapter = new DifficultySpinnerAdapter(activity, difficultLevesList);
			}
		}
		TextView topicNameTV = (TextView) view.findViewById(R.id.topicNameTV);

		topicNameTV.setText(getItem(position).getTopicName());
		Spinner diffSpinner = (Spinner) view.findViewById(R.id.difficultySpinner);
		diffSpinner.setAdapter(difficultySpinnerAdapter);
		diffSpinner.setSelection(diffLevelSelected.get(position));
		diffSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				diffLevelSelected.set(position, i);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
		Spinner noSpinner = (Spinner) view.findViewById(R.id.numberSpinner);
		noSpinner.setAdapter(questionQuantitySpinnerAdapter);
		noSpinner.setSelection(noOfQuestionSelected.get(position));
		noSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				noOfQuestionSelected.set(position, i );
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
		return view;
	}

}
