package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.PatrolView;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡查打卡】 dao
 */
public class PatrolViewDao {

	private static String Table_Name = "partol_view";

	/**
	 * 添加记录
	 *
	 * @param patrolView
	 */
	public long add(PatrolView patrolView) {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("userid", patrolView.getUserId());
		contentValues.put("nodeid", patrolView.getNodeId());
		contentValues.put("patrolTime", patrolView.getPatrolTime());
		contentValues.put("patrolPhoneTime", patrolView.getPatrolPhoneTime());
		contentValues.put("nodeName", patrolView.getNodeName());
		contentValues.put("sync", patrolView.getSync());
		return writableDatabase.insert(Table_Name, null, contentValues);
	}

	/**
	 * 获取指定用户的所有巡查打卡记录
	 *
	 * @return
	 */
	public List<PatrolView> getAll(int sync) {
		List<PatrolView> patrolViews = new ArrayList<PatrolView>();

		String where = "userid = ?";
		String[] whereArgs = new String[]{SystemVariables.user.getId()};
		if (sync != Constants.SYNC_ALL) {
			where = "userid = ? and sync = ?";
			whereArgs = new String[]{SystemVariables.user.getId(), String.valueOf(sync)};

		}

		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query(Table_Name, null, where, whereArgs, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				newInstant(cursor, patrolViews);
			}
		}
		return patrolViews;
	}

	/**
	 * 获取所有未同步数据
	 *
	 * @return
	 */
	public List<PatrolView> getAllNotSync() {
		List<PatrolView> patrolViews = new ArrayList<PatrolView>();
		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query(Table_Name, null, "sync <> ?", new String[]{String.valueOf(Constants.HAS_SYNC)}, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				newInstant(cursor, patrolViews);
			}
		}
		return patrolViews;
	}

	/**
	 * 删除用户的所有信息, 同步之后
	 */
	public void deleteAll() {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		writableDatabase.delete(Table_Name, "userid = ?", new String[]{SystemVariables.user.getId()});
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
	 * 组装PatrolView对象
	 * @param cursor
	 * @param patrolViews
	 * @return
	 */
	public List<PatrolView> newInstant(Cursor cursor, List<PatrolView> patrolViews){
		PatrolView patrolView = new PatrolView();
		patrolView.setId(cursor.getInt(cursor.getColumnIndex("id")));
		patrolView.setNodeId(cursor.getString(cursor.getColumnIndex("nodeid")));
		patrolView.setUserId(cursor.getString(cursor.getColumnIndex("userid")));
		patrolView.setPatrolTime(cursor.getString(cursor.getColumnIndex("patrolTime")));
		patrolView.setNodeName(cursor.getString(cursor.getColumnIndex("nodeName")));
		patrolView.setPatrolPhoneTime(cursor.getString(cursor.getColumnIndex("patrolPhoneTime")));
		patrolView.setSync(cursor.getInt(cursor.getColumnIndex("sync")));
		patrolViews.add(patrolView);
		return patrolViews;
	}
}