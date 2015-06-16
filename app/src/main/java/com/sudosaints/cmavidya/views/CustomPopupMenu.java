package com.sudosaints.cmavidya.views;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sudosaints.cmavidya.CMAVidyaApp;
import com.sudosaints.cmavidya.CalendarViewActivity;
import com.sudosaints.cmavidya.ComingSoonActivity;
import com.sudosaints.cmavidya.Preferences;
import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.dto.UserEventDTO;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.DateHelper;

import java.util.Calendar;

/**
 * Created by inni on 25/7/14.
 */
public class CustomPopupMenu {

	private Activity activity;
	private View anchor;
	private PopupWindow popupWindow;

    /*public int getViewHight() {
		return viewHight;
    }

    public void setViewHight(int viewHight) {
        this.viewHight = viewHight;
    }

    private int viewHight;*/

	public CustomPopupMenu(final Activity activity, View anchor, int popupHeight, Constants.CustomPopUpMenuOption popUpMenuOption) {
		this.activity = activity;
		this.anchor = anchor;

		try {

			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			switch (popUpMenuOption) {
				case PLANNER_OPTION:


					View popupMenuLayout = inflater.inflate(R.layout.planner_option_button_layout, null);
					TextView monthViewButton = (TextView) popupMenuLayout.findViewById(R.id.plannerCalenderYearTextView);
					TextView scannerButton = (TextView) popupMenuLayout.findViewById(R.id.plannerScannerTextView);
					TextView addCustumPlan = (TextView) popupMenuLayout.findViewById(R.id.plannerAddCustomEvenTextView);
					TextView myPlanEvent = (TextView) popupMenuLayout.findViewById(R.id.plannerMyPlanInputTextView);

					myPlanEvent.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(activity, ComingSoonActivity.class);
							activity.startActivity(intent);
						}
					});

					addCustumPlan.setOnClickListener(new View.OnClickListener() {
						ProgressDialog progressDialog = null;

						@Override
						public void onClick(View v) {
							dismiss();
							final Dialog dialog = new Dialog(activity);
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.popup_create_user_event);
							dialog.setCancelable(true);
							final EditText eventEditText = (EditText) dialog.findViewById(R.id.eventTitleET);
							Button addButton = (Button) dialog.findViewById(R.id.addEventBtn);
							addButton.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									dialog.dismiss();
									if (!TextUtils.isEmpty(eventEditText.getText().toString())) {
										progressDialog = new ProgressDialog(activity);
										progressDialog.setMessage("Updating...");
										progressDialog.setCancelable(false);
										progressDialog.setCanceledOnTouchOutside(false);
										progressDialog.show();
										final CMAVidyaApp cmaVidyaApp = (CMAVidyaApp) activity.getApplication();
										Preferences preferences = new Preferences(activity);
										UserEventDTO userEventDTO = new UserEventDTO();
										userEventDTO.setActive(true);
										userEventDTO.setUserName(preferences.getUserName());
										userEventDTO.setEventName(eventEditText.getText().toString());
										userEventDTO.setStrPlanDate(DateHelper.getDMYFormattedDate((Calendar.getInstance().getTime())));
										CommonTaskExecutor.createDeleteUserEvents(cmaVidyaApp, userEventDTO, new CommonTaskExecutor.OnPostExecute() {
											@Override
											public void onPostExecute(Object object) {
												if (null != object) {
													cmaVidyaApp.showToast("Event Added\nUpdate data to reflect data");
												}
												if (null != progressDialog && progressDialog.isShowing()) {
													progressDialog.dismiss();
												}
											}
										});
									} else {
										Toast.makeText(activity, "Enter Event Title", Toast.LENGTH_LONG).show();
									}
								}
							});
							dialog.show();
						}
					});
					scannerButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dismiss();
							Intent intent = new Intent(activity, ComingSoonActivity.class);
							activity.startActivity(intent);

						}
					});
					monthViewButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							dismiss();
							Intent intent = new Intent(activity, CalendarViewActivity.class);
							activity.startActivity(intent);
							activity.finish();
						}
					});
					popupWindow = new PopupWindow(activity);
					popupWindow.setContentView(popupMenuLayout);
					//Get Screen Width
					WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
					DisplayMetrics displayMetrics = new DisplayMetrics();
					windowManager.getDefaultDisplay().getMetrics(displayMetrics);
					//viewHight = (int) (displayMetrics.widthPixels);
					popupWindow.setWidth(LayoutParams.MATCH_PARENT);
					popupWindow.setHeight(popupHeight);
					popupWindow.setBackgroundDrawable(null);
					break;
				case SEARCH:

					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isShowing() {
		return popupWindow.isShowing();
	}

	public void show() {
		if (null != popupWindow)
			popupWindow.showAsDropDown(anchor, 0, 9);
	}

	public void dismiss() {
		if (null != popupWindow)
			popupWindow.dismiss();
	}
}