package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.sudosaints.cmavidya.adapter.PlannerListAdapter;
import com.sudosaints.cmavidya.api.ApiResponse;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.dto.UserEventDTO;
import com.sudosaints.cmavidya.model.PlanEvents;
import com.sudosaints.cmavidya.model.PlannerItem;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.DateHelper;
import com.sudosaints.cmavidya.util.UIHelper;
import com.sudosaints.cmavidya.views.CustomPopupMenu;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by inni on 25/7/14.
 */
public class PlannerActivity extends Activity {

	@Bind(R.id.plannerDateListView)
	ListView plannerListView;
	@Bind(R.id.plannerDateRelativeLayout)
	RelativeLayout plannerDateRelativeLayout;
	@Bind(R.id.plannerDateTextView)
	TextView dateTextView;
	@Bind(R.id.plannerDateMnYTextView)
	TextView monthNYearTextView;


	private PlannerListAdapter plannerListAdapter;
	private List<PlannerItem> plannerItemList;
	private CustomPopupMenu customPopupMenu;
	private CMAVidyaApp cmaVidyaApp;
	private Preferences preferences;
	private DBUtils dbUtils;
	private Calendar cal;
	private Date date;
	private DateFormat ddFormat = new SimpleDateFormat("dd");
	private DateFormat mmmyyFormat = new SimpleDateFormat("MMMM yyyy");
	private ProgressDialog progressDialog;
	private String userName;

