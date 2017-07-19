package com.auribises.cpdemogwa;

import android.net.Uri;

/**
 * Created by ishantkumar on 19/07/17.
 */

public class Util {

    // Symbolic Constants

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "cpdemo.db";

    public static final String TAB_NAME = "Users";
    public static final String COL_ID = "_ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_PASSWORD = "PASSWORD";
    public static final String COL_GENDER = "GENDER";
    public static final String COL_CITY = "CITY";

    public static final String CREATE_TAB_QUERY = "create table Users(" +
            "_ID integer primary key autoincrement," +
            "NAME text," +
            "PHONE text," +
            "EMAIL text," +
            "PASSWORD text," +
            "GENDER text," +
            "CITY text" +
            ")";

    public static final Uri USER_URI = Uri.parse("content://com.auribises.cpdemogwa.usercontentprovider/"+TAB_NAME);

}
