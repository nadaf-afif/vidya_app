package com.sudosaints.cmavidya.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.model.CalenderPlan;
import com.sudosaints.cmavidya.model.Course;
import com.sudosaints.cmavidya.model.FixedTest;
import com.sudosaints.cmavidya.model.ForumSubject;
import com.sudosaints.cmavidya.model.ForumThread;
import com.sudosaints.cmavidya.model.ForumTopic;
import com.sudosaints.cmavidya.model.KeyNotes;
import com.sudosaints.cmavidya.model.MasterForumTopic;
import com.sudosaints.cmavidya.model.PlanEvents;
import com.sudosaints.cmavidya.model.PreviousExam;
import com.sudosaints.cmavidya.model.PreviousTest;
import com.sudosaints.cmavidya.model.Question;
import com.sudosaints.cmavidya.model.Subject;
import com.sudosaints.cmavidya.model.SubjectReplanData;
import com.sudosaints.cmavidya.model.TemplateTest;
import com.sudosaints.cmavidya.model.Test;
import com.sudosaints.cmavidya.model.TestInfo;
import com.sudosaints.cmavidya.model.Topic;
import com.sudosaints.cmavidya.model.TopicReplanData;
import com.sudosaints.cmavidya.model.VideoInfo;

import java.sql.SQLException;

