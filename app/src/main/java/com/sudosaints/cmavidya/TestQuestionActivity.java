package com.sudosaints.cmavidya;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.dto.UpdateAnswer;
import com.sudosaints.cmavidya.model.Question;
import com.sudosaints.cmavidya.model.Test;
import com.sudosaints.cmavidya.util.CommonTaskExecutor;
import com.sudosaints.cmavidya.util.Constants;
import com.sudosaints.cmavidya.util.IntentExtras;
import com.sudosaints.cmavidya.util.UIHelper;

import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by inni on 2/1/15.
 */
public class TestQuestionActivity extends Activity {

	private long idTestLogMaster;
	private Test test;
	private DBUtils dbUtils;
	private Preferences preferences;
	private int questionNo = 0;
	private List<Question> questions;
	private String timer;
	private long startTime, endTime;
	private UIHelper uiHelper;
	private CMAVidyaApp cmaVidyaApp;

	//This is for if its saved test
	private boolean isActiveTest = false;


	View.OnClickListener actionBarLeftOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
		}
	};
	@InjectView(R.id.questionWV)
	WebView questionWV;

	@InjectView(R.id.optionsLL)
	LinearLayout optionsLL;

	@InjectView(R.id.option5LL)
	LinearLayout option5LL;

	@InjectView(R.id.questionRG)
	RadioGroup questionRG;

	@InjectView(R.id.option1)
	RadioButton option1;

	@InjectView(R.id.option2)
	RadioButton option2;

	@InjectView(R.id.option3)
	RadioButton option3;

	@InjectView(R.id.option4)
	RadioButton option4;

	@InjectView(R.id.option5)
	RadioButton option5;

	@InjectView(R.id.option1WV)
	WebView option1WV;

	@InjectView(R.id.option2WV)
	WebView option2WV;

	@InjectView(R.id.option3WV)
	WebView option3WV;

	@InjectView(R.id.option4WV)
	WebView option4WV;

	@InjectView(R.id.option5WV)
	WebView option5WV;


	@InjectView(R.id.nextBtn)
	Button nextBtn;
	@InjectView(R.id.backBtn)
	Button backBtn;
	@InjectView(R.id.pauseBtn)
	Button pauseBtn;
	@InjectView(R.id.endBtn)
	Button endBtn;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cmaVidyaApp = (CMAVidyaApp) getApplication();
		dbUtils = new DBUtils(TestQuestionActivity.this);
		preferences = new Preferences(TestQuestionActivity.this);


		if (!getIntent().hasExtra(IntentExtras.Id_Test_Log_Master)) {
			cmaVidyaApp.showToast("error");

			finish();
		} else {
			idTestLogMaster = getIntent().getLongExtra(IntentExtras.Id_Test_Log_Master, 0);
			setContentView(R.layout.activity_question_test);
			ButterKnife.inject(this);
			uiHelper = new UIHelper(this);
			uiHelper.setActionBar(Constants.ActivityABarAction.Test_SERIES, actionBarLeftOnClickListener, null);

			List<Test> tempTests = null;
			try {
				tempTests = dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", idTestLogMaster).query();
				if (null != tempTests && tempTests.size() > 0) {
					test = tempTests.get(0);
					if (test.getIdTestType() == 0) {
						updateQuestionListFromServer();
					} else {
						questions = dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", idTestLogMaster).query();
						if (questions.size() == 0) {
							updateQuestionListFromServer();
						}
						loadQuestion();
					}
				} else {
					updateQuestionListFromServer();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		option1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				option1.setChecked(true);
				option2.setChecked(false);
				option3.setChecked(false);
				option4.setChecked(false);
				option5.setChecked(false);

			}
		});
		option2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				option2.setChecked(true);
				option1.setChecked(false);
				option3.setChecked(false);
				option4.setChecked(false);
				option5.setChecked(false);

			}
		});
		option3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				option3.setChecked(true);
				option2.setChecked(false);
				option1.setChecked(false);
				option4.setChecked(false);
				option5.setChecked(false);

			}
		});
		option4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				option4.setChecked(true);
				option2.setChecked(false);
				option3.setChecked(false);
				option1.setChecked(false);
				option5.setChecked(false);

			}
		});
		option5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				option5.setChecked(true);
				option2.setChecked(false);
				option3.setChecked(false);
				option4.setChecked(false);
				option1.setChecked(false);

			}
		});


	}

	private void uncheckRadios() {
		option5.setChecked(false);
		option2.setChecked(false);
		option3.setChecked(false);
		option4.setChecked(false);
		option1.setChecked(false);
	}

	private void updateQuestionListFromServer() {
		{
			if (cmaVidyaApp.getApiRequestHelper().checkNetwork()) {
				CommonTaskExecutor.loadTest(cmaVidyaApp, TestQuestionActivity.this, preferences.getUserName(), idTestLogMaster, new CommonTaskExecutor.OnPostExecute() {
					@Override
					public void onPostExecute(Object object) {
						if (null == object) {
							cmaVidyaApp.showToast("Error loading question in this test ");
							finish();
						} else {
							test = (Test) object;
							try {
								questions = dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", idTestLogMaster).query();
								loadQuestion();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
			} else {
				cmaVidyaApp.showToast("No Active Internet Connection Detected");
			}
		}
	}


	private void loadQuestion() {
		if (test.getIdTestType() == 2) {
			optionsLL.setVisibility(View.GONE);
		} else {
			optionsLL.setVisibility(View.VISIBLE);
		}
		uncheckRadios();
		Question question = questions.get(questionNo);
		switch (question.getOptionSelected()) {
			case 1:
				option1.setChecked(true);
				break;
			case 2:
				option2.setChecked(true);
				break;
			case 3:
				option3.setChecked(true);
				break;
			case 4:
				option4.setChecked(true);
				break;
			case 5:
				option5.setChecked(true);
				break;


		}
		questionWV.loadUrl("about:blank");
		option1WV.loadUrl("about:blank");
		option2WV.loadUrl("about:blank");
		option3WV.loadUrl("about:blank");
		option4WV.loadUrl("about:blank");
		option5WV.loadUrl("about:blank");

		questionWV.loadData("<html><body><p>" + question.getQuestions() + "</p></body></html>", "text/html", "utf-8");
		option1WV.loadData("<html><body><p>" + question.getOption1() + "</p></body></html>", "text/html", "utf-8");
		option2WV.loadData("<html><body><p>" + question.getOption2() + "</p></body></html>", "text/html", "utf-8");
		option3WV.loadData("<html><body><p>" + question.getOption3() + "</p></body></html>", "text/html", "utf-8");
		option4WV.loadData("<html><body><p>" + question.getOption4() + "</p></body></html>", "text/html", "utf-8");
		option5WV.loadData("<html><body><p>" + question.getOption5() + "</p></body></html>", "text/html", "utf-8");
		if (TextUtils.isEmpty(question.getOption5())) {
			option5LL.setVisibility(View.GONE);
		} else {
			option5LL.setVisibility(View.VISIBLE);
		}
			/*option1.setText(question.getOption1());
			option2.setText(question.getOption2());
			option3.setText(question.getOption3());
			option4.setText(question.getOption4());
			option5.setText(question.getOption5());*/

		startTime = Calendar.getInstance().getTimeInMillis();

	}

	@OnClick(R.id.nextBtn)
	public void onclickNest() {
		if (null != questions) {
			Question question = questions.get(questionNo);
			if (option1.isChecked()) {
				question.setOptionSelected(1);
				saveOptionNUpdateServer(question, 1);
			} else if (option2.isChecked()) {
				question.setOptionSelected(2);
				saveOptionNUpdateServer(question, 2);
			} else if (option3.isChecked()) {
				question.setOptionSelected(3);
				saveOptionNUpdateServer(question, 3);
			} else if (option4.isChecked()) {
				question.setOptionSelected(4);
				saveOptionNUpdateServer(question, 4);
			} else if (option5.isChecked()) {
				question.setOptionSelected(5);
				saveOptionNUpdateServer(question, 5);
			} else {
				question.setOptionSelected(0);

			}
			loadNextQuestion();
		}
	}

	@OnClick(R.id.backBtn)
	public void onclickBack() {
		if (null != questions) {
			Question question = questions.get(questionNo);
			if (option1.isChecked()) {
				question.setOptionSelected(1);
				saveOptionNUpdateServer(question, 1);
			} else if (option2.isChecked()) {
				question.setOptionSelected(2);
				saveOptionNUpdateServer(question, 2);
			} else if (option3.isChecked()) {
				question.setOptionSelected(3);
				saveOptionNUpdateServer(question, 3);
			} else if (option4.isChecked()) {
				question.setOptionSelected(4);
				saveOptionNUpdateServer(question, 4);
			} else if (option5.isChecked()) {
				question.setOptionSelected(5);
				saveOptionNUpdateServer(question, 5);
			} else {
				question.setOptionSelected(0);

			}
			loadBackQuestion();
		}
	}

	@OnClick(R.id.pauseBtn)
	public void onclickPause() {

		if (null != questions && test.getIdTestType() != 2) {
			Question question = questions.get(questionNo);
			if (option1.isChecked()) {
				question.setOptionSelected(1);
				saveOptionNUpdateServer(question, 1);
			} else if (option2.isChecked()) {
				question.setOptionSelected(2);
				saveOptionNUpdateServer(question, 2);
			} else if (option3.isChecked()) {
				question.setOptionSelected(3);
				saveOptionNUpdateServer(question, 3);
			} else if (option4.isChecked()) {
				question.setOptionSelected(4);
				saveOptionNUpdateServer(question, 4);
			} else if (option5.isChecked()) {
				question.setOptionSelected(5);
				saveOptionNUpdateServer(question, 5);
			} else {
				question.setOptionSelected(0);
			}
			finish();
		} else {
			finish();
		}
	}

	@OnClick(R.id.endBtn)
	public void onclickEndTest() {
		if (null != questions) {
			Question question = questions.get(questionNo);
			if (option1.isChecked()) {
				question.setOptionSelected(1);
				saveOptionNEndTest(question, 1);
			} else if (option2.isChecked()) {
				question.setOptionSelected(2);
				saveOptionNEndTest(question, 2);
			} else if (option3.isChecked()) {
				question.setOptionSelected(3);
				saveOptionNEndTest(question, 3);
			} else if (option4.isChecked()) {
				question.setOptionSelected(4);
				saveOptionNEndTest(question, 4);
			} else if (option5.isChecked()) {
				question.setOptionSelected(5);
				saveOptionNEndTest(question, 5);
			} else {
				question.setOptionSelected(0);
				saveOptionNEndTest(question, 0);
			}
		}
	}

	private void saveOptionNEndTest(Question question, int id) {
		endTime = Calendar.getInstance().getTimeInMillis();
		final long temp = endTime - startTime;
		int seconds = (int) (temp / 1000) % 60;
		int minutes = (int) ((temp / (1000 * 60)) % 60);
		int hours = (int) ((temp / (1000 * 60 * 60)) % 24);
		if (hours > 9) {
			timer = hours + ":";
		} else {
			timer = "0" + hours + ":";
		}

		if (minutes > 9) {
			timer += minutes + ":";
		} else {
			timer += "0" + minutes + ":";
		}
		if (seconds > 9) {
			timer += seconds;
		} else {
			timer += "0" + seconds;
		}

		//new SimpleDateFormat("HH:mm:ss").format(new Date(endTime - startTime));
//		cmaVidyaApp.showToast("Option " + question.getRightAnswer() + " is the answer");
		dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().update(question);
		try {

			//List<Question> tempQuestionList = dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", idTestLogMaster.getIdTestLogMaster()).and().eq("optionSelected", 0).query();
			UpdateAnswer updateAnswer = new UpdateAnswer();
			updateAnswer.setUserName(preferences.getUserName());
			updateAnswer.setIdTestLogMaster(idTestLogMaster);
			updateAnswer.setIdTestLog(question.getIdTestLog());
			updateAnswer.setOptionSelected(id);
			updateAnswer.setTakenTime(timer);
			//if (tempQuestionList.size() == 0) {
			//	updateAnswer.setTestEnd(true);
			//	} else {
			updateAnswer.setTestEnd(true);
			//	}
			if (cmaVidyaApp.getApiRequestHelper().checkNetwork()) {
				CommonTaskExecutor.updateAnswer(cmaVidyaApp, updateAnswer, new CommonTaskExecutor.OnPostExecute() {
					@Override
					public void onPostExecute(Object object) {
						if (null != object) {
							final Dialog dialog = new Dialog(TestQuestionActivity.this);
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.popup_result);
							dialog.setCancelable(false);
							Button button = (Button) dialog.findViewById(R.id.okBtn);
							WebView webView = (WebView) dialog.findViewById(R.id.webViewWV);
							button.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									dialog.dismiss();
									test.setTestEnd(true);
									dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().update(test);
									finish();
								}
							});
							webView.loadData(object.toString(), "text/html", "utf-8");
							dialog.show();
						}
					}
				});
			} else {
				cmaVidyaApp.showToast("No Active Internet Connection Detected");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*final Dialog dialog = new Dialog(TestQuestionActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_explanation_answer);
		dialog.setCancelable(false);
		Button button = (Button) dialog.findViewById(R.id.popupExplanationOkBtn);
		WebView webView = (WebView) dialog.findViewById(R.id.popupExplanationAnswerWV);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadNextQuestion();
				dialog.dismiss();
			}
		});
		webView.loadData(question.getExplanation(), "text/html", "utf-8");
		dialog.show();*/
	}


	private void saveOptionNUpdateServer(Question question, int id) {
		endTime = Calendar.getInstance().getTimeInMillis();
		long temp = endTime - startTime;
		int seconds = (int) (temp / 1000) % 60;
		int minutes = (int) ((temp / (1000 * 60)) % 60);
		int hours = (int) ((temp / (1000 * 60 * 60)) % 24);
		if (hours > 9) {
			timer = hours + ":";
		} else {
			timer = "0" + hours + ":";
		}

		if (minutes > 9) {
			timer += minutes + ":";
		} else {
			timer += "0" + minutes + ":";
		}
		if (seconds > 9) {
			timer += seconds;
		} else {
			timer += "0" + seconds;
		}

		//new SimpleDateFormat("HH:mm:ss").format(new Date(endTime - startTime));
//		cmaVidyaApp.showToast("Option " + question.getRightAnswer() + " is the answer");
		dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().update(question);
		try {

			//List<Question> tempQuestionList = dbUtils.getDatabaseHelper().getQuestionRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", idTestLogMaster.getIdTestLogMaster()).and().eq("optionSelected", 0).query();
			UpdateAnswer updateAnswer = new UpdateAnswer();
			updateAnswer.setUserName(preferences.getUserName());
			updateAnswer.setIdTestLogMaster(idTestLogMaster);
			updateAnswer.setIdTestLog(question.getIdTestLog());
			updateAnswer.setOptionSelected(id);
			updateAnswer.setTakenTime(timer);
			//if (tempQuestionList.size() == 0) {
			//	updateAnswer.setTestEnd(true);
			//	} else {
			updateAnswer.setTestEnd(false);
			//	}
			if (cmaVidyaApp.getApiRequestHelper().checkNetwork()) {
				CommonTaskExecutor.updateAnswer(cmaVidyaApp, updateAnswer, new CommonTaskExecutor.OnPostExecute() {
					@Override
					public void onPostExecute(Object object) {
						if (null != object) {
							cmaVidyaApp.showToast(object.toString());
							final Dialog dialog = new Dialog(TestQuestionActivity.this);
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.popup_result);
							dialog.setCancelable(false);
							Button button = (Button) dialog.findViewById(R.id.okBtn);
							WebView webView = (WebView) dialog.findViewById(R.id.webViewWV);
							button.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
							webView.loadData(object.toString(), "text/html", "utf-8");
							dialog.show();
						}
					}
				});
			} else {
				cmaVidyaApp.showToast("No Active Internet Connection Detected");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*final Dialog dialog = new Dialog(TestQuestionActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_explanation_answer);
		dialog.setCancelable(false);
		Button button = (Button) dialog.findViewById(R.id.popupExplanationOkBtn);
		WebView webView = (WebView) dialog.findViewById(R.id.popupExplanationAnswerWV);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadNextQuestion();
				dialog.dismiss();
			}
		});
		webView.loadData(question.getExplanation(), "text/html", "utf-8");
		dialog.show();*/
	}


	private void loadNextQuestion() {
		if (questions.size() == (questionNo + 1)) {
			cmaVidyaApp.showToast("No next question in this test ");
		} else {
			questionRG.clearCheck();
			questionNo++;
			loadQuestion();
		}
	}

	private void loadBackQuestion() {
		if (questionNo == 0) {
			cmaVidyaApp.showToast("No Previous question in this test ");
		} else {
			questionRG.clearCheck();
			questionNo--;
			loadQuestion();
		}
	}
}
