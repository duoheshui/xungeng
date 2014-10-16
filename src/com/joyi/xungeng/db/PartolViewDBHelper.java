package com.joyi.xungeng.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.joyi.xungeng.util.Constants;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡查打卡】 DBHelper
 */
public class PartolViewDBHelper extends SQLiteOpenHelper {
	public static final String CREATE_TABLE_SQL =
			"create table partol_view(" +
			"id integer primary key autoincrement, " +
			"nodeid char(20)," +
			"userid char(20)," +
			"patrolTime datetime)";

	public PartolViewDBHelper(Context context) {
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
