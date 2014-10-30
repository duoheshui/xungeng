package com.joyi.xungeng.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.User;
import com.joyi.xungeng.util.StringUtils;

/**
 * Created by zhangyong on 2014/10/29.
 * 【替岗】
 */
public class TiGangActivity extends BaseActivity {
	private EditText loginNameET;
	private EditText passwordET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ti_gang);

		loginNameET = (EditText) findViewById(R.id.tg_login_name);
		passwordET = (EditText) findViewById(R.id.tg_password);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText("替岗【当前在岗人员:"+SystemVariables.tUser.getUserName()+"】");

	}


	/**
	 * 替岗
	 * @param view
	 */
	public void tiGang(View view) {
		String loginName = loginNameET.getText().toString();
		String password = passwordET.getText().toString();
		if (StringUtils.isNullOrEmpty(loginName) || StringUtils.isNullOrEmpty(password)) {
			showToast("请输入用户名和密码");
			return;
		}
		final User user = SystemVariables.ALL_USERS_MAP.get(loginName);
		if (user == null) {
			showToast("用户不存在");
			return;
		}

		if (!StringUtils.compareMd5(password, user.getPassword())) {
			showToast("用户名或密码错误");
			return;
		}


		String tuserName = SystemVariables.tUser.getUserName();
		Dialog alertDialog = new AlertDialog.Builder(this).
			setTitle("确定").
			setMessage("当前在岗人员为" + tuserName + ", 确定要替岗么？").
			setPositiveButton("替岗", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					SystemVariables.tUser.setHasPatrolViewPrivilege(user.isHasPatrolViewPrivilege());
					SystemVariables.tUser.setPatrolStationTypeId(user.getPatrolStationTypeId());
					SystemVariables.tUser.setPyShort(user.getPyShort());
					SystemVariables.tUser.setId(user.getId());
					SystemVariables.tUser.setLoginName(user.getLoginName());
					SystemVariables.tUser.setUserName(user.getUserName());
					SystemVariables.tUser.setPassword(user.getPassword());

					SystemVariables.T_USER_ID = user.getId();
					showToast("替岗成功");
					finish();
				}
			}).
			setNegativeButton("取消", null).create();
		alertDialog.show();
	}
}
