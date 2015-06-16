package com.sudosaints.cmavidya.util;

import com.j256.ormlite.dao.ForeignCollection;
import com.sudosaints.cmavidya.db.DBUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by inni on 14/8/14.
 */
public class DataMapParser {

	public static List<Course> parseCourses(List<Map<String, Object>> mapList) {

		List<Course> courseList = new ArrayList<Course>();

		for (Map<String, Object> courseMap : mapList) {
			Course course = new Course();
			course.setCourseId(Integer.parseInt(courseMap.get("IdCourseMaster").toString()));
			course.setCourseName(courseMap.get("CourseName").toString());

			courseList.add(course);
		}

		return courseList;
	}

	public static List<KeyNotes> parseKeyNotes(List<Map<String, Object>> dataMap) {

		List<KeyNotes> keyNotesList = new ArrayList<KeyNotes>();

		for (Map<String, Object> map : dataMap) {
			KeyNotes keyNotes = new KeyNotes();
			keyNotes.setAddedBy(Integer.parseInt(map.get("AddedBy") + ""));
			keyNotes.setDescription(map.get("Description") + "");
			keyNotes.setIdKeyNotes(Integer.parseInt(map.get("IdKeyNotes") + ""));
			keyNotes.setIdTopicMaster(Integer.parseInt(map.get("IdTopicMaster") + ""));
			keyNotes.setActive(Boolean.parseBoolean(map.get("IsActive") + ""));
			keyNotes.setThumbnailUrl(map.get("ThumbnailUrl") + "");
			keyNotes.setUploadedDate(map.get("UploadedDate") + "");
			keyNotes.setUploadedFileUrl(map.get("UploadedFileUrl") + "");
			keyNotesList.add(keyNotes);
		}

		return keyNotesList;
	}


   /* public static RegisterApiResponce parseRegisterUser(Object object){
		Map<String,Object> objectMap=(Map<String,Object>) object;

        RegisterApiResponce registerApiResponce= new RegisterApiResponce();
        registerApiResponce.setComments(objectMap.get("Comments").toString());
        registerApiResponce.setIdUserType(Integer.parseInt(objectMap.get("IdUserType").toString()));
        registerApiResponce.setActive(Boolean.parseBoolean(objectMap.get("IsActive").toString()));

        return registerApiResponce;

    }*/

	public static List<PlanEvents> getPlanEvents(List<Map<String, Object>> mapList) {
		List<PlanEvents> planEvents = new ArrayList<PlanEvents>();
		for (Map<String, Object> objectMap : mapList) {
			PlanEvents event = new PlanEvents();
			event.setAvavialableDayTime(Integer.parseInt(objectMap.get("AvavialableDayTime").toString()));
			event.setDayTime(Integer.parseInt(objectMap.get("DayTime").toString()));
			event.setDescription(objectMap.get("Description") + "");
			event.setIdPlannerOutPutDateWiseTime(Integer.parseInt(objectMap.get("IdPlannerOutPutDateWiseTime").toString()));
			event.setIdTopic(Integer.parseInt(objectMap.get("IdTopic").toString()));
			event.setActive(Boolean.parseBoolean(objectMap.get("IsActive").toString()));
			event.setIsRevision(Integer.parseInt(objectMap.get("IsRevision").toString()));
			event.setSubject(objectMap.get("Subject") + "");
			String date = objectMap.get("TopicDate") + "";
			if (date.contains("T00:00:00Z")) {
				date = date.replace("T00:00:00Z", "");
				event.setTopicDate((DateHelper.parseYMDDate(date).getTime()));
			} else
				event.setTopicDate((DateHelper.parseDate(date)).getTime());
			event.setTopicTime(objectMap.get("TopicTime") + "");
			event.setTopicName(objectMap.get("Topicname") + "");
			event.setTopicTimeFloat(Float.parseFloat(objectMap.get("topicTime") + ""));
			event.setRevisionNumber(Integer.parseInt(objectMap.get("RevisionNo") + ""));
			planEvents.add(event);
		}
		return planEvents;
	}

