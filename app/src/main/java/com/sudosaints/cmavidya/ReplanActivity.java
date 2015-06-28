package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.sudosaints.cmavidya.adapter.DragNDropSubjectListAdapter;
import com.sudosaints.cmavidya.adapter.DragNDropTopicListAdapter;
import com.sudosaints.cmavidya.adapter.RevisionSelectionSpinnerAdapter;
import com.sudosaints.cmavidya.adapter.SubjectSelectionSpinnerAdapter;
import com.sudosaints.cmavidya.api.ApiRequestHelper;
import com.sudosaints.cmavidya.api.ApiResponse;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.dto.SubjectReplanDTO;
import com.sudosaints.cmavidya.dto.TopicReplanDTO;
import com.sudosaints.cmavidya.model.SubjectReplanData;
import com.sudosaints.cmavidya.model.TopicReplanData;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.UIHelper;
import com.sudosaints.cmavidya.views.DynamicSubjectListView;
import com.sudosaints.cmavidya.views.DynamicTopictListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by inni on 10/12/14.
 */
public class ReplanActivity extends Activity {

	@Bind(R.id.replanTabHost)
	TabHost replanTabHost;

	@Bind(R.id.subjectReplanList)
	DynamicSubjectListView subjectReplanListView;

	@Bind(R.id.subjectReplanRevisionNoSpinner)
	Spinner subjectReplanRevisionNoSpinner;

	@Bind(R.id.topicReplanList)
	DynamicTopictListView topicReplanList;

	@Bind(R.id.topicReplanSubjectSpinner)
	Spinner topicReplanSubjectSpinner;

	@Bind(R.id.topicReplanRevisionNoSpinner)
	Spinner topicReplanRevisionNoSpinner;

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

