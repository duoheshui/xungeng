package com.joyi.xungeng;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.joyi.xungeng.service.LoginService;
import com.joyi.xungeng.util.HttpUtils;
import com.joyi.xungeng.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用程序入口Activity
 */
public class MainActivity extends Activity implements View.OnClickListener {
	private LoginService loginService;
	private EditText username;
	private EditText password;
	private Button loginButton;

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
		loginButton = (Button) findViewById(R.id.login_button);
		loginButton.setOnClickListener(this);


		String newVersionUrl = loginService.getNewVersionUrl();
		if (StringUtils.isNotNull(newVersionUrl)) {
			loginService.showAlert();
			return;
		}



	}

	@Override
	public void onClick(View view) {
		Map<String, String> param = new HashMap<String, String>();
		String inputUsername = username.getText().toString();
		String inputPassword = password.getText().toString();

		param.put("username", inputUsername);
		param.put("password", inputPassword);

		// TODO 根据响应做处理
		String response = HttpUtils.post(param);
	}
}