	public static List<TopicReplanData> parseTopicReplanDataList(Map<String, Object> map) {
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("oList");
		List<TopicReplanData> topicReplanDatas = new ArrayList<TopicReplanData>();
		for (Map<String, Object> objectMap : mapList) {
			TopicReplanData topicReplanData = new TopicReplanData();
			topicReplanData.setAvailableTopicRevTime(Long.parseLong(objectMap.get("AvailableTopicRevTime") + ""));
			topicReplanData.setIdIndex(Long.parseLong(objectMap.get("IdIndex") + ""));
			topicReplanData.setIdSubjectNameSakeMaster(Long.parseLong(objectMap.get("IdSubjectNameSakeMaster") + ""));
			topicReplanData.setIdTopic(Long.parseLong(objectMap.get("IdTopic") + ""));
			topicReplanData.setRevisionNo(Long.parseLong(objectMap.get("RevisionNo") + ""));
			topicReplanData.setSubject(objectMap.get("Subject") + "");
			topicReplanData.setTopicname(objectMap.get("Topicname") + "");
			topicReplanData.setActive(Boolean.parseBoolean(objectMap.get("IsActive") + ""));
			topicReplanDatas.add(topicReplanData);
		}
		return topicReplanDatas;
	}

	public static List<SubjectReplanData> parseSubjectReplanDataList(Map<String, Object> map) {
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("oList");
		List<SubjectReplanData> subjectReplanDatas = new ArrayList<SubjectReplanData>();
		for (Map<String, Object> objectMap : mapList) {
			SubjectReplanData subjectReplanData = new SubjectReplanData();
			subjectReplanData.setAvailableRevTime(Long.parseLong(objectMap.get("AvailableRevTime") + ""));
			subjectReplanData.setIdSubjectNameSakeMaster(Long.parseLong(objectMap.get("IdSubjectNameSakeMaster") + ""));
			subjectReplanData.setRevisionNo(Long.parseLong(objectMap.get("RevisionNo") + ""));
			subjectReplanData.setSubRevTime(Long.parseLong(objectMap.get("SubRevTime") + ""));
			subjectReplanData.setSubject(objectMap.get("Subject") + "");
			subjectReplanData.setIdIndex(Long.parseLong(objectMap.get("idIndex") + ""));
			subjectReplanDatas.add(subjectReplanData);
		}
		return subjectReplanDatas;
	}

