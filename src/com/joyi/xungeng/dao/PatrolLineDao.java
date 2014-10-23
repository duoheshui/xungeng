package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.PatrolLine;
import com.joyi.xungeng.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更路线】dao
 */
public class PatrolLineDao {

	/**
	 * 添加路线
	 * @param patrolLine
	 */
	public long add(PatrolLine patrolLine) {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("id", patrolLine.getId());
		contentValues.put("postid", patrolLine.getStationId());
		contentValues.put("frequency", patrolLine.getFrequency());
		contentValues.put("offset", patrolLine.getException());
		contentValues.put("beginTime", patrolLine.getBeginTime());
		contentValues.put("endTime", patrolLine.getEndTime());

        return writableDatabase.insert("partol_line", null, contentValues);
    }

	/**
	 * 获取所有路线
	 * @return
	 */
	public List<PatrolLine> getList() {
		List<PatrolLine> patrolLines = new ArrayList<PatrolLine>();
		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query("partol_line", null, null, null, null, null, null);
		if (cursor != null) {
			PatrolLine patrolLine = null;
			while (cursor.moveToNext()) {
				patrolLine = new PatrolLine();
				patrolLine.setId(cursor.getString(cursor.getColumnIndex("id")));
				patrolLine.setStationId(cursor.getString(cursor.getColumnIndex("postid")));
				patrolLine.setFrequency(cursor.getInt(cursor.getColumnIndex("frequency")));
				patrolLine.setException(cursor.getInt(cursor.getColumnIndex("offset")));
				patrolLine.setBeginTime(cursor.getString(cursor.getColumnIndex("beginTime")));
				patrolLine.setEndTime(cursor.getString(cursor.getColumnIndex("endTime")));
				patrolLines.add(patrolLine);
			}
		}

		return patrolLines;
	}

	public void deleteAll() {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		writableDatabase.delete("partol_line", null, null);
	}
}
