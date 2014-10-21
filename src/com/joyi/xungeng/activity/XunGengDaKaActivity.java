package com.joyi.xungeng.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.MainActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.PatrolRecordDao;
import com.joyi.xungeng.dao.UserPatrolDao;
import com.joyi.xungeng.db.WuYeSqliteOpenHelper;
import com.joyi.xungeng.domain.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更打卡】 Activity
 */
public class XunGengDaKaActivity extends BaseActivity {
	private TableLayout tableLayout;
    private Button startButton;
    private Button endButton;
	private NfcAdapter nfcAdapter;

    private static final Map<String, Integer> lunCiMap = new HashMap<>();                   // 路线名<->轮次映射表
	private static final Map<String, Map<Integer, Long>> lunCiPvidMap = new HashMap<>();    // 路线<->轮次<->轮软记录ID
    private User user = SystemVariables.user;
    private String lineName;
    private PatrolLine patrolLine;
    private UserPatrolDao upDao = new UserPatrolDao();
    private PatrolRecordDao prDao = new PatrolRecordDao();
	private List<LineNode> lineNodes = SystemVariables.ALL_LINE_NODES;




	@Override
	protected void onCreate(Bundle savedInstanceState) {

/* 初始化数据库操作对象 TODO 删除测试数据 */
SystemVariables.sqLiteOpenHelper = new WuYeSqliteOpenHelper(this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.xun_geng_da_ka);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText(SystemVariables.USER_NAME);
        startButton = (Button) findViewById(R.id.start_button);
        endButton = (Button) findViewById(R.id.end_button);
		tableLayout = (TableLayout) findViewById(R.id.patrol_record_table);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);


        patrolLine = (PatrolLine) getIntent().getSerializableExtra("patrolLine");
        lineName = getIntent().getStringExtra("lineName");
		Integer lunCi = lunCiMap.get(lineName);
        if (lunCi == null) {
            lunCiMap.put(lineName, 0);
        }
		List<LineNode> lineNodes = (List<LineNode>) getIntent().getSerializableExtra("lineNodes");
		for (LineNode node : lineNodes) {
			TableRow tableRow = new TableRow(this);
			tableRow.setBackgroundColor(Color.WHITE);
			tableRow.setPadding(1, 1, 1, 1);

			TextView dian = new TextView(this);
			dian.setGravity(Gravity.CENTER);
			dian.setText(node.getNodeName());
			dian.setTextColor(Color.WHITE);
			dian.setBackgroundColor(Color.BLACK);
			dian.setTextSize(20);
			TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
			layoutParams.setMargins(0, 0, 2, 0);
			dian.setLayoutParams(layoutParams);

			TextView status = new TextView(this);
			status.setGravity(Gravity.CENTER);
			status.setText(node.getStatus());
			status.setTextColor(Color.WHITE);
			status.setBackgroundColor(Color.BLACK);
			status.setTextSize(20);
			status.setLayoutParams(layoutParams);

			TextView time = new TextView(this);
			time.setGravity(Gravity.CENTER);
			time.setText(node.getTime());
			time.setTextColor(Color.WHITE);
			time.setBackgroundColor(Color.BLACK);
			time.setTextSize(20);
			time.setLayoutParams(layoutParams);

			tableRow.addView(dian);
			tableRow.addView(status);
			tableRow.addView(time);
			tableLayout.addView(tableRow);
		}
	}

    /**
     * 开始(本轮)巡更
     * @param view
     */
    public void startPatrol(View view) {
        Button startBtn = (Button)view;
        startBtn.setEnabled(false);
	    startBtn.setBackgroundResource(R.drawable.disable_round_button);
	    endButton.setEnabled(true);
	    endButton.setBackgroundResource(R.drawable.round_button);

        Integer lunCi = lunCiMap.get(lineName);
        lunCi++;
        lunCiMap.put(lineName, lunCi);
        UserPatrol userPatrol = new UserPatrol();
        userPatrol.setUserId(user.getId());
        userPatrol.setLineId(patrolLine.getId());
        userPatrol.setBeginPhoneTime(new Date());
        Date date = new Date(SystemVariables.SERVER_TIME.getTime());
        userPatrol.setBeginTime(date);
        userPatrol.setSequence(lunCi);
        userPatrol.setScheduleTypeId(userPatrol.getScheduleTypeId());
        long id = upDao.add(userPatrol);
	    Map<Integer, Long> lunciIDMap = lunCiPvidMap.get(lineName);
	    if (lunciIDMap == null) {
		    lunciIDMap = new HashMap<>();
		    lunCiPvidMap.put(lineName, lunciIDMap);
	    }
	    lunCiPvidMap.get(lineName).put(lunCi, id);
    }

    /**
     *
     * 结束(本轮)巡更
     * @param view
     */
    public void endPatrol(final View view) {
	    new AlertDialog.Builder(XunGengDaKaActivity.this)
		    .setTitle("确认")
		    .setIcon(android.R.drawable.ic_dialog_alert).setMessage("确定要结束本轮巡查么？")
		    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialogInterface, int i) {
				    Integer lunCi = lunCiMap.get(lineName);
				    Long id = lunCiPvidMap.get(lineName).get(lunCi);
				    Date serverDate = new Date(SystemVariables.SERVER_TIME.getTime());
				    upDao.updateEndDate(serverDate, new Date(), id);

				    Button endBtn = (Button)view;
				    endBtn.setEnabled(false);
				    endButton.setBackgroundResource(R.drawable.disable_round_button);
				    startButton.setBackgroundResource(R.drawable.round_green_shape);
				    startButton.setEnabled(true);
				    finish();
			    }
		    }).setNegativeButton("取消", null).show();
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		// TODO 读取node查节点名称
	}
}

