package com.sudosaints.cmavidya;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by inni on 16/8/14.
 */
public class Preferences {

    private Context context;

    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String GENDER = "GENDER";
    private static final String PASSWORD = "PASSWORD";
    private static final String USER_ID = "USER_ID";
    private static final String MOBILE = "MOBILE";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String MASTER_COURSE_ID = "MASTER_COURSE_ID";
    private static final String USER_NAME="USER_NAME";


    public Preferences(Context context) {
        super();
        this.context = context;
    }

    protected SharedPreferences getSharedPreferences(String key) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private String getString(String key, String def) {
        SharedPreferences prefs = getSharedPreferences(key);
        String s = prefs.getString(key, def);
        return s;
    }

    private int getInt(String key, int def) {
        SharedPreferences prefs = getSharedPreferences(key);
        int i = Integer.parseInt(prefs.getString(key, Integer.toString(def)));
        return i;
    }

    private float getFloat(String key, float def) {
        SharedPreferences prefs = getSharedPreferences(key);
        float f = Float.parseFloat(prefs.getString(key, Float.toString(def)));
        return f;
    }

    private long getLong(String key, long def) {
        SharedPreferences prefs = getSharedPreferences(key);
        long l = Long.parseLong(prefs.getString(key, Long.toString(def)));
        return l;
    }

    private void setString(String key, String val) {
        SharedPreferences prefs = getSharedPreferences(key);
        SharedPreferences.Editor e = prefs.edit();
        e.putString(key, val);
        e.commit();
    }

    private void setBoolean(String key, boolean val) {
        SharedPreferences prefs = getSharedPreferences(key);
        SharedPreferences.Editor e = prefs.edit();
        e.putBoolean(key, val);
        e.commit();
    }

    private void setInt(String key, int val) {
        SharedPreferences prefs = getSharedPreferences(key);
        SharedPreferences.Editor e = prefs.edit();
        e.putString(key, Integer.toString(val));
        e.commit();
    }

    private void setLong(String key, long val) {
        SharedPreferences prefs = getSharedPreferences(key);
        SharedPreferences.Editor e = prefs.edit();
        e.putString(key, Long.toString(val));
        e.commit();
    }

    private boolean getBoolean(String key, boolean def) {
        SharedPreferences prefs = getSharedPreferences(key);
        boolean b = prefs.getBoolean(key, def);
        return b;
    }

    private int[] getIntArray(String key, String def) {
        String s = getString(key, def);
        int[] ia = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            ia[i] = s.charAt(i) - '0';
        }
        return ia;
    }



    /*
     * Public methods to get/set prefs
	 */


    public String getAccessToken() {
        return getString(ACCESS_TOKEN, null);
    }

    public void setAccessToken(String accessToken) {
        setString(ACCESS_TOKEN, accessToken);
    }


    public String getUserEmail() {
        return getString(USER_EMAIL, null);
    }

    public void setUserEmail(String userEmail) {
        setString(USER_EMAIL, userEmail);
    }


    public String getFirstName() {
        return getString(FIRST_NAME, null);
    }

    public void setFirstName(String firstName) {
        setString(FIRST_NAME, firstName);
    }


    public String getLastName() {
        return getString(LAST_NAME, null);
    }

    public void setLastName(String lastName) {
        setString(LAST_NAME, lastName);
    }


    public boolean getGender() {
        return getBoolean(GENDER, true);
    }

    public void setGender(boolean gender) {
        setBoolean(GENDER, gender);
    }


    public String getPassword() {
        return getString(PASSWORD, null);
    }

    public void setPassword(String password) {
        setString(PASSWORD, password);
    }


    public long getUserId() {
        return getLong(USER_ID, 0l);
    }

    public void setUserId(long userId) {
        setLong(USER_ID, userId);
    }


    public String getMobile() {
        return getString(MOBILE, null);
    }

    public void setMobile(String mobile) {
        setString(MOBILE, mobile);
    }

    public int getMasterCourseId() {
        return getInt(MASTER_COURSE_ID, 0);
    }

    public void setMasterCourseId(int masterCourseId) {
        setInt(MASTER_COURSE_ID, masterCourseId);
    }

    public String getUserName(){return getString(USER_NAME,null);}
    public void setUserName(String userName){setString(USER_NAME,userName);}

}