	UIHelper uiHelper;
	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};
	View.OnClickListener actionBarRightOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if (customPopupMenu.isShowing()) {
				customPopupMenu.dismiss();
				//plannerDateRelativeLayout.setVisibility(View.VISIBLE);
			} else {
				customPopupMenu.show();
				//plannerDateRelativeLayout.setVisibility(View.GONE);
			}
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		if (null != customPopupMenu && customPopupMenu.isShowing()) {
			customPopupMenu.dismiss();
			//plannerDateRelativeLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onBackPressed() {
		if (customPopupMenu.isShowing()) {
			customPopupMenu.dismiss();
			//plannerDateRelativeLayout.setVisibility(View.VISIBLE);
		} else
			super.onBackPressed();
	}

	@OnClick(R.id.plannerDatePreImageView)
	public void onClickPreviousDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		date = cal.getTime();
		plannerListView.setAdapter(null);
		displayContentsDayWise();

	}

	@OnClick(R.id.plannerDateNxtImageView)
	public void onClickNextDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1
		);
		date = cal.getTime();
		plannerListView.setAdapter(null);
		displayContentsDayWise();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planner_date_viewlist);
		uiHelper = new UIHelper(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		preferences = cmaVidyaApp.getPreferences();
		dbUtils = new DBUtils(this);
		ButterKnife.bind(this);
		date = new Date();
		cal = Calendar.getInstance();
		date = cal.getTime();
		uiHelper.setActionBar(Constants.ActivityABarAction.PLANNER_MAIN, actionBarLeftOnClickListener, actionBarRightOnClickListener);

		if (getIntent().hasExtra(Constants.INTENT_MILLS)) {
			date.setTime(getIntent().getLongExtra(Constants.INTENT_MILLS, 0));
		}

		displayContentsDayWise();


		//customPopupMenu = new CustomPopupMenu(PlannerActivity.this, uiHelper.mRightImageView);


		plannerDateRelativeLayout.post(new Runnable() {
			@Override
			public void run() {
				customPopupMenu = new CustomPopupMenu(PlannerActivity.this, uiHelper.mRightImageView, LinearLayout.LayoutParams.WRAP_CONTENT, Constants.CustomPopUpMenuOption.PLANNER_OPTION);
			}
		});
		userName = cmaVidyaApp.getPreferences().getUserName();

	}

	private void showHourDialog(final String type, final PlanEvents planEvent) {


		final Dialog dialog = new Dialog(PlannerActivity.this);
		dialog.setTitle("Select " + type + " Time");
		dialog.setContentView(R.layout.hour_picker);

		final NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.numberPicker);
		numberPicker.setMaxValue(99);
		numberPicker.setMinValue(1);

		Button okButton = (Button) dialog.findViewById(R.id.okHourPickerButton);
		Button cancelButton = (Button) dialog.findViewById(R.id.cancelHourPickerButton);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				progressDialog = new ProgressDialog(PlannerActivity.this);
				progressDialog.setMessage("Updating...");
				progressDialog.setCancelable(false);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.show();
				final int hrs = numberPicker.getValue();
				CommonTaskExecutor.postPrePonePostPonePlan(cmaVidyaApp, userName, type, hrs, planEvent, new CommonTaskExecutor.OnPostExecute() {
					@Override
					public void onPostExecute(Object object) {
						ApiResponse apiResponse = (ApiResponse) object;

						if (apiResponse.isSuccess()) {
							cmaVidyaApp.showToast(type + " by " + hrs + " hrs");
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
														if (null != progressDialog && progressDialog.isShowing()) {
															progressDialog.dismiss();
														}

														displayContentsDayWise();
													}
												});
											}
										});

									} else {
										cmaVidyaApp.showToast(apiResponse.getError().getMessage());
										if (null != progressDialog && progressDialog.isShowing()) {
											progressDialog.dismiss();
										}
									}


								}
							}, preferences.getUserName());


						} else {
							cmaVidyaApp.showToast(apiResponse.getError().getMessage());
							if (null != progressDialog && progressDialog.isShowing()) {
								progressDialog.dismiss();
							}
						}

					}
				});
				dialog.dismiss();
			}


		});
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});

		dialog.show();


	}

	private synchronized void displayContentsDayWise() {
		dateTextView.setText(ddFormat.format(date));
		monthNYearTextView.setText(mmmyyFormat.format(date));

		Date dateSart = new Date(date.getTime());
		Date dateEnd = new Date(date.getTime());
		List<PlanEvents> eventsList = null;

		DateHelper.setToStartOfDay(dateSart);
		DateHelper.setToEndOfDay(dateEnd);

		long startMills, endMills;
		startMills = dateSart.getTime();
		endMills = dateEnd.getTime();
		plannerListView.setAdapter(null);
		try {
			QueryBuilder<PlanEvents, Long> eventsQueryBuilder = dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().queryBuilder();
			eventsQueryBuilder.where().between("topicDate", (startMills - 1000l), endMills);
			PreparedQuery<PlanEvents> preparedQuery = eventsQueryBuilder.prepare();
			eventsList = dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().query(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (null != eventsList && eventsList.size() > 0) {

			plannerListAdapter = new PlannerListAdapter(this, eventsList, new PlannerListAdapter.PlannerListOptionOnClickListener() {
				@Override
				public void longClick(final PlanEvents planEvent) {
					if (planEvent.getDescription().equalsIgnoreCase("UserDefinedEvents")) {
						final Dialog dialog = new Dialog(PlannerActivity.this);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.popup_user_event_deletion);
						dialog.setCancelable(true);
						TextView delTextView = (TextView) dialog.findViewById(R.id.delUserEvenTV);
						dialog.show();
						delTextView.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								progressDialog = new ProgressDialog(PlannerActivity.this);
								progressDialog.setMessage("Deleting...");
								progressDialog.setCancelable(false);
								progressDialog.setCanceledOnTouchOutside(false);
								progressDialog.show();
								UserEventDTO userEventDTO = new UserEventDTO();
								userEventDTO.setEventName(planEvent.getTopicName());
								userEventDTO.setUserName(preferences.getUserName());
								userEventDTO.setStrPlanDate(DateHelper.getDMYFormattedDate(new Date(planEvent.getTopicDate())));
								userEventDTO.setActive(false);
								userEventDTO.setIdUserEvents(planEvent.getIdTopic());
								CommonTaskExecutor.createDeleteUserEvents(cmaVidyaApp, userEventDTO, new CommonTaskExecutor.OnPostExecute() {
									@Override
									public void onPostExecute(Object object) {
										if (null != object) {
											cmaVidyaApp.showToast("Event Deleted successful \nUpdate DataBase to reflect changes ");
										}
										if (null != progressDialog && progressDialog.isShowing()) {
											progressDialog.dismiss();
										}

									}
								});
							}
						});


					} else {

						final Dialog dialog = new Dialog(PlannerActivity.this);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.plan_event_option_popup_layout);
						dialog.setCancelable(true);

						TextView prePond, postPond, pause, rePlan, postponeByDay;
						prePond = (TextView) dialog.findViewById(R.id.prepondPlanTV);
						postPond = (TextView) dialog.findViewById(R.id.postpondPlanTV);
						pause = (TextView) dialog.findViewById(R.id.pausePlanTV);
						rePlan = (TextView) dialog.findViewById(R.id.replanPlanTV);
						postponeByDay = (TextView) dialog.findViewById(R.id.postponeByDayPlanTV);

						prePond.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								showHourDialog("PrePone", planEvent);
								dialog.dismiss();
							}
						});
						postPond.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								showHourDialog("PostPone", planEvent);
								dialog.dismiss();
							}
						});
						pause.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								showHourDialog("Pause", planEvent);
								dialog.dismiss();

							}
						});
						rePlan.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								Intent intent = new Intent(PlannerActivity.this, ReplanActivity.class);
								startActivity(intent);
								dialog.dismiss();

							}
						});

						postponeByDay.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								progressDialog = new ProgressDialog(PlannerActivity.this);
								progressDialog.setMessage("Updating...");
								progressDialog.setCancelable(false);
								progressDialog.setCanceledOnTouchOutside(false);
								progressDialog.show();
								CommonTaskExecutor.postponeByDay(cmaVidyaApp, userName, planEvent.getIdPlannerOutPutDateWiseTime(), new CommonTaskExecutor.OnPostExecute() {
									@Override
									public void onPostExecute(Object object) {
										CommonTaskExecutor.getMyPlanDetails(cmaVidyaApp, new CommonTaskExecutor.OnPostExecute() {
											@Override
											public void onPostExecute(Object object) {
												ApiResponse apiResponse = (ApiResponse) object;
												if (apiResponse.isSuccess()) {
													cmaVidyaApp.showToast("PostPoned By 1 day !!!!!!");

													CommonTaskExecutor.setPlanEventsToDb(dbUtils, (List<Map<String, Object>>) apiResponse.getData(), new CommonTaskExecutor.OnPostExecute() {
														@Override
														public void onPostExecute(Object object) {
															runOnUiThread(new Runnable() {
																@Override
																public void run() {
																	if (null != progressDialog && progressDialog.isShowing()) {
																		progressDialog.dismiss();
																	}
																	displayContentsDayWise();
																}
															});
														}
													});

												} else {
													cmaVidyaApp.showToast(apiResponse.getError().getMessage());
													if (null != progressDialog && progressDialog.isShowing()) {
														progressDialog.dismiss();
													}
												}

											}
										}, preferences.getUserName());
									}
								});
								dialog.dismiss();
							}
						});

						dialog.show();
					}
				}

			});

			plannerListView.setAdapter(plannerListAdapter);


		}


	}

}
