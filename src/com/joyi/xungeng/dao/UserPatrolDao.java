package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.PatrolRecord;
import com.joyi.xungeng.domain.UserPatrol;
import com.joyi.xungeng.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【用户巡更】dao
 */
public class UserPatrolDao {

	/**
	 * 添加记录
	 * @param userPatrol
	 */
	public void add(UserPatrol userPatrol) {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
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
		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor userPatrolCursor = readableDatabase.query("user_patrol", null, null, null, null, null, null);
		if (userPatrolCursor != null) {
			UserPatrol userPatrol = null;
			while (userPatrolCursor.moveToNext()) {
				userPatrol = new UserPatrol();
                int id = userPatrolCursor.getInt(userPatrolCursor.getColumnIndex("id"));
				userPatrol.setId(id);
				userPatrol.setUserId(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("userid")));
				userPatrol.setLineId(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("lineid")));
				userPatrol.setSequence(userPatrolCursor.getInt(userPatrolCursor.getColumnIndex("sequence")));
				userPatrol.setBeginTime(DateUtil.getDateFromHumanReadStr(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("beginTime"))));
				userPatrol.setEndTime(DateUtil.getDateFromHumanReadStr(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("endTime"))));
				userPatrol.setBeginPhoneTime(DateUtil.getDateFromHumanReadStr(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("beginPhoneTime"))));
				userPatrol.setEndPhoneTime(DateUtil.getDateFromHumanReadStr(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("endPhoneTime"))));

                List<PatrolRecord> patrolRecords = userPatrol.getPatrolRecords();
                Cursor cursor = readableDatabase.query("patrol_record", null, "userPatrolId = ?", new String[]{String.valueOf(id)}, null, null, null);
                if (cursor != null) {
                    PatrolRecord patrolRecord = null;
                    while (cursor.moveToNext()) {
                        patrolRecord = new PatrolRecord();
                        patrolRecord.setId(cursor.getInt(cursor.getColumnIndex("id")));
                        patrolRecord.setNodeId(cursor.getString(cursor.getColumnIndex("nodeId")));
                        patrolRecord.setError(cursor.getString(cursor.getColumnIndex("error")));
                        patrolRecord.setUserPatrolId(cursor.getString(cursor.getColumnIndex("userPatrolId")));
                        patrolRecord.setPatrolTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("patrolTime"))));
                        patrolRecord.setPatrolPhoneTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("patrolPhoneTime"))));
                        patrolRecords.add(patrolRecord);
                    }
                }
            }
		}
		return userPatrols;
	}


	/**
	 * 删除所有记录
	 */
	public void deleteAll() {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		writableDatabase.delete("user_patrol", null, null);
	}
}
