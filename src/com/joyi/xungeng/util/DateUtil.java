package com.joyi.xungeng.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zhangyong on 2014/10/15.
 */
public class DateUtil {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	static{
		// 设置时区
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		timeFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}

	public static String getHumanReadStr(Date date) {
		if (date == null) {
			return "";
		}
		String format = dateFormat.format(date);
		return format;
	}

	/**
	 * 返回HH:mm:ss格式的字符串
	 * @param date
	 * @return
	 */
	public static String getTimeStr(Date date) {
		if (date == null) {
			return "";
		}
		return timeFormat.format(date);
	}

	/**
	 * 根据yyyy-MM-dd HH:mm:ss格式的字符串返回Date对象
	 * @param str
	 * @return
	 */
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

	/**
	 * 根据HH:mm:ss格式的字符串返回Date对象, 该对象的年月日为1970年1月1日
	 * @param str
	 * @return
	 */
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

	/**
	 * 根据HH:mm:ss格式的字符串返回Date对象, 该对象的年月日为当年当月当日
	 * @param str
	 * @return
	 */
	public static Date getDateFromTimeStr2(String str) {
		Date date = null;
		try {
			date = timeFormat.parse(str);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			Calendar now = Calendar.getInstance();
			calendar.set(Calendar.YEAR, now.get(Calendar.YEAR));
			calendar.set(Calendar.MONTH, now.get(Calendar.MONTH));
			calendar.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
			date = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("DateUtil.getDateFromHumanReadStr", e.toString());
		}
		return date;
	}
	/**
	 * 判断是否是时间 字符串
	 * @param dateStr
	 * @return
	 */
	public static boolean isValidDate(String dateStr) {
		boolean currect = true;
		if (StringUtils.isNullOrEmpty(dateStr)) {
			return false;
		}
		try {
			dateFormat.parse(dateStr);
		} catch (ParseException e) {
			currect = false;
			e.printStackTrace();
		}
		return currect;
	}
}
