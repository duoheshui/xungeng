package com.joyi.xungeng.activity;

import android.app.AlertDialog;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.PatrolViewDao;
import com.joyi.xungeng.domain.LineNode;
import com.joyi.xungeng.domain.PatrolView;
import com.joyi.xungeng.domain.User;
import com.joyi.xungeng.service.XunGengService;
import com.joyi.xungeng.util.DateUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/17.
 * 【巡查打卡】Activity
 */
public class XunchaDaKaActivity extends BaseActivity {
	private PatrolViewDao patrolViewDao = new PatrolViewDao();
	private TableLayout tableLayout;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xun_cha_da_ka);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText(SystemVariables.USER_NAME);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

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

		tableLayout = (TableLayout) findViewById(R.id.patrol_view_record_table);
		List<PatrolView> patrolViewList = patrolViewDao.getAll();
		int bgColor = Color.parseColor("#333333");
		TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();

		if (patrolViewList != null) {
			for (PatrolView pview : patrolViewList) {
				TableRow tableRow = new TableRow(this);
				tableRow.setBackgroundColor(Color.WHITE);

				TextView dian = new TextView(this);
				dian.setGravity(Gravity.CENTER);
				dian.setText(pview.getNodeName());
				dian.setBackgroundColor(bgColor);
				dian.setTextSize(18);
				layoutParams.setMargins(1, 0, 1, 1);
				dian.setLayoutParams(layoutParams);

				TextView time = new TextView(this);
				time.setGravity(Gravity.CENTER);
				time.setText(pview.getPatrolTime());
				time.setBackgroundColor(bgColor);
				time.setTextSize(18);
				time.setLayoutParams(layoutParams);

				tableRow.addView(dian);
				tableRow.addView(time);
				tableLayout.addView(tableRow);
			}
		}
	}

    /**
     * 打卡触发此事件
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        User user = SystemVariables.user;
        PatrolView patrolView = new PatrolView();
        LineNode lineNode = null;
        if (tag != null) {
            byte[] arr = tag.getId();
            String nfcCode = XunGengService.byteArray2HexString(arr);
            lineNode = SystemVariables.ALL_LINE_NODES_MAP.get(nfcCode);
            if (lineNode == null) {
                showToast("无效的NFC卡...");
                return;
            }

            patrolView.setPatrolTime(DateUtil.getHumanReadStr(SystemVariables.SERVER_TIME));
            patrolView.setPatrolPhoneTime(DateUtil.getHumanReadStr(new Date()));
            patrolView.setStatus("1");
	        patrolView.setNodeName(lineNode.getNodeName());
	        patrolView.setNodeId(lineNode.getId());
	        patrolView.setUserId(user.getId());
        }
        patrolViewDao.add(patrolView);
        showToast(lineNode.getNodeName()+"  打卡成功");
	    finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }
}
