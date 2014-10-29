package com.joyi.xungeng.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.joyi.xungeng.util.Constants;

/**
 * Created by zhangyong on 2014/10/16 0016.
 */
public class WuYeSqliteOpenHelper extends SQLiteOpenHelper {

    // 巡查打卡
    private static final String Patrol_View_Sql =
            "create table partol_view(" +
            "id integer primary key autoincrement, " +
            "nodeid char(20)," +
            "userid char(20)," +
            "patrolTime datetime," +
		    "nodeName varchar(50),"+
            "patrolPhoneTime datetime," +
            "sync integer)";

    // 打卡记录
    private static final String Patrol_Record_Sql =
            "create table patrol_record(" +
            "id integer primary key autoincrement, " +
            "nodeId char(20)," +
            "userPatrolId integer," +
            "patrolTime datetime," +
            "patrolPhoneTime datetime," +
            "sequence integer,"+
            "lineId char(20),"+
            "error varchar(200)," +
            "sync integer," +
            "tuserId char(20))";

    // 交接班记录
    private static final String Shift_Patrol_Sql =
            "create table shift_record(" +
            "id integer primary key autoincrement, " +
            "uid char(20)," +
            "station_id char(20)," +
            "schedule_type_id char(20)," +
            "submit_time datetime," +
            "submit_phone_time datetime," +
            "receive_time datetime," +
            "receive_phone_time datetime," +
            "sync integer," +
            "tuserId char(20))";

    // 轮次记录
    private static final String User_Patrol_Sql =
            "create table user_patrol (" +
            "id integer primary key autoincrement, " +
            "userid char(20)," +
            "lineid char(20), " +
            "sequence integer, " +
            "beginTime datetime," +
            "endTime datetime," +
            "scheduleId char(20), "+
            "beginPhoneTime datetime," +
		    "scheduleTypeId char(20)," +
            "endPhoneTime datetime," +
            "sync integer," +
            "tuserId char(20))";

	// 交接班状态
	private static final String Jiao_Jie_Ban_Status_Sql =
			"create table jiao_jie_ban_status(" +
			"userId char(20)," +
			"type char(1)," +
			"time varchar(20))";

	// 路线-已进行到的轮次
	private static final String Lu_Xian_Lun_Ci_Sql =
					"create table lun_xian_lun_ci(" +
					"userId char(20)," +
					"lineId char(20)," +
					"lunCi integer)";

	// 路线-轮次-该轮id
	private static final String Lu_Xian_Lun_Ci_Id_Sql =
			"create table lu_xian_lun_ci_id(" +
					"userId char(20)," +
					"lineId char(20)," +
					"lunCi integer," +
					"lunId integer)";

    public WuYeSqliteOpenHelper(Context context) {
        super(context, Constants.DATA_BASE_NAME, null, Constants.DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

	    try {
		    db.beginTransaction();
		    db.execSQL(Patrol_View_Sql);
		    db.execSQL(Patrol_Record_Sql);
		    db.execSQL(Shift_Patrol_Sql);
		    db.execSQL(User_Patrol_Sql);
		    db.execSQL(Jiao_Jie_Ban_Status_Sql);
		    db.execSQL(Lu_Xian_Lun_Ci_Sql);
		    db.execSQL(Lu_Xian_Lun_Ci_Id_Sql);
		    db.setTransactionSuccessful();
	    } finally {
		    db.endTransaction();
	    }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
