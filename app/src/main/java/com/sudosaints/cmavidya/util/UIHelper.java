package com.sudosaints.cmavidya.util;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.util.Constants.ActivityABarAction;


/**
 * Created by inni on 25/7/14.
 */
public class UIHelper {


	private Activity activity;
	private ActionBar mActionBar;
	public ImageView mRightImageView;

	public UIHelper(Activity activity) {
		this.activity = activity;
	}

	public void setActionBar(ActivityABarAction activityABarAction, View.OnClickListener actionBarLeftOnClickListener, View.OnClickListener actionBarRightOnClickListener) {
		mActionBar = activity.getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(activity);
		View mCustomView = mInflater.inflate(R.layout.action_bar, null);
		TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.actionBarHeadlineTextView);
		ImageView mLeftImageView = (ImageView) mCustomView.findViewById(R.id.actionBarLeftImageView);
		mRightImageView = (ImageView) mCustomView.findViewById(R.id.actionBarRightImageView);

		switch (activityABarAction) {
			case LANDING:
				break;
			case GUEST_USER:
				mTitleTextView.setText("Guest User");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case REGISTER:
				mTitleTextView.setText("Register");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case DASHBOARD:
				mTitleTextView.setText("Dashboard");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.logout_icon);
				mRightImageView.setImageResource(R.drawable.updates);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case PLANNER_MAIN:
				mTitleTextView.setText("Planner");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(R.drawable.planner_action_bar_option);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case PLANNER_ITEM:
				mTitleTextView.setText("Planner");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case PLANNER_CALENDER:
				mTitleTextView.setText("Planner");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case KEYNOTE_DISPLAY:
				mTitleTextView.setText("Keynotes");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(R.drawable.planner_action_bar_option);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case KEYNOTES_MAIN:
				mTitleTextView.setText("Keynotes");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(R.drawable.planner_action_bar_option);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case KEYNOTES_TOPICS:
				mTitleTextView.setText("Keynotes");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case KEYNOTES_ADD_NOTE:
				mTitleTextView.setText("Keynotes");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;

			case PDF:
				mTitleTextView.setText("PDF");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;

			case TEST_CREATE:
				mTitleTextView.setText("Test create");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case Test_SERIES:
				mTitleTextView.setText("Test series");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case COMING_SOON:
				mTitleTextView.setText("Coming Soon...");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case REPLAN:
				mTitleTextView.setText("Replan");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case FORUM:
				mTitleTextView.setText("Forum");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case FORUM_THREAD:
				mTitleTextView.setText("Forum Threads");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(R.drawable.small_plus_icon);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case NEW_THREAD:
				mTitleTextView.setText("Thread");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case LIST_THREAD:
				mTitleTextView.setText("Thread");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(R.drawable.cancel);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case ANALYSIS:
				mTitleTextView.setText("Analysis");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
			case VIDEO:
				mTitleTextView.setText("Video");
				mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
				mRightImageView.setOnClickListener(actionBarRightOnClickListener);
				mLeftImageView.setImageResource(R.drawable.back);
				mRightImageView.setImageResource(0);
				mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				break;
            case NOTIFICATION:
                mTitleTextView.setText("Notification");
                mLeftImageView.setOnClickListener(actionBarLeftOnClickListener);
                mRightImageView.setOnClickListener(actionBarRightOnClickListener);
                mLeftImageView.setImageResource(R.drawable.back);
                mRightImageView.setImageResource(0);
                mActionBar.setBackgroundDrawable(new ColorDrawable(0xFF0064AF));
                mActionBar.setCustomView(mCustomView);
                mActionBar.setDisplayShowCustomEnabled(true);
                break;


		}


	}
}
