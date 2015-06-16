package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sudosaints.cmavidya.adapter.VideoInfoGridAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.VideoInfo;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by inni on 17/3/15.
 */
public class VideoListActivity extends Activity {

	@InjectView(R.id.videosGridView)
	GridView videosGridView;

	private UIHelper uiHelper;
	private CMAVidyaApp cmaVidyaApp;
	private DBUtils dbUtils;
	private Preferences preferences;
	private Activity activity;
	private ProgressDialog progressDialog;
	private List<VideoInfo> videoInfos;
	private VideoInfoGridAdapter videoInfoGridAdapter;

	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_video_list);
		ButterKnife.inject(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		uiHelper = new UIHelper(this);
		preferences = new Preferences(activity);
		uiHelper.setActionBar(Constants.ActivityABarAction.VIDEO, actionBarLeftOnClickListener, null);
		displayVideoGrid();
	}

	private void displayVideoGrid() {
		videoInfos = dbUtils.getDatabaseHelper().getVideoInfoRuntimeExceptionDao().queryForAll();
		if (videoInfos.size() == 0) {
			cmaVidyaApp.showToast("No videos for this account");
		}
		videoInfoGridAdapter = new VideoInfoGridAdapter(activity, videoInfos);
		videosGridView.setAdapter(videoInfoGridAdapter);

		videosGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(activity, VideoViewActivity.class);
				intent.putExtra(IntentExtras.MEDIA_URL, videoInfos.get(position).getFilePath());

				startActivity(intent);
			}
		});
	}

}
