package com.joyi.xungeng.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.joyi.xungeng.util.Constants;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更路线】 DBHelper
 */
public class PatrolLineDBHelper extends SQLiteOpenHelper {
	public static final String CREATE_TABLE_SQL =
			"create table partol_line(" +
			"id char(20)," +
			"postid char(20)," +
			"frequency integer," +
			"offset integer," +
			"beginTime datetime" +
			"endTime datetime)";


	public PatrolLineDBHelper(Context context) {
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
