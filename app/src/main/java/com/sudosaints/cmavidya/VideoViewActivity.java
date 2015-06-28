package com.sudosaints.cmavidya;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sudosaints.cmavidya.util.IntentExtras;

import butterknife.ButterKnife;
import butterknife.Bind;

public class VideoViewActivity extends Activity {

	@Bind(R.id.videoViewWV)
	WebView web;

	private String videoUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_video_view);
		ButterKnife.bind(this);

		if (getIntent().hasExtra(IntentExtras.MEDIA_URL)) {
			videoUrl = getIntent().getStringExtra(IntentExtras.MEDIA_URL);
		} else {
			finish();
		}

		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
		web.getSettings().setSupportMultipleWindows(false);
		web.getSettings().setSupportZoom(false);
		web.setVerticalScrollBarEnabled(false);
		web.setHorizontalScrollBarEnabled(false);
		web.loadUrl(videoUrl);

		web.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.equalsIgnoreCase(videoUrl)) {
					return false;
				} else {
					return true;
				}
			}
		});

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (null != web && android.os.Build.VERSION.SDK_INT >= 11) {
			web.onPause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (null != web && android.os.Build.VERSION.SDK_INT >= 11) {
			web.onResume();
		}

	}
}