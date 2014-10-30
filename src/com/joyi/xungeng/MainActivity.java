package com.joyi.xungeng;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.joyi.xungeng.activity.MenuActivity;
import com.joyi.xungeng.activity.XunGengDaKaActivity;
import com.joyi.xungeng.db.WuYeSqliteOpenHelper;
import com.joyi.xungeng.domain.*;
import com.joyi.xungeng.service.LoginService;
import com.joyi.xungeng.service.XunGengService;
import com.joyi.xungeng.util.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 应用程序入口Activity
 */
public class MainActivity extends BaseActivity {
	private LoginService loginService;
	private EditText username;
	private EditText password;
	private Button loginButton;
	private Handler loginHandler;
	private Handler autoUpdateHandler;
	private Handler installHandler;

	private AsyncHttpClient client = new AsyncHttpClient();

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// 手机imei号
		SystemVariables.IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

		SystemVariables.STATION_LIST.clear();
		SystemVariables.PATROL_LINES.clear();
		SystemVariables.SHIFT_LIST.clear();
		SystemVariables.ALL_LINE_NODES.clear();
		SystemVariables.ALL_LINE_NODES_MAP.clear();
		SystemVariables.NODEID_NODE_MAP.clear();

		loginButton = (Button) findViewById(R.id.login_button);
		username = (EditText) findViewById(R.id.username_edittext);
		password = (EditText) findViewById(R.id.password_edittext);
		loginService = LoginService.getInstance(this);

		/* 初始化数据库操作对象 */
		SystemVariables.sqLiteOpenHelper = new WuYeSqliteOpenHelper(this);

		/* 检查上次打卡记录是否已上传 */
		loginService.syncPatrolData(MainActivity.this);

		/**
		 * 登录成功触发
		 */
		loginHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					/* 跳转至菜单目录页面 */
					MainActivity.this.finish();
					Intent intent = new Intent(MainActivity.this, MenuActivity.class);
					startActivity(intent);
				}
				if (msg.what == 2) {
					loginButton.setEnabled(true);
					loginButton.setText("登录");
				}
			}
		};

		/**
		 * 下载完成触发
		 */
		installHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				File file = (File) bundle.get("file");
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
				startActivity(intent);
			}
		};

		/**
		 * 检测到新版本触发
		 */
		autoUpdateHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			final String newAppUrl = bundle.getString("url");
			if (newAppUrl != null && !"".equals(newAppUrl) && newAppUrl.indexOf("/") > 0) {
				new AlertDialog.Builder(MainActivity.this).setTitle("").setMessage("检测到有新版本, 请升级...").setPositiveButton("升级", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						//显示ProgressDialog
						ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Loading...", "正在下载, 请稍等...",true, false);
						progressDialog.show();
						client.post(MainActivity.this, newAppUrl, null, new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int i, Header[] headers, byte[] bytes) {
								String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/app_package/";
								File pathFile = new File(path);
								if (!pathFile.exists()) {
									pathFile.mkdir();
								}
								String name = newAppUrl.substring(newAppUrl.lastIndexOf("/"));
								String fileName = path + name;
								File file = new File(fileName);
								try {
									FileOutputStream fos = new FileOutputStream(file);
									fos.write(bytes);
									fos.close();
								} catch (Exception e) {
								}

								Message message = new Message();
								Bundle bundle = new Bundle();
								bundle.putSerializable("file", file);
								message.setData(bundle);
								message.setTarget(installHandler);
								message.sendToTarget();
							}

							@Override
							public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

							}
						});
					}
				}).setNegativeButton("退出", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						MainActivity.this.finish();
						System.exit(1);
					}
				}).setCancelable(false).show();
			}
			}
		};

		/**
		 * 检测新版本
		 */
        loginService.autoUpdateApp(autoUpdateHandler);
	}

	/**
	 * 登录
	 * @param view
	 */
	public void login(View view) {
		String inputUsername = username.getText().toString();
		String inputPassword = password.getText().toString();
		try {
			RequestParams requestParams = new RequestParams();
			requestParams.put("loginName", inputUsername);
			requestParams.put("password", inputPassword);

			// 判断网络是否可用
			boolean isNetworkConnected = XunGengService.isNetworkConnected(this);
			if (!isNetworkConnected) {
				showToast("网络不可用...");
				return;
			}

			Button loginButton = (Button)view;
			loginButton.setEnabled(false);
			loginButton.setText("正在登录...");
			// 发起请求前的时间戳
			final long beforeHttp = System.currentTimeMillis();
			client.post(MainActivity.this, Constants.LOGIN_URL, requestParams, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
					// 请求响应时的时间戳
					long afterHttp = System.currentTimeMillis();
					try {
						String errorCode = jsonObject.getString("errorCode");
						Message failMsg = new Message();
						failMsg.what = 2;
						failMsg.setTarget(loginHandler);

						if ("400".equals(errorCode)) {
							showToast("用户名或密码错误");
							failMsg.sendToTarget();
							return;
						}
						if (!Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
							showToast("登录失败, 请稍后再试");
							failMsg.sendToTarget();
							return;
						}

                        /* 1, 同步服务器时间 */
						long serverTime = Long.parseLong(jsonObject.getString("serverTime")) + (afterHttp - beforeHttp);
						loginService.syncServerTime(serverTime);
						loginService.loginTimeCounter();

					    /* 2, 解析返回数据 */
						loginService.parseData(jsonObject);

						/* 3, 登录成功, 通知主线程跳转至菜单目录页面 */
						Message message = new Message();
						message.what = 1;
						message.setTarget(loginHandler);
						message.sendToTarget();

					} catch (JSONException e) {
						showToast("数据解析异常... 请联系技术人员");
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					Log.e("onFailure1,"+statusCode, String.valueOf(responseString)+","+throwable);
					showToast("服务器异常, 请稍后再试");
					Message message = new Message();
					message.what = 2;
					message.setTarget(loginHandler);
					message.sendToTarget();
					Log.e("onFailure,responseString:", String.valueOf(responseString));
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
					Log.e("onFailure2,"+statusCode, String.valueOf(errorResponse)+","+throwable);
					showToast("登录异常, 请稍后再试");
					Message message = new Message();
					message.what = 2;
					message.setTarget(loginHandler);
					message.sendToTarget();
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					Log.e("onFailure3,"+statusCode, String.valueOf(errorResponse)+","+throwable);
					showToast("登录超时, 请重试");
					Message message = new Message();
					message.what = 2;
					message.setTarget(loginHandler);
					message.sendToTarget();
				}
			});
		} catch (Exception e) {
			showToast("登录失败, 请稍后再试");
			Log.e(TAG, e.toString());
		}
	}
}
