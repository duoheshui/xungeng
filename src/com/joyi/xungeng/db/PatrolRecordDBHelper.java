package com.joyi.xungeng.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.joyi.xungeng.util.Constants;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更记录】 DBHelper
 */
public class PatrolRecordDBHelper extends SQLiteOpenHelper {

	public static final String CREATE_TABLE_SQL =
			"create table patrol_record(" +
			"id integer primary key autoincrement, " +
			"nodeId char(20)," +
			"userPatrolId integer," +
			"patrolTime datetime," +
			"partolPhoneTime datetime," +
			"error varchar(200))";


	public PatrolRecordDBHelper(Context context) {
		super(context, Constants.DATA_BASE_NAME, null, Constants.DATA_BASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

	}
}
