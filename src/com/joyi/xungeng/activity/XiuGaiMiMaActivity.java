package com.joyi.xungeng.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.util.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangyong on 2014/10/15.
 * 【修改密码】Activity
 */
public class XiuGaiMiMaActivity extends BaseActivity {
	private AsyncHttpClient httpClient = new AsyncHttpClient();

	private EditText oldPwd;
	private EditText newPwd;
	private EditText newPwd2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiu_gai_mi_ma);
		oldPwd = (EditText) findViewById(R.id.old_pwd);
		newPwd = (EditText) findViewById(R.id.new_pwd);
		newPwd2 = (EditText) findViewById(R.id.new_pwd2);
	}


	/**
	 * 修改密码
	 * @param view
	 */
	public void changePassword(View view) {
		String newPassword = newPwd.getText().toString();
		String newPassword2 = newPwd2.getText().toString();
		if (!newPassword.equals(newPassword2)) {
			showToast("两次密码输入不一致");
			return;
		}

		RequestParams requestParams = new RequestParams();
		requestParams.put("userId", SystemVariables.user.getId());
		requestParams.put("oldPassword", oldPwd.getText().toString());
		requestParams.put("newPassword", newPassword);
		httpClient.post(this, Constants.CHANGE_PASSWORD_URL, requestParams, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				try {
					String errorCode = jsonObject.getString("errorCode");
					if (Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
						showToast("修改成功");
						finish();
					}else{
						showToast("修改失败...");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}