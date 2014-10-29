package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.LuXianLunCi;

/**
 * Created by zhangyong on 2014/10/28.
 * 路线，当前路线进行到第几轮 持久层
 * 表名：lun_xian_lun_ci
 */
public class LuXianLunCiDao {

	/**
	 * 获取当前用户, 当前路线的进行到的轮次
	 * @param userId
	 * @param lineId
	 * @return
	 */
	public int getLunCi(String userId, String lineId) {
		int lunCi = -1;
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("lun_xian_lun_ci", null, "userId = ? and lineId = ?", new String[]{userId, lineId}, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			lunCi = cursor.getInt(cursor.getColumnIndex("lunCi"));
		}
		return lunCi;
	}

	/**
	 * 设置当前用户当前线路进行中的轮次
	 * @param lunCi
	 */
	public void setLunCi(LuXianLunCi lunCi) {
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getWritableDatabase();

		db.beginTransaction();
		db.delete("lun_xian_lun_ci", "userId = ? and lineId = ?", new String[]{lunCi.getUserId(), lunCi.getLineId()});
		ContentValues values = new ContentValues(3);
		values.put("userId", lunCi.getUserId());
		values.put("lineId", lunCi.getLineId());
		values.put("lunCi", lunCi.getLunCi());
		db.insert("lun_xian_lun_ci", null, values);
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	/**
	 * 设置当前用户当前线路进行中的轮次
	 * @param userId
	 * @param lineId
	 * @param lunCi
	 */
	public void setLunCi(String userId, String lineId, int lunCi) {
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getWritableDatabase();

		db.beginTransaction();
		db.delete("lun_xian_lun_ci", "userId = ? and lineId = ?", new String[]{userId, lineId});
		ContentValues values = new ContentValues(3);
		values.put("userId", userId);
		values.put("lineId", lineId);
		values.put("lunCi", lunCi);
		db.insert("lun_xian_lun_ci", null, values);
		db.setTransactionSuccessful();
		db.endTransaction();
	}
}
