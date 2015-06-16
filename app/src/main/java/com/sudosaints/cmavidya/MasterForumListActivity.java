package com.sudosaints.cmavidya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sudosaints.cmavidya.adapter.MasterForumTopicListAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.MasterForumTopic;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by inni on 13/1/15.
 */
public class MasterForumListActivity extends Activity {

	private CMAVidyaApp cmaVidyaApp;
	private List<MasterForumTopic> masterForumTopics;
	private MasterForumTopicListAdapter masterForumTopicListAdapter;
	private DBUtils dbUtils;
	private UIHelper uiHelper;

	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};


	@InjectView(R.id.forumTopicLV)
	ListView forumTopicLV;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forum_list);
		ButterKnife.inject(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		uiHelper = new UIHelper(this);
		uiHelper.setActionBar(Constants.ActivityABarAction.FORUM, actionBarLeftOnClickListener, null);

		dbUtils = new DBUtils(MasterForumListActivity.this);
		showList();
	}

	private void showList() {

		masterForumTopics = dbUtils.getDatabaseHelper().getMasterForumTopicRuntimeExceptionDao().queryForAll();
		masterForumTopicListAdapter = new MasterForumTopicListAdapter(MasterForumListActivity.this, masterForumTopics);
		forumTopicLV.setAdapter(masterForumTopicListAdapter);
		forumTopicLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent intent = new Intent(MasterForumListActivity.this, ForumSubjectListActivity.class);
				intent.putExtra(IntentExtras.MASTER_FORUM_ID, masterForumTopics.get(position).getForumMasterID());
				startActivity(intent);
				finish();
			}
		});


	}

}
