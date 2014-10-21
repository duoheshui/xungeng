package com.joyi.xungeng;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.joyi.xungeng.activity.MenuActivity;
import com.joyi.xungeng.db.WuYeSqliteOpenHelper;
import com.joyi.xungeng.domain.*;
import com.joyi.xungeng.service.LoginService;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.DateUtil;
import com.joyi.xungeng.util.StringUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 应用程序入口Activity
 */
public class MainActivity extends BaseActivity {
	private LoginService loginService;
	private EditText username;
	private EditText password;
	private Handler loginHandler;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		SystemVariables.IMEI = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

		loginService = LoginService.getInstance(this);

        username = (EditText) findViewById(R.id.username_edittext);
        password = (EditText) findViewById(R.id.password_edittext);


		loginHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					/* 跳转至菜单目录页面 */
					Intent intent = new Intent(MainActivity.this, MenuActivity.class);
					startActivity(intent);
				}
			}
		};


        String newVersionUrl = loginService.getNewVersionUrl();
		if (StringUtils.isNotNull(newVersionUrl)) {
			loginService.showAlert();
			return;
		}

        /* 初始化数据库操作对象 */
        SystemVariables.sqLiteOpenHelper = new WuYeSqliteOpenHelper(this);
	}

	/**
	 * 登录
	 * @param view
	 */
	public void login(View view) {
		String inputUsername = username.getText().toString();
		String inputPassword = password.getText().toString();

		try {
			RequestParams requestParams = new RequestParams();
			requestParams.put("loginName", inputUsername);
			requestParams.put("password", inputPassword);

			AsyncHttpClient client = new AsyncHttpClient();

            // 发起请求前的时间戳
            final long beforeHttp = System.currentTimeMillis();
			client.post(MainActivity.this, Constants.LOGIN_URL, requestParams, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                    // 请求响应时的时间戳
                    long afterHttp = System.currentTimeMillis();
                    try {
                        String errorCode = jsonObject.getString("errorCode");
                        if (!Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
                            showToast("登录失败, 请稍后再试");
                            return;
                        }

                        /* 1, 同步服务器时间 */
                        long serverTime = Long.parseLong(jsonObject.getString("serverTime")) + (afterHttp-beforeHttp);
                        loginService.syncServerTime(serverTime);

	                    /* 2, 解析返回数据 */
	                    User user = SystemVariables.user;
	                    JSONObject userJson = jsonObject.getJSONObject("userInfo");
	                    user.setId(userJson.getString("userId"));
	                    user.setUserName(userJson.getString("userName"));
	                    SystemVariables.USER_NAME = userJson.getString("userName");
	                    user.setLoginName(userJson.getString("loginName"));
	                    user.setPyShort(userJson.getString("pyShort"));
	                    user.setPatrolStationTypeId(userJson.getString("patrolStationTypeId"));
	                    user.setHasPatrolViewPrivilege(userJson.getBoolean("hasPatrolViewPrivilege"));
						// 岗位
	                    JSONArray jsonArray = jsonObject.getJSONArray("stationList");
	                    if (jsonArray != null && jsonArray.length() > 0) {
		                    Station station = null;
		                    for (int i = 0; i < jsonArray.length(); ++i) {
			                    JSONObject stationJson = jsonArray.getJSONObject(i);
			                    station = new Station(stationJson.getString("stationId"), stationJson.getString("stationName"));
			                    JSONArray linesJson = stationJson.getJSONArray("lines");
			                    KeyValuePair line = null;
			                    for (int j = 0; j < linesJson.length(); ++i) {
				                    JSONObject lineJson = linesJson.getJSONObject(j);
				                    line = new KeyValuePair(lineJson.getString("lineId"), lineJson.get("lineName"));
				                    station.getLines().add(line);
			                    }
			                    SystemVariables.STATION_LIST.add(station);
		                    }
	                    }
						// 所有节点信息
	                    JSONArray nodesJsonArray = jsonObject.getJSONArray("lineNodes");
	                    if (nodesJsonArray != null && nodesJsonArray.length() > 0) {
		                    LineNode lineNode = null;
		                    for (int i = 0; i < nodesJsonArray.length(); ++i) {
			                    lineNode = new LineNode();
			                    JSONObject nodeJson = nodesJsonArray.getJSONObject(i);
			                    lineNode.setId(nodeJson.getString("nodeId"));
			                    lineNode.setNodeName(nodeJson.getString("nodeName"));
			                    lineNode.setNfcCode(nodeJson.getString("nfcCode"));
			                    SystemVariables.ALL_LINE_NODES.add(lineNode);
		                    }
	                    }


	                    // 班次
	                    JSONArray scheduleList = jsonObject.getJSONArray("scheduleList");
	                    if (scheduleList != null && scheduleList.length() > 0) {
		                    KeyValuePair shift = null;
		                    for (int i = 0; i < scheduleList.length(); ++i) {
			                    JSONObject shiftJson = scheduleList.getJSONObject(i);
			                    shift = new KeyValuePair(shiftJson.getString("scheduleId"), shiftJson.get("scheduleName"));
			                    SystemVariables.SHIFT_LIST.add(shift);
		                    }
	                    }
						// 巡查路线
	                    JSONArray patrolLines = jsonObject.getJSONArray("patrolLine");
	                    if (patrolLines != null && patrolLines.length() > 0) {
							PatrolLine patrolLine = null;
		                    for (int i = 0; i < patrolLines.length(); ++i) {
			                    patrolLine = new PatrolLine();
			                    JSONObject patrolLineJson = patrolLines.getJSONObject(i);
			                    patrolLine.setName(patrolLineJson.getString("lineName"));
			                    patrolLine.setId(patrolLineJson.getString("lineId"));
			                    patrolLine.setStationId(patrolLineJson.getString("stationId"));
			                    patrolLine.setFrequency(patrolLineJson.getInt("frequency"));
			                    patrolLine.setException(patrolLineJson.getInt("exception"));
			                    patrolLine.setBeginTime(DateUtil.getDateFromHumanReadStr(patrolLineJson.getString("beginTime")));
			                    patrolLine.setEndTime(DateUtil.getDateFromHumanReadStr(patrolLineJson.getString("endTime")));

			                    JSONArray lineNodes = patrolLineJson.getJSONArray("nodes");
			                    if (lineNodes != null && lineNodes.length() > 0) {
				                    LineNode node = null;
				                    for (int j = 0; j < lineNodes.length(); ++j) {
					                    node = new LineNode();
					                    JSONObject nodeJson = lineNodes.getJSONObject(j);
					                    node.setId(nodeJson.getString("nodeId"));
					                    node.setLineId(nodeJson.getString("lineId"));
					                    node.setNodeName(nodeJson.getString("nodeName"));
					                    node.setNfcCode(nodeJson.getString("nfcCode"));
					                    patrolLine.getLineNodes().add(node);
				                    }
			                    }
		                    }
		                    SystemVariables.PATROL_LINES.add(patrolLine);
	                    }

                        /* 3, 检查上次打卡记录是否已上传 */
                        loginService.syncPatrolData(MainActivity.this);

	                    Message message = new Message();
	                    message.what = 1;
	                    message.setTarget(loginHandler);
	                    message.sendToTarget();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					showToast("系统异常, 请稍后再试");
				}
            });
		} catch (Exception e) {
			showToast("登录失败, 请稍后再试");
			Log.e(TAG, e.toString());
		}
	}

	public Handler getLoginHandler() {
		return loginHandler;
	}
}
