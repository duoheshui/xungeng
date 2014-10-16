package com.joyi.xungeng;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.joyi.xungeng.service.LoginService;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.StringUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.util.Date;

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
		loginService = LoginService.getInstance(this);
		loginService.serverTimeTask(new Date());
		username = (EditText) findViewById(R.id.username_edittext);
		password = (EditText) findViewById(R.id.password_edittext);


		String newVersionUrl = loginService.getNewVersionUrl();
		if (StringUtils.isNotNull(newVersionUrl)) {
			loginService.showAlert();
			return;
		}

	}

	/**
	 * 登录
	 * @param view
	 */
	public void login(View view) {
		String inputUsername = username.getText().toString();
		String inputPassword = password.getText().toString();

		Log.e("username", inputUsername + ", " + inputPassword);

		try {
			RequestParams requestParams = new RequestParams();
			requestParams.put("loginName", inputUsername);
			requestParams.put("password", inputPassword);

			AsyncHttpClient client = new AsyncHttpClient();
			client.post(MainActivity.this, Constants.LOGIN_URL, requestParams, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject jsonObject) {
					super.onSuccess(jsonObject);
					showToast(String.valueOf(jsonObject));
					Log.e("onSuccess", String.valueOf(jsonObject));
				}
			});
		} catch (Exception e) {
			showToast("登录失败, 请稍后再试");
			Log.e(TAG, e.toString());
		}
	}
}
