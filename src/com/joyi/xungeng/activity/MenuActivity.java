package com.joyi.xungeng.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.MainActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.User;
import com.joyi.xungeng.test.Test;
import com.joyi.xungeng.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuActivity extends BaseActivity implements AdapterView.OnItemClickListener {
	private GridView functionMenuGridView;
	private GridView systemMenuGridView;

	private int[] functionMenuImages = {R.drawable.icon_1, R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4};
	private int[] systemMenuImages = {R.drawable.icon_5, R.drawable.icon_6, R.drawable.icon_7};

	private static final String[] functionMenuNames = Constants.FUNCTION_MENU_NAME;
	private static final String[] systemMenuNames = Constants.SYSTEM_MENU_NAME;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.gong_neng_cai_dan);
// TODO 删除测试
Test.test();
		// 获取TabHost对象
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		// 如果没有继承TabActivity时，通过该种方法加载启动tabHost
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("功能菜单").setContent(R.id.view1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("系统设置").setContent(R.id.view2));

		// 功能菜单
		functionMenuGridView = (GridView) findViewById(R.id.functionMenuGridView);
		List<HashMap<String, Object>> data = new ArrayList<>();
		HashMap<String, Object> map = null;
		for (int i = 0; i < functionMenuImages.length; ++i) {
			map = new HashMap<>();
			map.put("image", functionMenuImages[i]);
			map.put("text", functionMenuNames[i]);
			data.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item, new String[]{"image", "text"}, new int[]{R.id.ItemImageView, R.id.ItemTextView});
		functionMenuGridView.setAdapter(adapter);
		functionMenuGridView.setOnItemClickListener(this);

		// 系统菜单
		systemMenuGridView = (GridView) findViewById(R.id.systemMenuGridView);
		List<HashMap<String, Object>> data2 = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map2 = null;
		for (int i = 0; i < systemMenuImages.length; ++i) {
			map2 = new HashMap<>();
			map2.put("image", systemMenuImages[i]);
			map2.put("text", systemMenuNames[i]);
			data2.add(map2);
		}
		SimpleAdapter adapter2 = new SimpleAdapter(this, data2, R.layout.item, new String[]{"image", "text"}, new int[]{R.id.ItemImageView, R.id.ItemTextView});
		systemMenuGridView.setAdapter(adapter2);
		systemMenuGridView.setOnItemClickListener(this);
	}

	/**
	 * 菜单点击事件
	 * @param adapterView
	 * @param view
	 * @param position
	 * @param l
	 */
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		String name = ((TextView) view.findViewById(R.id.ItemTextView)).getText().toString();

		// 退出系统
		if (Constants.SYSTEM_MENU_NAME[2].equals(name)) {
			new AlertDialog.Builder(this).setTitle("").setMessage("确定要退出系统么？").setPositiveButton("退出", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					finish();
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) { }
			}).show();
			return;
		}
		// 切换帐号
		if (Constants.SYSTEM_MENU_NAME[1].equals(name)) {
			new AlertDialog.Builder(this).setTitle("").setMessage("确定要切换帐号么？").setPositiveButton("切换", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					Intent intent = new Intent(MenuActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) { }
			}).show();
			return;
		}
		// 巡查打卡
		if (Constants.FUNCTION_MENU_NAME[1].equals(name)) {
			User user = SystemVariables.user;
			user.setHasPatrolViewPrivilege(false);
			if (user == null || !user.isHasPatrolViewPrivilege()) {
				new AlertDialog.Builder(this).setTitle("").setMessage("您没有巡查权限").show();
				return;
			}
		}
		Intent intent = new Intent(this, Constants.NAME_ACTIVITY_MAP.get(name));
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("").setIcon(android.R.drawable.ic_dialog_alert).setMessage("确定要退出么？\n退出系统后需要重新登录").setPositiveButton("退出", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				MenuActivity.this.finish();
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) { }
		}).show();
	}
}