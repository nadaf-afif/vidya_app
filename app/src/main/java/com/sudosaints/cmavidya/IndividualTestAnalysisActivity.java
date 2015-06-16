package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by inni on 12/3/15.
 */
public class IndividualTestAnalysisActivity extends Activity {
	@InjectView(R.id.reportOverallScoreTL)
	TableLayout reportOverallScoreTL;

	@InjectView(R.id.sectionalTL)
	TableLayout sectionalTL;

	@InjectView(R.id.questionWiseTL)
	TableLayout questionWiseTL;

	private UIHelper uiHelper;
	private CMAVidyaApp cmaVidyaApp;
	private DBUtils dbUtils;
	private Preferences preferences;
	private Activity activity;
	private ProgressDialog progressDialog;
	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};

	private Test test = null;
	private List<Question> questionList;
	private LayoutInflater inflater;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_individual_test_analysis);
		ButterKnife.inject(this);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(this);
		uiHelper = new UIHelper(this);
		preferences = new Preferences(activity);
		uiHelper.setActionBar(Constants.ActivityABarAction.ANALYSIS, actionBarLeftOnClickListener, null);
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		if (getIntent().hasExtra(IntentExtras.TEST)) {
			test = (Test) getIntent().getSerializableExtra(IntentExtras.TEST);
			displayReport();
		}
	}


	private void displayReport() {
		try {
			questionList = dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", (int) test.getIdTestLogMaster()).query();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != questionList && questionList.size() != 0) {
			//for Overall Score
			int noOfQuestion = 0, attempted = 0, notAttempted = 0, negativeMark = 0, totalMark = 0, outOfMarks = 0;
			List<String> subjects = new ArrayList<String>();
			int count = 1;

			noOfQuestion = questionList.size();
			negativeMark = (int) test.getNegativeMarks();
			for (Question question : questionList) {

				if (!subjects.contains(question.getSubjectName())) {
					subjects.add(question.getSubjectName());
				}
				outOfMarks += question.getMarks();
				if (question.getOptionSelected() == 0) {
					notAttempted++;
				} else {
					attempted++;
					if (question.getOptionSelected() == question.getRightAnswer()) {
						totalMark += question.getMarks();
					}
				}

				TableRow tableRow = new TableRow(this);
				tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

				TextView qNoTV = (TextView) inflater.inflate(R.layout.table_textview, null);
				TextView opSelTV = (TextView) inflater.inflate(R.layout.table_textview, null);
				TextView rOptTV = (TextView) inflater.inflate(R.layout.table_textview, null);

				LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.table_imageview, null);
				ImageView statusIV = (ImageView) linearLayout.findViewById(R.id.IV);

				TextView markTV = (TextView) inflater.inflate(R.layout.table_textview, null);
				TextView outOfMTV = (TextView) inflater.inflate(R.layout.table_textview, null);
				TextView subjNTV = (TextView) inflater.inflate(R.layout.table_textview, null);
				TextView topicNTV = (TextView) inflater.inflate(R.layout.table_textview, null);

				qNoTV.setText((count++) + "");
				opSelTV.setText(question.getOptionSelected() == 0 ? "Not Attempted" : (Character.toChars(64 + question.getOptionSelected()) + ""));
				rOptTV.setText(question.getOptionSelected() == 0 ? "NA" : (Character.toChars(64 + question.getRightAnswer()) + ""));
				if (question.getOptionSelected() == 0) {
					statusIV.setImageResource(R.drawable.unattempted);
					markTV.setText("0");
				} else if (question.getOptionSelected() == question.getRightAnswer()) {
					statusIV.setImageResource(R.drawable.tick);
					markTV.setText(question.getOutOf() + "");
				} else {
					statusIV.setImageResource(R.drawable.cross);
					markTV.setText("0");
				}
				outOfMTV.setText(question.getOutOf() + "");
				subjNTV.setText(question.getSubjectName());
				topicNTV.setText(question.getTopicName());

				tableRow.addView(qNoTV);
				tableRow.addView(opSelTV);
				tableRow.addView(rOptTV);
				tableRow.addView(linearLayout);
				tableRow.addView(markTV);
				tableRow.addView(outOfMTV);
				tableRow.addView(subjNTV);
				tableRow.addView(topicNTV);

				questionWiseTL.addView(tableRow, new TableLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

			}

			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
			TextView totalQTV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView attemptedTV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView notAttemptedTV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView negativeTV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView totalMTV = (TextView) inflater.inflate(R.layout.table_textview, null);
			TextView outOfMTV = (TextView) inflater.inflate(R.layout.table_textview, null);

			totalQTV.setText(noOfQuestion + "");
			attemptedTV.setText(attempted + "");
			notAttemptedTV.setText(notAttempted + "");
			negativeTV.setText(negativeMark + "");
			totalMTV.setText(totalMark + "");
			outOfMTV.setText(outOfMarks + "");

			tableRow.addView(totalQTV);
			tableRow.addView(attemptedTV);
			tableRow.addView(notAttemptedTV);
			tableRow.addView(negativeTV);
			tableRow.addView(totalMTV);
			tableRow.addView(outOfMTV);

			reportOverallScoreTL.addView(tableRow, new TableLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

			for (String s : subjects) {
				TableRow tableRow1 = new TableRow(this);
				tableRow1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
				TextView SubjectNameTV = (TextView) inflater.inflate(R.layout.table_textview, null);
				TextView totalQTV1 = (TextView) inflater.inflate(R.layout.table_textview, null);
				TextView attemptedTV1 = (TextView) inflater.inflate(R.layout.table_textview, null);
				TextView notAttemptedTV1 = (TextView) inflater.inflate(R.layout.table_textview, null);
				TextView totalMTV1 = (TextView) inflater.inflate(R.layout.table_textview, null);
				TextView outOfMTV1 = (TextView) inflater.inflate(R.layout.table_textview, null);

				SubjectNameTV.setText(s + "");
				try {
					long qCount = dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("subjectName", s).and().eq("idTestLogMaster", test.getIdTestLogMaster()).countOf();
					long notAttempCount = dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("subjectName", s).and().eq("idTestLogMaster", test.getIdTestLogMaster()).and().eq("optionSelected", 0).countOf();
					long attemptedCount = qCount - notAttempCount;
					long totalMCount = 0, outOfMCOunt = 0;
					for (Question question : dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("subjectName", s).and().eq("idTestLogMaster", test.getIdTestLogMaster()).and().not().eq("optionSelected", 0).query()) {
						outOfMarks += question.getOutOf();
						if (question.getOptionSelected() == question.getRightAnswer())
							totalMCount += question.getOutOf();
					}
					totalQTV1.setText((int) qCount + "");
					attemptedTV1.setText((int) attemptedCount + "");
					notAttemptedTV1.setText((int) notAttempCount + "");
					totalMTV1.setText((int) totalMCount + "");
					outOfMTV1.setText((int) outOfMCOunt + "");

				} catch (Exception e) {
					e.printStackTrace();
					cmaVidyaApp.getLogger().debug(e.toString());
				}

				tableRow1.addView(SubjectNameTV);
				tableRow1.addView(totalQTV1);
				tableRow1.addView(attemptedTV1);
				tableRow1.addView(notAttemptedTV1);
				tableRow1.addView(totalMTV1);
				tableRow1.addView(outOfMTV1);

				sectionalTL.addView(tableRow1);
			}


		} else {
			cmaVidyaApp.showToast("Error in creating Report ");
			finish();
		}
	}


}
