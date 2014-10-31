package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.PatrolRecord;
import com.joyi.xungeng.util.Constants;
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

	private static final String Table_Name = "patrol_record";
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
		contentValues.put("error", patrolRecord.getError());
		contentValues.put("tuserId", patrolRecord.getTuserId());
		contentValues.put("userId", patrolRecord.getUserId());
		contentValues.put("sync", patrolRecord.getSync());

		return writableDatabase.insert(Table_Name, null, contentValues);
	}


	/**
	 * 获取所有巡更记录
	 * @return
	 */
	public List<PatrolRecord> getAll(int sync) {
		List<PatrolRecord> patrolRecords = new ArrayList<PatrolRecord>();
		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();

		String where = "userId = ?";
		String[] whereArgs = new String[]{SystemVariables.USER_ID};
		if (sync != Constants.SYNC_ALL) {
			where = "userId = ? and sync = ?";
			whereArgs = new String[]{SystemVariables.USER_ID, String.valueOf(sync)};
		}
		Cursor cursor = readableDatabase.query(Table_Name, null, where, whereArgs, null, null, null);
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
				patrolRecord.setTuserId(cursor.getString(cursor.getColumnIndex("tuserId")));
				patrolRecord.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
				patrolRecord.setSync(cursor.getInt(cursor.getColumnIndex("sync")));
				patrolRecords.add(patrolRecord);
			}
		}
		return patrolRecords;
	}

	/**
	 * 根据轮次获取该轮次的记录
	 * @param sequence
	 * @return
	 */
	public List<PatrolRecord> getBySequence(String lineId, int sequence) {
		List<PatrolRecord> patrolRecords = new ArrayList<PatrolRecord>();
		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query(Table_Name, null, "sequence = ? and userId = ? and lineId = ?", new String[]{String.valueOf(sequence), SystemVariables.USER_ID, lineId}, null, null, null);
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
				patrolRecord.setTuserId(cursor.getString(cursor.getColumnIndex("tuserId")));
				patrolRecord.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
				patrolRecord.setSync(cursor.getInt(cursor.getColumnIndex("sync")));
				patrolRecords.add(patrolRecord);
			}
		}
		return patrolRecords;
	}


	/**
	 * 根据线路和轮次,获取该用户在该轮次所有的打卡记录 nodeId<->PatrolRecord 对应关系
	 * @param lineId
	 * @param sequence
	 * @return
	 */
    public Map<String, PatrolRecord> getMap(String lineId, int sequence) {
        Map<String, PatrolRecord> map = new HashMap<>();
        SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.query(Table_Name, null, "lineId = ? and sequence = ? and userId= ?", new String[]{lineId, String.valueOf(sequence), SystemVariables.USER_ID}, null, null, null);
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
	            patrolRecord.setTuserId(cursor.getString(cursor.getColumnIndex("tuserId")));
	            patrolRecord.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
	            patrolRecord.setSync(cursor.getInt(cursor.getColumnIndex("sync")));
                map.put(nodeid, patrolRecord);
            }
        }
        return map;
    }

	public void deleteAll() {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		writableDatabase.delete(Table_Name, "userId = ?", new String[]{SystemVariables.USER_ID});
	}

	/**
	 * 将已上传的记录做标记
	 */
	public void sync() {
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues(1);
		values.put("sync", Constants.HAS_SYNC);
		db.update(Table_Name, values, null, null);
	}
}
