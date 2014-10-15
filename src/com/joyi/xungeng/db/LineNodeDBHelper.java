package com.joyi.xungeng.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.joyi.xungeng.util.Constants;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更路线节点】 DBHelper
 */
public class LineNodeDBHelper extends SQLiteOpenHelper{

	public static final String CREATE_TABLE_SQL =
			"create table line_node(" +
			"id char(20)," +
			"lineid char(20)," +
			"nodeName varchar(50)," +
			"nfcCode varchar(50))";

	public LineNodeDBHelper(Context context) {
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