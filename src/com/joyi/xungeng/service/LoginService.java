package com.joyi.xungeng.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangyong on 2014/10/14.
 */
public class LoginService {
	private static LoginService loginService;
	private Context context;
	private Date serverDate;
	private boolean hasSyncServerTime;


	private LoginService(Context context) {this.context = context;}

	public static LoginService getInstance(Context context) {
		if (loginService == null) {
			loginService = new LoginService(context);
		}
		return loginService;
	}

	/**
	 * 获取新版请下载地下, 无新版本返回null
	 * @return
	 */
	public String getNewVersionUrl() {
		return null;
	}


	/**
	 * 本地新建一个TimerTask, 在离线状态下同步服务器时间
	 */
	public void serverTimeTask(final Date date) {
		if (hasSyncServerTime) {
			return;
		}
		hasSyncServerTime = true;
		serverDate = date;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				serverDate.setTime(serverDate.getTime() + 1000);
			}
		}, 0, 1000);
	}

	/**
	 * 新版本弹出层
	 */
	public void showAlert(){
		new AlertDialog.Builder(context).setTitle("").setMessage("检测到有新版本, 请升级...").setPositiveButton("升级", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

			}
		}).setNegativeButton("退出", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

			}
		}).show();
	}

	public Date getServerDate() {
		return serverDate;
	}
}
