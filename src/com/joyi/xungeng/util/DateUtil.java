package com.joyi.xungeng.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangyong on 2014/10/15.
 */
public class DateUtil {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getHumanReadStr(Date date) {
		if (date == null) {
			return "";
		}
		String format = dateFormat.format(date);
		return format;
	}
}
