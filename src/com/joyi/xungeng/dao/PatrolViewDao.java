package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.PatrolView;
import com.joyi.xungeng.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡查打卡】 dao
 */
public class PatrolViewDao {


	/**
	 * 添加记录
	 * @param patrolView
	 */
	public void add(PatrolView patrolView) {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("lineid", patrolView.getNodeId());
		contentValues.put("userid", patrolView.getUserId());
		contentValues.put("patrolTime", DateUtil.getHumanReadStr(patrolView.getPatrolTime()));
		writableDatabase.insert("partol_view", null, contentValues);
	}

	/**
	 * 获取所有巡查打卡记录
	 * @return
	 */
	public List<PatrolView> getAll() {
		List<PatrolView> patrolViews = new ArrayList<PatrolView>();
		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query("partol_view", null, null, null, null, null, null);
		if (cursor != null) {
			PatrolView patrolView = null;
			while (cursor.moveToNext()) {
				patrolView = new PatrolView();
				patrolView.setId(cursor.getInt(cursor.getColumnIndex("id")));
				patrolView.setNodeId(cursor.getString(cursor.getColumnIndex("lineid")));
				patrolView.setUserId(cursor.getString(cursor.getColumnIndex("userid")));
				patrolView.setPatrolTime(DateUtil.getDateFromHumanReadStr(cursor.getString(cursor.getColumnIndex("patrolTime"))));
				patrolViews.add(patrolView);
			}
		}

		return patrolViews;
	}

	public void deleteAll() {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		writableDatabase.delete("partol_view", null, null);
	}
}