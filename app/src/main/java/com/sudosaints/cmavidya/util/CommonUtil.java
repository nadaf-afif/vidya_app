package com.sudosaints.cmavidya.util;

import android.app.Activity;
import android.app.ProgressDialog;

import com.sudosaints.cmavidya.model.Subject;
import com.sudosaints.cmavidya.model.Topic;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by inni on 16/8/14.
 */
public class CommonUtil {

	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


	public static boolean validateEmail(String email) {
		Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = emailPattern.matcher(email);
		return matcher.matches();
	}

	public static String stringToCsvFromIntList(List<Integer> integerList) {
		String s = "";
		if (null != integerList) {
			for (Integer integer : integerList) {
				s += integer + ",";
			}
			return s;

		} else {
			return s;
		}
	}

	public static String TopicListToTopicIdsCsv(List<Topic> topicList) {
		String s = "";
		if (null != topicList) {
			for (Topic topic : topicList)
				s += topic.getIdTopicMaster() + ",";
			return s;
		} else {
			return s;
		}
	}

	public static String SubjectListToSubjectIdsCsv(List<Subject> subjectList) {
		String s = "";
		if (null != subjectList) {
			for (Subject subject : subjectList) {
				s += subject.getIdSubjectMaster() + ",";
			}
			return s;
		} else {
			return s;
		}

	}

	public static ProgressDialog getProgressDialog(Activity activity) {
		ProgressDialog progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("Loading...");
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);

		return progressDialog;
	}

}
