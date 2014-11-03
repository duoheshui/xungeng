package com.joyi.xungeng.util;

import android.app.Activity;
import com.joyi.xungeng.activity.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangyong on 2014/10/14.
 * 常量类
 */
public class Constants {

	public static final int MIN_PASSWORD_LENGTH = 6;                        // 密码最小长度
	public static final int MAX_TIME_TOLERANCE = 10;                        // 本地时间和服务器时间的最大容差 (分钟)
	public static String SUPER_PASSWORD = "";                               // 超级密码

	/**
	 * 菜单名称
	 */
	public static final String[] FUNCTION_MENU_NAME = {"巡更路线", "巡查打卡", "交接班", "信息上传", "替岗"};
	public static final String[] SYSTEM_MENU_NAME = {"修改密码", "切换帐号","我", "关于","退出"};

	/**
	 * 菜单名称与Activity类对应关系
	 */
	public static Map<String, Class<? extends Activity>> NAME_ACTIVITY_MAP = new HashMap();
	static {
		NAME_ACTIVITY_MAP.put(FUNCTION_MENU_NAME[0], XunGengLuXianActivity.class);
		NAME_ACTIVITY_MAP.put(FUNCTION_MENU_NAME[1], XunchaDaKaActivity.class);
		NAME_ACTIVITY_MAP.put(FUNCTION_MENU_NAME[2], JiaoJieBanActivity.class);
		NAME_ACTIVITY_MAP.put(FUNCTION_MENU_NAME[3], XinXiShangChuanActivity.class);
		NAME_ACTIVITY_MAP.put(FUNCTION_MENU_NAME[4], TiGangActivity.class);
		NAME_ACTIVITY_MAP.put(SYSTEM_MENU_NAME[0], XiuGaiMiMaActivity.class);
		NAME_ACTIVITY_MAP.put(SYSTEM_MENU_NAME[2], WoActivity.class);
		NAME_ACTIVITY_MAP.put(SYSTEM_MENU_NAME[3], GuanYuActivity.class);

	}

	public static final String DATA_BASE_NAME = "xinshiji_wuye_xungeng.db";     // 数据库名
	public static final int DATA_BASE_VERSION = 1;                              // 数据库版本号
	public static final String APP_VERSION = "v2.1";                            //  当前客户端版本号
	public static final String RELEASE_TIME = "2014-10-31 11:13:55";            // 发布日期


	public static final String HTTP_SUCCESS_CODE = "200";                                          // http请求成功状态
	public static final String TEST_DOMAIN_NAME = "http://eps.joyiwy.cn:4903";                     // 测试地址
	public static final String HTTP_DOMAIN_NAME = "http://eps.joyiwy.cn:4901";                      // 正式地址
	public static final String DOMAIN_NAME = HTTP_DOMAIN_NAME;
	public static final String LOGIN_URL = DOMAIN_NAME + "/api?act=login";                         // 登录地址
	public static final String CHANGE_PASSWORD_URL = DOMAIN_NAME+"/api?act=setpwd";                // 修改密码URL
	public static final String HAS_NEW_VERSION_URL = DOMAIN_NAME+"/api?act=getnewv";               // 获取是否有新的客户端版本URL
	public static final String UPLOAD_PARTOL_RECORD_URL = DOMAIN_NAME+"/api?act=uploadpatrol";     // 上传打卡信息URL
	public static final String UPLOAD_SHIFT_INFO_URL = DOMAIN_NAME+"/api?act=uploadschedule";      // 上传交接班信息URL
	public static final String UPLOAD_PATROL_VIEW_URL = DOMAIN_NAME+"/api?act=uploadpatrolview";   // 上传巡查信息

	public static final int WHAT_PATROL_RECORED = 1;                            // 上传标识:巡更记录
	public static final int WHAT_SHIFT_RECORD = 2;                              // 上传标识:交接班记录
	public static final int WHAT_PATROL_VIEW = 3;                               // 上传标识:巡查记录

	public static final int NOT_SYNC = 0;
	public static final int HAS_SYNC = 1;
	public static final int SYNC_ALL = 2;

}
