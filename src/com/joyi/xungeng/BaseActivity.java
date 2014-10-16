package com.joyi.xungeng;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by zhangyong on 2014/10/16.
 * Activity基类
 */
public class BaseActivity extends Activity {
	protected String TAG = getClass().getSimpleName();

	/**
	 * Toast提示
	 * @param text
	 */
	public void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	public void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

}
