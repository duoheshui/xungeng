package com.joyi.xungeng.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.*;
import com.joyi.xungeng.domain.*;
import com.joyi.xungeng.service.XunGengService;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.DateUtil;
import com.joyi.xungeng.util.StringUtils;

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
	private JiaoJieBanStatusDao jjbDao = new JiaoJieBanStatusDao();
	private LuXianLunCiDao llDao = new LuXianLunCiDao();
	private LuXianLunCiIdDao lliDao = new LuXianLunCiIdDao();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jiao_jie_ban);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText("交接班");


		// 三个Spinner
		gangWei = (Spinner) findViewById(R.id.gang_wei_spinner);
		gangWei.setOnItemSelectedListener(this);
		banCi = (Spinner) findViewById(R.id.ban_ci_spinner);
		luXian = (Spinner) findViewById(R.id.lu_xian_spinner);

		// 装入数据
		ArrayAdapter<Station> gangWeiAdapter = new ArrayAdapter<Station>(this, android.R.layout.select_dialog_item, SystemVariables.STATION_LIST);
		ArrayAdapter<KeyValuePair> banCiAdapter = new ArrayAdapter<KeyValuePair>(this, android.R.layout.select_dialog_item, SystemVariables.SHIFT_LIST);

		List<KeyValuePair> lines = new ArrayList<KeyValuePair>();
		if (SystemVariables.STATION_LIST.size() > 0) {
			lines = SystemVariables.STATION_LIST.get(0).getLines();
		}
		ArrayAdapter<KeyValuePair> luXianAdapter = new ArrayAdapter<KeyValuePair>(this, android.R.layout.select_dialog_item, lines);

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
		int type = -1;
		if ("交班".equals(name)) {
			type = JiaoJieBanStatus.TYPE_JIAO_BAN;
			// 检查巡更是否有未结束的
			UserPatrolDao upDao = new UserPatrolDao();
			List<UserPatrol> upList = upDao.getAll(Constants.SYNC_ALL);
			if (upList != null && upList.size() > 0) {
				for (UserPatrol patrol : upList) {
					String endTime = patrol.getEndTime();
					if (StringUtils.isNullOrEmpty(endTime)) {
						String lineName = XunGengService.getLineName(patrol.getLineId(), SystemVariables.PATROL_LINES);
						showToast(lineName+" 第"+patrol.getSequence()+" 轮还未结束");
						return;
					}
				}
			}

			shiftRecord.setSubmitPhoneTime(DateUtil.getHumanReadStr(new Date()));
			shiftRecord.setSubmitTime(DateUtil.getHumanReadStr(serverTime));
		}else if ("接班".equals(name)) {
			type = JiaoJieBanStatus.TYPE_JIE_BAN;
			shiftRecord.setRecivePhoneTime(DateUtil.getHumanReadStr(new Date()));
			shiftRecord.setReceiveTime(DateUtil.getHumanReadStr(serverTime));
		}



		Station selectedGangWei = (Station) gangWei.getSelectedItem();
		if (selectedGangWei == null) {
			showToast("岗位为空, 无法完成交接班");
			return;
		}
		KeyValuePair selectedBanCi = (KeyValuePair) banCi.getSelectedItem();
		KeyValuePair selectedLuXian = (KeyValuePair) luXian.getSelectedItem();
		if (selectedLuXian == null) {
			showToast("路线为空, 无法完成交接班");
			return;
		}
		shiftRecord.setStationId(selectedGangWei.getId());
        shiftRecord.setScheduleTypeId(selectedBanCi.getKey());
        shiftRecord.setLineId(selectedLuXian.getKey());
		shiftRecord.setUserId(SystemVariables.USER_ID);
		shiftRecord.setTuserId(SystemVariables.T_USER_ID);
		srDao.add(shiftRecord);

		JiaoJieBanStatus status = new JiaoJieBanStatus(SystemVariables.user.getId(), DateUtil.getHumanReadStr(SystemVariables.SERVER_TIME), type);
		jjbDao.userJiaoJieBan(status);
		int type2 = type == JiaoJieBanStatus.TYPE_JIE_BAN ? JiaoJieBanStatus.TYPE_JIAO_BAN : JiaoJieBanStatus.TYPE_JIE_BAN;
		jjbDao.userJiaoJieBan(SystemVariables.user.getId(), type2, "");
		lliDao.clear(SystemVariables.USER_ID);
		if (type == JiaoJieBanStatus.TYPE_JIAO_BAN) {
			List<PatrolLine> patrolLines = SystemVariables.PATROL_LINES;
			int i=0;
			for (PatrolLine line : patrolLines) {
				llDao.setLunCi(SystemVariables.USER_ID, line.getId(), -1);
			}
		}
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
		ArrayAdapter<KeyValuePair> luXianAdapter = new ArrayAdapter<KeyValuePair>(this, android.R.layout.select_dialog_item, station.getLines());
		luXian.setAdapter(luXianAdapter);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
}
