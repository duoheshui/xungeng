package com.joyi.xungeng.activity;

import android.app.AlertDialog;
import android.app.PendingIntent;
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
import com.google.gson.Gson;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.PatrolRecordDao;
import com.joyi.xungeng.dao.UserPatrolDao;
import com.joyi.xungeng.domain.*;
import com.joyi.xungeng.util.DateUtil;

import java.util.*;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更打卡】 Activity
 */
public class XunGengDaKaActivity extends BaseActivity {
	private TableLayout tableLayout;
    private Button startButton;
    private Button endButton;
	private TextView yiXunLunCi;            // 已巡轮次
	private TextView louXunQingKuang;       // 漏巡情况


	private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    private static final Map<String, Integer> luXianLunCiMap = new HashMap<>();                   // 路线ID<->轮次映射表
	private static final Map<String, Map<Integer, Long>> luXianLunCiIdMap = new HashMap<>();    // 路线ID<->轮次<->轮软记录ID
    private User user = SystemVariables.user;
	private String lineId;
    private PatrolLine patrolLine;
    private UserPatrolDao upDao = new UserPatrolDao();
    private PatrolRecordDao prDao = new PatrolRecordDao();
	private List<LineNode> allLineNodes = SystemVariables.ALL_LINE_NODES;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.xun_geng_da_ka);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText(SystemVariables.USER_NAME);
		startButton = (Button) findViewById(R.id.start_button);
		endButton = (Button) findViewById(R.id.end_button);
		tableLayout = (TableLayout) findViewById(R.id.patrol_record_table);
		yiXunLunCi = (TextView) findViewById(R.id.yi_xun_lun_ci_tv);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);

		patrolLine = (PatrolLine) getIntent().getSerializableExtra("patrolLine");
		lineId = patrolLine.getId();
		Integer lunCi = luXianLunCiMap.get(lineId);
		if (lunCi==null || lunCi==0) {
			luXianLunCiMap.put(lineId, 1);
		}
		yiXunLunCi.setText((luXianLunCiMap.get(lineId) - 1) + "次");

		// 初始化开始, 结束状态
		lunCi = luXianLunCiMap.get(lineId);
		Map<Integer, Long> integerLongMap = luXianLunCiIdMap.get(lineId);
		if (integerLongMap == null || integerLongMap.size() == 0) {
			// 还未开始第一轮
		}else {
			Long lunciId = integerLongMap.get(lunCi);
			UserPatrol byId = upDao.getById(lunciId);
			if (byId != null) {
				// 本轮已开始
				startButton.setEnabled(false);
				startButton.setBackgroundResource(R.drawable.disable_round_button);
				endButton.setEnabled(true);
				endButton.setBackgroundResource(R.drawable.round_button);
			}

		}

		// TODO 计算漏巡情况

		List<LineNode> lineNodes = (List<LineNode>) getIntent().getSerializableExtra("lineNodes");
		Map<String, PatrolRecord> map = prDao.getMap(lineId, luXianLunCiMap.get(lineId));
		Gson gson = new Gson();

		for (LineNode node : lineNodes) {
			TableRow tableRow = new TableRow(this);
			tableRow.setBackgroundColor(Color.WHITE);
			tableRow.setPadding(1, 1, 1, 1);

			String statusText = "未巡";
			String timeText = "--";
			PatrolRecord record = map.get(node.getId());
			if (record != null) {
				statusText = "已巡";
				timeText = record.getPatrolTime();
			}

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
			status.setText(statusText);
			status.setTextColor(Color.WHITE);
			status.setBackgroundColor(Color.BLACK);
			status.setTextSize(20);
			status.setLayoutParams(layoutParams);

			TextView time = new TextView(this);
			time.setGravity(Gravity.CENTER);
			time.setText(timeText);
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

upDao.deleteAll(); prDao.deleteAll();
        Button startBtn = (Button)view;
        startBtn.setEnabled(false);
	    startBtn.setBackgroundResource(R.drawable.disable_round_button);
	    endButton.setEnabled(true);
	    endButton.setBackgroundResource(R.drawable.round_button);

	    Integer lunCi = luXianLunCiMap.get(lineId);
	    luXianLunCiMap.put(lineId, lunCi);
        UserPatrol userPatrol = new UserPatrol();
        userPatrol.setUserId(user.getId());
        userPatrol.setLineId(lineId);
        userPatrol.setBeginPhoneTime(DateUtil.getHumanReadStr(new Date()));
        Date date = new Date(SystemVariables.SERVER_TIME.getTime());
        userPatrol.setBeginTime(DateUtil.getHumanReadStr(date));
        userPatrol.setSequence(lunCi);
//        userPatrol.setScheduleTypeId(patrolLine.g);
        long id = upDao.add(userPatrol);
	    Map<Integer, Long> lunciIDMap = luXianLunCiIdMap.get(patrolLine.getId());
	    if (lunciIDMap == null) {
		    lunciIDMap = new HashMap<>();
		    luXianLunCiIdMap.put(lineId, lunciIDMap);
	    }
	    luXianLunCiIdMap.get(lineId).put(lunCi, id);
Gson gson = new Gson();
Log.e("up:"+id, gson.toJson(upDao.getAll()));
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
				    Integer lunCi = luXianLunCiMap.get(lineId);
				    Long id = luXianLunCiIdMap.get(lineId).get(lunCi);
				    Date serverDate = new Date(SystemVariables.SERVER_TIME.getTime());
				    upDao.updateEndDate(serverDate, new Date(), id);

				    lunCi++;
				    luXianLunCiMap.put(lineId, lunCi);

				    Button endBtn = (Button)view;
				    endBtn.setEnabled(false);
				    endButton.setBackgroundResource(R.drawable.disable_round_button);
				    startButton.setBackgroundResource(R.drawable.round_green_shape);
				    startButton.setEnabled(true);
				    finish();
			    }
		    }).setNegativeButton("取消", null).show();
    }

    /**
     * 打卡触发事件
     * @param intent
     */
	@Override
	protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


		// 初始化开始, 结束状态
		Integer lunCi = luXianLunCiMap.get(lineId);
		Map<Integer, Long> integerLongMap = luXianLunCiIdMap.get(lineId);
		if (integerLongMap == null || integerLongMap.size() == 0) {
			// 还未开始第一轮
			showToast("请先点击开始再打卡");
			return;
		}else {
			Long lunciId = integerLongMap.get(lunCi);
			UserPatrol byId = upDao.getById(lunciId);
			if (byId == null) {
				showToast("请先点击开始再打卡");
				return;
			}

		}


        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
