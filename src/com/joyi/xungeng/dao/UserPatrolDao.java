package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.print.PrintJob;
import android.util.Log;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.PatrolRecord;
import com.joyi.xungeng.domain.User;
import com.joyi.xungeng.domain.UserPatrol;
import com.joyi.xungeng.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
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
	public long add(UserPatrol userPatrol) {

		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("userId", userPatrol.getUserId());
		contentValues.put("lineId", userPatrol.getLineId());
		contentValues.put("sequence", userPatrol.getSequence());
		contentValues.put("beginTime", userPatrol.getBeginTime());
		contentValues.put("endTime", userPatrol.getEndTime());
		contentValues.put("beginPhoneTime", userPatrol.getBeginPhoneTime());
		contentValues.put("endPhoneTime", userPatrol.getEndPhoneTime());

		long id = writableDatabase.insert("user_patrol", "", contentValues);
		return id;
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
                String lineid = userPatrolCursor.getString(userPatrolCursor.getColumnIndex("lineid"));
				int sequence = userPatrolCursor.getInt(userPatrolCursor.getColumnIndex("sequence"));
				userPatrol.setLineId(lineid);
				userPatrol.setSequence(sequence);
				userPatrol.setBeginTime(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("beginTime")));
				userPatrol.setEndTime(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("endTime")));
				userPatrol.setBeginPhoneTime(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("beginPhoneTime")));
				userPatrol.setEndPhoneTime(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("endPhoneTime")));

                List<PatrolRecord> patrolRecords = userPatrol.getPatrolRecords();
                Cursor cursor = readableDatabase.query("patrol_record", null, "userPatrolId = ?", new String[]{String.valueOf(id)}, null, null, null);
                if (cursor != null) {
                    PatrolRecord patrolRecord = null;
                    while (cursor.moveToNext()) {
	                    patrolRecord = new PatrolRecord();
	                    patrolRecord.setLineId(lineid);
	                    patrolRecord.setSequence(sequence);
                        patrolRecord.setId(cursor.getInt(cursor.getColumnIndex("id")));
                        patrolRecord.setNodeId(cursor.getString(cursor.getColumnIndex("nodeId")));
                        patrolRecord.setError(cursor.getString(cursor.getColumnIndex("error")));
                        patrolRecord.setUserPatrolId(cursor.getString(cursor.getColumnIndex("userPatrolId")));
                        patrolRecord.setPatrolTime(cursor.getString(cursor.getColumnIndex("patrolTime")));
                        patrolRecord.setPatrolPhoneTime(cursor.getString(cursor.getColumnIndex("patrolPhoneTime")));
                        patrolRecords.add(patrolRecord);
                    }
                }
				userPatrols.add(userPatrol);
            }
		}
		return userPatrols;
	}


	public UserPatrol getById(Long id) {
		if (id == null) {
			return null;
		}
		UserPatrol userPatrol = null;
		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor userPatrolCursor = readableDatabase.query("user_patrol", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
		if (userPatrolCursor != null && userPatrolCursor.moveToFirst()) {
			userPatrol = new UserPatrol();
			userPatrol.setId(id);
			userPatrol.setUserId(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("userid")));
			String lineid = userPatrolCursor.getString(userPatrolCursor.getColumnIndex("lineid"));
			userPatrol.setLineId(lineid);
			int sequence = userPatrolCursor.getInt(userPatrolCursor.getColumnIndex("sequence"));
			userPatrol.setSequence(sequence);
			userPatrol.setBeginTime(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("beginTime")));
			userPatrol.setEndTime(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("endTime")));
			userPatrol.setBeginPhoneTime(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("beginPhoneTime")));
			userPatrol.setEndPhoneTime(userPatrolCursor.getString(userPatrolCursor.getColumnIndex("endPhoneTime")));
		}
		return userPatrol;
	}

	/**
	 * 删除所有记录
	 */
	public void deleteAll() {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		writableDatabase.delete("user_patrol", null, null);
	}


    /**
     * 更新结束时间
     * @param endServerTime
     * @param endPhoneTime
     * @param id
     */
    public void updateEndDate(Date endServerTime, Date endPhoneTime, long id) {
        SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("endTime", DateUtil.getHumanReadStr(endServerTime));
        contentValues.put("endPhoneTime", DateUtil.getHumanReadStr(endPhoneTime));
        writableDatabase.update("user_patrol", contentValues, "id = ?", new String[]{String.valueOf(id)});
    }
}
