package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.sudosaints.cmavidya.adapter.DifficultySpinnerAdapter;
import com.sudosaints.cmavidya.adapter.TestSpinnerAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.Test;
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
 * Created by inni on 10/3/15.
 */
public class TestWiseReportActvity extends Activity {


	@Bind(R.id.testNameSP)
	Spinner testNameSp;

	@Bind(R.id.testTypeSP)
	Spinner testTypeSp;


	private UIHelper uiHelper;
	private CMAVidyaApp cmaVidyaApp;
	private DBUtils dbUtils;
	private Preferences preferences;
	private Activity activity;
	private ProgressDialog progressDialog;
	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};

	private DifficultySpinnerAdapter difficultySpinnerAdapter;
	private List<String> testTypeList;

	private List<Test> testList;
	private TestSpinnerAdapter testSpinnerAdapter;

	Test test;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_report_test);
		ButterKnife.bind(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		uiHelper = new UIHelper(this);
		preferences = new Preferences(activity);
		uiHelper.setActionBar(Constants.ActivityABarAction.ANALYSIS, actionBarLeftOnClickListener, null);

		testTypeList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.test_type)));
		testList = new ArrayList<Test>();

		testSpinnerAdapter = new TestSpinnerAdapter(activity, testList);
		testNameSp.setAdapter(testSpinnerAdapter);

	/*	adapter = new ArrayAdapter<Test>(activity, R.layout.spinner_row, R.id.spinnerTextView, testList);
		adapter.setDropDownViewResource(R.layout.spinner_row);
		testNameSp.setAdapter(adapter);*/

		difficultySpinnerAdapter = new DifficultySpinnerAdapter(activity, testTypeList);
		testTypeSp.setAdapter(difficultySpinnerAdapter);
		testTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				test = null;
				displayTestDataSpinner(position + 1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		testNameSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				test = testList.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	@OnClick(R.id.testCreateReportBtn)
	public void onClickCreateReport() {
		if (null != test) {
			Intent intent = new Intent(activity, IndividualTestAnalysisActivity.class);
			intent.putExtra(IntentExtras.TEST, test);
			startActivity(intent);
		} else {
			cmaVidyaApp.showToast("Select Test");
		}

	}

	private void displayTestDataSpinner(int testType) {
		testList.clear();
		try {
			testList.addAll(dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().queryBuilder().where().eq("idTestType", testType).and().eq("isTestEnd", true).query());
		} catch (Exception e) {
			e.printStackTrace();
		}
		testSpinnerAdapter.notifyDataSetChanged();
	}


}
