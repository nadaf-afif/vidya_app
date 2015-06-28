package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.dto.CreateNewThreadDTO;
import com.sudosaints.cmavidya.dto.UpdateThreadDTO;
import com.sudosaints.cmavidya.model.ForumThread;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.NanoHTTPD;
import com.sudosaints.cmavidya.util.UIHelper;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by inni on 27/2/15.
 */
public class NewThreadActivity extends Activity {

	private static final String MIME_JAVASCRIPT = "text/javascript";
	private static final String MIME_CSS = "text/css";
	private static final String MIME_JPEG = "image/jpeg";
	private static final String MIME_PNG = "image/png";
	private static final String MIME_SVG = "image/svg+xml";
	private static final String MIME_JSON = "application/json",
			MIME_XML = "text/xml",
			MIME_FONT_WOFF = "font/x-woff", MIME_FONT_EOT = "application/vnd.ms-fontobject",
			MIME_FONT_TTF = "application/octet-stream", MIME_FONT_SVG = "image/svg+xml",
			MIME_ICON = "image/x-icon";
	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};
	@Bind(R.id.newThreadTitleET)
	EditText title;
	@Bind(R.id.newThreadBodyET)
	WebView body;
	@Bind(R.id.newThreadcheckBox)
	CheckBox isColsedCB;
	@Bind(R.id.newThreadCreate)
	TextView createThread;
	private CMAVidyaApp cmaVidyaApp;
	private DBUtils dbUtils;
	private UIHelper uiHelper;
	private Preferences preferences;
	private int forumId = 0;
	private Activity activity;
	private ProgressDialog progressDialog;
	private ForumThread forumThread;
	private WebServer server;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;

		setContentView(R.layout.activity_create_new_thread);

		ButterKnife.bind(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		preferences = new Preferences(this);
		uiHelper = new UIHelper(this);
		activity = this;
		uiHelper.setActionBar(Constants.ActivityABarAction.NEW_THREAD, actionBarLeftOnClickListener, null);
		if (getIntent().hasExtra(IntentExtras.FORUM_ID)) {
			forumId = getIntent().getIntExtra(IntentExtras.FORUM_ID, 0);

		} else if (getIntent().hasExtra(IntentExtras.THREAD)) {
			forumThread = (ForumThread) getIntent().getSerializableExtra(IntentExtras.THREAD);
			displayThread();
		} else {
			finish();
		}

		server = new WebServer(8080);
		try {
			server.start();
		} catch (IOException ioe) {
			Log.w("Httpd", "The server could not start.");
		}
		body.setWebViewClient(new CustomWebViewClient());

		body.getSettings().setJavaScriptEnabled(true);

		body.loadUrl("http://localhost:8080");

		body.addJavascriptInterface(new WebAppInterface(this), "CMA");
		/*body.loadUrl("javascript:getData();");*/


	}

	@OnClick(R.id.newThreadCreate)
	public void onClickCreatePost() {
		body.loadUrl("javascript:getData();");
	}

	public void displayThread() {
		title.setText(forumThread.getTitle());
//		body.setText(forumThread.getBody());
		body.loadUrl("javascript:setData('" + forumThread.getBody() + "');");
		isColsedCB.setChecked(forumThread.isClosed());
		createThread.setText("Update Post");

	}

	public void updatePost(String htmlBody) {
		UpdateThreadDTO updateThreadDTO = new UpdateThreadDTO();
		updateThreadDTO.setTitle(title.getText().toString());
		updateThreadDTO.setBody(htmlBody);
		updateThreadDTO.setClosed(isColsedCB.isChecked());
		updateThreadDTO.setId(forumThread.getId());
		updateThreadDTO.setParentPostID(forumThread.getParentPostID());
		updateThreadDTO.setUserName(preferences.getUserName());
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("Updating...");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		CommonTaskExecutor.updateThread(cmaVidyaApp, updateThreadDTO, new CommonTaskExecutor.OnPostExecute() {
			@Override
			public void onPostExecute(Object object) {
				CommonTaskExecutor.updateMasterForumTopicsData(cmaVidyaApp, activity, new CommonTaskExecutor.OnPostExecute() {
					@Override
					public void onPostExecute(final Object object) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Boolean aBoolean = (Boolean) object;
								if (!aBoolean) {
									cmaVidyaApp.showToast("Fail to update FORUM Data");
								}
								if (null != progressDialog && progressDialog.isShowing()) {
									progressDialog.dismiss();
								}
								cmaVidyaApp.showToast("Thread updated");
								finish();
							}
						});
					}
				});
			}
		});
	}

	public void createPost(String htmlBody) {
		CreateNewThreadDTO createNewThreadDTO = new CreateNewThreadDTO();
		createNewThreadDTO.setTitle(title.getText().toString());
		createNewThreadDTO.setBody(htmlBody);
		createNewThreadDTO.setClosed(isColsedCB.isChecked());
		createNewThreadDTO.setForumID(forumId);
		createNewThreadDTO.setParentPostID(0);
		createNewThreadDTO.setUserName(preferences.getUserName());
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("Updating...");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		CommonTaskExecutor.createNewThread(cmaVidyaApp, createNewThreadDTO, new CommonTaskExecutor.OnPostExecute() {
			@Override
			public void onPostExecute(Object object) {
				CommonTaskExecutor.updateMasterForumTopicsData(cmaVidyaApp, activity, new CommonTaskExecutor.OnPostExecute() {
					@Override
					public void onPostExecute(final Object object) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Boolean aBoolean = (Boolean) object;
								if (!aBoolean) {
									cmaVidyaApp.showToast("Fail to update FORUM Data");
								}
								if (null != progressDialog && progressDialog.isShowing()) {
									progressDialog.dismiss();
								}
								cmaVidyaApp.showToast("Thread updated");
								finish();
							}
						});
					}
				});
			}
		});
	}

	private class CustomWebViewClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (null != forumThread)
				body.loadUrl("javascript:setData('" + forumThread.getBody() + "');");
		}
	}

	private class WebServer extends NanoHTTPD {

		public WebServer(int port) {
			super(port);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Response serve(IHTTPSession session) {

			Context mContext = activity;
			String uri = session.getUri();

			InputStream mbuffer = null;
			Response resp = new Response("");

			try {
				if (uri != null) {

					if (uri.contains(".js")) {
						mbuffer = mContext.getAssets().open(uri.substring(1));
						resp.setMimeType(MIME_JAVASCRIPT);
					} else if (uri.contains(".css")) {
						mbuffer = mContext.getAssets().open(uri.substring(1));
						resp.setMimeType(MIME_CSS);
					} else if (uri.contains(".png")) {
						mbuffer = mContext.getAssets().open(uri.substring(1));
						resp.setMimeType(MIME_PNG);
					} else if (uri.contains(".jpg")) {
						mbuffer = mContext.getAssets().open(uri.substring(1));
						resp.setMimeType(MIME_JPEG);
					} else if (uri.contains(".html")) {
						mbuffer = mContext.getAssets().open(uri.substring(1));
						resp.setMimeType(MIME_HTML);
					} else if (uri.contains(".ico")) {
						mbuffer = mContext.getAssets().open(uri.substring(1));
						resp.setMimeType(MIME_ICON);
					} else if (uri.contains(".ttf")) {
						mbuffer = mContext.getAssets().open(uri.substring(1));
						resp.setMimeType(MIME_FONT_TTF);
					} else if (uri.contains(".eot")) {
						mbuffer = mContext.getAssets().open(uri.substring(1));
						resp.setMimeType(MIME_FONT_EOT);
					} else if (uri.contains(".svg")) {
						mbuffer = mContext.getAssets().open(uri.substring(1));
						resp.setMimeType(MIME_FONT_SVG);
					} else if (uri.contains(".woff")) {
						mbuffer = mContext.getAssets().open(uri.substring(1));
						resp.setMimeType(MIME_FONT_WOFF);
					} else {
						mbuffer = mContext.getAssets().open("index.html");
						resp.setMimeType(MIME_HTML);
					}
				}
				resp.setData(mbuffer);
				resp.setStatus(Response.Status.OK);
				return resp;

			} catch (IOException e) {
				Log.d("tag", "Error opening file" + uri.substring(1));
				e.printStackTrace();
			}


			resp.setStatus(Response.Status.OK);
			return resp;
		}
	}

	public class WebAppInterface {
		Context mContext;

		/**
		 * Instantiate the interface and set the context
		 */
		WebAppInterface(Context c) {
			mContext = c;
		}

		/**
		 * Show a toast from the web page
		 */
		@JavascriptInterface
		public void showToast(String htmlBody) {
			//	Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();

			if (!TextUtils.isEmpty(title.getText().toString()))
				if (!TextUtils.isEmpty(htmlBody)) {
					String[] strings = htmlBody.split(" ");
					if (strings.length > 14) {
						if (forumId != 0) {
							createPost(htmlBody);
						} else {
							updatePost(htmlBody);
						}
					} else cmaVidyaApp.showToast("Enter more words");
				} else cmaVidyaApp.showToast("Enter Body");
			else cmaVidyaApp.showToast("Enter Title");


		}
	}

}
