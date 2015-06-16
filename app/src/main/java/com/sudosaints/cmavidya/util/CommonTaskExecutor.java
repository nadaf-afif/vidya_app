package com.sudosaints.cmavidya.util;

import android.app.Activity;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.sudosaints.cmavidya.CMAVidyaApp;
import com.sudosaints.cmavidya.Preferences;
import com.sudosaints.cmavidya.api.ApiRequestHelper;
import com.sudosaints.cmavidya.api.ApiResponse;
import com.sudosaints.cmavidya.db.DBUtils;
import com.sudosaints.cmavidya.db.DatabaseHelper;
import com.sudosaints.cmavidya.dto.CreateNewThreadDTO;
import com.sudosaints.cmavidya.dto.FixedTestDTO;
import com.sudosaints.cmavidya.dto.PrepareTestDTO;
import com.sudosaints.cmavidya.dto.PreviousTestDTO;
import com.sudosaints.cmavidya.dto.TemplateTestDTO;
import com.sudosaints.cmavidya.dto.UpdateAnswer;
import com.sudosaints.cmavidya.dto.UpdateThreadDTO;
import com.sudosaints.cmavidya.dto.UserEventDTO;
import com.sudosaints.cmavidya.model.Course;
import com.sudosaints.cmavidya.model.FixedTest;
import com.sudosaints.cmavidya.model.ForumSubject;
import com.sudosaints.cmavidya.model.ForumThread;
import com.sudosaints.cmavidya.model.ForumTopic;
import com.sudosaints.cmavidya.model.KeyNotes;
import com.sudosaints.cmavidya.model.MasterForumTopic;
import com.sudosaints.cmavidya.model.PlanEvents;
import com.sudosaints.cmavidya.model.PreviousTest;
import com.sudosaints.cmavidya.model.Subject;
import com.sudosaints.cmavidya.model.SubjectReplanData;
import com.sudosaints.cmavidya.model.TemplateTest;
import com.sudosaints.cmavidya.model.Test;
import com.sudosaints.cmavidya.model.TestInfo;
import com.sudosaints.cmavidya.model.Topic;
import com.sudosaints.cmavidya.model.TopicReplanData;
import com.sudosaints.cmavidya.model.User;
import com.sudosaints.cmavidya.model.VideoInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by inni on 14/8/14.
 */
public class CommonTaskExecutor {

