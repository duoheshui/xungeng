package com.joyi.xungeng;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.joyi.xungeng.activity.MenuActivity;
import com.joyi.xungeng.db.WuYeSqliteOpenHelper;
import com.joyi.xungeng.domain.User;
import com.joyi.xungeng.service.LoginService;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.StringUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 应用程序入口Activity
 */
public class MainActivity extends BaseActivity {
	private LoginService loginService;
	private EditText username;
	private EditText password;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		SystemVariables.IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
		loginService = LoginService.getInstance(this);

        username = (EditText) findViewById(R.id.username_edittext);
        password = (EditText) findViewById(R.id.password_edittext);

        String newVersionUrl = loginService.getNewVersionUrl();
		if (StringUtils.isNotNull(newVersionUrl)) {
			loginService.showAlert();
			return;
		}

        /* 初始化数据库操作对象 */
        SystemVariables.sqLiteOpenHelper = new WuYeSqliteOpenHelper(this);

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

			AsyncHttpClient client = new AsyncHttpClient();

            // 发起请求前的时间戳
            final long beforeHttp = System.currentTimeMillis();
			client.post(MainActivity.this, Constants.LOGIN_URL, requestParams, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject jsonObject) {
					super.onSuccess(jsonObject);
                    // 请求响应时的时间戳
                    long afterHttp = System.currentTimeMillis();
                    try {
                        String errorCode = jsonObject.getString("errorCode");
                        if (!Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
                            showToast("登录失败, 请稍后再试");
                            return;
                        }

                        /* 1, 同步服务器时间 */
                        long serverTime = Long.parseLong(jsonObject.getString("serverTime")) + (afterHttp-beforeHttp);
                        loginService.syncServerTime(serverTime);

	                    /* 2, 解析返回数据 */
	                    User user = SystemVariables.user;
	                    JSONObject userJson = jsonObject.getJSONObject("userInfo");
	                    user.setId(userJson.getString("userId"));
	                    user.setUserName(userJson.getString("userName"));
	                    user.setLoginName(userJson.getString("loginName"));
	                    user.setPyShort(userJson.getString("pyShort"));
	                    user.setPatrolStationTypeId(userJson.getString("patrolStationTypeId"));
	                    user.setHasPatrolViewPrivilege(userJson.getBoolean("hasPatrolViewPrivilege"));
					// TODO

                        /* 3, 检查上次打卡记录是否已上传 */
                        loginService.syncPatrolData(MainActivity.this);

						/* 4, 跳转至菜单目录页面 */
	                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
	                    startActivity(intent);

                        Log.e("onSuccess", String.valueOf(jsonObject));
                        Log.e("errorCode", errorCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
				}

                @Override
                public void onFailure(Throwable throwable, String s) {
                    super.onFailure(throwable, s);
                    showToast("系统异常, 请稍后再试");
                }
            });
		} catch (Exception e) {
			showToast("登录失败, 请稍后再试");
			Log.e(TAG, e.toString());
		}
	}
}
