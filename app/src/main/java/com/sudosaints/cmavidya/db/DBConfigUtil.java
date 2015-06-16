package com.sudosaints.cmavidya.db;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
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

import java.io.IOException;
import java.sql.SQLException;


/**
 * Created by inni on 11/8/14.
 */
public class DBConfigUtil extends OrmLiteConfigUtil {
	private static final Class<?>[] classes = new Class[]{
			Course.class,
			CalenderPlan.class,
			Subject.class,
			Topic.class,
			PlanEvents.class,
			KeyNotes.class,
			TopicReplanData.class,
			SubjectReplanData.class,
			TestInfo.class,
			Test.class,
			Question.class,
			MasterForumTopic.class,
			ForumTopic.class,
			PreviousExam.class,
			PreviousTest.class,
			TemplateTest.class,
			FixedTest.class,
			ForumThread.class,
			ForumSubject.class,
			VideoInfo.class
	};

	public static void main(String[] args) throws IOException, SQLException {
		writeConfigFile("ormlite_config.txt", classes);
	}

}