package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.db.UserPatrolDBHelper;
import com.joyi.xungeng.domain.UserPatrol;
import com.joyi.xungeng.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 */
public class UserPatrolDao {

	private UserPatrolDBHelper userPatrolDBHelper;

	public UserPatrolDao(Context context) {
		this.userPatrolDBHelper = new UserPatrolDBHelper(context);
	}

	/**
	 * 添加记录
	 * @param userPatrol
	 */
	public void add(UserPatrol userPatrol) {
		SQLiteDatabase writableDatabase = userPatrolDBHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("userid", userPatrol.getUserId());
		contentValues.put("lineid", userPatrol.getLineId());
		contentValues.put("sequence", userPatrol.getSequence());
		contentValues.put("beginTime", DateUtil.getHumanReadStr(userPatrol.getBeginTime()));
		contentValues.put("endTime", DateUtil.getHumanReadStr(userPatrol.getEndTime()));
		contentValues.put("beginPhoneTime", DateUtil.getHumanReadStr(userPatrol.getBeginPhoneTime()));
		contentValues.put("endPhoneTime", DateUtil.getHumanReadStr(userPatrol.getEndPhoneTime()));


		writableDatabase.insert("user_patrol", null, contentValues);
	}

	/**
	 * 获取所有记录
	 * @return
	 */
	public List<UserPatrol> getAll() {
		List<UserPatrol> userPatrols = new ArrayList<UserPatrol>();
		SQLiteDatabase readableDatabase = userPatrolDBHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query("user_patrol", null, null, null, null, null, null);
		if (cursor != null) {
			UserPatrol userPatrol = null;
			while (cursor.moveToNext()) {
				userPatrol = new UserPatrol();
				userPatrol.setId(cursor.getInt(cursor.getColumnIndex("id")));
				userPatrol.setUserId(cursor.getString(cursor.getColumnIndex("userid")));
				userPatrol.setLineId(cursor.getString(cursor.getColumnIndex("lineid")));
				userPatrol.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
				userPatrol.setBeginTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("beginTime"))));
				userPatrol.setEndTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("endTime"))));
				userPatrol.setBeginPhoneTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("beginPhoneTime"))));
				userPatrol.setEndPhoneTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("endPhoneTime"))));
			}
		}
		return userPatrols;
	}


	/**
	 * 删除所有记录
	 */
	public void deleteAll() {
		SQLiteDatabase writableDatabase = userPatrolDBHelper.getWritableDatabase();
		writableDatabase.delete("user_patrol", null, null);
	}
}
