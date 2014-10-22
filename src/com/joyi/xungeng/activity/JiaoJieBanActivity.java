package com.joyi.xungeng.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.ShiftRecordDao;
import com.joyi.xungeng.domain.KeyValuePair;
import com.joyi.xungeng.domain.ShiftRecord;
import com.joyi.xungeng.domain.Station;
import com.joyi.xungeng.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 */
public class JiaoJieBanActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

	private Spinner gangWei;
	private Spinner banCi;
	private Spinner luXian;
    private ShiftRecordDao srDao = new ShiftRecordDao();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jiao_jie_ban);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText(SystemVariables.USER_NAME);

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

	/**
	 * 点击【交班】 【接班】
	 * @param view
	 */
	public void jiaoJieBan(View view) {
		String name = ((Button) view).getText().toString();
		ShiftRecord shiftRecord = new ShiftRecord();
		Date serverTime = new Date(SystemVariables.SERVER_TIME.getTime());
		if ("交班".equals(name)) {
			shiftRecord.setSubmitPhoneTime(DateUtil.getHumanReadStr(new Date()));
			shiftRecord.setSubmitTime(DateUtil.getHumanReadStr(serverTime));
		}else if ("接班".equals(name)) {
			shiftRecord.setRecivePhoneTime(DateUtil.getHumanReadStr(new Date()));
			shiftRecord.setReceiveTime(DateUtil.getHumanReadStr(serverTime));
		}

		Station selectedGangWei = (Station) gangWei.getSelectedItem();
		KeyValuePair selectedBanCi = (KeyValuePair) banCi.getSelectedItem();
		KeyValuePair selectedLuXian = (KeyValuePair) luXian.getSelectedItem();
        shiftRecord.setStationId(selectedGangWei.getId());
        shiftRecord.setScheduleTypeId(selectedBanCi.getKey());
        shiftRecord.setLineId(selectedLuXian.getKey());
        srDao.add(shiftRecord);
        showToast(name + "成功");
        finish();
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
