package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.PatrolRecord;
import com.joyi.xungeng.util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更记录】dao
 */
public class PatrolRecordDao {

	/**
	 * 添加记录
	 * @param patrolRecord
	 */
	public long add(PatrolRecord patrolRecord) {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("nodeId", patrolRecord.getNodeId());
		contentValues.put("userPatrolId", patrolRecord.getUserPatrolId());
		contentValues.put("patrolTime", patrolRecord.getPatrolTime());
		contentValues.put("patrolPhoneTime", patrolRecord.getPatrolPhoneTime());
		contentValues.put("sequence", patrolRecord.getSequence());
		contentValues.put("lineId", patrolRecord.getLineId());
		contentValues.put("error", "");

		return writableDatabase.insert("patrol_record", null, contentValues);
	}


	/**
	 * 获取所有巡更记录
	 * @return
	 */
	public List<PatrolRecord> getAll() {
		List<PatrolRecord> patrolRecords = new ArrayList<PatrolRecord>();
		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query("patrol_record", null, null, null, null, null, null);
		if (cursor != null) {
			PatrolRecord patrolRecord = null;
			while (cursor.moveToNext()) {
				patrolRecord = new PatrolRecord();
				patrolRecord.setLineId(cursor.getString(cursor.getColumnIndex("lineId")));
				patrolRecord.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
				patrolRecord.setPatrolTime(cursor.getString(cursor.getColumnIndex("patrolTime")));
				patrolRecord.setError(cursor.getString(cursor.getColumnIndex("error")));
				patrolRecord.setId(cursor.getInt(cursor.getColumnIndex("id")));
				patrolRecord.setNodeId(cursor.getString(cursor.getColumnIndex("nodeId")));
				patrolRecord.setPatrolPhoneTime(cursor.getString(cursor.getColumnIndex("patrolPhoneTime")));
				patrolRecord.setUserPatrolId(cursor.getString(cursor.getColumnIndex("userPatrolId")));
				patrolRecords.add(patrolRecord);
			}
		}
		return patrolRecords;
	}

    public Map<String, PatrolRecord> getMap(String lineId, int sequence) {
        Map<String, PatrolRecord> map = new HashMap<>();
        SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.query("patrol_record", null, "lineId = ? and sequence = ?", new String[]{lineId, String.valueOf(sequence)}, null, null, null);
        if (cursor != null) {
            PatrolRecord patrolRecord = null;
            while (cursor.moveToNext()) {
                patrolRecord = new PatrolRecord();
                patrolRecord.setPatrolTime(cursor.getString(cursor.getColumnIndex("patrolTime")));
                patrolRecord.setError(cursor.getString(cursor.getColumnIndex("error")));
                patrolRecord.setId(cursor.getInt(cursor.getColumnIndex("id")));
                String nodeid = cursor.getString(cursor.getColumnIndex("nodeId"));
                patrolRecord.setNodeId(nodeid);
                patrolRecord.setLineId(cursor.getString(cursor.getColumnIndex("lineId")));
                patrolRecord.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
                patrolRecord.setPatrolPhoneTime(cursor.getString(cursor.getColumnIndex("patrolPhoneTime")));
                patrolRecord.setUserPatrolId(cursor.getString(cursor.getColumnIndex("userPatrolId")));
                map.put(nodeid, patrolRecord);
            }
        }
        return map;
    }

	public void deleteAll() {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		writableDatabase.delete("patrol_record", null, null);
	}

}
