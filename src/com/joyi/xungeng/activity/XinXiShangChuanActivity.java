package com.joyi.xungeng.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.PatrolRecordDao;
import com.joyi.xungeng.dao.PatrolViewDao;
import com.joyi.xungeng.dao.ShiftRecordDao;
import com.joyi.xungeng.domain.PatrolRecord;
import com.joyi.xungeng.domain.PatrolView;
import com.joyi.xungeng.domain.ShiftRecord;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.PhoneUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 信息上传
 */
public class XinXiShangChuanActivity extends BaseActivity {
	private Button uploadButton;

	private AsyncHttpClient httpClient = new AsyncHttpClient();
	private PatrolRecordDao prDao = new PatrolRecordDao();
	private PatrolViewDao pvDao = new PatrolViewDao();
	private ShiftRecordDao srDao = new ShiftRecordDao();
	private Handler handler;

	private boolean uploadedPR; // 巡更记录
	private boolean uploadedPV; // 巡查记录
	private boolean uploadedSR; // 交接班记录
	private int totalRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xin_xi_shang_chuan);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText(SystemVariables.USER_NAME);

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case Constants.WHAT_PATROL_RECORED: uploadedPR = true; break;
					case Constants.WHAT_PATROL_VIEW: uploadedPV = true; break;
					case Constants.WHAT_SHIFT_RECORD: uploadedSR = true; break;
				}
				totalRequest++;
				if (totalRequest == 3 && uploadedSR && uploadedPV && uploadedSR) {
					// 删除本地记录
					prDao.deleteAll();
					pvDao.deleteAll();
					srDao.deleteAll();
					uploadButton.setText("上传完成");
					showToast("上传完成");
				}else{
					uploadButton.setClickable(true);
					uploadButton.setText("上传");
					showToast("上传失败, 请重试");
				}
			}
		};
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
		List<PatrolRecord> patrolRecords = prDao.getAll();
		if (patrolRecords != null && patrolRecords.size() > 0) {
			JSONArray jsonArray = new JSONArray(patrolRecords);
			RequestParams requestParams = new RequestParams();
			requestParams.put("data", jsonArray.toString());
			sendAsyncHttpRequest(Constants.UPLOAD_PARTOL_RECORD_URL, requestParams, Constants.WHAT_PATROL_RECORED);
		}
		// 2, 上传巡查
		List<PatrolView> patrolViews = pvDao.getAll();
		if (patrolViews != null && patrolViews.size() > 0) {
			JSONArray jsonArray = new JSONArray(patrolViews);
			RequestParams requestParams = new RequestParams();
			requestParams.put("data", jsonArray.toString());
			sendAsyncHttpRequest(Constants.UPLOAD_PATROL_VIEW_URL, requestParams, Constants.WHAT_PATROL_VIEW);
		}
		// 3, 上传交接班
		List<ShiftRecord> shiftRecords = srDao.getAll();
		if (shiftRecords != null && shiftRecords.size() > 0) {
			JSONArray jsonArray = new JSONArray(shiftRecords);
			RequestParams requestParams = new RequestParams();
			requestParams.put("data", jsonArray.toString());
			sendAsyncHttpRequest(Constants.UPLOAD_SHIFT_INFO_URL, requestParams, Constants.WHAT_SHIFT_RECORD);
		}
	}

	/**
	 * 发送请求上传数据
	 * @param requestUrl
	 * @param requestParams
	 * @param what
	 */
	public void sendAsyncHttpRequest(String requestUrl, RequestParams requestParams, final int what) {
		httpClient.post(this, requestUrl, requestParams, new JsonHttpResponseHandler(){


			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
				super.onSuccess(statusCode, headers, json);
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