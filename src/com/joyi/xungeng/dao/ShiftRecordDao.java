package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.ShiftRecord;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【交接班记录】dao
 */
public class ShiftRecordDao {
	private static final String Table_Name = "shift_record";

	/**
	 * 添加记录
	 * @param shiftRecord
	 */
	public long add(ShiftRecord shiftRecord) {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("uid", shiftRecord.getUserId());
		contentValues.put("station_id", shiftRecord.getStationId());
		contentValues.put("schedule_type_id", shiftRecord.getScheduleTypeId());
		contentValues.put("submit_time", shiftRecord.getSubmitTime());
		contentValues.put("submit_phone_time", shiftRecord.getSubmitPhoneTime());
		contentValues.put("receive_time", shiftRecord.getReceiveTime());
		contentValues.put("receive_phone_time", shiftRecord.getRecivePhoneTime());
		contentValues.put("tuserId", shiftRecord.getTuserId());

		return writableDatabase.insert(Table_Name, null, contentValues);
	}

	/**
	 * 获取所有交接班记录
	 * @return
	 */
	public List<ShiftRecord> getAll(int sync) {
		List<ShiftRecord> shiftRecords = new ArrayList<ShiftRecord>();
		String where = "uid = ?";
		String[] whereArgs = new String[]{SystemVariables.user.getId()};
		if (sync != Constants.SYNC_ALL) {
			where = "uid = ? and sync = ?";
			whereArgs = new String[]{SystemVariables.user.getId(), String.valueOf(sync)};

		}

		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query(Table_Name, null, where, whereArgs, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				newInstance(cursor, shiftRecords);
			}
		}

		return shiftRecords;
	}

	/**
	 * 获取所有未同步数据
	 * @return
	 */
	public List<ShiftRecord> getAllNotSync() {
		List<ShiftRecord> shiftRecords = new ArrayList<ShiftRecord>();
		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query(Table_Name, null, "sync <> ?", new String[]{String.valueOf(Constants.HAS_SYNC)}, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				newInstance(cursor, shiftRecords);
			}
		}
		return shiftRecords;
	}

	/**
	 * 删除所有记录
	 */
	public void deleteAll() {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		writableDatabase.delete(Table_Name,"uid = ?", new String[]{SystemVariables.user.getId()});
	}


	/**
	 * 更新数据的同步状态
	 */
	public void sync() {
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues(1);
		values.put("sync", Constants.HAS_SYNC);
		db.update(Table_Name, values, null, null);
	}


	/**
	 * 组装ShiftRecord对象
	 * @param cursor
	 * @param shiftRecords
	 * @return
	 */
	public List<ShiftRecord> newInstance(Cursor cursor, List<ShiftRecord> shiftRecords) {
		ShiftRecord shiftRecord = new ShiftRecord();
		shiftRecord.setId(cursor.getInt(cursor.getColumnIndex("id")));
		shiftRecord.setReceiveTime(cursor.getString(cursor.getColumnIndex("receive_time")));
		shiftRecord.setRecivePhoneTime(cursor.getString(cursor.getColumnIndex("receive_phone_time")));
		shiftRecord.setSubmitPhoneTime(cursor.getString(cursor.getColumnIndex("submit_phone_time")));
		shiftRecord.setSubmitTime(cursor.getString(cursor.getColumnIndex("submit_time")));
		shiftRecord.setScheduleTypeId(cursor.getString(cursor.getColumnIndex("schedule_type_id")));
		shiftRecord.setStationId(cursor.getString(cursor.getColumnIndex("station_id")));
		shiftRecord.setUserId(cursor.getString(cursor.getColumnIndex("uid")));
		shiftRecord.setTuserId(cursor.getString(cursor.getColumnIndex("tuserId")));
		shiftRecords.add(shiftRecord);
		return shiftRecords;
	}
}

