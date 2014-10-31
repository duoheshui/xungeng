package com.joyi.xungeng.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.PatrolRecordDao;
import com.joyi.xungeng.dao.PatrolViewDao;
import com.joyi.xungeng.dao.ShiftRecordDao;
import com.joyi.xungeng.dao.UserPatrolDao;
import com.joyi.xungeng.domain.User;
import com.joyi.xungeng.domain.UserPatrol;
import com.joyi.xungeng.service.XunGengService;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.DateUtil;
import com.joyi.xungeng.util.PhoneUtils;
import com.joyi.xungeng.util.StringUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangyong on 2014/10/15.
 * 【修改密码】Activity
 */
public class XiuGaiMiMaActivity extends BaseActivity {

	private EditText oldPwd;
	private EditText newPwd;
	private EditText newPwd2;
	private AsyncHttpClient httpClient = new AsyncHttpClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiu_gai_mi_ma);

		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText("修改密码");

		oldPwd = (EditText) findViewById(R.id.old_pwd);
		newPwd = (EditText) findViewById(R.id.new_pwd);
		newPwd2 = (EditText) findViewById(R.id.new_pwd2);

	}


	/**
	 * 修改密码
	 * @param view
	 */
	public void changePassword(View view) {
		// 判断网络是否可用
		boolean networkConnected = PhoneUtils.isNetworkConnected(this);
		if (!networkConnected) {
			showToast("网络未连接....");
			return;
		}
		if (!SystemVariables.USER_ID.equals(SystemVariables.T_USER_ID)) {
			String userName = SystemVariables.user.getUserName();
			String toast = userName == null ? "您无法修改别人的密码" : "您无法修改" + userName + "的密码";
			showToast(toast);
			return;
		}

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

		// 判断原密码
		User user = SystemVariables.ALL_USERS_MAP.get(SystemVariables.user.getLoginName());
		if (!StringUtils.compareMd5(oldPwd.getText().toString(), user.getPassword())) {
			showToast("原密码错误");
			return;
		}


		if (!newPassword.equals(newPassword2)) {
			showToast("两次密码输入不一致");
			return;
		}

		RequestParams requestParams = new RequestParams();
		requestParams.put("userId", SystemVariables.user.getId());
		requestParams.put("oldPassword", oldPwd.getText().toString());
		requestParams.put("newPassword", newPassword);
		httpClient.post(this, Constants.CHANGE_PASSWORD_URL, requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
				try {
					String errorCode = jsonObject.getString("errorCode");
					if (Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
						showToast("修改成功");
						finish();
					} else {
						showToast("修改失败...");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}