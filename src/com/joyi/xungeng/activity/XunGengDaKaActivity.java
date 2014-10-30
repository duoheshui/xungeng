package com.joyi.xungeng.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
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
import com.joyi.xungeng.dao.*;
import com.joyi.xungeng.domain.*;
import com.joyi.xungeng.service.XunGengService;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.DateUtil;
import com.joyi.xungeng.util.VibratorUtil;

import java.text.DateFormat;
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

    private User user = SystemVariables.user;
	private String lineId;
	private int lunCi;
    private PatrolLine patrolLine;
	private List<LineNode> lineNodes;
    private UserPatrolDao upDao = new UserPatrolDao();
    private PatrolRecordDao prDao = new PatrolRecordDao();
	private JiaoJieBanStatusDao jjbDao = new JiaoJieBanStatusDao();
	private LuXianLunCiDao llDao = new LuXianLunCiDao();
	private LuXianLunCiIdDao lliDao = new LuXianLunCiIdDao();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xun_geng_da_ka);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText("巡更打卡");
		startButton = (Button) findViewById(R.id.start_button);
		endButton = (Button) findViewById(R.id.end_button);
		tableLayout = (TableLayout) findViewById(R.id.patrol_record_table);
		yiXunLunCi = (TextView) findViewById(R.id.yi_xun_lun_ci_tv);
		louXunQingKuang = (TextView) findViewById(R.id.lou_xun_qing_kuang_tv);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
		lineNodes = (List<LineNode>) getIntent().getSerializableExtra("lineNodes");

		// 判断手机是否支持NFC
		if (nfcAdapter == null) {
			new AlertDialog.Builder(this)
					.setTitle("")
					.setMessage("您的手机不支持NFC功能").setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					XunGengDaKaActivity.this.finish();
				}}).setCancelable(false).show();
			return;
		}
		// 判断NFC是否已开启
		if (!nfcAdapter.isEnabled()) {
			new AlertDialog.Builder(this)
					.setTitle("")
					.setMessage("检测到您还未开启NFC功能, 立即开启？")
					.setPositiveButton("取消",null).setNegativeButton("开启", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					Intent intent =  new Intent(Settings.ACTION_NFC_SETTINGS);
					startActivity(intent);
				}}).show();
			return;
		}

		patrolLine = (PatrolLine) getIntent().getSerializableExtra("patrolLine");
		lineId = patrolLine.getId();
		if (patrolLine != null) {
			textView.setText("巡更打卡:"+patrolLine.getLineName());
		}
		lunCi = llDao.getLunCi(SystemVariables.USER_ID, lineId);

		if (lunCi <= 0) {
			llDao.setLunCi(SystemVariables.USER_ID, lineId, 1);
			lunCi = 1;
		}
		yiXunLunCi.setText((lunCi - 1) + "次");


		// 初始化 开始, 结束按钮状态
		long lunCiId = lliDao.getLunCiId(SystemVariables.USER_ID, lineId, lunCi);
		UserPatrol byId = upDao.getById(lunCiId);
		if (byId != null) {
			// 本轮已开始
			startButton.setEnabled(false);
			startButton.setBackgroundResource(R.drawable.disable_round_button);
			endButton.setEnabled(true);
			endButton.setBackgroundResource(R.drawable.round_button);
		}
		// 漏巡情况
		StringBuffer buffer = new StringBuffer();
		if (lunCi > 1) {
			for (int i = 1; i < lunCi; ++i) {
				List<PatrolRecord> hasPatrol = prDao.getBySequence(i);
				XunGengService.getLouXunList(patrolLine.getLineNodes(), hasPatrol, i, buffer);
			}
		}

		louXunQingKuang.setText(buffer.toString());
		freshPage();
	}


    /**
     * 开始(本轮)巡更
     * @param view
     */
    boolean gogogo = true;
    public void startPatrol(View view) {
	    int shouldPatrolTimes = patrolLine.getShouldPatrolTimes();
	    if (lunCi > shouldPatrolTimes) {
		    new AlertDialog.Builder(this)
			    .setTitle("")
			    .setMessage("共" + shouldPatrolTimes + "轮已完部巡更完,确定还要继续巡更么？")
			    .setPositiveButton("取消",new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialogInterface, int i) {
					    gogogo = true;
				    }
			    }).setNegativeButton("继续巡更", null).show();
	    }
	    if (!gogogo) {
		    return;
	    }


	    String jiaoBanTime = jjbDao.getJieBanTime(SystemVariables.user.getId());
	    if (jiaoBanTime == null || "".equals(jiaoBanTime)) {
		    Dialog alertDialog = new AlertDialog.Builder(this).
				    setTitle("确定").
				    setMessage("您还没有接班, 现在就去接班么").
				    setPositiveButton("马上接班", new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
						    Intent intent = new Intent(XunGengDaKaActivity.this, JiaoJieBanActivity.class);
						    startActivity(intent);
					    }
				    }).
				    setNegativeButton("取消", null).setCancelable(false).create();
		    alertDialog.show();
		    return;
	    }

	    // 判断是否到开始时间
	    Date beginTime = DateUtil.getDateFromTimeStr2(patrolLine.getBeginTime());
	    if (beginTime != null) {
		    long now = SystemVariables.SERVER_TIME.getTime();
		    long begin = beginTime.getTime();
		    if (begin > (now + patrolLine.getException() * 60 * 1000)) {
			    showToast("还没有到开始时间, 当前时间:" + DateUtil.getTimeStr(SystemVariables.SERVER_TIME));
			    return;
		    }
	    }

	    Button startBtn = (Button) view;
	    startBtn.setEnabled(false);
	    startBtn.setBackgroundResource(R.drawable.disable_round_button);
	    endButton.setEnabled(true);
	    endButton.setBackgroundResource(R.drawable.round_button);

	    llDao.setLunCi(SystemVariables.USER_ID, lineId, lunCi);
	    UserPatrol userPatrol = new UserPatrol();
	    userPatrol.setTuserId(SystemVariables.T_USER_ID);
	    userPatrol.setUserId(user.getId());
	    userPatrol.setLineId(lineId);
	    userPatrol.setBeginPhoneTime(DateUtil.getHumanReadStr(new Date()));
	    Date date = new Date(SystemVariables.SERVER_TIME.getTime());
	    userPatrol.setBeginTime(DateUtil.getHumanReadStr(date));
	    userPatrol.setSequence(lunCi);
	    userPatrol.setScheduleId(patrolLine.getScheduleId());
	    long id = upDao.add(userPatrol);
	    lliDao.setLunCiId(SystemVariables.USER_ID, lineId, lunCi, id);
    }

    /**
     *
     * 结束(本轮)巡更
     * @param view
     */
    public void endPatrol(final View view) {
	    // 判断是否有漏巡
	    String str = "确定要结束本轮巡查么？";
	    Map<String, PatrolRecord> map = prDao.getMap(lineId, lunCi);
	    try {
		    int hasPatrol = map.entrySet().size();
		    int totalPatrol = lineNodes.size();
		    if (totalPatrol > hasPatrol) {
				str = "您还有 "+(totalPatrol-hasPatrol) +" 处未打卡, 确定要结束本轮巡查么？";
		    }
	    } catch (Exception e) { }
	    new AlertDialog.Builder(this)
		    .setTitle("确认")
		    .setIcon(android.R.drawable.ic_dialog_alert).setMessage(str)
		    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialogInterface, int i) {
				    Long id = lliDao.getLunCiId(SystemVariables.USER_ID, lineId, lunCi);
				    upDao.updateEndDate(SystemVariables.SERVER_TIME, new Date(), id);

				    lunCi++;
				    llDao.setLunCi(SystemVariables.USER_ID, lineId, lunCi);

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
		long lunciId = lliDao.getLunCiId(SystemVariables.USER_ID, lineId, lunCi);
		UserPatrol byId = upDao.getById(lunciId);
		if (byId == null) {
			VibratorUtil.vibrate(this);
			showToast("请先点击开始再打卡");
			return;
		}

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            byte[] arr = tag.getId();
            String nfcCode = XunGengService.byteArray2HexString(arr);
	        LineNode lineNode = SystemVariables.ALL_LINE_NODES_MAP.get(nfcCode);
	        if (lineNode == null) {
		        VibratorUtil.vibrate(this);
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
			        VibratorUtil.vibrate(this);
			        showToast("打卡失败, 该节点不属于该路线");
			        return;
		        }

		        Map<String, PatrolRecord> map = prDao.getMap(lineId, lunCi);
		        PatrolRecord record = map.get(lineNode.getId());
		        if (record != null && record.getPatrolTime()!=null && record.getPatrolTime().length()>10) {
			        VibratorUtil.vibrate(this);
			        showLongToast("该地点已经在 " + record.getPatrolTime().substring(11)+ "打过, 本次打卡无效");
			        return;
		        }
		        long userPatrolId = lliDao.getLunCiId(SystemVariables.USER_ID, lineId, lunCi);

		        PatrolRecord patrolRecord = new PatrolRecord();
		        patrolRecord.setUserId(SystemVariables.USER_ID);
		        patrolRecord.setTuserId(SystemVariables.T_USER_ID);
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
		        freshPage();
	        }
        }else{
	        VibratorUtil.vibrate(this);
            showToast("无效的NFC卡...");
        }
    }

	/**
	 * 刷新页面
	 */
	private void freshPage() {

		int childCount = tableLayout.getChildCount();
		tableLayout.removeViews(1, childCount-1);

		Map<String, PatrolRecord> map = prDao.getMap(lineId, lunCi);
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
			dian.setBackgroundColor(Color.parseColor("#333333"));
			dian.setTextSize(18);
			TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
			layoutParams.setMargins(0, 0, 1, 0);
			dian.setLayoutParams(layoutParams);

			TextView status = new TextView(this);
			status.setGravity(Gravity.CENTER);
			status.setText(statusText);
			status.setTextColor(Color.WHITE);
			status.setBackgroundColor(Color.parseColor("#333333"));
			status.setTextSize(18);
			status.setLayoutParams(layoutParams);

			TextView time = new TextView(this);
			time.setGravity(Gravity.CENTER);
			time.setText(timeText);
			time.setTextColor(Color.WHITE);
			time.setBackgroundColor(Color.parseColor("#333333"));
			time.setTextSize(18);
			time.setLayoutParams(layoutParams);

			tableRow.addView(dian);
			tableRow.addView(status);
			tableRow.addView(time);
			tableLayout.addView(tableRow);
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