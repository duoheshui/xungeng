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

	/**
	 * 域名
	 */
	public static final String HTTP_DOMAIN_NAME = "http://218.28.243.172:4900";

	/**
	 * 登录地址
	 */
	public static final String LOGIN_URL = HTTP_DOMAIN_NAME + "/api?act=login";


    /**
     * http请求状态码
     */
    public static final String HTTP_SUCCESS_CODE = "200";

	/**
	 * 菜单
	 */
	public static final String[] FUNCTION_MENU_NAME = {"巡更路线", "巡查打卡", "交接班", "信息上传"};
	public static final String[] SYSTEM_MENU_NAME = {"修改密码", "切换帐号", "退出"};

	/**
	 * 菜单名称与Activity类对应关系
	 */
	public static Map<String, Class<? extends Activity>> NAME_ACTIVITY_MAP = new HashMap();
	static {
		NAME_ACTIVITY_MAP.put(FUNCTION_MENU_NAME[0], XunGengLuXianActivity.class);
		NAME_ACTIVITY_MAP.put(FUNCTION_MENU_NAME[1], ChaXunDaKaActivity.class);
		NAME_ACTIVITY_MAP.put(FUNCTION_MENU_NAME[2], JiaoJieBanActivity.class);
		NAME_ACTIVITY_MAP.put(FUNCTION_MENU_NAME[3], XinXiShangChuanActivity.class);
		NAME_ACTIVITY_MAP.put(SYSTEM_MENU_NAME[0], XiuGaiMiMaActivity.class);
	}

	/**
	 * 数据库名
	 */
	public static final String DATA_BASE_NAME = "xinshiji_wuye_xungeng.db";


	/**
	 * 数据库版本号
	 */
	public static final int DATA_BASE_VERSION = 1;

	/**
	 * 当前客户端版本号
	 */
	public static final String APP_VERSION = "1.0";


	/**
	 * 请求地址
	 */
	public static final String HAS_NEW_VERSION_URL = "";        // 获取是否有新的客户端版本URL
	public static final String UPLOAD_PARTOL_RECORD_URL = "";     // 上传打卡信息URL
	public static final String UPLOAD_SHIFT_INFO_URL = "";      // 上传交接班信息URL
	public static final String UPLOAD_MOBILE_INFO_URL = "";     // 上传手机硬件信息URL
	public static final String UPLOAD_PATROL_VIEW_URL = "";     // 上传巡查信息
	public static final String UPLOAD_NFC_INFO_URL = "";        // 上传NFC卡信息URL


}
