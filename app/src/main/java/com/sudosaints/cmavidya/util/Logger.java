package com.sudosaints.cmavidya.util;

import android.content.Context;
import android.util.Log;

import com.sudosaints.cmavidya.CMAVidyaApp;
import com.sudosaints.cmavidya.R;

public class Logger {

    private static Logger instance;
	private CMAVidyaApp application;
	boolean debugEnabled;
	public static String tag = "CMAVidya";

    public static synchronized Logger init(CMAVidyaApp application) {
        if(null == instance) {
            instance = new Logger(application);
        }
        return instance;
    }

    private Logger(CMAVidyaApp application) {
		super();
		this.setApplication(application);
        debugEnabled = this.application.getResources().getBoolean(R.bool.isDebugEnabled);
	}

	public void debug(String msg) {
		if (debugEnabled && msg!=null) {
			Log.d(tag, msg);
		}
	}

	public void debug(String msg, Throwable t) {
		if (debugEnabled && msg!=null) {
			Log.d(tag, msg, t);
		}
	}

	public void debug(Throwable t) {
		if (debugEnabled) {
			Log.d(tag, "Exception:", t);
		}
	}

	public void debug(String tag, String msg) {
		if (debugEnabled && msg!=null) {
			Log.d(tag, msg);
		}
	}
	
	public void warn(String msg) {
		Log.w(tag, msg);
	}

	public void info(String msg) {
		Log.i(tag, msg);
	}

	public void error(String msg) {
		Log.e(tag, msg);
	}

	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	/**
	 * @return the application
	 */
	public Context getApplication() {
		return application;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(CMAVidyaApp application) {
		this.application = application;
	}

}
