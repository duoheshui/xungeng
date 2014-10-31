package com.joyi.xungeng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.User;

/**
 * Created by zhangyong on 2014/10/31.
 * 【我】
 */
public class WoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wo);
		User user = SystemVariables.user;
		((TextView) findViewById(R.id.loginName)).setText(user.getLoginName());
		((TextView) findViewById(R.id.userName)).setText(user.getUserName());
		((TextView) findViewById(R.id.pyShort)).setText(user.getPyShort());

		// 替岗人员
		if (!SystemVariables.USER_ID.equals(SystemVariables.T_USER_ID)) {
			User tuser = SystemVariables.tUser;
			findViewById(R.id.ti_gang_layout).setVisibility(View.VISIBLE);
			((TextView) findViewById(R.id.tloginName)).setText(tuser.getLoginName());
			((TextView) findViewById(R.id.tuserName)).setText(tuser.getUserName());
			((TextView) findViewById(R.id.tpyShort)).setText(tuser.getPyShort());
		}
	}
}
