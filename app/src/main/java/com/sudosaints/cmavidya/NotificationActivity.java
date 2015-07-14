package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.sudosaints.cmavidya.adapter.NotificationAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.Notification;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.JSONParser;
import com.sudosaints.cmavidya.util.UIHelper;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Akshay M on 7/14/2015.
 */
public class NotificationActivity extends Activity {

    private UIHelper uiHelper;
    private CMAVidyaApp cmaVidyaApp;
    private DBUtils dbUtils;
    private Preferences preferences;
    private ProgressDialog progressDialog;
    private int updaateCount = 0;
    private ListView notification_listView;
    private ArrayList<Notification> result_arr;
    private NotificationAdapter adapter;

    View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        uiHelper = new UIHelper(this);
        cmaVidyaApp = (CMAVidyaApp) getApplication();
        preferences = new Preferences(this);
        ButterKnife.inject(this);
        dbUtils = new DBUtils(NotificationActivity.this);
        uiHelper.setActionBar(Constants.ActivityABarAction.NOTIFICATION, actionBarLeftOnClickListener, null);
        initViews();
        getAllNotification();
    }

    private void getAllNotification() {
        result_arr=new ArrayList<Notification>();
        if (cmaVidyaApp.getApiRequestHelper().checkNetwork())
            new NotificationAsynTask().execute();
        else
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG).show();
    }

    private void initViews() {
        notification_listView= (ListView) findViewById(R.id.notification_listView);
    }

    private class NotificationAsynTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONParser jsonParser=new JSONParser();
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            JSONObject json = jsonParser.makeHttpRequest(Constants.NOTIFICATION_API, "GET", parameters);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
        if(progressDialog!=null && progressDialog.isShowing())
            progressDialog.dismiss();

            try {
                if(result.getBoolean("success"))
                {
                    Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                    JSONArray oListArray =result.getJSONArray("oList");
                    for(int i=0;i<oListArray.length();i++)
                    {
                        JSONObject notificationItem = oListArray.getJSONObject(i);
                        Notification item = new Notification();
                        item.setActive(notificationItem.getBoolean("IsActive"));
                        item.setDescription(notificationItem.getString("Description"));
                        item.setEndDate(notificationItem.getString("EndDate"));
                        item.setStartDate(notificationItem.getString("StartDate"));
                        item.setNote(notificationItem.getString("Note"));
                        item.setNotificationId(notificationItem.getInt("IdNotification"));
                        item.setCourseBatchId(notificationItem.getInt("IdCourseBatch"));
                        result_arr.add(item);
                    }
                    if(result_arr.size()>0) {
                        adapter = new NotificationAdapter(NotificationActivity.this, result_arr);
                        notification_listView.setAdapter(adapter);
                    }
                    else
                        Toast.makeText(getApplicationContext(), R.string.no_notification, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(NotificationActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
}
