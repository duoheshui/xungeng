package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.LuXianLunCi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/28.
 * 路线，当前路线进行到第几轮 持久层
 * 表名：lun_xian_lun_ci
 */
public class LuXianLunCiDao{
	private static final String Table_Name = "lun_xian_lun_ci";

	/**
	 * 获取当前用户, 当前路线的进行到的轮次
	 * @param userId
	 * @param lineId
	 * @return
	 */
	public int getLunCi(String userId, String lineId) {
		int lunCi = -1;
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = db.query(Table_Name, null, "userId = ? and lineId = ?", new String[]{userId, lineId}, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			lunCi = cursor.getInt(cursor.getColumnIndex("lunCi"));
		}
		return lunCi;
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
		db.delete(Table_Name, "userId = ? and lineId = ?", new String[]{userId, lineId});
		ContentValues values = new ContentValues(3);
		values.put("userId", userId);
		values.put("lineId", lineId);
		values.put("lunCi", lunCi);
		db.insert(Table_Name, null, values);
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	/**
	 * 获取当前用户的记录
	 * @return
	 */
	public List<LuXianLunCi> getAll() {
		List<LuXianLunCi> list = new ArrayList<>();
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = db.query(Table_Name, null, "userId = ?", new String[]{SystemVariables.USER_ID}, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			LuXianLunCi ll = new LuXianLunCi();
			ll.setLineId(cursor.getString(cursor.getColumnIndex("lineId")));
			ll.setLunCi(cursor.getInt(cursor.getColumnIndex("lunCi")));
			ll.setUserId(SystemVariables.USER_ID);
			list.add(ll);
		}
		return list;
	}
}
