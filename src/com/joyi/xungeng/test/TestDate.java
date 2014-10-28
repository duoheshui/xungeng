package com.joyi.xungeng.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by zhangyong on 2014/10/22.
 */
public class TestDate {
	public static void main(String[] args) {
		//		String datestr = "08:25:39";
		//		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		//		Date date = format.parse(datestr);
//				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//		System.out.println(dateFormat.format(date));
		//		Calendar calendar = Calendar.getInstance();
		//
		//		Calendar begin = Calendar.getInstance();
		//		begin.setTime(date);
		//		begin.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		//		System.out.println(dateFormat.format(begin.getTime()));
		//		Integer integer = new Integer(0);
		//		System.out.println(integer == 0);

		//		int i = Color.parseColor("#00AABBCC");
		//		System.out.println("I:" + i);
		String newAppUrl = "http://www.joyi.com/wuye/xungeng1.1.apk";
		String name = newAppUrl.substring(newAppUrl.lastIndexOf("/"));
		System.out.println(name);

	}
}
