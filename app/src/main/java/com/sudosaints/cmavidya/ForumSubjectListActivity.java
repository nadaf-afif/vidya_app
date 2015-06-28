package com.sudosaints.cmavidya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sudosaints.cmavidya.adapter.ForumSubjectListAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.ForumSubject;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by inni on 13/1/15.
 */
public class ForumSubjectListActivity extends Activity {

	private CMAVidyaApp cmaVidyaApp;
	private List<ForumSubject> forumSubjects;
	private ForumSubjectListAdapter forumSubjectListAdapter;
	private DBUtils dbUtils;
	private UIHelper uiHelper;
	private Activity activity;
	private int masterForumId = 0;
	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};


	@Bind(R.id.forumTopicLV)
	ListView forumTopicLV;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;

		if (getIntent().hasExtra(IntentExtras.MASTER_FORUM_ID)) {
			masterForumId = getIntent().getIntExtra(IntentExtras.MASTER_FORUM_ID, 0);

		} else {
			finish();
		}


		setContentView(R.layout.activity_forum_list);
		ButterKnife.bind(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		uiHelper = new UIHelper(this);
		uiHelper.setActionBar(Constants.ActivityABarAction.FORUM, actionBarLeftOnClickListener, null);


		dbUtils = new DBUtils(ForumSubjectListActivity.this);
		showList();
	}

	private void showList() {
		try {
			forumSubjects = dbUtils.getDatabaseHelper().getForumSubjectRuntimeExceptionDao().queryBuilder().where().eq("forumMasterID", masterForumId).query();

		} catch (Exception e) {
			e.printStackTrace();
		}
		forumSubjectListAdapter = new ForumSubjectListAdapter(activity, forumSubjects);
		forumTopicLV.setAdapter(forumSubjectListAdapter);
		forumTopicLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				forumSubjects.get(position).getId();

				Intent intent = new Intent(ForumSubjectListActivity.this, ForumListActivity.class);
				intent.putExtra(IntentExtras.SUBJECT_FORUM_ID, forumSubjects.get(position).getId());
				startActivity(intent);
			}
		});


	}

}
