package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.db.PatrolRecordDBHelper;
import com.joyi.xungeng.domain.PatrolRecord;
import com.joyi.xungeng.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更记录】dao
 */
public class PatrolRecordDao {
	private PatrolRecordDBHelper patrolRecordDBHelper;

	public PatrolRecordDao(Context context) {
		patrolRecordDBHelper = new PatrolRecordDBHelper(context);
	}

	/**
	 * 添加记录
	 * @param patrolRecord
	 */
	public void add(PatrolRecord patrolRecord) {
		SQLiteDatabase writableDatabase = patrolRecordDBHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("nodeId", patrolRecord.getNodeId());
		contentValues.put("userPatrolId", patrolRecord.getUserPatrolId());
		contentValues.put("patrolTime", DateUtil.getHumanReadStr(patrolRecord.getPatrolTime()));
		contentValues.put("partolPhoneTime", DateUtil.getHumanReadStr(patrolRecord.getPartolPhoneTime()));
		contentValues.put("error", "");

		writableDatabase.insert("patrol_record", null, contentValues);
	}


	/**
	 * 获取所有巡更记录
	 * @return
	 */
	public List<PatrolRecord> getList() {
		List<PatrolRecord> patrolRecords = new ArrayList<PatrolRecord>();
		SQLiteDatabase readableDatabase = patrolRecordDBHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query("patrol_record", null, null, null, null, null, null);
		if (cursor != null) {
			PatrolRecord patrolRecord = null;
			while (cursor.moveToNext()) {
				patrolRecord = new PatrolRecord();
				patrolRecord.setPatrolTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("patrolTime"))));
				patrolRecord.setError(cursor.getString(cursor.getColumnIndex("error")));
				patrolRecord.setId(cursor.getInt(cursor.getColumnIndex("id")));
				patrolRecord.setNodeId(cursor.getString(cursor.getColumnIndex("nodeId")));
				patrolRecord.setPartolPhoneTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("partolPhoneTime"))));
				patrolRecord.setUserPatrolId(cursor.getString(cursor.getColumnIndex("userPatrolId")));
				patrolRecords.add(patrolRecord);
			}
		}
		return patrolRecords;
	}

	public void deleteAll() {
		SQLiteDatabase writableDatabase = patrolRecordDBHelper.getWritableDatabase();
		writableDatabase.delete("patrol_record", null, null);
	}

}
