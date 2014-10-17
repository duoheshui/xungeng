package com.joyi.xungeng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.KeyValuePair;
import com.joyi.xungeng.domain.ShiftRecord;
import com.joyi.xungeng.domain.Station;
import com.joyi.xungeng.test.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 */
public class JiaoJieBanActivity extends Activity implements AdapterView.OnItemSelectedListener {

	private Spinner gangWei;
	private Spinner banCi;
	private Spinner luXian;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jiao_jie_ban);

		// 三个Spinner
		gangWei = (Spinner) findViewById(R.id.gang_wei_spinner);
		gangWei.setOnItemSelectedListener(this);
		banCi = (Spinner) findViewById(R.id.ban_ci_spinner);
		luXian = (Spinner) findViewById(R.id.lu_xian_spinner);

		// 装入数据
		ArrayAdapter<Station> gangWeiAdapter = new ArrayAdapter<Station>(this, android.R.layout.simple_list_item_1, SystemVariables.STATION_LIST);
		ArrayAdapter<KeyValuePair> banCiAdapter = new ArrayAdapter<KeyValuePair>(this, android.R.layout.simple_list_item_1, SystemVariables.SHIFT_LIST);

		List<KeyValuePair> lines = new ArrayList<KeyValuePair>();
		if (SystemVariables.STATION_LIST.size() > 0) {
			lines = SystemVariables.STATION_LIST.get(0).getLines();
		}
		ArrayAdapter<KeyValuePair> luXianAdapter = new ArrayAdapter<KeyValuePair>(this, android.R.layout.simple_list_item_1, lines);

		gangWei.setAdapter(gangWeiAdapter);
		banCi.setAdapter(banCiAdapter);
		luXian.setAdapter(luXianAdapter);
	}


	public void retreat(View view) {
		finish();
	}


	/**
	 * 点击【交班】 【接班】
	 * @param view
	 */
	public void jiaoJieBan(View view) {
		String name = ((Button) view).getText().toString();
		ShiftRecord shiftRecord = new ShiftRecord();
		Date serverTime = new Date(SystemVariables.SERVER_TIME.getTime());
		if ("交班".equals(name)) {
			shiftRecord.setSubmitPhoneTime(new Date());
			shiftRecord.setSubmitTime(serverTime);
		}else if ("接班".equals(name)) {
			shiftRecord.setRecivePhoneTime(new Date());
			shiftRecord.setReceiveTime(serverTime);
		}

		Station selectedGangWei = (Station) gangWei.getSelectedItem();
		KeyValuePair selectedBanCi = (KeyValuePair) banCi.getSelectedItem();
		KeyValuePair selectedLuXian = (KeyValuePair) luXian.getSelectedItem();

		Log.e("gangwei", selectedGangWei.getId() + ", " + selectedGangWei.getName());
		Log.e("banci", selectedBanCi.getKey() + ", " + selectedBanCi.getValue());
		Log.e("luxian", selectedLuXian.getKey() + ", " + selectedLuXian.getValue());
	}

	/**
	 * 切换岗位时, 联动路线
	 * @param adapterView
	 * @param view
	 * @param i
	 * @param l
	 */
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		Station station = (Station) adapterView.getAdapter().getItem(i);
		ArrayAdapter<KeyValuePair> luXianAdapter = new ArrayAdapter<KeyValuePair>(this, android.R.layout.simple_list_item_1, station.getLines());
		luXian.setAdapter(luXianAdapter);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {


	}
}
