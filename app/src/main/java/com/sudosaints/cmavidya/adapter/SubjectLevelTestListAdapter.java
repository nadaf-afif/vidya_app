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
import com.sudosaints.cmavidya.model.Subject;
import com.sudosaints.cmavidya.model.SubjectLevelDetails;

import java.util.List;

/**
 * Created by inni on 19/11/14.
 */
public class SubjectLevelTestListAdapter extends BaseAdapter {
	private Activity activity;
	private List<Subject> subjectList;
	private List<String> difficultLevesList;
	private List<Integer> noOfQuestionList;
	private List<Integer> diffLevelSelected, noOfQuestionSelected;

	private DifficultySpinnerAdapter difficultySpinnerAdapter;
	private QuestionQuantitySpinnerAdapter questionQuantitySpinnerAdapter;
	LayoutInflater inflater;

	public SubjectLevelTestListAdapter(Activity activity, SubjectLevelDetails subjectLevelDetails) {
		this.activity = activity;
		this.subjectList = subjectLevelDetails.getSubjectList();
		this.difficultLevesList = subjectLevelDetails.getDifficultLevesList();
		this.noOfQuestionList = subjectLevelDetails.getNoOfQuestionList();
		this.diffLevelSelected = subjectLevelDetails.getDiffLevelSelected();
		this.noOfQuestionSelected = subjectLevelDetails.getNoOfQuestionSelected();
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return subjectList.size();
	}

	@Override
	public Subject getItem(int i) {
		return subjectList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewGroup) {

		if (null == view) {
			view = (View) inflater.inflate(R.layout.subject_level_test_list_item, null);
			if (null == questionQuantitySpinnerAdapter) {
				questionQuantitySpinnerAdapter = new QuestionQuantitySpinnerAdapter(activity, R.layout.spinner_row, noOfQuestionList);
			}
			if (null == difficultySpinnerAdapter) {
				difficultySpinnerAdapter = new DifficultySpinnerAdapter(activity,  difficultLevesList);
			}
		}
		TextView textView = (TextView) view.findViewById(R.id.subjectNameTV);
		textView.setText(getItem(position).getSubjectName());

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
				noOfQuestionSelected.set(position, i);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		return view;
	}
}
