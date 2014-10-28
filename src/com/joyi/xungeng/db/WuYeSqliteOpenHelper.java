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

    // 路线节点
    private static final String Line_Node_Sql =
            "create table line_node(" +
            "id char(20)," +
            "lineid char(20)," +
            "nodeName varchar(50)," +
            "nfcCode varchar(50))";

    // 巡查打卡
    private static final String Patrol_View_Sql =
            "create table partol_view(" +
            "id integer primary key autoincrement, " +
            "nodeid char(20)," +
            "userid char(20)," +
            "patrolTime datetime," +
		    "nodeName varchar(50),"+
            "patrolPhoneTime datetime)";

    // 路线
    private static final String Patrol_Line_Sql =
            "create table partol_line(" +
            "id char(20)," +
            "postid char(20)," +
            "frequency integer," +
            "offset integer," +
            "beginTime datetime," +
            "endTime datetime)";

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
            "error varchar(200))";

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
            "receive_phone_time datetime)";

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
            "endPhoneTime datetime)";


    public WuYeSqliteOpenHelper(Context context) {
        super(context, Constants.DATA_BASE_NAME, null, Constants.DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

	    try {
		    db.beginTransaction();
		    db.execSQL(Line_Node_Sql);
		    db.execSQL(Patrol_View_Sql);
		    db.execSQL(Patrol_Line_Sql);
		    db.execSQL(Patrol_Record_Sql);
		    db.execSQL(Shift_Patrol_Sql);
		    db.execSQL(User_Patrol_Sql);
		    db.setTransactionSuccessful();
	    } finally {
		    db.endTransaction();
	    }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
