package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.sudosaints.cmavidya.api.ApiRequestHelper;
import com.sudosaints.cmavidya.api.ApiResponse;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.KeyNotes;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by inni on 25/7/14.
 */
public class DashboardActivity extends Activity {


	private UIHelper uiHelper;
	private CMAVidyaApp cmaVidyaApp;
	private DBUtils dbUtils;
	private Preferences preferences;
	private ProgressDialog progressDialog;
	private int updaateCount = 0;

	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			CommonTaskExecutor.logOutUser(cmaVidyaApp.getPreferences(), dbUtils.getDatabaseHelper());
			finish();
			startActivity(new Intent(DashboardActivity.this, SignInActivity.class));
		}
	};

	View.OnClickListener actionBarRightOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			updateDB();
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		uiHelper = new UIHelper(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		preferences = new Preferences(this);
		ButterKnife.bind(this);
		dbUtils = new DBUtils(DashboardActivity.this);
		uiHelper.setActionBar(Constants.ActivityABarAction.DASHBOARD, actionBarLeftOnClickListener, actionBarRightOnClickListener);


	}

	@Override
	protected void onResume() {
		super.onResume();
		RuntimeExceptionDao<KeyNotes, Integer> keyNotesDao = dbUtils.getDatabaseHelper().getKeyNotesRuntimeExceptionDao();
		final List<KeyNotes> keyNotesList = keyNotesDao.queryForAll();
		if (keyNotesList.size() == 0)
			updateDB();

	}

	@OnClick(R.id.dashboardPlannerButton)
	public void onPlannerButtonClick() {
		Intent intent = new Intent(DashboardActivity.this, PlannerActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.dashboardKeynotesButton)
	public void onKeynotesButtonClick() {
		Intent intent = new Intent(DashboardActivity.this, KeynotesMainActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.dashboardVideosButton)
	public void onVideoButtonClick() {

		Intent intent = new Intent(DashboardActivity.this, VideoListActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.dashboardForumButton)
	public void onForumButtonClick() {
		Intent intent = new Intent(DashboardActivity.this, MasterForumListActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.dashboardDownloadsButton)
	public void onDownloadButtonClick() {
		Intent intent = new Intent(DashboardActivity.this, ComingSoonActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.dashboardNotificationsButton)
	public void onNotificationButtonClick() {
		Intent intent = new Intent(DashboardActivity.this, ComingSoonActivity.class);
		startActivity(intent);
	}


	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("Closing CMAVidya")
				.setMessage("Are you sure you want to close app?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}

				})
				.setNegativeButton("No", null)
				.show();
	}

	@OnClick(R.id.dashboardTestSeriesButton)
	public void testSeriesOnClick() {

		Intent intent = new Intent(DashboardActivity.this, PreTestActivity.class);
		startActivity(intent);


	}

	private void updateDB() {
		progressDialog = new ProgressDialog(DashboardActivity.this);
		progressDialog.setMessage("Updating...");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		if (cmaVidyaApp.getApiRequestHelper().checkNetwork()) {
			updaateCount++;
			cmaVidyaApp.getApiRequestHelper().getSubjectReplanData(preferences.getUserEmail(), new ApiRequestHelper.OnRequestComplete() {
				@Override
				public void onSuccess(ApiResponse apiResponse) {
					CommonTaskExecutor.setSubjectReplanDataToDb(dbUtils, ((Map<String, Object>) apiResponse.getData()), new CommonTaskExecutor.OnPostExecute() {
						@Override
						public void onPostExecute(Object object) {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									updaateCount--;
									if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
										progressDialog.dismiss();
									}
								}
							});
						}
					});
				}

				@Override
				public void onFailure(ApiResponse apiResponse) {
				}
			});


			updaateCount++;
			cmaVidyaApp.getApiRequestHelper().getTopicReplanData(preferences.getUserEmail(), new ApiRequestHelper.OnRequestComplete() {
				@Override
				public void onSuccess(ApiResponse apiResponse) {
					if (apiResponse.isSuccess()) {
						CommonTaskExecutor.setTopicReplanDataToDb(dbUtils, (Map<String, Object>) apiResponse.getData(), new CommonTaskExecutor.OnPostExecute() {
							@Override
							public void onPostExecute(Object object) {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										updaateCount--;
										if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
											progressDialog.dismiss();
										}
									}
								});
							}
						});
					} else {
						updaateCount--;
						if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
							progressDialog.dismiss();
						}
					}

				}

				@Override
				public void onFailure(ApiResponse apiResponse) {
					updaateCount--;
					if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
						progressDialog.dismiss();
					}
				}
			});


			updaateCount++;
			CommonTaskExecutor.getMyPlanDetails(cmaVidyaApp, new CommonTaskExecutor.OnPostExecute() {
				@Override
				public void onPostExecute(Object object) {
					ApiResponse apiResponse = (ApiResponse) object;
					if (apiResponse.isSuccess()) {
						CommonTaskExecutor.setPlanEventsToDb(dbUtils, (List<Map<String, Object>>) apiResponse.getData(), new CommonTaskExecutor.OnPostExecute() {
							@Override
							public void onPostExecute(Object object) {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										updaateCount--;
										if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
											progressDialog.dismiss();
										}
									}
								});
							}
						});
					} else {
						cmaVidyaApp.showToast(apiResponse.getError().getMessage());
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								updaateCount--;
								if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
									progressDialog.dismiss();
								}
							}
						});
					}

				}
			}, preferences.getUserName());


			updaateCount++;
			CommonTaskExecutor.getKeyNotes(cmaVidyaApp, new CommonTaskExecutor.OnPostExecute() {
				@Override
				public void onPostExecute(Object object) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							updaateCount--;
							if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
								progressDialog.dismiss();
							}
						}
					});
				}
			}, dbUtils.getDatabaseHelper());
			updaateCount++;
			CommonTaskExecutor.fetchNStoreSubjectListForTest(dbUtils, cmaVidyaApp, preferences.getUserName(), new CommonTaskExecutor.OnPostExecute() {
				@Override
				public void onPostExecute(Object object) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							CommonTaskExecutor.fetchNStoreTopicListForTest(dbUtils, cmaVidyaApp, preferences.getUserName(), new CommonTaskExecutor.OnPostExecute() {
								@Override
								public void onPostExecute(Object object) {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											updaateCount--;
											if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
												progressDialog.dismiss();
											}
										}
									});
								}
							});
						}
					});
				}
			});

			updaateCount++;
			CommonTaskExecutor.getNUpdateAllPreviousTest(cmaVidyaApp, DashboardActivity.this, new CommonTaskExecutor.OnPostExecute() {
				@Override
				public void onPostExecute(Object object) {
					if (null == object) {
						//cmaVidyaApp.showToast("Fail to update PreviousTest Data");
					}
					updaateCount--;
					if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
						progressDialog.dismiss();
					}
				}
			});

			updaateCount++;
			CommonTaskExecutor.getNUpdateTemplateTest(cmaVidyaApp, DashboardActivity.this, new CommonTaskExecutor.OnPostExecute() {
				@Override
				public void onPostExecute(Object object) {
					if (null == object) {
						//cmaVidyaApp.showToast("Fail to update PreviousTest Data");
					}
					updaateCount--;
					if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
						progressDialog.dismiss();
					}
				}
			});

			updaateCount++;
			CommonTaskExecutor.getNUpdateFixedTest(cmaVidyaApp, DashboardActivity.this, new CommonTaskExecutor.OnPostExecute() {
				@Override
				public void onPostExecute(Object object) {
					if (null == object) {
						//cmaVidyaApp.showToast("Fail to update PreviousTest Data");
					}
					updaateCount--;
					if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
						progressDialog.dismiss();
					}
				}
			});

			updaateCount++;
			CommonTaskExecutor.updateMasterForumTopicsData(cmaVidyaApp, DashboardActivity.this, new CommonTaskExecutor.OnPostExecute() {
				@Override
				public void onPostExecute(final Object object) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Boolean aBoolean = (Boolean) object;
							if (!aBoolean) {
								//cmaVidyaApp.showToast("Fail to update FORUM Data");
							}
							updaateCount--;
							if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
								progressDialog.dismiss();
							}

						}
					});


				}
			});

			updaateCount++;
			CommonTaskExecutor.getUserVideos(cmaVidyaApp, DashboardActivity.this, new CommonTaskExecutor.OnPostExecute() {
				@Override
				public void onPostExecute(Object object) {
					updaateCount--;
					if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
						progressDialog.dismiss();
					}
				}
			});
		} else {
			if (null != progressDialog && progressDialog.isShowing() && updaateCount == 0) {
				progressDialog.dismiss();
			}
			cmaVidyaApp.showToast("No Active Internet Connection Detected");
		}


	}
}
