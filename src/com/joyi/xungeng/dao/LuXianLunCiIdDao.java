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

	/**
	 * 获取指定用户, 指定线路, 指定轮次, 的轮id
	 * @param lid
	 * @return
	 */
	public long getLunCiId(LuXianLunCiId lid) {
		long id = -1;
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("lu_xian_lun_ci_id", null, "userId = ? and lineId = ? and lunCi = ?",
				new String[]{lid.getUserId(), lid.getLineId(), String.valueOf(lid.getLunCi())}, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			id = cursor.getLong(cursor.getColumnIndex("lunId"));
		}

		return id;
	}

	/**
	 * 设置指定用户, 指定线路, 指定轮次, 的轮id
	 * @param lid
	 * @return
	 */
	public void setLunCiId(LuXianLunCiId lid) {
		SQLiteDatabase db = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		db.beginTransaction();

		db.delete("lu_xian_lun_ci_id", "userId = ? and lineId = ? and lunCi = ?", new String[]{lid.getUserId(), lid.getLineId(), String.valueOf(lid.getLunCi())});
		ContentValues values = new ContentValues(4);
		values.put("userId", lid.getUserId());
		values.put("lineId", lid.getLineId());
		values.put("lunCi", lid.getLunCi());
		values.put("lunId", lid.getLunId());
		db.insert("lu_xian_lun_ci_id", null, values);
		db.setTransactionSuccessful();
		db.endTransaction();
	}
}
