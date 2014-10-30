package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.LuXianLunCiId;

/**
 * Created by zhangyong on 2014/10/28.
 * 路线，当前轮次，当前轮次id  持久层
 * 表名：lu_xian_lun_ci_id
 */
public class LuXianLunCiIdDao {
	private static String Table_Name = "lu_xian_lun_ci_id";

	/**
	 * 获取指定用户, 指定线路, 指定轮次, 的轮id
	 * @param userId
	 * @param lineId
	 * @param lunCi
	 * @return
	 */
	public long getLunCiId(String userId, String lineId, int lunCi) {
		long id = -1;
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = db.query(Table_Name, null, "userId = ? and lineId = ? and lunCi = ?",
				new String[]{userId, lineId, String.valueOf(lunCi)}, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			id = cursor.getLong(cursor.getColumnIndex("lunId"));
		}

		return id;
	}

	/**
	 * 设置指定用户, 指定线路, 指定轮次, 的轮id
	 * @param userId
	 * @param lineId
	 * @param lunCi
	 * @param lunId
	 */
	public void setLunCiId(String userId, String lineId, int lunCi, long lunId) {
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		db.beginTransaction();

		db.delete(Table_Name, "userId = ? and lineId = ? and lunCi = ?",
				new String[]{userId, lineId, String.valueOf(lunCi)});
		ContentValues values = new ContentValues(4);
		values.put("userId", userId);
		values.put("lineId", lineId);
		values.put("lunCi", lunCi);
		values.put("lunId", lunId);
		db.insert("lu_xian_lun_ci_id", null, values);
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	/**
	 * 清除用户记录
	 * @param userId
	 */
	public void clear(String userId) {
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues(1);
		values.put("lunId", -1);

		db.update(Table_Name, values, "userId = ?", new String[]{userId});
	}
}
