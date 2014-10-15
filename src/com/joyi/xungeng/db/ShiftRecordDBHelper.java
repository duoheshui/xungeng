package com.joyi.xungeng.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.joyi.xungeng.util.Constants;

/**
 * Created by zhangyong on 2014/10/15.
 */
public class ShiftRecordDBHelper extends SQLiteOpenHelper {
	public static final String CREATE_TABLE_SQL =
			"create table shift_record(" +
			"id integer primary key autoincrement, " +
			"station_id char(20)," +
			"schedule_type_id char(20)," +
			"submit_time datetime," +
			"submit_phone_time datetime," +
			"receive_time datetime," +
			"receive_phone_time datetime)";

	public ShiftRecordDBHelper(Context context) {
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
