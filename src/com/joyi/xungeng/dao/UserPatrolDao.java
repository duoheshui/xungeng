package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.db.UserPatrolDBHelper;
import com.joyi.xungeng.domain.UserPatrol;
import com.joyi.xungeng.util.DateUtil;

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
		contentValues.put("userid", userPatrol.getUid());
		contentValues.put("lineid", userPatrol.getLineid());
		contentValues.put("sequence", userPatrol.getSequence());
		contentValues.put("beginTime", DateUtil.getHumanReadStr(userPatrol.getBeginTime()));
		contentValues.put("endTime", DateUtil.getHumanReadStr(userPatrol.getEndTime()));
		contentValues.put("beginPhoneTime", DateUtil.getHumanReadStr(userPatrol.getBeginPhoneTime()));
		contentValues.put("endPhoneTime", DateUtil.getHumanReadStr(userPatrol.getEndPhoneTime()));


		writableDatabase.insert("user_patrol", null, contentValues);
	}
}
