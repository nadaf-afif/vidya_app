package com.sudosaints.cmavidya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sudosaints.cmavidya.adapter.ThreadDisplayListAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.ForumThread;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by inni on 17/1/15.
 */
public class ThreadListActivity extends Activity {
	private CMAVidyaApp cmaVidyaApp;
	private DBUtils dbUtils;
	private UIHelper uiHelper;
	private long threadId;
	private Activity activity;
	private List<ForumThread> forumThreads;
	private ThreadDisplayListAdapter threadDisplayListAdapter;
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
			CommonTaskExecutor.clodeThread(cmaVidyaApp, activity, threadId, new CommonTaskExecutor.OnPostExecute() {
				@Override
				public void onPostExecute(Object object) {
					cmaVidyaApp.showToast(object + "");
					finish();
				}
			});

		}
	};

	@Bind(R.id.tdThreadName)
	TextView threadName;

	@Bind(R.id.tdTopicName)
	TextView topicName;

	@Bind(R.id.tdListView)
	ListView threadListView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().hasExtra(IntentExtras.THREAD_ID)) {
			threadId = getIntent().getLongExtra(IntentExtras.THREAD_ID, 0);

		} else {
			finish();
		}

		setContentView(R.layout.activity_thread_display);
		ButterKnife.bind(this);
		activity = this;
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		uiHelper = new UIHelper(this);
		preferences = new Preferences(activity);
		uiHelper.setActionBar(Constants.ActivityABarAction.LIST_THREAD, actionBarLeftOnClickListener, actionBarRightOnClickListener);


		dbUtils = new DBUtils(activity);
		CommonTaskExecutor.getForumThreadsData(cmaVidyaApp, threadId, new CommonTaskExecutor.OnPostExecute() {
			@Override
			public void onPostExecute(Object object) {
				if (null != object) {
					forumThreads = (List<ForumThread>) object;
					getData();
				}
			}
		});
	}

	private void getData() {
		threadDisplayListAdapter = new ThreadDisplayListAdapter(activity, forumThreads);
		threadListView.setAdapter(threadDisplayListAdapter);
		threadListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				if (preferences.getUserEmail().equalsIgnoreCase(forumThreads.get(position).getAddedBy())) {
					Intent intent = new Intent(activity, NewThreadActivity.class);
					intent.putExtra(IntentExtras.THREAD, forumThreads.get(position));
					startActivity(intent);
				} else {
					cmaVidyaApp.showToast("you cannot edit this post");
				}


				return true;

			}
		});

		if (forumThreads.size() > 0) {
			topicName.setText(forumThreads.get(0).getForumTitle());
			threadName.setText(forumThreads.get(0).getTitle());
		}
	}

	@OnClick(R.id.tdPostReply)
	public void onClickPostReply() {
		if (forumThreads.size() > 0) {
			if (!forumThreads.get(0).isClosed()) {
				cmaVidyaApp.showToast("Coming Soon");
			} else {
				cmaVidyaApp.showToast("Thread Closed");
			}
		}
	}

}
