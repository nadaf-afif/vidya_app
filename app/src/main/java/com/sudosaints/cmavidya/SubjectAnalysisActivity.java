package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.model.Question;
import com.sudosaints.cmavidya.model.Test;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.UIHelper;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by inni on 12/3/15.
 */
public class SubjectAnalysisActivity extends Activity {
	private UIHelper uiHelper;
	private CMAVidyaApp cmaVidyaApp;
	private DBUtils dbUtils;
	private Preferences preferences;
	private Activity activity;
	private ProgressDialog progressDialog;

	private long diffLevel, testType;
	private int subjectMasterId;
	private List<Question> questions;
	private List<Test> testList;
	private String subjectName;

	private LayoutInflater inflater;


	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};


	@InjectView(R.id.scoreTestTypeTV)
	TextView subjectTestTypeTV;

	@InjectView(R.id.scoreDiffLevelTV)
	TextView ssubjectDiffLevelTV;

	@InjectView(R.id.subjectNameTV)
	TextView subjectNameTV;

	@InjectView(R.id.avgScoreTV)
	TextView avgScoreTV;

	@InjectView(R.id.higScoreTV)
	TextView higScoreTV;

	@InjectView(R.id.lowScoreTV)
	TextView lowScoreTV;

	@InjectView(R.id.scoreDetailsTL)
	TableLayout scoreDetailsTL;

	@InjectView(R.id.scorePieChart)
	LinearLayout scorePieChart;

	@InjectView(R.id.scorePerformanceLineChart)
	LinearLayout scorePerformanceLineChart;

	@InjectView(R.id.scoreQuestionChart)
	LinearLayout scoreQuestionChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_subject_report);
		ButterKnife.inject(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		uiHelper = new UIHelper(this);
		preferences = new Preferences(activity);
		uiHelper.setActionBar(Constants.ActivityABarAction.ANALYSIS, actionBarLeftOnClickListener, null);
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


		if (getIntent().hasExtra(IntentExtras.TEST_TYPE) && getIntent().hasExtra(IntentExtras.DIFFICULTY_LEVEL)) {
			diffLevel = getIntent().getLongExtra(IntentExtras.DIFFICULTY_LEVEL, 0);
			testType = getIntent().getLongExtra(IntentExtras.TEST_TYPE, 0);
			subjectMasterId = getIntent().getIntExtra(IntentExtras.SUBJECT_MASTER_ID, 0);
			subjectName = getIntent().getStringExtra(IntentExtras.SUBJECT_NAME);
		}

		ssubjectDiffLevelTV.setText(getResources().getStringArray(R.array.difficultie_level)[(int) diffLevel] + "");
		subjectTestTypeTV.setText(getResources().getStringArray(R.array.test_type)[(int) testType] + "");
		subjectNameTV.setText(subjectName);

		testType++;

		questions = new ArrayList<Question>();


		try {
			testList = dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().queryBuilder().where().eq("idTestType", testType).and().eq("isTestEnd", true).query();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (testList.size() == 0) {
			cmaVidyaApp.showToast("No Test Found Of this type");
			finish();
		}
		for (Test test : testList) {
			try {
				if (subjectMasterId == 0) {
					if (diffLevel == 0) {
						questions.addAll(dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", (int) test.getIdTestLogMaster()).query());
					} else {
						questions.addAll(dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", (int) test.getIdTestLogMaster()).and().eq("difficultyLevel", (int) diffLevel).query());
					}
				} else {
					if (diffLevel == 0) {
						questions.addAll(dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", (int) test.getIdTestLogMaster()).and().eq("idSubjectMaster", subjectMasterId).query());
					} else {
						questions.addAll(dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", (int) test.getIdTestLogMaster()).and().eq("difficultyLevel", (int) diffLevel).and().eq("idSubjectMaster", subjectMasterId).query());
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		float avg, high = 0, low = 100, total = 0;

		for (Test test : testList) {
			for (Question question : questions) {
				float testTotal = 0, obtained = 0, count = 0;
				if (question.getIdTestLogMaster() == test.getIdTestLogMaster()) {
					testTotal = question.getOutOf();
					obtained = question.getMarks();
					count++;
				}
				float testAvg = obtained / count;
				if (high < testAvg) {
					high = testAvg;
				}
				if (low > testAvg) {
					low = testAvg;
				}
				total += testAvg;
			}
		}

		avg = total / testList.size();

		avgScoreTV.setText(Float.parseFloat(avg + 0f + "") + "");
		higScoreTV.setText(high + "");
		lowScoreTV.setText(low + "");
		int testCount = 1;
		double totalq = 0, totalUnAtt = 0, totalRig = 0;

		XYSeries seriesTotalQ = new XYSeries("Total Question");
		XYSeries seriesCorrectA = new XYSeries("Correct Answer");
		XYSeries seriesAttemptedQ = new XYSeries("Attempted Question");
		XYSeries seriesUnAttemptedQ = new XYSeries("UnAttempted Question");

		for (Test test : testList) {
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

			long totalCount = 0, diffCount = 0, attempted = 0, correct = 0;
			try {
				totalCount = dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", test.getIdTestLogMaster()).countOf();
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (Question question : questions) {
				if (question.getIdTestLogMaster() == test.getIdTestLogMaster()) {
					diffCount++;
					if (question.getOptionSelected() != 0) {
						attempted++;
						if (question.getOptionSelected() == question.getRightAnswer()) {
							correct++;
							totalRig++;
						}
					}
					{
						totalUnAtt++;
					}
				}
			}

			seriesTotalQ.add(testCount, diffCount);
			seriesCorrectA.add(testCount, correct);
			seriesAttemptedQ.add(testCount, attempted);
			seriesUnAttemptedQ.add(testCount, (diffCount - attempted));

			totalq += diffCount;
			float testPer;
			if (diffCount == 0) {
				testPer = 0;
			} else
				testPer = correct / diffCount;

			TextView srnoTV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView testIdTV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView diffLevelTV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView totalQTV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView totalDQTV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView attemQTV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView corrATV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView testPerTV = (TextView) inflater.inflate(R.layout.table_textview, null);


			srnoTV.setText(testCount++ + "");
			testIdTV.setText(test.getTestId());
			diffLevelTV.setText(getResources().getStringArray(R.array.difficultie_level)[(int) diffLevel] + "");
			totalQTV.setText(totalCount + "");
			totalDQTV.setText(diffCount + "");
			attemQTV.setText(attempted + "");
			corrATV.setText(correct + "");
			testPerTV.setText(testPer + "%");

			tableRow.addView(srnoTV);
			tableRow.addView(testIdTV);
			tableRow.addView(diffLevelTV);
			tableRow.addView(totalQTV);
			tableRow.addView(totalDQTV);
			tableRow.addView(attemQTV);
			tableRow.addView(corrATV);
			tableRow.addView(testPerTV);


			scoreDetailsTL.addView(tableRow, new TableLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


		}
		totalq = 0;
		double attp = (totalq - totalUnAtt) / totalq;
		double unattp = (totalUnAtt / totalq);
		double corr = (totalRig / totalq);


		int[] colors = {Color.rgb(255, 102, 0), Color.rgb(0, 128, 0), Color.rgb(128, 0, 128)};
		String[] code = new String[]{"Attempted Question", "Correct Answers", "UnAttempted Question"};
		double[] distribution = {attp, corr, unattp};
		CategorySeries distributionSeries = new CategorySeries("Total Questions");

		for (int i = 0; i < distribution.length; i++) {
			// Adding a slice with its values and name to the Pie Chart
			distributionSeries.add(code[i], distribution[i]);
		}

		DefaultRenderer defaultRenderer = new DefaultRenderer();
		for (int i = 0; i < distribution.length; i++) {
			SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
			seriesRenderer.setColor(colors[i]);
			defaultRenderer.addSeriesRenderer(seriesRenderer);
		}
		defaultRenderer.setChartTitle("Total Questions");
		defaultRenderer.setChartTitleTextSize(20);
		defaultRenderer.setZoomButtonsVisible(false);

		scorePieChart.addView(ChartFactory.getPieChartView(activity, distributionSeries, defaultRenderer));

		XYSeriesRenderer rendererAttempted = new XYSeriesRenderer();
		rendererAttempted.setLineWidth(2);
		rendererAttempted.setColor(Color.rgb(255, 102, 0));
		rendererAttempted.setDisplayBoundingPoints(true);
		rendererAttempted.setPointStyle(PointStyle.SQUARE);
		rendererAttempted.setPointStrokeWidth(3);

		XYSeriesRenderer rendererTotal = new XYSeriesRenderer();
		rendererTotal.setLineWidth(2);
		rendererTotal.setColor(Color.rgb(128, 0, 128));
		rendererTotal.setDisplayBoundingPoints(true);
		rendererTotal.setPointStyle(PointStyle.SQUARE);
		rendererTotal.setPointStrokeWidth(3);

		XYSeriesRenderer rendererCorrect = new XYSeriesRenderer();
		rendererCorrect.setLineWidth(2);
		rendererCorrect.setColor(Color.rgb(0, 128, 0));
		rendererCorrect.setDisplayBoundingPoints(true);
		rendererCorrect.setPointStyle(PointStyle.SQUARE);
		rendererCorrect.setPointStrokeWidth(3);

		XYSeriesRenderer rendererUnAttempted = new XYSeriesRenderer();
		rendererUnAttempted.setLineWidth(2);
		rendererUnAttempted.setColor(Color.rgb(0, 0, 255));
		rendererUnAttempted.setDisplayBoundingPoints(true);
		rendererUnAttempted.setPointStyle(PointStyle.SQUARE);
		rendererUnAttempted.setPointStrokeWidth(3);

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(seriesTotalQ);
		dataset.addSeries(seriesCorrectA);
		dataset.addSeries(seriesAttemptedQ);
		dataset.addSeries(seriesUnAttemptedQ);


		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(rendererTotal);
		mRenderer.addSeriesRenderer(rendererCorrect);
		mRenderer.addSeriesRenderer(rendererAttempted);
		mRenderer.addSeriesRenderer(rendererUnAttempted);
		mRenderer.setXTitle("Sr. No.");
		mRenderer.setYTitle("Questions");
		mRenderer.setChartTitle("Performance Analysis");


		mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
// Disable Pan on two axis
		mRenderer.setPanEnabled(false, false);
		mRenderer.setYAxisMin(0);
		mRenderer.setShowGrid(true);

		GraphicalView chartView = ChartFactory.getLineChartView(activity, dataset, mRenderer);
		scorePerformanceLineChart.addView(chartView);


		XYMultipleSeriesDataset dataset1 = new XYMultipleSeriesDataset();
		dataset1.addSeries(seriesTotalQ);
		dataset1.addSeries(seriesCorrectA);
		dataset1.addSeries(seriesAttemptedQ);

		XYMultipleSeriesRenderer mRenderer1 = new XYMultipleSeriesRenderer();
		mRenderer1.addSeriesRenderer(rendererTotal);
		mRenderer1.addSeriesRenderer(rendererCorrect);
		mRenderer1.addSeriesRenderer(rendererAttempted);
		mRenderer1.setXTitle("Sr. No.");
		mRenderer1.setYTitle("Questions");
		mRenderer1.setChartTitle("Question Analysis");
		mRenderer1.setXLabelsPadding(2);
		mRenderer1.setShowGrid(true);
		mRenderer1.setBarSpacing(1);
		mRenderer1.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
// Disable Pan on two axis
		mRenderer1.setPanEnabled(false, false);
		mRenderer1.setYAxisMin(0);
		mRenderer1.setShowGrid(true);

		GraphicalView chartViewBar = ChartFactory.getBarChartView(activity, dataset1, mRenderer1, BarChart.Type.DEFAULT);
		scoreQuestionChart.addView(chartViewBar);
	}
}
