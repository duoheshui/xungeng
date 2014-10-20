package com.joyi.xungeng.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.PatrolRecordDao;
import com.joyi.xungeng.dao.UserPatrolDao;
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

    private static final Map<String, Integer> lunCiMap = new HashMap<>();   // 路线名<->轮次映射表
    private static final Map<Integer, Long> LunCi_PVID = new HashMap<>();   // 轮次<->轮次记录ID映射表
    private User user = SystemVariables.user;
    private String lineName;
    private PatrolLine patrolLine;
    private UserPatrolDao upDao = new UserPatrolDao();
    private PatrolRecordDao prDao = new PatrolRecordDao();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xun_geng_da_ka);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText(SystemVariables.USER_NAME);
        startButton = (Button) findViewById(R.id.start_button);
        endButton = (Button) findViewById(R.id.end_button);


        tableLayout = (TableLayout) findViewById(R.id.patrol_record_table);

        patrolLine = (PatrolLine) getIntent().getSerializableExtra("patrolLine");
        String lineName = getIntent().getStringExtra("lineName");
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
        endButton.setEnabled(true);
        endButton.setBackgroundResource(R.drawable.round_button);
        startBtn.setBackgroundResource(R.drawable.disable_round_button);


        Integer lunCi = lunCiMap.get(lineName);
        lunCi++;
        lunCiMap.put(lineName, lunCi);
        // TODO 记录UserPatrol数据
        UserPatrol userPatrol = new UserPatrol();
        userPatrol.setUserId(user.getId());
        userPatrol.setLineId(patrolLine.getId());
        userPatrol.setBeginPhoneTime(new Date());
        Date date = new Date(SystemVariables.SERVER_TIME.getTime());
        userPatrol.setBeginTime(date);
        userPatrol.setSequence(lunCi);
        userPatrol.setScheduleTypeId(userPatrol.getScheduleTypeId());
        long id = upDao.add(userPatrol);
        LunCi_PVID.put(lunCi, id);
    }

    /**
     * 结束(本轮)巡更
     * @param view
     */
    public void endPatrol(View view) {
        Integer lunCi = lunCiMap.get(lineName);
        Long id = LunCi_PVID.get(lunCi);
        Date serverDate = new Date(SystemVariables.SERVER_TIME.getTime());
        upDao.updateEndDate(serverDate, new Date(), id);

        // TODO
        Button endBtn = (Button)view;
        endBtn.setEnabled(false);
        endButton.setBackgroundResource(R.drawable.disable_round_button);
        startButton.setBackgroundResource(R.drawable.round_green_shape);
        startButton.setEnabled(true);
    }
}

