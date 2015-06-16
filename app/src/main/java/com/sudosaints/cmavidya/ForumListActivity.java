package com.sudosaints.cmavidya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sudosaints.cmavidya.adapter.ForumTopicListAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.ForumTopic;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by inni on 13/1/15.
 */
public class ForumListActivity extends Activity {

	private CMAVidyaApp cmaVidyaApp;
	private List<ForumTopic> forumTopics;
	private ForumTopicListAdapter forumTopicListAdapter;
	private DBUtils dbUtils;
	private UIHelper uiHelper;
	private long subjectForumId = 0;
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

		if (getIntent().hasExtra(IntentExtras.SUBJECT_FORUM_ID)) {
			subjectForumId = getIntent().getLongExtra(IntentExtras.SUBJECT_FORUM_ID, 0);

		} else {
			finish();
		}


		setContentView(R.layout.activity_forum_list);
		ButterKnife.inject(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		uiHelper = new UIHelper(this);
		uiHelper.setActionBar(Constants.ActivityABarAction.FORUM, actionBarLeftOnClickListener, null);


		dbUtils = new DBUtils(ForumListActivity.this);
		showList();
	}

	private void showList() {
		try {
			forumTopics = dbUtils.getDatabaseHelper().getForumTopicRuntimeExceptionDao().queryBuilder().where().eq("forumMasterID", subjectForumId).query();

		} catch (Exception e) {
			e.printStackTrace();
		}
		forumTopicListAdapter = new ForumTopicListAdapter(ForumListActivity.this, forumTopics);
		forumTopicLV.setAdapter(forumTopicListAdapter);
		forumTopicLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				forumTopics.get(position).getId();

				Intent intent = new Intent(ForumListActivity.this, ForumThreadListActivity.class);
				intent.putExtra(IntentExtras.FORUM_ID, forumTopics.get(position).getId());
				startActivity(intent);
			}
		});


	}

}