/**
 * Created by inni on 12/8/14.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "CMAVIDYA";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 2;


	// the DAO object we use to access the  table

	private RuntimeExceptionDao<CalenderPlan, Integer> calenderPlansRuntimeExceptionDao = null;
	private RuntimeExceptionDao<Course, Integer> courseRuntimeExceptionDao = null;
	private RuntimeExceptionDao<Topic, Integer> topicRuntimeExceptionDao = null;
	private RuntimeExceptionDao<Subject, Integer> subjectRuntimeExceptionDao = null;
	private RuntimeExceptionDao<PlanEvents, Long> planEventsRuntimeExceptionDao = null;
	private RuntimeExceptionDao<KeyNotes, Integer> keyNotesesRuntimeExceptionDao = null;
	private RuntimeExceptionDao<TopicReplanData, Integer> topicReplanDatasRuntimeExceptionDao = null;
	private RuntimeExceptionDao<SubjectReplanData, Integer> subjectReplanDatasRuntimeExceptionDao = null;
	private RuntimeExceptionDao<TestInfo, Long> testInfoRuntimeExceptionDao = null;
	private RuntimeExceptionDao<Test, Long> testRuntimeExceptionDao = null;
	private RuntimeExceptionDao<Question, Long> questionRuntimeExceptionDao = null;
	private RuntimeExceptionDao<MasterForumTopic, Long> masterForumTopicRuntimeExceptionDao = null;
	private RuntimeExceptionDao<ForumTopic, Integer> forumTopicRuntimeExceptionDao = null;
	private RuntimeExceptionDao<PreviousTest, Long> previousTestRuntimeExceptionDao = null;
	private RuntimeExceptionDao<PreviousExam, Long> previousExamRuntimeExceptionDao = null;
	private RuntimeExceptionDao<TemplateTest, Long> TemplateTestRuntimeExceptionDao = null;
	private RuntimeExceptionDao<FixedTest, Long> fixedTestLongRuntimeExceptionDao = null;
	private RuntimeExceptionDao<ForumThread, Long> forumThreadRuntimeExceptionDao = null;
	private RuntimeExceptionDao<ForumSubject, Long> forumSubjectRuntimeExceptionDao = null;
	private RuntimeExceptionDao<VideoInfo, Integer> videoInfoRuntimeExceptionDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, CalenderPlan.class);
			TableUtils.createTable(connectionSource, Course.class);
			TableUtils.createTable(connectionSource, Topic.class);
			TableUtils.createTable(connectionSource, Subject.class);
			TableUtils.createTable(connectionSource, PlanEvents.class);
			TableUtils.createTable(connectionSource, KeyNotes.class);
			TableUtils.createTable(connectionSource, SubjectReplanData.class);
			TableUtils.createTable(connectionSource, TopicReplanData.class);
			TableUtils.createTable(connectionSource, TestInfo.class);
			TableUtils.createTable(connectionSource, Test.class);
			TableUtils.createTable(connectionSource, Question.class);
			TableUtils.createTable(connectionSource, MasterForumTopic.class);
			TableUtils.createTable(connectionSource, ForumTopic.class);
			TableUtils.createTable(connectionSource, PreviousTest.class);
			TableUtils.createTable(connectionSource, PreviousExam.class);
			TableUtils.createTable(connectionSource, TemplateTest.class);
			TableUtils.createTable(connectionSource, FixedTest.class);
			TableUtils.createTable(connectionSource, ForumThread.class);
			TableUtils.createTable(connectionSource, ForumSubject.class);
			TableUtils.createTable(connectionSource, VideoInfo.class);


		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
		try {

			TableUtils.dropTable(connectionSource, CalenderPlan.class, true);
			TableUtils.dropTable(connectionSource, Course.class, true);
			TableUtils.dropTable(connectionSource, Topic.class, true);
			TableUtils.dropTable(connectionSource, Subject.class, true);
			TableUtils.dropTable(connectionSource, PlanEvents.class, true);
			TableUtils.dropTable(connectionSource, KeyNotes.class, true);
			TableUtils.dropTable(connectionSource, SubjectReplanData.class, true);
			TableUtils.dropTable(connectionSource, TopicReplanData.class, true);
			TableUtils.dropTable(connectionSource, TestInfo.class, true);
			TableUtils.dropTable(connectionSource, Test.class, true);
			TableUtils.dropTable(connectionSource, Question.class, true);
			if (i > 1) {
				TableUtils.dropTable(connectionSource, MasterForumTopic.class, true);
				TableUtils.dropTable(connectionSource, ForumTopic.class, true);
				TableUtils.dropTable(connectionSource, PreviousExam.class, true);
				TableUtils.dropTable(connectionSource, PreviousTest.class, true);
				TableUtils.dropTable(connectionSource, TemplateTest.class, true);
				TableUtils.dropTable(connectionSource, FixedTest.class, true);
			}
			if (i > 2) {
				TableUtils.dropTable(connectionSource, ForumThread.class, true);
				TableUtils.dropTable(connectionSource, ForumSubject.class, true);
				TableUtils.dropTable(connectionSource, VideoInfo.class, true);
			}
			// after we drop the old databases, we create the new ones
			onCreate(sqLiteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}


	synchronized public int truncateVideoInfoTable() {
		try {
			return TableUtils.clearTable(connectionSource, VideoInfo.class);
		} catch (Exception e) {
		}
		return -1;
	}

	synchronized public int truncateForumSubjectTable() {
		try {
			return TableUtils.clearTable(connectionSource, ForumSubject.class);
		} catch (Exception e) {
		}
		return -1;
	}

	synchronized public int truncateForumTreadTable() {
		try {
			return TableUtils.clearTable(connectionSource, ForumThread.class);
		} catch (Exception e) {
		}
		return -1;
	}

	synchronized public int truncateCalenderPlanTable() {
		try {
			return TableUtils.clearTable(connectionSource, CalenderPlan.class);
		} catch (Exception e) {
		}
		return -1;
	}

	synchronized public int truncatePreviousTest() {
		try {
			return TableUtils.clearTable(connectionSource, PreviousTest.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	synchronized public int truncateTemplateTest() {
		try {
			return TableUtils.clearTable(connectionSource, TemplateTest.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	synchronized public int truncatePreviousExam() {
		try {
			return TableUtils.clearTable(connectionSource, PreviousExam.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	synchronized public int truncateFixedTest() {
		try {
			return TableUtils.clearTable(connectionSource, FixedTest.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	synchronized public int truncateForumTopicTable() {
		try {

			return TableUtils.clearTable(connectionSource, ForumTopic.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	synchronized public int truncateMasterForumTopicTable() {
		try {
			return TableUtils.clearTable(connectionSource, MasterForumTopic.class);
		} catch (Exception e) {
		}
		return -1;
	}

	synchronized public int truncateQuestionTable() {
		try {
			return TableUtils.clearTable(connectionSource, Question.class);
		} catch (Exception e) {
		}
		return -1;
	}

	synchronized public int truncateCourseTable() {
		try {
			return TableUtils.clearTable(connectionSource, Course.class);
		} catch (Exception e) {
		}
		return -1;
	}

	synchronized public int truncateTopicTable() {
		try {
			return TableUtils.clearTable(connectionSource, Topic.class);
		} catch (Exception e) {
		}
		return -1;
	}

	synchronized public int truncateSubjectTable() {
		try {
			return TableUtils.clearTable(connectionSource, Subject.class);
		} catch (Exception e) {
		}
		return -1;
	}

	synchronized public int truncatePlanEventsTable() {
		try {
			return TableUtils.clearTable(connectionSource, PlanEvents.class);
		} catch (Exception e) {
		}
		return -1;
	}

	synchronized public int truncateKeyNotesTable() {
		try {
			return TableUtils.clearTable(connectionSource, KeyNotes.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	synchronized public int truncateSubjectReplanDataTable() {
		try {
			return TableUtils.clearTable(connectionSource, SubjectReplanData.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	synchronized public int truncateTopicReplanDataTable() {
		try {
			return TableUtils.clearTable(connectionSource, TopicReplanData.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	synchronized public int truncateTestinfoTable() {
		try {
			return TableUtils.clearTable(connectionSource, TestInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	synchronized public int truncateTestTable() {
		try {
			return TableUtils.clearTable(connectionSource, Test.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	synchronized public RuntimeExceptionDao<Question, Long> getQuestionRuntimeExceptionDao() {
		if (null == testInfoRuntimeExceptionDao) {
			questionRuntimeExceptionDao = getRuntimeExceptionDao(Question.class);
		}
		return questionRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<TestInfo, Long> getTestInfoRuntimeExceptionDao() {
		if (null == testInfoRuntimeExceptionDao) {
			testInfoRuntimeExceptionDao = getRuntimeExceptionDao(TestInfo.class);
		}
		return testInfoRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<CalenderPlan, Integer> getCalenderPlansRuntimeExceptionDao() {
		if (calenderPlansRuntimeExceptionDao == null) {
			calenderPlansRuntimeExceptionDao = getRuntimeExceptionDao(CalenderPlan.class);
		}
		return calenderPlansRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<Course, Integer> getCourseRuntimeExceptionDao() {
		if (null == courseRuntimeExceptionDao)
			courseRuntimeExceptionDao = getRuntimeExceptionDao(Course.class);
		return courseRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<Topic, Integer> getTopicRuntimeExceptionDao() {
		if (null == topicRuntimeExceptionDao)
			topicRuntimeExceptionDao = getRuntimeExceptionDao(Topic.class);
		return topicRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<Subject, Integer> getSubjectRuntimeExceptionDao() {
		if (null == subjectRuntimeExceptionDao)
			subjectRuntimeExceptionDao = getRuntimeExceptionDao(Subject.class);
		return subjectRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<PlanEvents, Long> getPlanEventsRuntimeExceptionDao() {
		if (null == planEventsRuntimeExceptionDao) {
			planEventsRuntimeExceptionDao = getRuntimeExceptionDao(PlanEvents.class);
		}
		return planEventsRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<KeyNotes, Integer> getKeyNotesRuntimeExceptionDao() {
		if (null == keyNotesesRuntimeExceptionDao) {
			keyNotesesRuntimeExceptionDao = getRuntimeExceptionDao(KeyNotes.class);
		}
		return keyNotesesRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<SubjectReplanData, Integer> getSubjectReplanDataRuntimeExceptionDao() {
		if (null == subjectReplanDatasRuntimeExceptionDao) {
			subjectReplanDatasRuntimeExceptionDao = getRuntimeExceptionDao(SubjectReplanData.class);
		}
		return subjectReplanDatasRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<TopicReplanData, Integer> getTopicReplanDataRuntimeExceptionDao() {
		if (null == topicReplanDatasRuntimeExceptionDao) {
			topicReplanDatasRuntimeExceptionDao = getRuntimeExceptionDao(TopicReplanData.class);
		}
		return topicReplanDatasRuntimeExceptionDao;
	}


	synchronized public RuntimeExceptionDao<Test, Long> getTestRuntimeExceptionDao() {
		if (null == testRuntimeExceptionDao) {
			testRuntimeExceptionDao = getRuntimeExceptionDao(Test.class);
		}
		return testRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<MasterForumTopic, Long> getMasterForumTopicRuntimeExceptionDao() {
		if (null == masterForumTopicRuntimeExceptionDao) {
			masterForumTopicRuntimeExceptionDao = getRuntimeExceptionDao(MasterForumTopic.class);
		}
		return masterForumTopicRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<ForumTopic, Integer> getForumTopicRuntimeExceptionDao() {
		if (null == forumTopicRuntimeExceptionDao) {
			forumTopicRuntimeExceptionDao = getRuntimeExceptionDao(ForumTopic.class);
		}
		return forumTopicRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<PreviousTest, Long> getPreviousTestRuntimeExceptionDao() {
		if (null == previousTestRuntimeExceptionDao) {
			previousTestRuntimeExceptionDao = getRuntimeExceptionDao(PreviousTest.class);
		}
		return previousTestRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<PreviousExam, Long> getPreviousExamRuntimeExceptionDao() {
		if (null == previousExamRuntimeExceptionDao) {
			previousExamRuntimeExceptionDao = getRuntimeExceptionDao(PreviousExam.class);
		}
		return previousExamRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<TemplateTest, Long> getTemplateTestRuntimeExceptionDao() {
		if (null == TemplateTestRuntimeExceptionDao) {
			TemplateTestRuntimeExceptionDao = getRuntimeExceptionDao(TemplateTest.class);
		}
		return TemplateTestRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<FixedTest, Long> getFixedTestLongRuntimeExceptionDao() {
		if (null == fixedTestLongRuntimeExceptionDao) {
			fixedTestLongRuntimeExceptionDao = getRuntimeExceptionDao(FixedTest.class);
		}
		return fixedTestLongRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<ForumThread, Long> getForumThreadRuntimeExceptionDao() {
		if (null == forumThreadRuntimeExceptionDao) {
			forumThreadRuntimeExceptionDao = getRuntimeExceptionDao(ForumThread.class);
		}
		return forumThreadRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<ForumSubject, Long> getForumSubjectRuntimeExceptionDao() {
		if (null == forumSubjectRuntimeExceptionDao) {
			forumSubjectRuntimeExceptionDao = getRuntimeExceptionDao(ForumSubject.class);
		}
		return forumSubjectRuntimeExceptionDao;
	}

	synchronized public RuntimeExceptionDao<VideoInfo, Integer> getVideoInfoRuntimeExceptionDao() {
		if (null == videoInfoRuntimeExceptionDao) {
			videoInfoRuntimeExceptionDao = getRuntimeExceptionDao(VideoInfo.class);
		}
		return videoInfoRuntimeExceptionDao;
	}


	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		calenderPlansRuntimeExceptionDao = null;
		courseRuntimeExceptionDao = null;
		topicRuntimeExceptionDao = null;
		subjectRuntimeExceptionDao = null;
		planEventsRuntimeExceptionDao = null;
		keyNotesesRuntimeExceptionDao = null;
		topicReplanDatasRuntimeExceptionDao = null;
		subjectReplanDatasRuntimeExceptionDao = null;
		testInfoRuntimeExceptionDao = null;
		testRuntimeExceptionDao = null;
		questionRuntimeExceptionDao = null;
		masterForumTopicRuntimeExceptionDao = null;
		forumTopicRuntimeExceptionDao = null;
		previousTestRuntimeExceptionDao = null;
		previousExamRuntimeExceptionDao = null;
		TemplateTestRuntimeExceptionDao = null;
		fixedTestLongRuntimeExceptionDao = null;
		forumThreadRuntimeExceptionDao = null;
		forumSubjectRuntimeExceptionDao = null;
		videoInfoRuntimeExceptionDao = null;
	}
}
