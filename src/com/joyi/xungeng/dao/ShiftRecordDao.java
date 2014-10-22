package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.ShiftRecord;
import com.joyi.xungeng.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【交接班记录】dao
 */
public class ShiftRecordDao {

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

		return writableDatabase.insert("shift_record", null, contentValues);
	}

	/**
	 * 获取所有交接班记录
	 * @return
	 */
	public List<ShiftRecord> getAll() {
		List<ShiftRecord> shiftRecords = new ArrayList<ShiftRecord>();
		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query("shift_record", null, null, null, null, null, null);
		if (cursor != null) {
			ShiftRecord shiftRecord = null;
			while (cursor.moveToNext()) {
				shiftRecord = new ShiftRecord();
				shiftRecord.setId(cursor.getInt(cursor.getColumnIndex("id")));
				shiftRecord.setReceiveTime(cursor.getString(cursor.getColumnIndex("receive_time")));
				shiftRecord.setRecivePhoneTime(cursor.getString(cursor.getColumnIndex("receive_phone_time")));
				shiftRecord.setSubmitPhoneTime(cursor.getString(cursor.getColumnIndex("submit_phone_time")));
				shiftRecord.setSubmitTime(cursor.getString(cursor.getColumnIndex("submit_time")));
				shiftRecord.setScheduleTypeId(cursor.getString(cursor.getColumnIndex("schedule_type_id")));
				shiftRecord.setStationId(cursor.getString(cursor.getColumnIndex("station_id")));
				shiftRecord.setUserId(cursor.getString(cursor.getColumnIndex("uid")));
				shiftRecords.add(shiftRecord);
			}
		}

		return shiftRecords;
	}

	/**
	 * 删除所有记录
	 */
	public void deleteAll() {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		writableDatabase.delete("shift_record", null, null);
	}
}

