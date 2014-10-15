package com.joyi.xungeng.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.joyi.xungeng.util.Constants;

/**
 * 【用户巡更】 DBHelper
 */
public class UserPatrolDBHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE_SQL =
		    "create table user_patrol (" +
				    "id integer primary key autoincrement, " +
				    "userid char(20)," +
				    "lineid char(20), " +
				    "sequence integer, " +
				    "beginTime datetime," +
				    "endTime datetime," +
				    "beginPhoneTime datetime," +
				    "endPhoneTime datetime)";

    public UserPatrolDBHelper(Context context) {
        super(context, Constants.DATA_BASE_NAME, null, Constants.DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}