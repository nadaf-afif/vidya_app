package com.sudosaints.cmavidya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sudosaints.cmavidya.adapter.ForumThreadListAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.ForumThread;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by inni on 24/2/15.
 */
public class ForumThreadListActivity extends Activity {

	private CMAVidyaApp cmaVidyaApp;
	private List<ForumThread> forumThreads;
	private ForumThreadListAdapter forumThreadListAdapter;
	private DBUtils dbUtils;
	private UIHelper uiHelper;
	private int forumId = 0;
	private Activity activity;
	private Preferences preferences;

	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};

	View.OnClickListener actionBarRightOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(activity, NewThreadActivity.class);
			intent.putExtra(IntentExtras.FORUM_ID, forumId);
			startActivity(intent);
		}
	};


	@Bind(R.id.forumThreadLV)
	ListView forumThreadLV;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		if (getIntent().hasExtra(IntentExtras.FORUM_ID)) {
			forumId = getIntent().getIntExtra(IntentExtras.FORUM_ID, 0);

		} else {
			finish();
		}
		setContentView(R.layout.activity_forum_thread_list);

		ButterKnife.bind(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		uiHelper = new UIHelper(this);
		preferences = new Preferences(activity);
		uiHelper.setActionBar(Constants.ActivityABarAction.FORUM_THREAD, actionBarLeftOnClickListener, actionBarRightOnClickListener);


		dbUtils = new DBUtils(activity);
		showList();
	}

	private void showList() {
		try {
			forumThreads = dbUtils.getDatabaseHelper().getForumThreadRuntimeExceptionDao().queryBuilder().where().eq("ForumID", forumId).query();

		} catch (Exception e) {
			e.printStackTrace();
		}
		forumThreadListAdapter = new ForumThreadListAdapter(activity, forumThreads);
		forumThreadLV.setAdapter(forumThreadListAdapter);
		forumThreadLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


				Intent intent = new Intent(activity, ThreadListActivity.class);
				intent.putExtra(IntentExtras.THREAD_ID, forumThreads.get(position).getId());
				startActivity(intent);
			}
		});




	}

}