// TODO 删除假数据
	        //            byte[] arr = tag.getId();
	        //            String nfcCode = XunGengService.byteArray2HexString(arr);
	        String nfcCode = "123";
	        LineNode lineNode = SystemVariables.ALL_LINE_NODES_MAP.get(nfcCode);
	        if (lineNode == null) {
		        showToast("无效的NFC卡...");
		        return;
	        }
	        List<LineNode> lineNodes = patrolLine.getLineNodes();
	        // 检查此节点是否属于本路线
	        if (lineNodes != null && lineNodes.size() > 0) {
		        boolean rightNode = false;
		        for (LineNode node : lineNodes) {
			        if (lineNode.getNfcCode().equals(node.getNfcCode())) {
				        rightNode = true;
				        break;
			        }
		        }
		        if (!rightNode) {
			        showToast("打卡失败, 该节点不属于该路线");
			        return;
		        }
		        long userPatrolId = luXianLunCiIdMap.get(lineId).get(lunCi);
		        PatrolRecord patrolRecord = new PatrolRecord();
		        patrolRecord.setPatrolTime(DateUtil.getHumanReadStr(SystemVariables.SERVER_TIME));
		        patrolRecord.setUserPatrolId(String.valueOf(userPatrolId));
		        if (lineNode != null) {
			        patrolRecord.setNodeId(lineNode.getId());
		        }
		        patrolRecord.setPatrolPhoneTime(DateUtil.getHumanReadStr(new Date()));
		        patrolRecord.setNodeId(lineNode.getId());
		        patrolRecord.setLineId(lineId);
		        patrolRecord.setSequence(lunCi);
		        prDao.add(patrolRecord);

		        showLongToast(lineNode.getNodeName() + "  打卡成功");
		        finish();
	        }
        }else{
            showToast("无效的NFC卡...");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }
}
