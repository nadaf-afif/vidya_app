package com.sudosaints.cmavidya;

import android.app.Application;
import android.widget.Toast;

import com.sudosaints.cmavidya.api.ApiRequestHelper;
import com.sudosaints.cmavidya.db.DatabaseHelper;
import com.sudosaints.cmavidya.util.BusProvider;
import com.sudosaints.cmavidya.util.Logger;

/**
 * Created by Vishal on 7/30/2014.
 */
public class CMAVidyaApp extends Application {

    private ApiRequestHelper ApiRequestHelper;
    private BusProvider busProvider;
    private Logger logger;
    private Preferences preferences;


    @Override
    public void onCreate() {
        super.onCreate();
        doInit();
    }

    private void doInit() {
        ApiRequestHelper = ApiRequestHelper.init(this);
        busProvider = BusProvider.init(this);
        logger = Logger.init(this);
        preferences = new Preferences(this);
        Toast.makeText(this, "", Toast.LENGTH_LONG);

    }

    public synchronized ApiRequestHelper getApiRequestHelper() {
        return ApiRequestHelper;
    }

    public synchronized BusProvider getBusProvider() {
        return busProvider;
    }

    public synchronized Logger getLogger() {
        return logger;
    }

    public synchronized Preferences getPreferences() {
        return preferences;
    }

    public void showToast(String message) {
        if (null != message)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int stringResId) {
        String message = getString(stringResId);
        if (null != message)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
