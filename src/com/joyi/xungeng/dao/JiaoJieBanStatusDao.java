package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.JiaoJieBanStatus;

/**
 * Created by zhangyong on 2014/10/28.
 * 用户，交接班状态 持久层
 */
public class JiaoJieBanStatusDao {

	/**
	 * 获取用户次本<strong>交班</strong>时间
	 * @param userId
	 * @return
	 */
	public String getJiaoBanTime(String userId) {
		String time = "";
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("jiao_jie_ban_status", null, "userId = ? and type = ?", new String[]{userId, String.valueOf(JiaoJieBanStatus.TYPE_JIAO_BAN)}, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			time = cursor.getString(cursor.getColumnIndex("time"));
		}
		return time;
	}

	/**
	 * 获取用户次本<strong>接班</strong>时间
	 * @param userId
	 * @return
	 */
	public String getJieBanTime(String userId) {
		String time = "";
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("jiao_jie_ban_status", null, "userId = ? and type = ?", new String[]{userId, String.valueOf(JiaoJieBanStatus.TYPE_JIE_BAN)}, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			time = cursor.getString(cursor.getColumnIndex("time"));
		}
		return time;
	}

	/**
	 * 用户交/接班
	 * @param status
	 */
	public void userJiaoJieBan(JiaoJieBanStatus status) {
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		db.beginTransaction();
		db.delete("jiao_jie_ban_status", "userId = ? and type = ?", new String[]{status.getUserId(), String.valueOf(status.getType())});
		ContentValues values = new ContentValues();
		values.put("userId", status.getUserId());
		values.put("type", status.getType());
		values.put("time", status.getTime());
		db.insert("jiao_jie_ban_status", null, values);
		db.setTransactionSuccessful();
		db.endTransaction();

	}

	/**
	 * 用户交/接班
	 * @param userId
	 * @param type
	 * @param time
	 */
	public void userJiaoJieBan(String userId, int type, String time) {
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		db.beginTransaction();
		db.delete("jiao_jie_ban_status", "userId = ? and type = ?", new String[]{userId, String.valueOf(type)});
		ContentValues values = new ContentValues();
		values.put("userId", userId);
		values.put("type", type);
		values.put("time", time);
		db.insert("jiao_jie_ban_status", null, values);
		db.setTransactionSuccessful();
		db.endTransaction();

	}
}