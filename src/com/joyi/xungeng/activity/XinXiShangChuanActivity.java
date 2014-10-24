package com.joyi.xungeng.activity;

import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.CellIdentityGsm;
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
import com.joyi.xungeng.dao.PatrolViewDao;
import com.joyi.xungeng.dao.ShiftRecordDao;
import com.joyi.xungeng.dao.UserPatrolDao;
import com.joyi.xungeng.domain.PatrolRecord;
import com.joyi.xungeng.domain.PatrolView;
import com.joyi.xungeng.domain.ShiftRecord;
import com.joyi.xungeng.domain.UserPatrol;
import com.joyi.xungeng.service.XunGengService;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.DateUtil;
import com.joyi.xungeng.util.PhoneUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 信息上传
 */
public class XinXiShangChuanActivity extends BaseActivity {
	private Button uploadButton;
	private TableLayout xinxiLayout;

	private AsyncHttpClient httpClient = new AsyncHttpClient();
	private PatrolRecordDao prDao = new PatrolRecordDao();
	private PatrolViewDao pvDao = new PatrolViewDao();
	private ShiftRecordDao srDao = new ShiftRecordDao();
	private UserPatrolDao upDao = new UserPatrolDao();
	private Handler handler;

	private boolean uploadedPR;     // 巡更记录
	private boolean uploadedPV;     // 巡查记录
	private boolean uploadedSR;     // 交接班记录

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xin_xi_shang_chuan);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText(SystemVariables.USER_NAME);
		xinxiLayout = (TableLayout) findViewById(R.id.xin_xi_table_layout);

		// 上传回调
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case Constants.WHAT_PATROL_RECORED: uploadedPR = true;  upDao.deleteAll(); prDao.deleteAll(); break;
					case Constants.WHAT_PATROL_VIEW: uploadedPV = true; pvDao.deleteAll();      break;
					case Constants.WHAT_SHIFT_RECORD: uploadedSR = true;    srDao.deleteAll();  break;
				}

				if (uploadedPR && uploadedPV && uploadedSR) {
					// 删除本地记录
					uploadButton.setText("上传");
					showToast("上传完成");
				}
			}
		};
		List<PatrolRecord> prList = prDao.getAll();
		List<PatrolView> pvList = pvDao.getAll();
		List<ShiftRecord> srList = srDao.getAll();

		TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
		layoutParams.setMargins(0, 0, 1, 0);
		int bgColor = Color.parseColor("#333333");

		if (prList != null && prList.size() > 0) {
			for (PatrolRecord record : prList) {
				int sequence = record.getSequence();
				String nodeId = record.getNodeId();
				String nodeName = SystemVariables.NODEID_NODE_MAP.get(nodeId).getNodeName();

				TableRow tableRow = new TableRow(this);
				tableRow.setBackgroundColor(Color.WHITE);
				tableRow.setPadding(1, 1, 1, 1);

				TextView name = new TextView(this);
				name.setText("第" + sequence + "轮" + nodeName);
				name.setLayoutParams(layoutParams);
				name.setGravity(Gravity.CENTER);
				name.setBackgroundColor(bgColor);

				TextView time = new TextView(this);
				time.setText(record.getPatrolTime());
				time.setLayoutParams(layoutParams);
				time.setGravity(Gravity.CENTER);
				time.setBackgroundColor(bgColor);

				tableRow.addView(name);
				tableRow.addView(time);
				xinxiLayout.addView(tableRow);
			}
		}
		if (pvList != null && pvList.size() > 0) {
			for (PatrolView view : pvList) {
				TableRow tableRow = new TableRow(this);

				TextView name = new TextView(this);
				name.setText(view.getNodeName());
				name.setLayoutParams(layoutParams);
				name.setBackgroundColor(bgColor);
				name.setGravity(Gravity.CENTER);

				TextView time = new TextView(this);
				time.setText(view.getPatrolTime());
				time.setLayoutParams(layoutParams);
				time.setBackgroundColor(bgColor);
				time.setGravity(Gravity.CENTER);

				tableRow.addView(name);
				tableRow.addView(time);
				xinxiLayout.addView(tableRow);
			}
		}
		if (srList != null && srList.size() > 0) {
			TableRow tableRow = new TableRow(this);
			TextView tip = new TextView(this);
			tip.setText("交接班信息:");
			tip.setTextColor(Color.GREEN);
			tableRow.addView(tip);

			TableRow jiaoJIeBan = new TableRow(this);
			TextView jiaoJie = new TextView(this);
			jiaoJie.setText("交班/接班");
			jiaoJie.setTextSize(18);
			jiaoJie.setGravity(Gravity.CENTER);
			jiaoJie.setTextColor(Color.WHITE);
			jiaoJIeBan.addView(jiaoJie);

			TextView jiaoJieShiJian = new TextView(this);
			jiaoJieShiJian.setText("时间");
			jiaoJieShiJian.setGravity(Gravity.CENTER);
			jiaoJieShiJian.setTextSize(18);
			jiaoJieShiJian.setTextColor(Color.WHITE);
			jiaoJIeBan.addView(jiaoJieShiJian);

			xinxiLayout.addView(tableRow);
			xinxiLayout.addView(jiaoJIeBan);


			for (ShiftRecord record : srList) {
				TableRow row = new TableRow(this);
				TextView name = new TextView(this);
				boolean currect = DateUtil.isValidDate(record.getReceiveTime());
				name.setText(currect ? "接班" : "交班");
				name.setLayoutParams(layoutParams);
				name.setBackgroundColor(bgColor);
				name.setGravity(Gravity.CENTER);
				row.addView(name);

				TextView time = new TextView(this);
				time.setText(currect ? record.getReceiveTime() : record.getSubmitTime());
				time.setLayoutParams(layoutParams);
				time.setBackgroundColor(bgColor);
				time.setGravity(Gravity.CENTER);
				row.addView(time);
				xinxiLayout.addView(row);
			}
		}
	}

	/**
	 * 上传巡更/巡查/交接班 信息
	 * @param view
	 */
	public void uploadInfo(View view) {

		// 判断网络是否可用
		boolean networkConnected = PhoneUtils.isNetworkConnected(this);
		if (!networkConnected) {
			showToast("网络未连接....");
			return;
		}
		// 禁用按钮
		uploadButton = (Button)view;
		uploadButton.setClickable(false);
		uploadButton.setText("请在上传, 请稍等");

		// 1, 上传巡更
		List<UserPatrol> patrolRecords = upDao.getAll();

		// 2, 上传巡查
		List<PatrolView> patrolViews = pvDao.getAll();
		// 3, 上传交接班
		List<ShiftRecord> shiftRecords = srDao.getAll();
		if (XunGengService.isNullList(patrolRecords) && XunGengService.isNullList(patrolViews) && XunGengService.isNullList(shiftRecords)) {
			showToast("上传完成。");
			uploadButton.setText("上传");
		}
		Gson gson = new Gson();
		if (patrolRecords != null && patrolRecords.size() > 0) {
			RequestParams requestParams = new RequestParams();
			requestParams.put("data", gson.toJson(patrolRecords));
			sendAsyncHttpRequest(Constants.UPLOAD_PARTOL_RECORD_URL, requestParams, Constants.WHAT_PATROL_RECORED);
		}else{
			uploadedPR = true;
		}

		if (patrolViews != null && patrolViews.size() > 0) {
			RequestParams requestParams = new RequestParams();
			requestParams.put("data", gson.toJson(patrolViews));
			sendAsyncHttpRequest(Constants.UPLOAD_PATROL_VIEW_URL, requestParams, Constants.WHAT_PATROL_VIEW);
		}else{
			uploadedPV = true;
		}

		if (shiftRecords != null && shiftRecords.size() > 0) {
			RequestParams requestParams = new RequestParams();
			requestParams.put("data", gson.toJson(shiftRecords));
			sendAsyncHttpRequest(Constants.UPLOAD_SHIFT_INFO_URL, requestParams, Constants.WHAT_SHIFT_RECORD);
		}else{
			uploadedSR = true;
		}
	}

	/**
	 * 发送请求上传数据
	 * @param requestUrl
	 * @param requestParams
	 * @param what
	 */
	public void sendAsyncHttpRequest(String requestUrl, RequestParams requestParams, final int what) {
		httpClient.post(this, requestUrl, requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
				try {
					String status = json.getString("errorCode");
					if (Constants.HTTP_SUCCESS_CODE.equals(status)) {
						Message message = new Message();
						message.what = what;
						message.setTarget(handler);
						message.sendToTarget();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}