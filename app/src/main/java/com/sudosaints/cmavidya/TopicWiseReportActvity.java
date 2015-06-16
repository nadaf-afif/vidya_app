package com.sudosaints.cmavidya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.sudosaints.cmavidya.adapter.DifficultySpinnerAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.Subject;
import com.sudosaints.cmavidya.model.Topic;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by inni on 12/3/15.
 */
public class TopicWiseReportActvity extends Activity {


	@InjectView(R.id.testTypeSP)
	Spinner testTypeSP;

	@InjectView(R.id.difficultyLevelSP)
	Spinner difficultyLevelSP;

	@InjectView(R.id.subjectsSP)
	Spinner subjectsSP;

	@InjectView(R.id.topicSP)
	Spinner topicSP;

	private UIHelper uiHelper;
	private Activity activity;
	private DBUtils dbUtils;

	private DifficultySpinnerAdapter difficultySpinnerAdapter, testTypeSpinnerAdapter, subjectSpinnerAdapter, topicSpinnerAdapter;
	private List<String> diffList, testTypeList, subjectNameList, topicNameList;
	private List<Subject> subjectList;
	private List<Topic> topicList;
	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_pre_topic_report);
		ButterKnife.inject(this);
		uiHelper = new UIHelper(this);
		dbUtils = new DBUtils(activity);
		uiHelper.setActionBar(Constants.ActivityABarAction.ANALYSIS, actionBarLeftOnClickListener, null);

		diffList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.difficultie_level)));
		testTypeList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.test_type)));

		difficultySpinnerAdapter = new DifficultySpinnerAdapter(activity, diffList);
		difficultyLevelSP.setAdapter(difficultySpinnerAdapter);

		testTypeSpinnerAdapter = new DifficultySpinnerAdapter(activity, testTypeList);
		testTypeSP.setAdapter(testTypeSpinnerAdapter);

		subjectList = dbUtils.getDatabaseHelper().getSubjectRuntimeExceptionDao().queryForAll();
		subjectNameList = new ArrayList<String>();
		for (Subject subject : subjectList) {
			subjectNameList.add(subject.getSubjectName());
		}

		subjectSpinnerAdapter = new DifficultySpinnerAdapter(activity, subjectNameList);
		subjectsSP.setAdapter(subjectSpinnerAdapter);

		subjectsSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				try {
					topicList = dbUtils.getDatabaseHelper().getTopicRuntimeExceptionDao().queryBuilder().where().eq("subject_id", subjectList.get(position).getIdSubjectMaster()).query();
					topicNameList = new ArrayList<String>();
					topicNameList.add("All Topics");
					for (Topic topic : topicList) {
						topicNameList.add(topic.getTopicName());
					}
					topicSpinnerAdapter = new DifficultySpinnerAdapter(activity, topicNameList);
					topicSP.setAdapter(topicSpinnerAdapter);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	@OnClick(R.id.scoreCreateReportBtn)
	public void onclickCreateReport() {
		int topicId = topicSP.getSelectedItemPosition() == 0 ? 0 : topicList.get(topicSP.getSelectedItemPosition() - 1).getIdTopicMaster();
		int subjectId = subjectList.get(subjectsSP.getSelectedItemPosition()).getIdSubjectMaster();
		Intent intent = new Intent(activity, TopicAnalysisActivity.class);
		intent.putExtra(IntentExtras.TEST_TYPE, testTypeSP.getSelectedItemId());
		intent.putExtra(IntentExtras.DIFFICULTY_LEVEL, difficultyLevelSP.getSelectedItemId());
		intent.putExtra(IntentExtras.SUBJECT_NAME, subjectList.get(subjectsSP.getSelectedItemPosition()).getSubjectName());
		intent.putExtra(IntentExtras.TOPIC_MASTER_ID, topicId);
		intent.putExtra(IntentExtras.TOPIC_NAME, topicNameList.get(topicSP.getSelectedItemPosition()));
		intent.putExtra(IntentExtras.SUBJECT_MASTER_ID, subjectId);
		startActivity(intent);
	}

}
