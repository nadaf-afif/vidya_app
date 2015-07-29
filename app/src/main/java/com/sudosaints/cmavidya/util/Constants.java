package com.sudosaints.cmavidya.util;

public class Constants {


	public static final String SELECTED_CALENDAR_BUTTON_TAG = "CurrentDay";

	public static final String INTENT_MILLS = "INTENT_MILLS";


	public static final String SERVER_DATE_FORMAT = "dd-MM-yyyy";
	public static final String SERVER_MONTH_YEAR_FORMAT = "MMMM-yyyy";
    public static final String NOTIFICATION_API = "http://cavidya.com/Service/Auth.svc/GetNotificationList/laxman";
    public static final String DOWNLOAD_LIST_API = "http://cavidya.com/Service/Auth.svc/GetCourseDownloadList/laxman";
    public static final String UPLOADEDPATH ="http://cavidya.com/Download/" ;
	public static final String POST_COMMENT_API = "http://cavidya.com/Service/ForumSrv.svc/InsertPost";

	public static enum ActivityABarAction {
		REGISTER,
		GUEST_USER,
		LANDING,
		DASHBOARD,
		PLANNER_MAIN,
		PLANNER_ITEM,
		PLANNER_CALENDER,
		KEYNOTE_DISPLAY,
		KEYNOTES_MAIN,
		KEYNOTES_TOPICS,
		KEYNOTES_ADD_NOTE,
		PDF,
		TEST_CREATE,
		REPLAN,
		Test_SERIES,
		COMING_SOON,
		FORUM,
		FORUM_THREAD,
		NEW_THREAD,
		LIST_THREAD,
		ANALYSIS,
		VIDEO,
        NOTIFICATION,
        DOWNLOADS
	}

	public static enum CustomPopUpMenuOption {
		SEARCH,
		PLANNER_OPTION
	}
}