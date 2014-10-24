package com.joyi.xungeng.dao;

import android.content.Context;
import android.os.Parcelable;
import com.joyi.xungeng.db.PartolViewDBHelper;
import com.joyi.xungeng.domain.PatrolView;

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

	}
}