	private CMAVidyaApp cmaVidyaApp;
	private UIHelper uiHelper;
	private DBUtils dbUtils;
	private RevisionSelectionSpinnerAdapter subjectSelectionSpinnerAdapter, topicSelectionSpinnerAdapter;
	private SubjectSelectionSpinnerAdapter topicSubjectSelectionSpinnerAdapter;
	private List<Integer> subjectRevesionList, topicRevesionList;
	private List<String> topicSubjectsList;
	private ArrayList<SubjectReplanData> subjectReplanDatas = null;
	private ArrayList<TopicReplanData> topicReplanDatas = null;
	private boolean isSubjectReplanButton = true;
	private int IdUserPlannerInputsMaster = 0;
	private Preferences preferences;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_replan);
		ButterKnife.bind(this);
		uiHelper = new UIHelper(this);
		dbUtils = new DBUtils(this);
		uiHelper.setActionBar(Constants.ActivityABarAction.REPLAN, actionBarLeftOnClickListener, ActionBarRightOnClickListener);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		preferences = new Preferences(this);

		replanTabHost.setup();
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TabHost.TabSpec mapTabSpec1 = replanTabHost.newTabSpec("MAP1");
		Button tabButton = (Button) inflater.inflate(R.layout.tab_button_layout, null);
		tabButton.setText("Subject Replan");
		mapTabSpec1.setIndicator(tabButton);
		mapTabSpec1.setContent(R.id.subjectReplanTab);

		TabHost.TabSpec mapTabSpec2 = replanTabHost.newTabSpec("MAP2");
		tabButton = (Button) inflater.inflate(R.layout.tab_button_layout, null);
		tabButton.setText("Topic Replan");
		mapTabSpec2.setIndicator(tabButton);
		mapTabSpec2.setContent(R.id.topicReplanTab);

		replanTabHost.addTab(mapTabSpec1);
		replanTabHost.addTab(mapTabSpec2);

		replanTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String s) {
				isSubjectReplanButton = s.equalsIgnoreCase("MAP1") ? true : false;
			}
		});

		cmaVidyaApp.getApiRequestHelper().getUserPlannerInfo(preferences.getUserEmail(), new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				Map<String, Object> objectMap = (Map<String, Object>) apiResponse.getData();
				IdUserPlannerInputsMaster = (Integer.parseInt(objectMap.get("IdUserPlannerInputsMaster") + ""));
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {

			}
		});

		subjectRevesionList = CommonTaskExecutor.getSubjectRevesions(dbUtils);


		subjectSelectionSpinnerAdapter = new RevisionSelectionSpinnerAdapter(ReplanActivity.this, R.layout.spinner_row, subjectRevesionList);

		subjectReplanRevisionNoSpinner.setAdapter(subjectSelectionSpinnerAdapter);

		subjectReplanRevisionNoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				displaySubjectData(subjectRevesionList.get(i));
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		topicSubjectsList = CommonTaskExecutor.getTopicSubjects(dbUtils);
		topicSubjectSelectionSpinnerAdapter = new SubjectSelectionSpinnerAdapter(ReplanActivity.this, R.layout.spinner_row, topicSubjectsList);
		topicReplanSubjectSpinner.setAdapter(topicSubjectSelectionSpinnerAdapter);

		topicReplanSubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				setSpinnerTopicSubjectRevisionNo(topicSubjectsList.get(i));
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

	}

	public void setSpinnerTopicSubjectRevisionNo(final String subjectName) {
		topicRevesionList = CommonTaskExecutor.getTopicRevesions(dbUtils, subjectName);
		topicSelectionSpinnerAdapter = new RevisionSelectionSpinnerAdapter(ReplanActivity.this, R.layout.spinner_row, topicRevesionList);
		topicReplanRevisionNoSpinner.setAdapter(topicSelectionSpinnerAdapter);
		topicReplanRevisionNoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				displayTopicData(topicRevesionList.get(i), subjectName);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

	}

	private void displayTopicData(int revisionNo, String subjectName) {
		if (null != topicReplanDatas) {
			for (TopicReplanData topicReplanData : topicReplanDatas)
				dbUtils.getDatabaseHelper().getTopicReplanDataRuntimeExceptionDao().update(topicReplanData);
		}
		try {
			topicReplanDatas = (ArrayList) dbUtils.getDatabaseHelper().getTopicReplanDataRuntimeExceptionDao().queryBuilder().orderBy("IdIndex", true).where().eq("RevisionNo", "" + revisionNo).and().eq("Subject", subjectName).query();
			DragNDropTopicListAdapter dragNDropTopicListAdapter = new DragNDropTopicListAdapter(ReplanActivity.this, topicReplanDatas);

			topicReplanList.setCheeseList(topicReplanDatas);
			topicReplanList.setAdapter(dragNDropTopicListAdapter);
			topicReplanList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void displaySubjectData(int revisionNo) {
		if (null != subjectReplanDatas) {
			for (SubjectReplanData subjectReplanData : subjectReplanDatas)
				dbUtils.getDatabaseHelper().getSubjectReplanDataRuntimeExceptionDao().update(subjectReplanData);
		}
		try {
			subjectReplanDatas = (ArrayList) dbUtils.getDatabaseHelper().getSubjectReplanDataRuntimeExceptionDao().queryBuilder().orderBy("idIndex", true).where().eq("RevisionNo", "" + revisionNo).query();
			DragNDropSubjectListAdapter dragNDropSubjectListAdapter = new DragNDropSubjectListAdapter(ReplanActivity.this, subjectReplanDatas);

			subjectReplanListView.setCheeseList(subjectReplanDatas);
			subjectReplanListView.setAdapter(dragNDropSubjectListAdapter);
			subjectReplanListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@OnClick(R.id.replanButton)
	public void onClickReplan() {
		progressDialog = new ProgressDialog(ReplanActivity.this);
		progressDialog.setMessage("Updating...");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		if (isSubjectReplanButton) {
			if (null != subjectReplanDatas) {
				for (SubjectReplanData subjectReplanData : subjectReplanDatas)
					dbUtils.getDatabaseHelper().getSubjectReplanDataRuntimeExceptionDao().update(subjectReplanData);
			}
			try {
				subjectReplanDatas = (ArrayList<SubjectReplanData>) dbUtils.getDatabaseHelper().getSubjectReplanDataRuntimeExceptionDao().queryBuilder().orderBy("idIndex", true).query();
			} catch (Exception e) {
				e.printStackTrace();
			}
			SubjectReplanDTO subjectReplanDTO = new SubjectReplanDTO();
			subjectReplanDTO.setIdPlanner(IdUserPlannerInputsMaster);
			subjectReplanDTO.setoList(subjectReplanDatas);
			cmaVidyaApp.getApiRequestHelper().updateSubjectReplanData(subjectReplanDTO, new ApiRequestHelper.OnRequestComplete() {
				@Override
				public void onSuccess(ApiResponse apiResponse) {
					if (null != progressDialog && progressDialog.isShowing()) {
						progressDialog.dismiss();
					}

					Toast.makeText(ReplanActivity.this, "Updated", Toast.LENGTH_LONG).show();
					finish();
				}

				@Override
				public void onFailure(ApiResponse apiResponse) {
					if (null != progressDialog && progressDialog.isShowing()) {
						progressDialog.dismiss();
					}

					Toast.makeText(ReplanActivity.this, apiResponse.getError().getMessage(), Toast.LENGTH_LONG).show();

				}
			});
		} else {
			if (null != topicReplanDatas) {
				for (TopicReplanData topicReplanData : topicReplanDatas)
					dbUtils.getDatabaseHelper().getTopicReplanDataRuntimeExceptionDao().update(topicReplanData);
			}
			try {
				topicReplanDatas = (ArrayList<TopicReplanData>) dbUtils.getDatabaseHelper().getTopicReplanDataRuntimeExceptionDao().queryBuilder().orderBy("IdIndex", true).query();
			} catch (Exception e) {
				e.printStackTrace();
			}
			TopicReplanDTO topicReplanDTO = new TopicReplanDTO();
			topicReplanDTO.setIdPlanner(IdUserPlannerInputsMaster);
			topicReplanDTO.setoList(topicReplanDatas);
			cmaVidyaApp.getApiRequestHelper().updateTopicReplanData(topicReplanDTO, new ApiRequestHelper.OnRequestComplete() {
				@Override
				public void onSuccess(ApiResponse apiResponse) {
					if (null != progressDialog && progressDialog.isShowing()) {
						progressDialog.dismiss();
					}

					Toast.makeText(ReplanActivity.this, "Updated", Toast.LENGTH_LONG).show();
					finish();

				}

				@Override
				public void onFailure(ApiResponse apiResponse) {
					if (null != progressDialog && progressDialog.isShowing()) {
						progressDialog.dismiss();
					}

					Toast.makeText(ReplanActivity.this, apiResponse.getError().getMessage(), Toast.LENGTH_LONG).show();

				}
			});


		}
	}


}
