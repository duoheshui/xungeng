package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import com.joyi.xungeng.db.PartolViewDBHelper;
import com.joyi.xungeng.domain.PatrolView;
import com.joyi.xungeng.util.DateUtil;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡查打卡】 dao
 */
public class PatrolViewDao {
	private PartolViewDBHelper partolViewDBHelper;

	public PatrolViewDao(Context context) {
		partolViewDBHelper = new PartolViewDBHelper(context);
	}


	/**
	 * 添加记录
	 * @param patrolView
	 */
	public void add(PatrolView patrolView) {
		SQLiteDatabase writableDatabase = partolViewDBHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("lineid", patrolView.getLineid());
		contentValues.put("userid", patrolView.getUid());
		contentValues.put("patrolTime", DateUtil.getHumanReadStr(patrolView.getPatrolTime()));
		writableDatabase.insert("partol_view", null, contentValues);
	}
}