	public static void getKeyNotes(final CMAVidyaApp cmaVidyaApp, final OnPostExecute onPostExecute, final DatabaseHelper databaseHelper) {
		cmaVidyaApp.getApiRequestHelper().getKeyNotesData(new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				if (apiResponse.isSuccess()) {
					List<Map<String, Object>> mapList = (List<Map<String, Object>>) apiResponse.getData();
					List<KeyNotes> keyNotesList = DataMapParser.parseKeyNotes(mapList);

					for (KeyNotes keyNotes : keyNotesList) {
						databaseHelper.getKeyNotesRuntimeExceptionDao().create(keyNotes);
					}

					onPostExecute.onPostExecute(true);
				} else {
					cmaVidyaApp.showToast(apiResponse.getError().getMessage());
					onPostExecute.onPostExecute(false);
				}
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(false);
			}
		});
	}

	public static void updateCourses(final CMAVidyaApp cmaVidyaApp, final OnPostExecute onPostExecute, final DatabaseHelper databaseHelper) {
		/*final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getString(R.string.getting_data));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();*/

		cmaVidyaApp.getApiRequestHelper().getAllCourss(new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				if (apiResponse.isSuccess()) {
					List<Map<String, Object>> mapList = (List<Map<String, Object>>) apiResponse.getData();
					List<Course> courseList = DataMapParser.parseCourses(mapList);
					RuntimeExceptionDao<Course, Integer> courseDao = databaseHelper.getCourseRuntimeExceptionDao();
					List<Course> tblCourses = courseDao.queryForAll();
					for (Course courses : tblCourses) {
						courseDao.delete(courses);
					}
					for (Course course : courseList) {
						Course tblCourse = new Course();
						tblCourse.setCourseName(course.getCourseName());
						tblCourse.setCourseId(course.getCourseId());
						courseDao.create(tblCourse);
					}
					onPostExecute.onPostExecute(true);

				} else {
					cmaVidyaApp.showToast(apiResponse.getError().getMessage());
					onPostExecute.onPostExecute(true);
				}
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(false);
			}
		});
	}

	public static void registerUser(final CMAVidyaApp cmaVidyaApp, final OnPostExecute onPostExecute, User user) {
	   /* ProgressDialog progressDialog = new ProgressDialog(cmaVidyaApp);
		progressDialog.setMessage("loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();*/
		cmaVidyaApp.getApiRequestHelper().registerUser(new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse);
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse);
			}
		}, user);

	}

	public static void loginUser(final CMAVidyaApp cmaVidyaApp, final OnPostExecute onPostExecute, final User user) {
		cmaVidyaApp.getApiRequestHelper().loginUser(new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse);
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse);
			}
		}, user);

	}

	public static void getMyPlanDetails(final CMAVidyaApp cmaVidyaApp, final OnPostExecute onPostExecute, String username) {
		cmaVidyaApp.getApiRequestHelper().getMyPlanDetails(new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse);
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse);
			}
		}, username);
	}

	public static void postPrePonePostPonePlan(final CMAVidyaApp cmaVidyaApp, String username, String type, int hours, PlanEvents planEvents, final OnPostExecute onPostExecute) {

		cmaVidyaApp.getApiRequestHelper().postPrePonePostPonePlanEvent(new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse);
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse);
			}
		}, username, hours, type, planEvents);
	}

	public static void postponeByDay(final CMAVidyaApp cmaVidyaApp, String username, long idPlannerOutputDateWiseTime, final OnPostExecute onPostExecute) {
		cmaVidyaApp.getApiRequestHelper().postponeByDate(username, idPlannerOutputDateWiseTime, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse);
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse);
			}
		});
	}

	public static void setUserPreferance(Preferences userPreferance, User user) {
		userPreferance.setMobile(user.getMobile());
		userPreferance.setFirstName(user.getFirstname());
		userPreferance.setGender(user.isIsmale());
		userPreferance.setLastName(user.getLastname());
		userPreferance.setPassword(user.getPassword());
		userPreferance.setUserEmail(user.getEmail());
		userPreferance.setMasterCourseId(user.getCourseId());
		userPreferance.setUserName(user.getUsername());
	}

	public static User getUserPreferance(Preferences preferences) {
		if (null == preferences.getUserEmail()) {
			return null;
		}
		User user = new User();
		user.setCourseId(preferences.getMasterCourseId());
		user.setEmail(preferences.getUserEmail());
		user.setFirstname(preferences.getFirstName());
		user.setLastname(preferences.getLastName());
		user.setMobile(preferences.getMobile());
		user.setIsmale(preferences.getGender());
		user.setPassword(preferences.getPassword());
		return user;
	}

	public static void logOutUser(Preferences preferences, DatabaseHelper dbHelper) {
		preferences.setPassword(null);
		preferences.setFirstName(null);
		preferences.setLastName(null);
		preferences.setUserEmail(null);
		preferences.setGender(false);
		preferences.setPassword(null);
		preferences.setUserId(0);
		preferences.setMobile(null);
		preferences.setAccessToken(null);
		preferences.setMasterCourseId(0);
		preferences.setUserName(null);

		dbHelper.truncateCalenderPlanTable();
		dbHelper.truncateCourseTable();
		dbHelper.truncateTopicTable();
		dbHelper.truncateSubjectTable();
		dbHelper.truncatePlanEventsTable();
		dbHelper.truncateKeyNotesTable();
		dbHelper.truncateTopicReplanDataTable();
		dbHelper.truncateSubjectReplanDataTable();
		dbHelper.truncateTestinfoTable();
		dbHelper.truncateTestTable();
		dbHelper.truncateQuestionTable();
		dbHelper.truncateMasterForumTopicTable();
		dbHelper.truncateForumTopicTable();
		dbHelper.truncatePreviousTest();
		dbHelper.truncatePreviousExam();
		dbHelper.truncateTemplateTest();
		dbHelper.truncateFixedTest();

	}

	public static void setPlanEventsToDb(final DBUtils dbUtils, List<Map<String, Object>> mapList, final OnPostExecute onPostExecute) {

		dbUtils.getDatabaseHelper().truncatePlanEventsTable();

		final List<PlanEvents> planEventses = DataMapParser.getPlanEvents(mapList);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (PlanEvents events : planEventses) {
					try {
						dbUtils.getDatabaseHelper().getPlanEventsRuntimeExceptionDao().create(events);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				onPostExecute.onPostExecute(true);

			}
		}).start();


	}

	public static void fetchNStoreSubjectListForTest(final DBUtils dbUtils, final CMAVidyaApp cmaVidyaApp, String username, final OnPostExecute onPostExecute) {
		cmaVidyaApp.getApiRequestHelper().getSubjectListForTest(username, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				dbUtils.getDatabaseHelper().truncateSubjectTable();
				final List<Subject> subjects = DataMapParser.parseSubjectListForTest((Map) apiResponse.getData());
				new Thread(new Runnable() {
					@Override
					public void run() {
						for (Subject subject : subjects) {
							dbUtils.getDatabaseHelper().getSubjectRuntimeExceptionDao().create(subject);
						}
						onPostExecute.onPostExecute(true);
					}
				}).start();

			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(false);
			}
		});
	}

	public static void fetchNStoreTopicListForTest(final DBUtils dbUtils, final CMAVidyaApp cmaVidyaApp, String username, final OnPostExecute onPostExecute) {
		cmaVidyaApp.getApiRequestHelper().getTopicForTest(username, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				final List<Topic> topics = DataMapParser.parseTopicListForTest((Map<String, Object>) apiResponse.getData());
				dbUtils.getDatabaseHelper().truncateTopicTable();
				new Thread(new Runnable() {
					@Override
					public void run() {
						for (Topic topic : topics) {
							dbUtils.getDatabaseHelper().getTopicRuntimeExceptionDao().create(topic);
						}
						onPostExecute.onPostExecute(true);
					}
				}).start();
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(false);

			}
		});

	}

	public static void setSubjectReplanDataToDb(final DBUtils dbUtils, Map<String, Object> map, final OnPostExecute onPostExecute) {
		dbUtils.getDatabaseHelper().truncateSubjectReplanDataTable();
		final List<SubjectReplanData> subjectReplanDatas = DataMapParser.parseSubjectReplanDataList(map);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (SubjectReplanData subjectReplanData : subjectReplanDatas) {
					dbUtils.getDatabaseHelper().getSubjectReplanDataRuntimeExceptionDao().create(subjectReplanData);
				}
				onPostExecute.onPostExecute(true);
			}
		}).start();
	}

	public static void setTopicReplanDataToDb(final DBUtils dbUtils, Map<String, Object> map, final OnPostExecute onPostExecute) {
		dbUtils.getDatabaseHelper().truncateTopicReplanDataTable();
		final List<TopicReplanData> topicReplanDatas = DataMapParser.parseTopicReplanDataList(map);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (TopicReplanData topicReplanData : topicReplanDatas) {
					dbUtils.getDatabaseHelper().getTopicReplanDataRuntimeExceptionDao().create(topicReplanData);
				}
				onPostExecute.onPostExecute(true);
			}
		}).start();
	}

	public static List<Integer> getSubjectRevesions(DBUtils dbUtils) {
		List<Integer> integerList = new ArrayList<Integer>();
		try {
			ArrayList<SubjectReplanData> subjectReplanDatas = (ArrayList) dbUtils.getDatabaseHelper().getSubjectReplanDataRuntimeExceptionDao().queryBuilder().distinct().groupBy("RevisionNo").query();
			for (SubjectReplanData subjectReplanData : subjectReplanDatas) {
				integerList.add((int) subjectReplanData.getRevisionNo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return integerList;
	}

	public static List<Integer> getTopicRevesions(DBUtils dbUtils, String subjectName) {
		List<Integer> integerList = new ArrayList<Integer>();
		try {
			ArrayList<TopicReplanData> topicReplanDatas = (ArrayList) dbUtils.getDatabaseHelper().getTopicReplanDataRuntimeExceptionDao().queryBuilder().groupBy("RevisionNo").where().eq("Subject", subjectName).query();
			for (TopicReplanData topicReplanData : topicReplanDatas) {
				integerList.add((int) topicReplanData.getRevisionNo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return integerList;
	}

	public static List<String> getTopicSubjects(DBUtils dbUtils) {
		List<String> stringList = new ArrayList<String>();
		try {
			ArrayList<TopicReplanData> topicReplanDatas = (ArrayList) dbUtils.getDatabaseHelper().getTopicReplanDataRuntimeExceptionDao().queryBuilder().distinct().groupBy("Subject").query();
			for (TopicReplanData topicReplanData : topicReplanDatas) {
				stringList.add(topicReplanData.getSubject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringList;
	}

	public static void generateTopicTest(final CMAVidyaApp cmaVidyaApp, final Activity activity, PrepareTestDTO prepareTestDTO, final DBUtils dbUtils, final OnPostExecute onPostExecute) {

		cmaVidyaApp.getApiRequestHelper().generateTopicTest(prepareTestDTO, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				final TestInfo testInfo = DataMapParser.parseTestInfo((Map) apiResponse.getData());
				dbUtils.getDatabaseHelper().getTestInfoRuntimeExceptionDao().create(testInfo);
				//final Dialog dialog1 = new Dialog(activity);
				//	dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
				//	dialog1.setContentView(R.layout.popup_bookmark);
				//	dialog1.setCancelable(false);
				//	final EditText bookmarkET = (EditText) dialog1.findViewById(R.id.editTextView);
				//	Button saveBookmark = (Button) dialog1.findViewById(R.id.saveBookmarkBtn);
				//	saveBookmark.setOnClickListener(new View.OnClickListener() {
				//	@Override
				//	public void onClick(View v) {
				//	if (!TextUtils.isEmpty(bookmarkET.getText().toString()))// {
				testInfo.setBookMarkName("");
				testInfo.setTimeOfCreation(Calendar.getInstance().getTimeInMillis());
				dbUtils.getDatabaseHelper().getTestInfoRuntimeExceptionDao().update(testInfo);
				//	dialog1.dismiss();
				onPostExecute.onPostExecute(testInfo);
				//		}
				//		}
				//	});
				//	dialog1.show();


			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void generateSubjectTest(final CMAVidyaApp cmaVidyaApp, final Activity activity, PrepareTestDTO prepareTestDTO, final DBUtils dbUtils, final OnPostExecute onPostExecute) {

		cmaVidyaApp.getApiRequestHelper().generateSubjectTest(prepareTestDTO, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				final TestInfo testInfo = DataMapParser.parseTestInfo((Map) apiResponse.getData());
				dbUtils.getDatabaseHelper().getTestInfoRuntimeExceptionDao().create(testInfo);
				//final Dialog dialog1 = new Dialog(activity);
				//	dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
				//	dialog1.setContentView(R.layout.popup_bookmark);
				//	dialog1.setCancelable(false);
				//	final EditText bookmarkET = (EditText) dialog1.findViewById(R.id.editTextView);
				//	Button saveBookmark = (Button) dialog1.findViewById(R.id.saveBookmarkBtn);
				//	saveBookmark.setOnClickListener(new View.OnClickListener() {
				//	@Override
				//	public void onClick(View v) {
				//	if (!TextUtils.isEmpty(bookmarkET.getText().toString()))// {
				testInfo.setBookMarkName("");
				testInfo.setTimeOfCreation(Calendar.getInstance().getTimeInMillis());
				dbUtils.getDatabaseHelper().getTestInfoRuntimeExceptionDao().update(testInfo);
				//	dialog1.dismiss();
				onPostExecute.onPostExecute(testInfo);
				//		}
				//		}
				//	});
				//	dialog1.show();


			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void updateAnswer(CMAVidyaApp app, UpdateAnswer updateAnswer, final OnPostExecute onPostExecute) {
		app.getApiRequestHelper().updateAnswer(updateAnswer, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				Map<String, Object> objectMap = (Map) apiResponse.getData();
				Map<String, Object> oTestLog = (Map) objectMap.get("oTestLog");
				String comments = oTestLog.get("Comments") + "";
				if (comments.length() == 0) {
					onPostExecute.onPostExecute(null);
				} else {
					onPostExecute.onPostExecute(comments);
				}
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});

	}

	public static void updateMasterForumTopicsData(final CMAVidyaApp app, final Activity activity, final OnPostExecute onPostExecute) {
		app.getApiRequestHelper().getMasterforumTopics(app.getPreferences().getMasterCourseId(), new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {

				Map<String, Object> objectMap = (Map) apiResponse.getData();
				final List<MasterForumTopic> masterForumTopics = DataMapParser.parseMasterForumTopicList((List<Map<String, Object>>) objectMap.get("oList"));
				final DBUtils dbUtils = new DBUtils(activity);
				dbUtils.getDatabaseHelper().truncateMasterForumTopicTable();
				dbUtils.getDatabaseHelper().truncateForumSubjectTable();
				dbUtils.getDatabaseHelper().truncateForumTopicTable();
				dbUtils.getDatabaseHelper().truncateForumTreadTable();
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						for (MasterForumTopic forumTopic : masterForumTopics) {
							try {
								dbUtils.getDatabaseHelper().getMasterForumTopicRuntimeExceptionDao().create(forumTopic);
							} catch (Exception e) {
								e.printStackTrace();
							}


							updateForumSubjectData(app, activity, forumTopic.getForumMasterID());
						}
						onPostExecute.onPostExecute(true);
					}
				});
				thread.start();
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(false);
			}
		});
	}

	public static void updateForumSubjectData(final CMAVidyaApp app, final Activity activity, int masterForumId) {
		app.getApiRequestHelper().getForumSubjectFromMasterFourmTopic(masterForumId, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				Map<String, Object> objectMap = (Map<String, Object>) apiResponse.getData();
				final List<ForumSubject> forumSubjects = DataMapParser.parseForumSubjectList((List<Map<String, Object>>) objectMap.get("oList"));
				final DBUtils dbUtils = new DBUtils(activity);
				new Thread(new Runnable() {
					@Override
					public void run() {
						for (ForumSubject forumSubject : forumSubjects) {
							try {
								dbUtils.getDatabaseHelper().getForumSubjectRuntimeExceptionDao().create(forumSubject);
							} catch (Exception e) {
								e.printStackTrace();
							}


							updateForumTopicsData(app, activity, (int) forumSubject.getId());
						}
					}
				}).start();
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {

			}
		});
	}

	public static void updateForumTopicsData(final CMAVidyaApp app, final Activity activity, final int forumSubjectId) {
		app.getApiRequestHelper().getForumTopics(forumSubjectId, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				Map<String, Object> objectMap = (Map) apiResponse.getData();
				final List<ForumTopic> forumTopics = DataMapParser.parseForumTopicList((List<Map<String, Object>>) objectMap.get("oList"));
				final DBUtils dbUtils = new DBUtils(activity);
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						for (ForumTopic forumTopic : forumTopics) {
							try {
								dbUtils.getDatabaseHelper().getForumTopicRuntimeExceptionDao().create(forumTopic);
							} catch (Exception e) {
								e.printStackTrace();
							}
							updateForumThreadData(app, activity, forumTopic.getId());
						}
					}
				});
				thread.start();
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
			}
		});
	}

	public static void updateForumThreadData(CMAVidyaApp app, final Activity activity, long forumThreadId) {
		int startRow = 0, maxRows = 2147483647;
		app.getApiRequestHelper().getForumThreadByForumId(forumThreadId, startRow, maxRows, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				Map<String, Object> objectMap = (Map<String, Object>) apiResponse.getData();
				final List<ForumThread> forumThreads = DataMapParser.parseForumThreads((List<Map<String, Object>>) objectMap.get("oList"));
				final DBUtils dbUtils = new DBUtils(activity);
				new Thread(new Runnable() {
					@Override
					public void run() {
						for (ForumThread forumThread : forumThreads) {
							try {
								dbUtils.getDatabaseHelper().getForumThreadRuntimeExceptionDao().create(forumThread);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}).start();

			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
			}
		});
	}

	public static void updateThread(CMAVidyaApp app, UpdateThreadDTO updateThreadDTO, final OnPostExecute onPostExecute) {
		app.getApiRequestHelper().updateThread(updateThreadDTO, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				Map<String, Object> objectMap = (Map<String, Object>) apiResponse.getData();
				onPostExecute.onPostExecute(objectMap.get("message") + "");
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse.getError().getMessage());
			}
		});

	}

	public static void createNewThread(CMAVidyaApp app, CreateNewThreadDTO createNewThreadDTO, final OnPostExecute onPostExecute) {
		app.getApiRequestHelper().createNewThreadPost(createNewThreadDTO, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(true);
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void getForumThreadsData(CMAVidyaApp app, long threadId, final OnPostExecute onPostExecute) {
		app.getApiRequestHelper().getForumThreads(threadId, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				Map<String, Object> objectMap = (Map<String, Object>) apiResponse.getData();
				onPostExecute.onPostExecute(DataMapParser.parseForumThreads((List<Map<String, Object>>) objectMap.get("oList")));
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void clodeThread(CMAVidyaApp app, final Activity activity, long threadID, final OnPostExecute onPostExecute) {
		app.getApiRequestHelper().closeThread(threadID, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				Map<String, Object> objectMap = (Map<String, Object>) apiResponse.getData();
				onPostExecute.onPostExecute(objectMap.get("message") + "");
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(apiResponse.getError().getMessage());
			}
		});
	}

	public static void loadTest(CMAVidyaApp app, final Activity activity, String username, long idTestLogMaster, final OnPostExecute onPostExecute) {
		app.getApiRequestHelper().loadTest(username, idTestLogMaster, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				DBUtils dbUtils = new DBUtils(activity);
				if (apiResponse.isSuccess()) {
					Test test = DataMapParser.parseTest(dbUtils, (Map<String, Object>) apiResponse.getData());
					dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().createOrUpdate(test);
					onPostExecute.onPostExecute(test);
				} else {
					onPostExecute.onPostExecute(null);
				}

			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void getNUpdateAllPreviousTest(CMAVidyaApp app, final Activity activity, final OnPostExecute onPostExecute) {
		Preferences preferences = new Preferences(activity);
		final DBUtils dbUtils = new DBUtils(activity);
		app.getApiRequestHelper().getAllPreviousTest(preferences.getUserName(), new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				if (apiResponse.isSuccess()) {
					dbUtils.getDatabaseHelper().truncatePreviousExam();
					dbUtils.getDatabaseHelper().truncatePreviousTest();
					Map<String, Object> objectMap = (Map) apiResponse.getData();
					final List<PreviousTest> previousTests = DataMapParser.parsePreviousTest(dbUtils, objectMap);
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							for (PreviousTest previousTest : previousTests) {
								dbUtils.getDatabaseHelper().getPreviousTestRuntimeExceptionDao().create(previousTest);
							}
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									onPostExecute.onPostExecute(previousTests);
								}
							});

						}
					});
					thread.start();
				} else {
					onPostExecute.onPostExecute(null);
				}
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void getNUpdateTemplateTest(CMAVidyaApp app, final Activity activity, final OnPostExecute onPostExecute) {
		Preferences preferences = new Preferences(activity);
		final DBUtils dbUtils = new DBUtils(activity);
		app.getApiRequestHelper().getTemplateTestList(preferences.getUserName(), new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				if (apiResponse.isSuccess()) {
					Map<String, Object> objectMap = (Map) apiResponse.getData();
					final List<TemplateTest> templateTests = DataMapParser.parseTemplateTestList(objectMap);
					dbUtils.getDatabaseHelper().truncateTemplateTest();
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							for (TemplateTest templateTest : templateTests) {
								dbUtils.getDatabaseHelper().getTemplateTestRuntimeExceptionDao().create(templateTest);
							}
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									onPostExecute.onPostExecute(templateTests);
								}
							});
						}
					});
					thread.start();

				} else {
					onPostExecute.onPostExecute(null);
				}
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void generateTemplateTest(CMAVidyaApp app, final Activity activity, TemplateTestDTO templateTestDTO, final OnPostExecute onPostExecute) {
		final DBUtils dbUtils = new DBUtils(activity);
		app.getApiRequestHelper().genrateTemplateTest(templateTestDTO, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				if (apiResponse.isSuccess()) {
					Map<String, Object> objectMap = (Map) apiResponse.getData();
					Map<String, Object> oTest = (Map) objectMap.get("oTest");
					long idTestLogMaster = Long.parseLong(oTest.get("IdTestLogMaster") + "");
					onPostExecute.onPostExecute(idTestLogMaster);

				} else {
					onPostExecute.onPostExecute(null);
				}
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void generatePreviousTest(CMAVidyaApp app, final Activity activity, PreviousTestDTO previousTestDTO, final OnPostExecute onPostExecute) {
		final DBUtils dbUtils = new DBUtils(activity);
		app.getApiRequestHelper().generatePreviousTest(previousTestDTO, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				if (apiResponse.isSuccess()) {
					Map<String, Object> objectMap = (Map) apiResponse.getData();
					Map<String, Object> oTest = (Map) objectMap.get("oTest");
					long idTestLogMaster = Long.parseLong(oTest.get("IdTestLogMaster") + "");
					onPostExecute.onPostExecute(idTestLogMaster);

				} else {
					onPostExecute.onPostExecute(null);
				}
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);

			}
		});
	}

	public static void generateFixedTest(CMAVidyaApp app, final Activity activity, FixedTestDTO fixedTestDTO, final OnPostExecute onPostExecute) {
		final DBUtils dbUtils = new DBUtils(activity);
		app.getApiRequestHelper().generateFixedTest(fixedTestDTO, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				if (apiResponse.isSuccess()) {
					Map<String, Object> objectMap = (Map) apiResponse.getData();
					Map<String, Object> oTest = (Map) objectMap.get("oTest");
					long idTestLogMaster = Long.parseLong(oTest.get("IdTestLogMaster") + "");
					onPostExecute.onPostExecute(idTestLogMaster);
				} else {
					onPostExecute.onPostExecute(null);
				}
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void getNUpdateFixedTest(CMAVidyaApp app, final Activity activity, final OnPostExecute onPostExecute) {
		Preferences preferences = new Preferences(activity);
		final DBUtils dbUtils = new DBUtils(activity);
		app.getApiRequestHelper().getFixedTestList(preferences.getUserName(), new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				if (apiResponse.isSuccess()) {
					Map<String, Object> objectMap = (Map) apiResponse.getData();
					final List<FixedTest> fixedTests = DataMapParser.parseFixedTestList(objectMap);
					dbUtils.getDatabaseHelper().truncateFixedTest();
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							for (FixedTest fixedTest : fixedTests) {
								dbUtils.getDatabaseHelper().getFixedTestLongRuntimeExceptionDao().create(fixedTest);
							}
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									onPostExecute.onPostExecute(fixedTests);
								}
							});
						}
					});
					thread.start();
				} else {
					onPostExecute.onPostExecute(null);
				}
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void getUserVideos(CMAVidyaApp app, final Activity activity, final OnPostExecute onPostExecute) {
		Preferences preferences = new Preferences(activity);
		final DBUtils dbUtils = new DBUtils(activity);
		app.getApiRequestHelper().getUserVideos(preferences.getUserName(), new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				if (apiResponse.isSuccess()) {
					Map<String, Object> objectMap = (Map<String, Object>) apiResponse.getData();
					final List<VideoInfo> videoInfos = DataMapParser.parseVideoInfoList(objectMap);
					dbUtils.getDatabaseHelper().truncateVideoInfoTable();
					new Thread(new Runnable() {
						@Override
						public void run() {
							for (VideoInfo videoInfo : videoInfos) {
								dbUtils.getDatabaseHelper().getVideoInfoRuntimeExceptionDao().create(videoInfo);
							}
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									onPostExecute.onPostExecute(videoInfos);
								}
							});
						}
					}).start();

				} else {
					onPostExecute.onPostExecute(null);
				}
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void createDeleteUserEvents(CMAVidyaApp app, final UserEventDTO userEventDTO, final OnPostExecute onPostExecute) {
		app.getApiRequestHelper().createDeleteUserEvent(userEventDTO, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				if (apiResponse.isSuccess()) {
					onPostExecute.onPostExecute(true);
				} else {
					onPostExecute.onPostExecute(null);
				}

			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static void getNUpdateAllSavedTest(final CMAVidyaApp app, final Activity activity, final OnPostExecute onPostExecute) {
		final Preferences preferences = new Preferences(activity);
		final DBUtils dbUtils = new DBUtils(activity);
		app.getApiRequestHelper().getAllTestDependingOnIsTestEnd(preferences.getUserName(), false, new ApiRequestHelper.OnRequestComplete() {
			@Override
			public void onSuccess(ApiResponse apiResponse) {
				if (apiResponse.isSuccess()) {
					dbUtils.getDatabaseHelper().truncateTestTable();
					Map<String, Object> objectMap = (Map) apiResponse.getData();
					final List<Test> tests = DataMapParser.parseTestList(objectMap);
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							for (Test test : tests) {
								try {
									List<Test> tempTests = null;
									tempTests = dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", test.getIdTestLogMaster()).query();
									if (null != tempTests && tempTests.size() == 0) {
										dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().create(test);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					});
					thread.start();
					app.getApiRequestHelper().getAllTestDependingOnIsTestEnd(preferences.getUserName(), true, new ApiRequestHelper.OnRequestComplete() {
						@Override
						public void onSuccess(ApiResponse apiResponse) {
							if (apiResponse.isSuccess()) {
								Map<String, Object> objectMap = (Map) apiResponse.getData();
								final List<Test> tests = DataMapParser.parseTestList(objectMap);
								Thread thread = new Thread(new Runnable() {
									@Override
									public void run() {
										for (Test test : tests) {
											try {
												List<Test> tempTests = null;
												tempTests = dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().queryBuilder().where().eq("idTestLogMaster", test.getIdTestLogMaster()).query();
												if (null != tempTests && tempTests.size() == 0) {
													dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().create(test);
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
										activity.runOnUiThread(new Runnable() {
											@Override
											public void run() {
												onPostExecute.onPostExecute(true);
											}
										});
									}
								});
								thread.start();

							} else {
								onPostExecute.onPostExecute(null);
							}
						}

						@Override
						public void onFailure(ApiResponse apiResponse) {
							onPostExecute.onPostExecute(null);
						}
					});
				} else {
					onPostExecute.onPostExecute(null);
				}
			}

			@Override
			public void onFailure(ApiResponse apiResponse) {
				onPostExecute.onPostExecute(null);
			}
		});
	}

	public static interface OnPostExecute {
		public void onPostExecute(Object object);
	}


}
