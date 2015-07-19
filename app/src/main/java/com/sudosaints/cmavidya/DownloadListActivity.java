package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.artifex.mupdfdemo.MuPDFActivity;
import com.squareup.okhttp.internal.Util;
import com.sudosaints.cmavidya.adapter.DownloadListAdapter;
import com.sudosaints.cmavidya.adapter.NotificationAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.Downloads;
import com.sudosaints.cmavidya.model.Notification;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.CommonUtil;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.JSONParser;
import com.sudosaints.cmavidya.util.UIHelper;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Akshay M on 7/14/2015.
 */
public class DownloadListActivity extends Activity {

    private UIHelper uiHelper;
    private CMAVidyaApp cmaVidyaApp;
    private DBUtils dbUtils;
    private Preferences preferences;
    private ProgressDialog progressDialog;
    private int updaateCount = 0;
    private ListView downloadsListViewList;
    private ArrayList<Downloads> result_arr;
    private DownloadListAdapter adapter;

    View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    private String pdfUrl;
    private File dir;
    private File file;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadlist);
        uiHelper = new UIHelper(this);
        cmaVidyaApp = (CMAVidyaApp) getApplication();
        preferences = new Preferences(this);
        ButterKnife.inject(this);
        dbUtils = new DBUtils(DownloadListActivity.this);
        uiHelper.setActionBar(Constants.ActivityABarAction.DOWNLOADS, actionBarLeftOnClickListener, null);
        initViews();
        getAllDownloads();
        downloadsListViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Downloads item = result_arr.get(position);
                pdfUrl = item.getUploadFileUrl();
                dir = getFilesDir();
                file = new File(dir, pdfUrl.substring((pdfUrl.lastIndexOf("/") + 1)).replace(" ", ""));
                if (!file.exists()) {
                    //PDF file not available so first download it and load
                    if(cmaVidyaApp.getApiRequestHelper().checkNetwork()) {
                        if(getFileDate(item))
                            new DownloadFileAsync().execute(pdfUrl);
                        else
                            cmaVidyaApp.showToast("Cannot download file.");
                    }else{
                        cmaVidyaApp.showToast("No Active Internet Connection Detected To download the file");
                    }
                }
                else
                {
                    //PDF file is available so directly load.
                    String fileName = file.getName().replace(" ", "");
                    String path = getFilesDir().getPath() + "/" + fileName;

                    Uri uri = Uri.parse(path);
                    Intent intent = new Intent(DownloadListActivity.this, MuPDFActivity.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        });

    }

    private boolean getFileDate(Downloads item) {
        boolean flag=false;
        String strActivationDate=item.getStrActivationDate();
        String strEnddate=item.getStrEnddate();
        DateFormat df = new SimpleDateFormat("dd-MMM, yyyy");
        Date startDate,endDate,currentDate;
        try {
            startDate = df.parse(strActivationDate);
            endDate=df.parse(strEnddate);
            currentDate=df.parse(CommonUtil.getCurrentDate());
            if(currentDate.after(startDate) && currentDate.before(endDate))
                flag= true;
            else
                flag= false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    private void getAllDownloads() {
        result_arr=new ArrayList<Downloads>();
        if (cmaVidyaApp.getApiRequestHelper().checkNetwork())
            new DownloadListAsynTask().execute();
        else
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG).show();
    }

    private void initViews() {
        downloadsListViewList= (ListView) findViewById(R.id.downloadsGridList);
    }

    private class DownloadListAsynTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONParser jsonParser=new JSONParser();
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            JSONObject json = jsonParser.makeHttpRequest(Constants.DOWNLOAD_LIST_API, "GET", parameters);
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
                        JSONObject downloadItem = oListArray.getJSONObject(i);
                        Downloads item = new Downloads();
                        item.setStrActivationDate(downloadItem.getString("strActivationDate"));
                        item.setStrEnddate(downloadItem.getString("strEnddate"));
                        item.setUploadFileUrl(Constants.UPLOADEDPATH+downloadItem.getString("FileName"));
                        item.setTopicName(downloadItem.getString("TopicName"));
                        item.setSubjectName(downloadItem.getString("SubjectName"));
                        item.setFileName(downloadItem.getString("FileName"));
                        item.setDescription(downloadItem.getString("Description"));
                        // int values
                        item.setIdDownload(downloadItem.getInt("idDownload"));
                        item.setIdTopicMaster(downloadItem.getInt("idTopicMaster"));
                        // boolean values
                        item.setActive(downloadItem.getBoolean("IsActive"));

                        result_arr.add(item);
                    }
                    if(result_arr.size()>0) {
                        adapter = new DownloadListAdapter(DownloadListActivity.this, result_arr);
                        downloadsListViewList.setAdapter(adapter);
                    }
                    else
                        Toast.makeText(getApplicationContext(), R.string.no_downloads, Toast.LENGTH_LONG).show();
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
            progressDialog = new ProgressDialog(DownloadListActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }



    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(DownloadListActivity.this);
            mProgressDialog.setMessage("Downloading file..");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            try {
                String s = aurl[0].replace(" ", "%20");
                URL url = new URL(s);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();

                int lenghtOfFile = urlConnection.getContentLength();

                InputStream input = new BufferedInputStream(url.openStream());
                FileOutputStream output = openFileOutput(file.getName(), DownloadListActivity.MODE_PRIVATE);
                //OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String fileName = file.getName().replace(" ", "");
            String path = getFilesDir().getPath() + "/" + fileName;
            mProgressDialog.dismiss();
            Uri uri = Uri.parse(path);
            Intent intent = new Intent(DownloadListActivity.this, MuPDFActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
        }
    }

}
