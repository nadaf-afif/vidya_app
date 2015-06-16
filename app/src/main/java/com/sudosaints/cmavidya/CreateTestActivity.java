package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.sudosaints.cmavidya.adapter.SubjectLevelTestListAdapter;
import com.sudosaints.cmavidya.adapter.SubjectListAdapter;
import com.sudosaints.cmavidya.adapter.TopicLevelTestListAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.dto.PrepareTestDTO;
import com.sudosaints.cmavidya.model.Subject;
import com.sudosaints.cmavidya.model.SubjectLevelDetails;
import com.sudosaints.cmavidya.model.TestInfo;
import com.sudosaints.cmavidya.model.Topic;
import com.sudosaints.cmavidya.model.TopicLevelDetails;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.CommonUtil;
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
 * Created by inni on 18/11/14.
 */
public class CreateTestActivity extends Activity {

	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};
	View.OnClickListener ActionBarRightOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {

		}
	};
	@InjectView(R.id.topicLevelLV)
	ListView topicLevelLV;
	@InjectView(R.id.subjectLevelLV)
	ListView subjectLevelLV;
	@InjectView(R.id.tabHost)
	TabHost tabHost;
	@InjectView(R.id.selectSubjectForTopicTestTV)
	TextView selectSubjectForTopicTestTV;
	@InjectView(R.id.createTextButton)
	Button createTextButton;

	private UIHelper uiHelper;
	private List<Topic> topicList;
	private DBUtils dbUtils;
	private List<String> diffLevelList;
	private List<Integer> quantityOfQuestionsList;
	private SubjectLevelDetails subjectLevelDetails;
	private TopicLevelDetails topicLevelDetails;
	private SubjectLevelTestListAdapter subjectLevelTestListAdapter;
	private TopicLevelTestListAdapter topicLevelTestListAdapter;
	private SubjectListAdapter subjectListAdapter;
	private PrepareTestDTO prepareTestDTO;
	private CMAVidyaApp cmaVidyaApp;
	private Preferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		ButterKnife.inject(this);
		uiHelper = new UIHelper(this);
		uiHelper.setActionBar(Constants.ActivityABarAction.TEST_CREATE, actionBarLeftOnClickListener, ActionBarRightOnClickListener);
		dbUtils = new DBUtils(this);
		preferences = new Preferences(CreateTestActivity.this);


		diffLevelList = Arrays.asList(getResources().getStringArray(R.array.difficulties));
		int[] ints = getResources().getIntArray(R.array.quantity_questions);
		quantityOfQuestionsList = new ArrayList<Integer>();
		for (int index = 0; index < ints.length; index++) {
			quantityOfQuestionsList.add(ints[index]);
		}

		tabHost.setup();
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TabHost.TabSpec mapTabSpec1 = tabHost.newTabSpec("MAP1");
		Button tabButton = (Button) inflater.inflate(R.layout.tab_button_layout, null);
		tabButton.setText("Topic Level");
		mapTabSpec1.setIndicator(tabButton);
		mapTabSpec1.setContent(R.id.tab1);

		// Map Tab
		TabHost.TabSpec mapTabSpec2 = tabHost.newTabSpec("MAP2");
		tabButton = (Button) inflater.inflate(R.layout.tab_button_layout, null);
		tabButton.setText("Subject Level");
		mapTabSpec2.setIndicator(tabButton);
		mapTabSpec2.setContent(R.id.tab2);

		tabHost.addTab(mapTabSpec1);
		tabHost.addTab(mapTabSpec2);

		displaySubjectData();


	}

	@OnClick(R.id.selectSubjectForTopicTestTV)
	public void onClickSelectSubjectForTopic() {
		final List<Subject> subjects = dbUtils.getDatabaseHelper().getSubjectRuntimeExceptionDao().queryForAll();
		final Dialog dialog = new Dialog(CreateTestActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.list_subject);
		dialog.setCancelable(true);

		ListView listView = (ListView) dialog.findViewById(R.id.subjectList);
		subjectListAdapter = new SubjectListAdapter(CreateTestActivity.this, subjects);
		listView.setAdapter(subjectListAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialog.dismiss();
				selectSubjectForTopicTestTV.setText(subjects.get(position).getSubjectName());
				displayTopicData(subjects.get(position));
			}
		});

		dialog.show();
	}

	@OnClick(R.id.createTextButton)
	public void onClickCreateTextButton() {
		if (cmaVidyaApp.getApiRequestHelper().checkNetwork()) {
			if (preferences.getMasterCourseId() == 1) {
				prepareTestDTO = new PrepareTestDTO();
				prepareTestDTO.setUserName(preferences.getUserName());

				if (tabHost.getCurrentTab() == 0)//topic wise
					if (null != topicLevelDetails) {
						prepareTestDTO.setIdTestType(1);
						for (int count = topicLevelDetails.getNoOfQuestionSelected().size() - 1; count >= 0; count--) {
							if (topicLevelDetails.getNoOfQuestionSelected().get(count).intValue() == 0) {
								topicLevelDetails.getNoOfQuestionSelected().remove(count);
								topicLevelDetails.getTopicList().remove(count);
								topicLevelDetails.getDiffLevelSelected().remove(count);

							}
						}
						prepareTestDTO.setNumOfQuestion(CommonUtil.stringToCsvFromIntList(topicLevelDetails.getNoOfQuestionSelected()));
						prepareTestDTO.setIds(CommonUtil.TopicListToTopicIdsCsv(topicLevelDetails.getTopicList()));
						prepareTestDTO.setDifficultyLevel(CommonUtil.stringToCsvFromIntList(topicLevelDetails.getDiffLevelSelected()));
						CommonTaskExecutor.generateTopicTest(cmaVidyaApp, CreateTestActivity.this, prepareTestDTO, dbUtils, new CommonTaskExecutor.OnPostExecute() {
							@Override
							public void onPostExecute(Object object) {
								if (null == object) {
									cmaVidyaApp.showToast("error creating test");
								} else {
									cmaVidyaApp.showToast("Test Created");
									getTestStarted((TestInfo) object);
								}
							}
						});
					} else {
						cmaVidyaApp.showToast("Select Subject");
					}
					//subject wise
				else {
					prepareTestDTO.setIdTestType(1);

					for (int count = subjectLevelDetails.getNoOfQuestionSelected().size() - 1; count >= 0; count--) {
						if (subjectLevelDetails.getNoOfQuestionSelected().get(count).intValue() == 0) {
							subjectLevelDetails.getNoOfQuestionSelected().remove(count);
							subjectLevelDetails.getSubjectList().remove(count);
							subjectLevelDetails.getDiffLevelSelected().remove(count);

						}
					}
					prepareTestDTO.setIds(CommonUtil.SubjectListToSubjectIdsCsv(subjectLevelDetails.getSubjectList()));
					prepareTestDTO.setNumOfQuestion(CommonUtil.stringToCsvFromIntList(subjectLevelDetails.getNoOfQuestionSelected()));
					prepareTestDTO.setDifficultyLevel(CommonUtil.stringToCsvFromIntList(subjectLevelDetails.getDiffLevelSelected()));
					CommonTaskExecutor.generateSubjectTest(cmaVidyaApp, CreateTestActivity.this, prepareTestDTO, dbUtils, new CommonTaskExecutor.OnPostExecute() {
						@Override
						public void onPostExecute(Object object) {
							if (null == object) {
								cmaVidyaApp.showToast("error creating test");
							} else {
								cmaVidyaApp.showToast("Test Created");
								getTestStarted((TestInfo) object);
							}

						}
					});
				}
			} else  /*if(preferences.getMasterCourseId() == 2)*/ {
				prepareTestDTO = new PrepareTestDTO();
				prepareTestDTO.setUserName(preferences.getUserName());
				if (tabHost.getCurrentTab() == 0)//topic wise
					if (null != topicLevelDetails) {
						prepareTestDTO.setIdTestType(2);
						for (int count = topicLevelDetails.getNoOfQuestionSelected().size() - 1; count >= 0; count--) {
							if (topicLevelDetails.getNoOfQuestionSelected().get(count).intValue() == 0) {
								topicLevelDetails.getNoOfQuestionSelected().remove(count);
								topicLevelDetails.getTopicList().remove(count);
								topicLevelDetails.getDiffLevelSelected().remove(count);
							}
						}
						prepareTestDTO.setIds(CommonUtil.TopicListToTopicIdsCsv(topicLevelDetails.getTopicList()));
						prepareTestDTO.setNumOfQuestion(CommonUtil.stringToCsvFromIntList(topicLevelDetails.getNoOfQuestionSelected()));
						prepareTestDTO.setDifficultyLevel(CommonUtil.stringToCsvFromIntList(topicLevelDetails.getDiffLevelSelected()));
						CommonTaskExecutor.generateTopicTest(cmaVidyaApp, CreateTestActivity.this, prepareTestDTO, dbUtils, new CommonTaskExecutor.OnPostExecute() {
							@Override
							public void onPostExecute(final Object object) {
								if (null == object) {
									cmaVidyaApp.showToast("error creating test");
								} else {
									cmaVidyaApp.showToast("Test Created");
									getTestStarted((TestInfo) object);
								}

							}
						});
					} else {
						cmaVidyaApp.showToast("Select Subject");
					}
					//subject wise
				else {
					prepareTestDTO.setIdTestType(2);
					for (int count = (subjectLevelDetails.getNoOfQuestionSelected().size() - 1); count >= 0; count--) {
						if (subjectLevelDetails.getNoOfQuestionSelected().get(count).intValue() == 0) {
							subjectLevelDetails.getNoOfQuestionSelected().remove(count);
							subjectLevelDetails.getSubjectList().remove(count);
							subjectLevelDetails.getDiffLevelSelected().remove(count);
						}
					}
					prepareTestDTO.setIds(CommonUtil.SubjectListToSubjectIdsCsv(subjectLevelDetails.getSubjectList()));
					prepareTestDTO.setNumOfQuestion(CommonUtil.stringToCsvFromIntList(subjectLevelDetails.getNoOfQuestionSelected()));
					prepareTestDTO.setDifficultyLevel(CommonUtil.stringToCsvFromIntList(subjectLevelDetails.getDiffLevelSelected()));
					CommonTaskExecutor.generateSubjectTest(cmaVidyaApp, CreateTestActivity.this, prepareTestDTO, dbUtils, new CommonTaskExecutor.OnPostExecute() {
						@Override
						public void onPostExecute(Object object) {
							if (null == object) {
								cmaVidyaApp.showToast("error creating test");
							} else {
								cmaVidyaApp.showToast("Test Created");
								getTestStarted((TestInfo) object);
							}

						}
					});

				}
			} /*else {
				final Dialog dialog = new Dialog(CreateTestActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.test_option_picker_dialog);
				dialog.setCancelable(true);
				final RadioButton theoryRB = (RadioButton) dialog.findViewById(R.id.theoryTestRB);
				Button continueForTest = (Button) dialog.findViewById(R.id.proceedTest);

				continueForTest.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();

						prepareTestDTO = new PrepareTestDTO();
						prepareTestDTO.setUserName(preferences.getUserName());
						if (tabHost.getCurrentTab() == 0)//topic wise
							if (null != topicLevelDetails) {
								if (theoryRB.isChecked()) {
									prepareTestDTO.setIdTestType(2);
									prepareTestDTO.setIds(CommonUtil.TopicListToTopicIdsCsv(topicLevelDetails.getTopicList()));
									prepareTestDTO.setNumOfQuestion(CommonUtil.stringToCsvFromIntList(topicLevelDetails.getNoOfQuestionSelected()));
									prepareTestDTO.setDifficultyLevel(CommonUtil.stringToCsvFromIntList(topicLevelDetails.getDiffLevelSelected()));
									CommonTaskExecutor.generateTopicTest(cmaVidyaApp, CreateTestActivity.this, prepareTestDTO, dbUtils, new CommonTaskExecutor.OnPostExecute() {
										@Override
										public void onPostExecute(final Object object) {
											if (null == object) {
												cmaVidyaApp.showToast("error creating test");
											} else {
												cmaVidyaApp.showToast("Test Created");
												getTestStarted((TestInfo) object);
											}

										}
									});
								} else {
									prepareTestDTO.setIdTestType(1);
									prepareTestDTO.setIds(CommonUtil.TopicListToTopicIdsCsv(topicLevelDetails.getTopicList()));
									prepareTestDTO.setNumOfQuestion(CommonUtil.stringToCsvFromIntList(topicLevelDetails.getNoOfQuestionSelected()));
									prepareTestDTO.setDifficultyLevel(CommonUtil.stringToCsvFromIntList(topicLevelDetails.getDiffLevelSelected()));
									CommonTaskExecutor.generateTopicTest(cmaVidyaApp, CreateTestActivity.this, prepareTestDTO, dbUtils, new CommonTaskExecutor.OnPostExecute() {
										@Override
										public void onPostExecute(Object object) {
											if (null == object) {
												cmaVidyaApp.showToast("error creating test");
											} else {
												cmaVidyaApp.showToast("Test Created");
												getTestStarted((TestInfo) object);
											}

										}
									});

								}
							} else {
								cmaVidyaApp.showToast("Select Subject");
							}
							//subject wise
						else if (theoryRB.isChecked()) {
							prepareTestDTO.setIdTestType(2);
							prepareTestDTO.setIds(CommonUtil.SubjectListToSubjectIdsCsv(subjectLevelDetails.getSubjectList()));
							prepareTestDTO.setNumOfQuestion(CommonUtil.stringToCsvFromIntList(subjectLevelDetails.getNoOfQuestionSelected()));
							prepareTestDTO.setDifficultyLevel(CommonUtil.stringToCsvFromIntList(subjectLevelDetails.getDiffLevelSelected()));
							CommonTaskExecutor.generateSubjectTest(cmaVidyaApp, CreateTestActivity.this, prepareTestDTO, dbUtils, new CommonTaskExecutor.OnPostExecute() {
								@Override
								public void onPostExecute(Object object) {
									if (null == object) {
										cmaVidyaApp.showToast("error creating test");
									} else {
										cmaVidyaApp.showToast("Test Created");
										getTestStarted((TestInfo) object);
									}

								}
							});

						} else {
							prepareTestDTO.setIdTestType(1);
							prepareTestDTO.setIds(CommonUtil.SubjectListToSubjectIdsCsv(subjectLevelDetails.getSubjectList()));
							prepareTestDTO.setNumOfQuestion(CommonUtil.stringToCsvFromIntList(subjectLevelDetails.getNoOfQuestionSelected()));
							prepareTestDTO.setDifficultyLevel(CommonUtil.stringToCsvFromIntList(subjectLevelDetails.getDiffLevelSelected()));
							CommonTaskExecutor.generateSubjectTest(cmaVidyaApp, CreateTestActivity.this, prepareTestDTO, dbUtils, new CommonTaskExecutor.OnPostExecute() {
								@Override
								public void onPostExecute(Object object) {
									if (null == object) {
										cmaVidyaApp.showToast("error creating test");
									} else {
										cmaVidyaApp.showToast("Test Created");
										getTestStarted((TestInfo) object);
									}

								}
							});
						}


//				Intent intent = new Intent(DashboardActivity.this, TestCustomiseActivity.class);
//				intent.putExtra(IntentExtras.TEST_AS_THEORY, theoryRB.isSelected());
//				startActivity(intent);
					}


				});
				dialog.show();

			}*/
		} else {
			cmaVidyaApp.showToast("No Active Internet Connection Detected");
		}


	}

	void displaySubjectData() {
		List<Subject> subjects = dbUtils.getDatabaseHelper().getSubjectRuntimeExceptionDao().queryForAll();

		subjectLevelDetails = new SubjectLevelDetails(subjects, diffLevelList, quantityOfQuestionsList);
		subjectLevelTestListAdapter = new SubjectLevelTestListAdapter(CreateTestActivity.this, subjectLevelDetails);
		subjectLevelLV.setAdapter(subjectLevelTestListAdapter);

	}

	void displayTopicData(Subject subject) {
		try {
			List<Topic> topics = dbUtils.getDatabaseHelper().getTopicRuntimeExceptionDao().queryBuilder().where().eq("subject_id", subject.getIdSubjectMaster()).query();
			topicLevelDetails = new TopicLevelDetails(topics, diffLevelList, quantityOfQuestionsList);
			topicLevelTestListAdapter = new TopicLevelTestListAdapter(CreateTestActivity.this, topicLevelDetails);
			topicLevelLV.setAdapter(topicLevelTestListAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void getTestStarted(TestInfo testInfo) {
		Intent intent = new Intent(CreateTestActivity.this, TestQuestionActivity.class);
		intent.putExtra(IntentExtras.Id_Test_Log_Master, testInfo.getIdTestLogMaster());
		startActivity(intent);
		finish();
	}
}
