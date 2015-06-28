package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.sudosaints.cmavidya.adapter.FixedTestListAdapter;
import com.sudosaints.cmavidya.adapter.PreviousExamSpinnerAdapter;
import com.sudosaints.cmavidya.adapter.PreviousTestSpinnerAdapter;
import com.sudosaints.cmavidya.adapter.SavedTestAdapter;
import com.sudosaints.cmavidya.adapter.TemplateTestAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.dto.FixedTestDTO;
import com.sudosaints.cmavidya.dto.PreviousTestDTO;
import com.sudosaints.cmavidya.dto.TemplateTestDTO;
import com.sudosaints.cmavidya.model.FixedTest;
import com.sudosaints.cmavidya.model.PreviousExam;
import com.sudosaints.cmavidya.model.PreviousTest;
import com.sudosaints.cmavidya.model.TemplateTest;
import com.sudosaints.cmavidya.model.Test;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by inni on 31/12/14.
 */
public class PreTestActivity extends Activity {

	private UIHelper uiHelper;
	private CMAVidyaApp cmaVidyaApp;
	private DBUtils dbUtils;
	private List<Test> tests;
	private ProgressDialog progressDialog = null;
	private List<TemplateTest> templateTests;
	private List<PreviousTest> previousTests = null;
	private List<PreviousExam> previousExams = null;
	private Preferences preferences;
	private PreviousExamSpinnerAdapter previousExamSpinnerAdapter;
	private PreviousTestSpinnerAdapter previousTestSpinnerAdapter;
	private List<FixedTest> fixedTests = null;
	private FixedTestListAdapter fixedTestListAdapter = null;

	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_series);
		ButterKnife.bind(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		uiHelper = new UIHelper(this);
		preferences = new Preferences(PreTestActivity.this);
		uiHelper.setActionBar(Constants.ActivityABarAction.Test_SERIES, actionBarLeftOnClickListener, null);
	}

	@OnClick(R.id.createTestBtn)
	public void onclickCreateTestBtn() {
		Intent intent = new Intent(PreTestActivity.this, CreateTestActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.continueTestBtn)
	public void onclickContinueTestBtn() {
		progressDialog = new ProgressDialog(PreTestActivity.this);
		progressDialog.setMessage("Loading...");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		CommonTaskExecutor.getNUpdateAllSavedTest(cmaVidyaApp, PreTestActivity.this, new CommonTaskExecutor.OnPostExecute() {
			@Override
			public void onPostExecute(Object object) {
				if (null != progressDialog && progressDialog.isShowing()) {
 					progressDialog.dismiss();
				}
				if (null != object) {
					cmaVidyaApp.showToast("Tests Updated");
				}
				try {
					tests = dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().queryBuilder().where().eq("isTestEnd", false).query();
					if (null != tests && tests.size() > 0) {
						final Dialog dialog = new Dialog(PreTestActivity.this);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.list_subject);
						dialog.setCancelable(true);
						ListView listView = (ListView) dialog.findViewById(R.id.subjectList);
						SavedTestAdapter savedTestInfoAdapter = new SavedTestAdapter(PreTestActivity.this, tests);
						listView.setAdapter(savedTestInfoAdapter);
						listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								Intent intent = new Intent(PreTestActivity.this, TestQuestionActivity.class);
								intent.putExtra(IntentExtras.Id_Test_Log_Master, tests.get(position).getIdTestLogMaster());
								startActivity(intent);
								finish();
								dialog.dismiss();
							}
						});
						dialog.show();
					} else {
						cmaVidyaApp.showToast("No saved test on device");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});


	}

	@OnClick(R.id.templateTestBtn)
	public void onclickTemplateTestBtn() {
		try {
			templateTests = dbUtils.getDatabaseHelper().getTemplateTestRuntimeExceptionDao().queryForAll();
			if (null != templateTests && templateTests.size() > 0) {
				final Dialog dialog = new Dialog(PreTestActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.list_subject);
				dialog.setCancelable(true);
				ListView listView = (ListView) dialog.findViewById(R.id.subjectList);
				TemplateTestAdapter savedTestInfoAdapter = new TemplateTestAdapter(PreTestActivity.this, templateTests);
				listView.setAdapter(savedTestInfoAdapter);
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

						genTemplateTest(templateTests.get(position));
						dialog.dismiss();
					}
				});
				dialog.show();
			} else {
				cmaVidyaApp.showToast("No Template test on device");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public void genTemplateTest(TemplateTest templateTest) {
		final TemplateTestDTO templateTestDTO = new TemplateTestDTO();
		templateTestDTO.setUserName(preferences.getUserName());
		templateTestDTO.setIdTemplateMaster(templateTest.getIdTemplateMaster());
		if (preferences.getMasterCourseId() == 1) {
			//mcq
			templateTestDTO.setIdTestType(1);
			startTemplateTest(templateTestDTO);

		} else /*if (preferences.getMasterCourseId() == 2)*/ {
			//theory
			templateTestDTO.setIdTestType(2);
			startTemplateTest(templateTestDTO);
		} /*else {

			final Dialog dialog = new Dialog(PreTestActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.test_option_picker_dialog);
			dialog.setCancelable(true);
			final RadioButton theoryRB = (RadioButton) dialog.findViewById(R.id.theoryTestRB);
			Button continueForTest = (Button) dialog.findViewById(R.id.proceedTest);
			continueForTest.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (theoryRB.isChecked()) {
						templateTestDTO.setIdTestType(1);
					} else {
						templateTestDTO.setIdTestType(2);
					}
					startTemplateTest(templateTestDTO);
				}
			});
		}*/
	}

	public void startTemplateTest(TemplateTestDTO templateTestDTO) {
		CommonTaskExecutor.generateTemplateTest(cmaVidyaApp, PreTestActivity.this, templateTestDTO, new CommonTaskExecutor.OnPostExecute() {
			@Override
			public void onPostExecute(Object object) {
				if (null != object) {
					Intent intent = new Intent(PreTestActivity.this, TestQuestionActivity.class);
					intent.putExtra(IntentExtras.Id_Test_Log_Master, (Long) object);
					startActivity(intent);
					finish();
				} else {
					cmaVidyaApp.showToast("Error creating Template Test");
				}
			}
		});
	}

	@OnClick(R.id.previousTestBtn)
	public void onclickPreviousTestBtn() {


		previousTests = dbUtils.getDatabaseHelper().getPreviousTestRuntimeExceptionDao().queryForAll();
		if (null != previousTests && previousTests.size() > 0) {
			previousTestSpinnerAdapter = new PreviousTestSpinnerAdapter(PreTestActivity.this, previousTests);
			final Dialog dialog = new Dialog(PreTestActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.popup_previous_test_opt);
			final Spinner pTest = (Spinner) dialog.findViewById(R.id.examNameSP);
			final Spinner pExam = (Spinner) dialog.findViewById(R.id.examYearSP);
			final Button createTestBtn = (Button) dialog.findViewById(R.id.createPreviousTestBtn);

			pTest.setAdapter(previousTestSpinnerAdapter);
			pTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					try {
						previousExams = dbUtils.getDatabaseHelper().getPreviousExamRuntimeExceptionDao().queryBuilder().where().eq("IdExam", previousTests.get(position).getIdExam()).query();
						previousExamSpinnerAdapter = new PreviousExamSpinnerAdapter(PreTestActivity.this, previousExams);
						pExam.setAdapter(previousExamSpinnerAdapter);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
			createTestBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					final PreviousTestDTO previousTestDTO = new PreviousTestDTO();
					previousTestDTO.setUserName(preferences.getUserName());
					previousTestDTO.setIdPreviousExam(previousExams.get((int) pExam.getSelectedItemId()).getIdPreviousExam());
					previousTestDTO.setIdTestType(previousTests.get((int) pTest.getSelectedItemPosition()).getIdTestType());
					startPreviousTest(previousTestDTO);
				}
			});
			dialog.show();
		} else {
			cmaVidyaApp.showToast("No Previous Test On device ");
		}
	}

	private void startPreviousTest(PreviousTestDTO previousTestDTO) {
		CommonTaskExecutor.generatePreviousTest(cmaVidyaApp, PreTestActivity.this, previousTestDTO, new CommonTaskExecutor.OnPostExecute() {
			@Override
			public void onPostExecute(Object object) {
				if (null != object) {
					Intent intent = new Intent(PreTestActivity.this, TestQuestionActivity.class);
					intent.putExtra(IntentExtras.Id_Test_Log_Master, (Long) object);
					startActivity(intent);
					finish();
				} else {
					cmaVidyaApp.showToast("Error creating Previous Test");
				}
			}
		});
	}

	@OnClick(R.id.fixedTestBtn)
	public void onclickFixedTest() {
		fixedTests = dbUtils.getDatabaseHelper().getFixedTestLongRuntimeExceptionDao().queryForAll();

		if (null != fixedTests && fixedTests.size() > 0) {

			final Dialog dialog = new Dialog(PreTestActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.list_subject);
			dialog.setCancelable(true);
			ListView listView = (ListView) dialog.findViewById(R.id.subjectList);
			fixedTestListAdapter = new FixedTestListAdapter(PreTestActivity.this, fixedTests);
			listView.setAdapter(fixedTestListAdapter);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					generateFixedTest(fixedTests.get(position));
					dialog.dismiss();
				}
			});
			dialog.show();
		} else {
			cmaVidyaApp.showToast("No Fixed test on device");
		}


	}

	private void generateFixedTest(FixedTest fixedTest) {
		FixedTestDTO fixedTestDTO = new FixedTestDTO();
		fixedTestDTO.setUserName(preferences.getUserName());
		fixedTestDTO.setIdFixedTestMaster(fixedTest.getIdFixedTestMaster());
		CommonTaskExecutor.generateFixedTest(cmaVidyaApp, PreTestActivity.this, fixedTestDTO, new CommonTaskExecutor.OnPostExecute() {
			@Override
			public void onPostExecute(Object object) {
				if (null != object) {
					Intent intent = new Intent(PreTestActivity.this, TestQuestionActivity.class);
					intent.putExtra(IntentExtras.Id_Test_Log_Master, (Long) object);
					startActivity(intent);
					finish();
				} else {
					cmaVidyaApp.showToast("Error creating Fixed Test");
				}
			}
		});


	}

	@OnClick(R.id.reportTestBtn)
	public void onclickReports() {
		Intent intent = new Intent(PreTestActivity.this, PreReportActivity.class);
		startActivity(intent);
	}

}
