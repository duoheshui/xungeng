package com.joyi.xungeng.util;

import android.text.method.HideReturnsTransformationMethod;

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


	/**
	 * 比较源数据和MD5指是否和md5Str一致
	 * @param src
	 * @param md5Str
	 * @return
	 */
	public static boolean compareMd5(String src, String md5Str) {
		if (src == null || md5Str == null) {
			return false;
		}
		String srcMd5 = MD5Util.GetMD5(src);
		return md5Str.equals(srcMd5);
	}
}
