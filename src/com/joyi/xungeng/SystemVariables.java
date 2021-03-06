package com.joyi.xungeng;

import android.database.sqlite.SQLiteOpenHelper;
import com.joyi.xungeng.domain.*;

import java.security.spec.PSSParameterSpec;
import java.util.*;

/**
 * Created by zhangyong on 2014/10/16.
 * 系统级变量<br>
 * 用于保存从服务端读取到的数据, 如:用户下的路线, 岗位, 班次
 */
public class SystemVariables {
	// 登录用户
	public static final User user = new User();
	public static String USER_ID = "";

	// 替岗用户
	public static final User tUser = new User();
	public static String T_USER_ID = "";

	public static final Map<String, User> ALL_USERS_MAP = new HashMap<>();          // 所有用户 <loginName, User>映射关系

	/**
	 * 所有节点数据
	 */
	public static final List<LineNode> ALL_LINE_NODES = new ArrayList<>();
    public static final Map<String, LineNode> ALL_LINE_NODES_MAP = new HashMap<>(); // nfcCode<->PatrolNode
	public static final Map<String, LineNode> NODEID_NODE_MAP = new HashMap<>();    // nodeId<->PatrolNode
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
	public static final List<PatrolLine> PATROL_LINES = new ArrayList<>();


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
	public static String IMEI = "";

}
