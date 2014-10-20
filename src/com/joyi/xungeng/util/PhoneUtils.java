package com.joyi.xungeng.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zhangyong on 2014/10/20.
 * 手机工具
 */
public class PhoneUtils {

	/**
	 * 判断当前网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isConnected() && mNetworkInfo.getState()== NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前网络链接类型
	 * @param context
	 * @return ["wifi", "3g"]
	 */
	public static String getNetworkType(Context context) {
		String networkType = "";
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo == null) {
				return networkType;
			}
			int type = mNetworkInfo.getType();
			if (type == ConnectivityManager.TYPE_WIFI) {
				return "wifi";
			}
			if (type == ConnectivityManager.TYPE_MOBILE) {
				return "3g";
			}
		}
		return "";
	}

}