	public static List<Subject> parseSubjectListForTest(Map<String, Object> objectMap) {
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) objectMap.get("oList");
		List<Subject> subjectList = new ArrayList<Subject>();
		for (Map<String, Object> map : mapList) {
			Subject subject = new Subject();
			subject.setIdSubjectMaster(Integer.parseInt(map.get("IdSubjectMaster") + ""));
			subject.setSubjectName(map.get("SubjectName") + "");
			subjectList.add(subject);
		}
		return subjectList;
	}

	public static List<Topic> parseTopicListForTest(Map<String, Object> map) {
		List<Topic> topics = new ArrayList<Topic>();
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("oList");
		for (Map<String, Object> objectMap : mapList) {
			Topic topic = new Topic();
			topic.setIdSubjectMaster(Integer.parseInt(objectMap.get("IdSubjectMaster") + ""));
			topic.setIdTopicMaster(Integer.parseInt(objectMap.get("IdTopicMaster") + ""));
			topic.setSubjectName(objectMap.get("SubjectName") + "");
			topic.setTopicName(objectMap.get("TopicName") + "");
			topics.add(topic);
		}

		return topics;
	}

	public static TestInfo parseTestInfo(Map<String, Object> objectMap) {
		TestInfo testInfo = new TestInfo();
		Map<String, Object> olist = (Map<String, Object>) objectMap.get("oTest");
		testInfo.setComments(olist.get("Comments") + "");
		testInfo.setIdTestLogMaster(Long.parseLong(olist.get("IdTestLogMaster") + ""));
		return testInfo;
	}

	public static Test parseTest(DBUtils dbUtils, Map<String, Object> objectMap) {
		Test test = new Test();

		test.setIdTestLogMaster(Integer.parseInt(objectMap.get("IdTestLogMaster") + ""));
		test.setTestEnd(Boolean.parseBoolean(objectMap.get("IsTestEnd") + ""));
		test.setNegativeMarks(Double.parseDouble(objectMap.get("NegativeMarks") + ""));
		test.setNoOfQuestion(Integer.parseInt(objectMap.get("NoOfQuestion") + ""));
		test.setRequireTime(objectMap.get("RequireTime") + "");
		test.setStrDate(objectMap.get("StrDate") + "");
		test.setTakenTime(objectMap.get("TakenTime") + "");
		test.setIdTestType(Integer.parseInt(objectMap.get("IdTestType") + ""));
		test.setTestId(objectMap.get("TestId") + "");
		test.setTopics(objectMap.get("Topics") + "");
		test.setQuestions(parseQuestionList(dbUtils, (List<Map<String, Object>>) objectMap.get("Questions")));
		return test;
	}

	public static ForeignCollection<Question> parseQuestionList(DBUtils dbUtils, List<Map<String, Object>> mapList) {
		ForeignCollection<Question> questions = dbUtils.getDatabaseHelper().getTestRuntimeExceptionDao().getEmptyForeignCollection("questions");
		for (Map<String, Object> objectMap : mapList) {
			Question question = new Question();
			question.setDifficultyLevel(Integer.parseInt(objectMap.get("DifficultyLevel") + ""));
			question.setDuration(Integer.parseInt(objectMap.get("Duration") + ""));
			question.setExplanation(objectMap.get("Explanation") + "");
			question.setIdIndex(Integer.parseInt(objectMap.get("IdIndex") + ""));
			question.setIdQuestionMaster(Integer.parseInt(objectMap.get("IdQuestionMaster") + ""));
			question.setIdSubjectMaster(Integer.parseInt(objectMap.get("IdSubjectMaster") + ""));
			question.setIdTestLog(Integer.parseInt(objectMap.get("IdTestLog") + ""));
			question.setIdTestLogMaster(Integer.parseInt(objectMap.get("IdTestLogMaster") + ""));
			question.setIdTopicMaster(Integer.parseInt(objectMap.get("IdTopicMaster") + ""));
			question.setMarks(Integer.parseInt(objectMap.get("Marks") + ""));
			question.setOption1(objectMap.get("Option1") + "");
			question.setOption2(objectMap.get("Option2") + "");
			question.setOption3(objectMap.get("Option3") + "");
			question.setOption4(objectMap.get("Option4") + "");
			question.setOption5(objectMap.get("Option5") + "");
			question.setOptionSelected(Integer.parseInt(objectMap.get("OptionSelected") + ""));
			question.setOutOf(Integer.parseInt(objectMap.get("OutOf") + ""));
			question.setQuestions(objectMap.get("Questions") + "");
			question.setRightAnswer(Integer.parseInt(objectMap.get("RightAnswer") + ""));
			question.setSubjectName(objectMap.get("SubjectName") + "");
			question.setTopicName(objectMap.get("TopicName") + "");
			questions.add(question);
		}
		return questions;
	}


	public static List<PreviousTest> parsePreviousTest(DBUtils dbUtils, Map<String, Object> objectMap) {
		List<PreviousTest> previousTests = new ArrayList<PreviousTest>();
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) objectMap.get("oTestList");

		for (Map<String, Object> map : mapList) {
			PreviousTest previousTest = new PreviousTest();
			previousTest.setExamName(map.get("ExamName") + "");
			previousTest.setIdExam(Integer.parseInt(map.get("IdExam") + ""));
			previousTest.setIdTestType(Integer.parseInt(map.get("IdTestType") + ""));
			previousTest.setPreviousExams(parsePreviousExam(dbUtils, (List<Map<String, Object>>) map.get("PreviousExam")));
			previousTests.add(previousTest);
		}
		return previousTests;
	}

	public static ForeignCollection<PreviousExam> parsePreviousExam(DBUtils dbUtils, List<Map<String, Object>> mapList) {
		ForeignCollection<PreviousExam> previousExams = dbUtils.getDatabaseHelper().getPreviousTestRuntimeExceptionDao().getEmptyForeignCollection("previousExams");
		for (Map<String, Object> objectMap : mapList) {
			PreviousExam previousExam = new PreviousExam();
			previousExam.setExamYear(objectMap.get("ExamYear") + "");
			previousExam.setIdExam(Integer.parseInt(objectMap.get("IdExam") + ""));
			previousExam.setIdPreviousExam(Integer.parseInt(objectMap.get("IdPreviousExam") + ""));
			previousExams.add(previousExam);
			//	dbUtils.getDatabaseHelper().getPreviousExamRuntimeExceptionDao().createOrUpdate(previousExam);
		}
		return previousExams;
	}


	public static List<MasterForumTopic> parseMasterForumTopicList(List<Map<String, Object>> mapList) {
		List<MasterForumTopic> masterForumTopics = new ArrayList<MasterForumTopic>();
		for (Map<String, Object> objectMap : mapList) {

			MasterForumTopic masterForumTopic = new MasterForumTopic();
			masterForumTopic.setAddedBy(objectMap.get("AddedBy") + "");
			masterForumTopic.setAddedDate(objectMap.get("StrAddedDate") + "");
			masterForumTopic.setDescription(objectMap.get("Description") + "");
			masterForumTopic.setForumMasterID(Integer.parseInt(objectMap.get("ForumMasterID") + ""));
			masterForumTopic.setId(Integer.parseInt(objectMap.get("ID") + ""));
			masterForumTopic.setImageUrl(objectMap.get("ImageUrl") + "");
			masterForumTopic.setImportance(Integer.parseInt(objectMap.get("Importance") + ""));
			masterForumTopic.setModerated(Boolean.parseBoolean(objectMap.get("Moderated") + ""));
			masterForumTopic.setTitle(objectMap.get("Title") + "");

			masterForumTopics.add(masterForumTopic);
		}
		return masterForumTopics;
	}

	public static List<ForumTopic> parseForumTopicList(List<Map<String, Object>> mapList) {
		List<ForumTopic> forumTopics = new ArrayList<ForumTopic>();
		for (Map<String, Object> objectMap : mapList) {
			ForumTopic forumTopic = new ForumTopic();
			forumTopic.setAddedBy(objectMap.get("AddedBy") + "");
			forumTopic.setAddedDate(objectMap.get("StrAddedDate") + "");
			forumTopic.setDescription(objectMap.get("Description") + "");
			forumTopic.setForumMasterID(Integer.parseInt(objectMap.get("ForumMasterID") + ""));
			forumTopic.setId(Integer.parseInt(objectMap.get("ID") + ""));
			forumTopic.setImageUrl(objectMap.get("ImageUrl") + "");
			forumTopic.setImportance(Integer.parseInt(objectMap.get("Importance") + ""));
			forumTopic.setModerated(Boolean.parseBoolean(objectMap.get("Moderated") + ""));
			forumTopic.setTitle(objectMap.get("Title") + "");

			forumTopics.add(forumTopic);
		}
		return forumTopics;
	}

	public static List<ForumSubject> parseForumSubjectList(List<Map<String, Object>> mapList) {
		List<ForumSubject> forumSubjects = new ArrayList<ForumSubject>();
		for (Map<String, Object> objectMap : mapList) {
			ForumSubject forumSubject = new ForumSubject();
			forumSubject.setAddedBy(objectMap.get("AddedBy") + "");
			forumSubject.setAddedDate(objectMap.get("StrAddedDate") + "");
			forumSubject.setDescription(objectMap.get("Description") + "");
			forumSubject.setForumMasterID(Integer.parseInt(objectMap.get("ForumMasterID") + ""));
			forumSubject.setId(Long.parseLong(objectMap.get("ID") + ""));
			forumSubject.setImageUrl(objectMap.get("ImageUrl") + "");
			forumSubject.setImportance(Integer.parseInt(objectMap.get("Importance") + ""));
			forumSubject.setModerated(Boolean.parseBoolean(objectMap.get("Moderated") + ""));
			forumSubject.setTitle(objectMap.get("Title") + "");
			forumSubjects.add(forumSubject);
		}
		return forumSubjects;
	}

	public static List<ForumThread> parseForumThreads(List<Map<String, Object>> mapList) {
		List<ForumThread> forumThreads = new ArrayList<ForumThread>();
		for (Map<String, Object> map : mapList) {
			ForumThread forumThread = new ForumThread();
			forumThread.setAddedBy((String) map.get("AddedBy"));
			forumThread.setAddedDate((String) map.get("AddedDate"));
			forumThread.setAddedByIP((String) map.get("AddedByIP"));
			forumThread.setApproved((Boolean) map.get("Approved"));
			forumThread.setBody((String) map.get("Body"));
			forumThread.setClosed((Boolean) map.get("Closed"));
			forumThread.setForumID(Long.parseLong(map.get("ForumID") + ""));
			forumThread.setForumMasterID(Long.parseLong(map.get("ForumMasterID") + ""));
			forumThread.setForumTitle((String) map.get("ForumTitle"));
			forumThread.setId(Long.parseLong(map.get("ID") + ""));
			forumThread.setImportant((Boolean) map.get("Important"));
			forumThread.setLastPostBy((String) map.get("LastPostBy"));
			forumThread.setLastPostDate((String) map.get("LastPostDate"));
			forumThread.setName((String) map.get("Name"));
			forumThread.setParentPost((String) map.get("ParentPost"));
			forumThread.setParentPostID(Long.parseLong(map.get("ParentPostID") + ""));
			forumThread.setPhoto((String) map.get("Photo"));
			forumThread.setReplyCount(Long.parseLong(map.get("ReplyCount") + ""));
			forumThread.setStrAddedDate((String) map.get("StrAddedDate"));
			forumThread.setStrLastPostDate((String) map.get("StrLastPostDate"));
			forumThread.setTitle((String) map.get("Title"));
			forumThread.setUserName((String) map.get("UserName"));
			forumThread.setViewCount(Long.parseLong(map.get("ViewCount") + ""));
			forumThreads.add(forumThread);
		}
		return forumThreads;
	}

	public static List<TemplateTest> parseTemplateTestList(Map<String, Object> objectMap) {
		List<TemplateTest> templateTests = new ArrayList<TemplateTest>();
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) objectMap.get("oTestList");
		for (Map<String, Object> map : mapList) {
			TemplateTest templateTest = new TemplateTest();
			templateTest.setIdTemplateMaster(Integer.parseInt(map.get("IdTemplateMaster") + ""));
			templateTest.setTemplateName(map.get("TemplateName") + "");
			templateTests.add(templateTest);
		}
		return templateTests;
	}

	public static List<FixedTest> parseFixedTestList(Map<String, Object> objectMap) {
		List<FixedTest> fixedTests = new ArrayList<FixedTest>();
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) objectMap.get("oTestList");
		for (Map<String, Object> map : mapList) {
			FixedTest fixedTest = new FixedTest();
			fixedTest.setCreatedDate(map.get("CreatedDate") + "");
			fixedTest.setIdFixedTestMaster(Integer.parseInt(map.get("IdFixedTestMaster") + ""));
			fixedTest.setIdTestType(Integer.parseInt(map.get("IdTestType") + ""));
			fixedTest.setTestId(map.get("TestId") + "");
			fixedTests.add(fixedTest);
		}
		return fixedTests;
	}

	public static List<Test> parseTestList(Map<String, Object> objectMap) {
		List<Test> tests = new ArrayList<Test>();
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) objectMap.get("oTestList");
		for (Map<String, Object> map : mapList) {
			Test test = new Test();
			test.setIdTestLogMaster(Long.parseLong(map.get("IdTestLogMaster") + ""));
			test.setIdTestType(Integer.parseInt(map.get("IdTestType") + ""));
			test.setTestEnd(Boolean.parseBoolean(map.get("IsTestEnd") + ""));
			test.setNegativeMarks(Integer.parseInt(map.get("NegativeMarks") + ""));
			test.setNoOfQuestion(Integer.parseInt(map.get("NoOfQuestion") + ""));
			test.setStrDate(map.get("StrDate") + "");
			test.setTestId(map.get("TestId") + "");
			test.setTopics(map.get("Topics") + "");
			tests.add(test);
		}
		return tests;
	}

	public static List<VideoInfo> parseVideoInfoList(Map<String, Object> objectMap) {
		List<VideoInfo> videoInfos = new ArrayList<VideoInfo>();
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) objectMap.get("UserVideoList");
		for (Map<String, Object> map : mapList) {
			VideoInfo videoInfo = new VideoInfo();
			videoInfo.setCommandType(Integer.parseInt(map.get("CommandType") + ""));
			videoInfo.setCount(Integer.parseInt(map.get("Count") + ""));
			videoInfo.setDescription(map.get("Description") + "");
			videoInfo.setDuration(map.get("Duration") + "");
			videoInfo.setFilePath(map.get("FilePath") + "");
			videoInfo.setIdCustomLevelMaster(Integer.parseInt(map.get("IdCustomLevelMaster") + ""));
			videoInfo.setIdSubjectMaster(Integer.parseInt(map.get("IdSubjectMaster") + ""));
			videoInfo.setIdTopicMaster(Integer.parseInt(map.get("IdTopicMaster") + ""));
			videoInfo.setIdVideo(Integer.parseInt(map.get("IdVideo") + ""));
			videoInfo.setIdVideoLog(Integer.parseInt(map.get("IdVideoLog") + ""));
			videoInfo.setActive(Boolean.parseBoolean(map.get("IsActive") + ""));
			videoInfo.setPriority(Integer.parseInt(map.get("Priority") + ""));
			videoInfo.setSize(map.get("Size") + "");
			videoInfo.setSubjectName(map.get("SubjectName") + "");
			videoInfo.setTopicName(map.get("TopicName") + "");
			videoInfo.setTotalTime(map.get("TotalTime") + "");
			videoInfos.add(videoInfo);
		}

		return videoInfos;
	}
}
