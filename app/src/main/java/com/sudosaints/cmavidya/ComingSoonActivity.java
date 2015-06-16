package com.sudosaints.cmavidya;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.UIHelper;

import butterknife.ButterKnife;

/**
 * Created by inni on 12/1/15.
 */
public class ComingSoonActivity extends Activity {
	private UIHelper uiHelper;
	private CMAVidyaApp cmaVidyaApp;
	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coming_soon);
		ButterKnife.inject(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		uiHelper = new UIHelper(this);
		uiHelper.setActionBar(Constants.ActivityABarAction.Test_SERIES, actionBarLeftOnClickListener, null);
	}
}
