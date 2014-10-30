package com.joyi.xungeng.util;

/**
 * Created by zhangyong on 2014/10/14.
 * 字符串工具类
 */
public class StringUtils {

	public static final boolean isNull(String str) {
		return str == null;
	}

	public static final boolean isNotNull(String str) {
		return str != null;
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str);
	}

	/**
	 * 如果str==null返回 "",否则返回str
	 * @param str
	 * @return
	 */
	public static String getNotNullValue(String str) {
		return str == null ? "" : str;
	}
}
