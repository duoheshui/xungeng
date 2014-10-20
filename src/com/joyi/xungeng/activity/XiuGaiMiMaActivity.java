package com.joyi.xungeng.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.StringUtils;
import com.loopj.android.http.*;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

/**
 * Created by zhangyong on 2014/10/15.
 * 【修改密码】Activity
 */
public class XiuGaiMiMaActivity extends BaseActivity {

	private EditText oldPwd;
	private EditText newPwd;
	private EditText newPwd2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiu_gai_mi_ma);

		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText(SystemVariables.USER_NAME);

		oldPwd = (EditText) findViewById(R.id.old_pwd);
		newPwd = (EditText) findViewById(R.id.new_pwd);
		newPwd2 = (EditText) findViewById(R.id.new_pwd2);

	}


	/**
	 * 修改密码
	 * @param view
	 */
	public void changePassword(View view) {



		final StringBuffer buffer = new StringBuffer("Hello->");
		SyncHttpClient syncHttpClient = new SyncHttpClient() {
			@Override
			public String onRequestFailed(Throwable throwable, String s) {
				return null;
			}

		};
		syncHttpClient.post("http://www.baidu.com", new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(String s) {
				super.onSuccess(s);
				Log.e(TAG, "onSuccess->" + s);
				buffer.append("s");
			}
		});
//		httpClient.get("http://www.baidu.com", new AsyncHttpResponseHandler() {
//			@Override
//			public void onSuccess(String s) {
//				super.onSuccess(s);
//				Log.e(TAG, "onSuccess->" + s);
//				buffer.append(s);
//			}
//		});

		Log.e(TAG, "Response:" + buffer.toString());


		String newPassword = newPwd.getText().toString();
		String newPassword2 = newPwd2.getText().toString();
		if (StringUtils.isNullOrEmpty(oldPwd.getText().toString())) {
			showToast("请输入旧密码");
			return;
		}
		if (StringUtils.isNullOrEmpty(newPassword)) {
			showToast("请输入新密码");
			return;
		}
		if (newPassword.length() < Constants.MIN_PASSWORD_LENGTH) {
			showToast("新密码过短");
			return;
		}
		if (!newPassword.equals(newPassword2)) {
			showToast("两次密码输入不一致");
			return;
		}


//		RequestParams requestParams = new RequestParams();
//		requestParams.put("userId", SystemVariables.user.getId());
//		requestParams.put("oldPassword", oldPwd.getText().toString());
//		requestParams.put("newPassword", newPassword);
//		httpClient.post(this, Constants.CHANGE_PASSWORD_URL, requestParams, new JsonHttpResponseHandler(){
//			@Override
//			public void onSuccess(JSONObject jsonObject) {
//				try {
//					String errorCode = jsonObject.getString("errorCode");
//					if (Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
//						showToast("修改成功");
//						finish();
//					}else{
//						showToast("修改失败...");
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}
}