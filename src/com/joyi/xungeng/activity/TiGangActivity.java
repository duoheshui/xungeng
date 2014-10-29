package com.joyi.xungeng.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
		User user = SystemVariables.ALL_USERS_MAP.get(loginName);
		if (user == null) {
			showToast("用户不存在");
			return;
		}
		if (!password.equals(user.getPassword())) {
			showToast("用户名或密码错误");
			return;
		}

		SystemVariables.tUser.setHasPatrolViewPrivilege(user.isHasPatrolViewPrivilege());
		SystemVariables.tUser.setPatrolStationTypeId(user.getPatrolStationTypeId());
		SystemVariables.tUser.setPyShort(user.getPyShort());
		SystemVariables.tUser.setId(user.getId());
		SystemVariables.tUser.setLoginName(user.getLoginName());
		SystemVariables.tUser.setUserName(user.getUserName());
		SystemVariables.tUser.setPassword(user.getPassword());

		SystemVariables.T_USER_ID = user.getId();

		showToast("替岗成功");
	}

}
