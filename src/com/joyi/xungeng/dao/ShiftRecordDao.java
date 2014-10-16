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
	public void add(ShiftRecord shiftRecord) {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("uid", shiftRecord.getUserId());
		contentValues.put("station_id", shiftRecord.getStationId());
		contentValues.put("schedule_type_id", shiftRecord.getScheduleTypeId());
		contentValues.put("submit_time", DateUtil.getHumanReadStr(shiftRecord.getSubmitTime()));
		contentValues.put("submit_phone_time", DateUtil.getHumanReadStr(shiftRecord.getSubmitPhoneTime()));
		contentValues.put("receive_time", DateUtil.getHumanReadStr(shiftRecord.getReceiveTime()));
		contentValues.put("receive_phone_time", DateUtil.getHumanReadStr(shiftRecord.getRecivePhoneTime()));

		writableDatabase.insert("shift_record", null, contentValues);
	}

	/**
	 * 获取所有交接班记录
	 * @return
	 */
	public List<ShiftRecord> getList() {
		List<ShiftRecord> shiftRecords = new ArrayList<ShiftRecord>();
		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query("shift_record", null, null, null, null, null, null);
		if (cursor != null) {
			ShiftRecord shiftRecord = null;
			while (cursor.moveToNext()) {
				shiftRecord = new ShiftRecord();
				shiftRecord.setId(cursor.getInt(cursor.getColumnIndex("id")));
				shiftRecord.setReceiveTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("receive_time"))));
				shiftRecord.setRecivePhoneTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("receive_phone_time"))));
				shiftRecord.setSubmitPhoneTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("submit_phone_time"))));
				shiftRecord.setSubmitTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("submit_time"))));
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

