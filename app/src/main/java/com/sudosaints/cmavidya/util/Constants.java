package com.sudosaints.cmavidya.util;

public class Constants {


	public static final String SELECTED_CALENDAR_BUTTON_TAG = "CurrentDay";

	public static final String INTENT_MILLS = "INTENT_MILLS";


	public static final String SERVER_DATE_FORMAT = "dd-MM-yyyy";
	public static final String SERVER_MONTH_YEAR_FORMAT = "MMMM-yyyy";
    public static final String NOTIFICATION_API = "http://cavidya.com/Service/Auth.svc/GetNotificationList/laxman";

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
        NOTIFICATION
	}

	public static enum CustomPopUpMenuOption {
		SEARCH,
		PLANNER_OPTION
	}
}