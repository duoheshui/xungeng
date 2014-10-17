package com.joyi.xungeng;

import android.database.sqlite.SQLiteOpenHelper;
import com.joyi.xungeng.domain.KeyValuePair;
import com.joyi.xungeng.domain.PatrolLine;
import com.joyi.xungeng.domain.Station;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/16.
 * 系统级变量<br>
 * 用于保存从服务端读取到的数据, 如:用户下的路线, 岗位, 班次
 */
public class SystemVariables {


    /**
     * 据数据库操作对象
     */
    public static SQLiteOpenHelper sqLiteOpenHelper;

	/**
	 * 服务器时间
	 */
	public static final Date SERVER_TIME = new Date();

	/**
	 * 巡更路线
	 */
	public static final List<PatrolLine> PATROL_LINES = new ArrayList<PatrolLine>();


	/**
	 * 班次数据
	 */
	public static final List<KeyValuePair> SHIFT_LIST = new ArrayList<KeyValuePair>();


	/**
	 * 岗位数据
	 */
	public static final List<Station> STATION_LIST = new ArrayList<Station>();

	/**
	 * imei号
	 */
	public static final String IMEI = "";

}
