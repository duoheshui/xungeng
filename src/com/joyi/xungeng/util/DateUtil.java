package com.joyi.xungeng.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangyong on 2014/10/15.
 */
public class DateUtil {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	public static String getHumanReadStr(Date date) {
		if (date == null) {
			return "";
		}
		String format = dateFormat.format(date);
		return format;
	}

	public static Date getDateFromHumanReadStr(String str) {
		Date date = null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.e("DateUtil.getDateFromHumanReadStr", e.toString());
		}
		return date;
	}

	public static Date getDateFromTimerStr(String str) {
		Date date = null;
		try {
			date = timeFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.e("DateUtil.getDateFromHumanReadStr", e.toString());
		}
		return date;
	}
}
