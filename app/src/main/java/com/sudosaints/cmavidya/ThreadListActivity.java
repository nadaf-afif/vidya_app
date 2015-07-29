package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sudosaints.cmavidya.adapter.DownloadListAdapter;
import com.sudosaints.cmavidya.adapter.ThreadDisplayListAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.Downloads;
import com.sudosaints.cmavidya.model.ForumThread;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.JSONParser;
import com.sudosaints.cmavidya.util.UIHelper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
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

    private int parentPostId,forumId;
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

    @InjectView(R.id.tdThreadName)
    TextView threadName;

    @InjectView(R.id.tdTopicName)
    TextView topicName;

    @InjectView(R.id.tdListView)
    ListView threadListView;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(IntentExtras.THREAD_ID)) {
            threadId = getIntent().getLongExtra(IntentExtras.THREAD_ID, 0);
        } else {
            finish();
        }

        setContentView(R.layout.activity_thread_display);
        ButterKnife.inject(this);
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
            parentPostId = (int) forumThreads.get(0).getParentPostID();
            forumId = (int) forumThreads.get(0).getForumID();
        }
    }

    @OnClick(R.id.tdPostReply)
    public void onClickPostReply() {
        if (forumThreads.size() > 0) {
            if (!forumThreads.get(0).isClosed()) {
                showPostReplyDialog();
            } else {
                cmaVidyaApp.showToast("Thread Closed");
            }
        }
    }

    private void showPostReplyDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.post_reply_dialog);
        dialog.setTitle(getResources().getString(R.string.post_reply));

        // set the custom dialog components - text, image and button
        final EditText titleEditText = (EditText) dialog.findViewById(R.id.titleEditText);
        final EditText bodyEditText = (EditText) dialog.findViewById(R.id.bodyEditText);
        Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
        Button replyButton = (Button) dialog.findViewById(R.id.replyButton);

        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String body = bodyEditText.getText().toString();
                if(!title.isEmpty() && !body.isEmpty() )
                {
                    new PostCommentAsynTask().execute(title,body);
                    dialog.dismiss();
                }
                else
                    cmaVidyaApp.showToast("Please fill all data");
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private class PostCommentAsynTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject json=null;
            String Title = params[0], Body = params[1], Filename = "";
            String UserName = preferences.getUserEmail();
            boolean Closed = false;
            int ParentPostID = parentPostId, ForumID = forumId;
            try {

                JSONObject jsobObj = new JSONObject();
                jsobObj.put("Title", Title);
                jsobObj.put("Body", Body);
                jsobObj.put("Filename", Filename);
                jsobObj.put("UserName", UserName);
                jsobObj.put("Closed", Closed);
                jsobObj.put("ParentPostID", ParentPostID);
                jsobObj.put("ForumID", ForumID);

                json= postData(Constants.POST_COMMENT_API,jsobObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if(result!=null)
            {
                try {
                    Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ThreadListActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }


    public JSONObject postData(String url, JSONObject obj) {
        // Create a new HttpClient and Post Header
        JSONObject result = null;
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 10000);
        HttpConnectionParams.setSoTimeout(myParams, 10000);
        HttpClient httpclient = new DefaultHttpClient(myParams);
        String json = obj.toString();

        try {

            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());
            Log.i("tag", temp);
            try {
                result = new JSONObject(temp); //Convert String to JSON Object
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (ClientProtocolException e) {

        } catch (IOException e) {
        }
        return result;
    }


}
