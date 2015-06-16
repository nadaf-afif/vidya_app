package com.sudosaints.cmavidya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.sudosaints.cmavidya.adapter.DifficultySpinnerAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.Subject;
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
public class SubjectWiseReportActvity extends Activity {


	@InjectView(R.id.testTypeSP)
	Spinner testTypeSP;

	@InjectView(R.id.difficultyLevelSP)
	Spinner difficultyLevelSP;

	@InjectView(R.id.subjectsSP)
	Spinner subjectsSp;

	private UIHelper uiHelper;
	private Activity activity;
	private DBUtils dbUtils;

	private DifficultySpinnerAdapter difficultySpinnerAdapter, testTypeSpinnerAdapter, subjectNameSpinnerAdapter;
	private List<String> diffList, testTypeList, subjecNametList;
	private List<Subject> subjects;


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
		setContentView(R.layout.activity_pre_subject_report);
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

		subjects = dbUtils.getDatabaseHelper().getSubjectRuntimeExceptionDao().queryForAll();

		subjecNametList = new ArrayList<String>();
		subjecNametList.add("All Subjects");
		for (Subject subject : subjects) {
			subjecNametList.add(subject.getSubjectName());
		}
		subjectNameSpinnerAdapter = new DifficultySpinnerAdapter(activity, subjecNametList);
		subjectsSp.setAdapter(subjectNameSpinnerAdapter);


	}

	@OnClick(R.id.scoreCreateReportBtn)
	public void onclickCreateReport() {
		int subjectId = subjectsSp.getSelectedItemId() == 0 ? 0 : subjects.get(subjectsSp.getSelectedItemPosition()-1).getIdSubjectMaster();
		Intent intent = new Intent(activity, SubjectAnalysisActivity.class);
		intent.putExtra(IntentExtras.TEST_TYPE, testTypeSP.getSelectedItemId());
		intent.putExtra(IntentExtras.DIFFICULTY_LEVEL, difficultyLevelSP.getSelectedItemId());
		intent.putExtra(IntentExtras.SUBJECT_MASTER_ID, subjectId);
		intent.putExtra(IntentExtras.SUBJECT_NAME, subjecNametList.get(subjectsSp.getSelectedItemPosition()));
		startActivity(intent);

	}

}
