package com.joyi.xungeng.activity;

import android.os.Bundle;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.util.Constants;

/**
 * Created by zhangyong on 2014/10/31.
 * 【关于】
 */
public class GuanYuActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guan_yu);
		((TextView) findViewById(R.id.version)).setText("当前版本："+Constants.APP_VERSION);
		((TextView) findViewById(R.id.release_time)).setText("发布日期："+Constants.RELEASE_TIME);
	}
}
