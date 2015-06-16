package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.aphidmobile.flip.FlipViewController;
import com.artifex.mupdfdemo.MuPDFActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.sudosaints.cmavidya.adapter.FlipViewAdapter;
import com.sudosaints.cmavidya.adapter.KeyNotesPDFFileAdapter;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.KeyNotes;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.UIHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by inni on 26/7/14.
 */
public class KeynotesMainActivity extends Activity {//implements AdapterView.OnItemClickListener {

	/*  @InjectView(R.id.keynotesGridList)
	  public GridView gridView;
	  @InjectView(R.id.keynotesTxtView)
	  public TextView tvEmptyView;*/
	private KeyNotesPDFFileAdapter keyNotesPDFFileAdapter;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private ProgressDialog mProgressDialog;
	private String pdfUrl = "";
	private List<KeyNotes> keyNotesList;
	private CMAVidyaApp cmaVidyaApp;
	private UIHelper uiHelper;
	private DBUtils dbUtils;
	private File file;
	private File dir;
	private FlipViewController flipView;
	private List<List<KeyNotes>> gridKeynotesList;
	private FlipViewAdapter flipViewAdapter;

	private CommonTaskExecutor.OnPostExecute onPostExecuteGetCourse = new CommonTaskExecutor.OnPostExecute() {
		@Override
		public void onPostExecute(Object object) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.flip_first_layout);
		ButterKnife.inject(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(KeynotesMainActivity.this);
		uiHelper = new UIHelper(this);
		uiHelper.setActionBar(Constants.ActivityABarAction.KEYNOTES_MAIN, actionBarLeftOnClickListener, actionBarRightOnClickListener);

		flipView = new FlipViewController(this, FlipViewController.VERTICAL);


		RuntimeExceptionDao<KeyNotes, Integer> keyNotesDao = dbUtils.getDatabaseHelper().getKeyNotesRuntimeExceptionDao();
		keyNotesList = keyNotesDao.queryForAll();
		int i = 0;
		gridKeynotesList = new ArrayList<List<KeyNotes>>();
		List<KeyNotes> keyNoteses = new ArrayList<KeyNotes>();
		for (KeyNotes keyNotes : keyNotesList) {
			if (i % 6 == 0 && i != 0) {
				gridKeynotesList.add(keyNoteses);
				keyNoteses = new ArrayList<KeyNotes>();
			}
			keyNoteses.add(keyNotes);
			i++;
		}
		if (keyNoteses.size() > 0)
			gridKeynotesList.add(keyNoteses);

		flipViewAdapter = new FlipViewAdapter(KeynotesMainActivity.this, gridKeynotesList, new FlipViewAdapter.OnClickListner() {
			@Override
			public void onKeynoteClick(KeyNotes keyNotes) {
				dir = getFilesDir();
				pdfUrl = keyNotes.getUploadedFileUrl();
				file = new File(dir, pdfUrl.substring((pdfUrl.lastIndexOf("/") + 1)).replace(" ", ""));
				if (!file.exists()) {
					//PDF file not available so first download it and load
					if(cmaVidyaApp.getApiRequestHelper().checkNetwork()) {
						new DownloadFileAsync().execute(pdfUrl);
					}else{
						cmaVidyaApp.showToast("No Active Internet Connection Detected To download the file");
					}
				} else {
					//PDF file is available so directly load.
					String fileName = file.getName().replace(" ", "");
					String path = getFilesDir().getPath() + "/" + fileName;

                   /* Intent intent = new Intent(KeynotesMainActivity.this, KeynoteDisplayActivity.class);
					intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
                    startActivity(intent);*/

					Uri uri = Uri.parse(path);
					Intent intent = new Intent(KeynotesMainActivity.this, MuPDFActivity.class);
					intent.setAction(Intent.ACTION_VIEW);
					intent.setData(uri);
					startActivity(intent);

				}
			}
		});

		flipView.setAdapter(flipViewAdapter);

		setContentView(flipView);


	}

	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};
	View.OnClickListener actionBarRightOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {

		}
	};


	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(KeynotesMainActivity.this);
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
				FileOutputStream output = openFileOutput(file.getName(), KeynotesMainActivity.MODE_PRIVATE);
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
			/*Intent intent = new Intent(KeynotesMainActivity.this, KeynoteDisplayActivity.class);
			intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
			startActivity(intent);*/
			Uri uri = Uri.parse(path);
			Intent intent = new Intent(KeynotesMainActivity.this, MuPDFActivity.class);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setData(uri);
			startActivity(intent);
		}
	}


}
