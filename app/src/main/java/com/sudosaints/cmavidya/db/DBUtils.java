package com.sudosaints.cmavidya.db;

import android.content.Context;

/**
 * Created by inni on 14/8/14.
 */
public class DBUtils {

    Context context = null;
    DatabaseHelper databaseHelper = null;

    public DBUtils(Context context) {
        super();
        this.context = context;
    }

    synchronized public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper != null) {
            return databaseHelper;
        }
        databaseHelper = new DatabaseHelper(context);

        return databaseHelper;
    }


}
