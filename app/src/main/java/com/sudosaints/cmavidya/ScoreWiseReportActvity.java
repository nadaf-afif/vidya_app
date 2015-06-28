package com.sudosaints.cmavidya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.sudosaints.cmavidya.adapter.DifficultySpinnerAdapter;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by inni on 12/3/15.
 */
public class ScoreWiseReportActvity extends Activity {


	@Bind(R.id.testTypeSP)
	Spinner testTypeSP;

	@Bind(R.id.difficultyLevelSP)
	Spinner difficultyLevelSP;

	private UIHelper uiHelper;
	private Activity activity;

	private DifficultySpinnerAdapter difficultySpinnerAdapter, testTypeSpinnerAdapter;
	private List<String> diffList, testTypeList;
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
		setContentView(R.layout.activity_pre_score_report);
		ButterKnife.bind(this);
		uiHelper = new UIHelper(this);
		uiHelper.setActionBar(Constants.ActivityABarAction.ANALYSIS, actionBarLeftOnClickListener, null);

		diffList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.difficultie_level)));
		testTypeList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.test_type)));

		difficultySpinnerAdapter = new DifficultySpinnerAdapter(activity, diffList);
		difficultyLevelSP.setAdapter(difficultySpinnerAdapter);

		testTypeSpinnerAdapter = new DifficultySpinnerAdapter(activity, testTypeList);
		testTypeSP.setAdapter(testTypeSpinnerAdapter);
	}

	@OnClick(R.id.scoreCreateReportBtn)
	public void onclickCreateReport() {
		Intent intent = new Intent(activity, ScoreAnalysisActivity.class);
		intent.putExtra(IntentExtras.TEST_TYPE, testTypeSP.getSelectedItemId());
		intent.putExtra(IntentExtras.DIFFICULTY_LEVEL, difficultyLevelSP.getSelectedItemId());
		startActivity(intent);

	}

}
