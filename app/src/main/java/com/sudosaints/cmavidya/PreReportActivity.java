package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.Test;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by inni on 9/3/15.
 */
public class PreReportActivity extends Activity {

	private UIHelper uiHelper;
	private CMAVidyaApp cmaVidyaApp;
	private DBUtils dbUtils;
	private Preferences preferences;
	private Activity activity;
	private ProgressDialog progressDialog;

	private List<Test> tests;
	private int requestCount = 0;

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
		setContentView(R.layout.activity_pre_report);
		ButterKnife.bind(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		uiHelper = new UIHelper(this);
		preferences = new Preferences(activity);
		uiHelper.setActionBar(Constants.ActivityABarAction.ANALYSIS, actionBarLeftOnClickListener, null);
	}


	private void getTestDetails() {
		if (cmaVidyaApp.getApiRequestHelper().checkNetwork()) {
			dbUtils.getDatabaseHelper().truncateTestTable();
			dbUtils.getDatabaseHelper().truncateQuestionTable();
			for (Test test : tests) {
				requestCount++;
				CommonTaskExecutor.loadTest(cmaVidyaApp, activity, preferences.getUserName(), test.getIdTestLogMaster(), new CommonTaskExecutor.OnPostExecute() {
					@Override
					public void onPostExecute(Object object) {
						requestCount--;
						if (requestCount < 1 && null != progressDialog && progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
					}
				});
			}
		} else {
			cmaVidyaApp.showToast("No Active Internet Connection Detected");
		}
	}

	@OnClick(R.id.reportUpdateBtn)
	public void onClickUpdateBtn() {
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("Updating...");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

		tests = dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().queryForAll();
		if (null != tests && tests.size() > 0) {
			getTestDetails();
		} else {
			CommonTaskExecutor.getNUpdateAllSavedTest(cmaVidyaApp, activity, new CommonTaskExecutor.OnPostExecute() {
				@Override
				public void onPostExecute(Object object) {
					tests = dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().queryForAll();
					getTestDetails();
				}
			});
		}
	}


	@OnClick(R.id.reportScoreBtn)
	public void onClickScoreBtn() {
		Intent intent = new Intent(activity, ScoreWiseReportActvity.class);
		startActivity(intent);

	}

	@OnClick(R.id.reportSubjectBtn)
	public void onClickSubjectBtn() {
		Intent intent = new Intent(activity, SubjectWiseReportActvity.class);
		startActivity(intent);

	}

	@OnClick(R.id.reportTestBtn)
	public void onClickTestBtn() {
		Intent intent = new Intent(activity, TestWiseReportActvity.class);
		startActivity(intent);

	}

	@OnClick(R.id.reportTopicBtn)
	public void onClickTopicBtn() {
		Intent intent = new Intent(activity, TopicWiseReportActvity.class);
		startActivity(intent);

	}

}
