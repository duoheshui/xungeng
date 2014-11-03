package com.joyi.xungeng.service;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.gson.Gson;
import com.joyi.xungeng.MainActivity;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.PatrolRecordDao;
import com.joyi.xungeng.dao.PatrolViewDao;
import com.joyi.xungeng.dao.ShiftRecordDao;
import com.joyi.xungeng.dao.UserPatrolDao;
import com.joyi.xungeng.domain.*;
import com.joyi.xungeng.util.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangyong on 2014/10/14.
 */
public class LoginService {
	private String TAG = "LoginService";
	private static LoginService loginService;
	private MainActivity context;
	private boolean hasSyncServerTime;

	private PatrolViewDao pvDao = new PatrolViewDao();
	private UserPatrolDao upDao = new UserPatrolDao();
	private PatrolRecordDao prDao = new PatrolRecordDao();
	private ShiftRecordDao srDao = new ShiftRecordDao();

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 100) {
				System.exit(0);
			}
		}
	};

	private AsyncHttpClient httpClient = new AsyncHttpClient();
	private LoginService(MainActivity context) {this.context = context;}

	public static LoginService getInstance(MainActivity context) {
		if (loginService == null) {
			loginService = new LoginService(context);
		}
		return loginService;
	}

	/**
	 * 获取新版请下载地下, 无新版本返回null
	 *
	 * @return
	 */
	public String autoUpdateApp(final Handler handler) {
		RequestParams requestParams = new RequestParams();
		requestParams.add("version", Constants.APP_VERSION);
		httpClient.post(context, Constants.HAS_NEW_VERSION_URL, requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				String url = null;
				try {
					url = response.getString("url");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (url == null || "".equals(url) ||url.indexOf("/")<0) {
					return;
				}
				Message newVersionMessage = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("url", url);
				newVersionMessage.setData(bundle);
				newVersionMessage.setTarget(handler);
				newVersionMessage.sendToTarget();
			}
		});

		return null;
	}


	/**
	 * 本地新建一个TimerTask, 在离线状态下同步服务器时间
	 */
	public void syncServerTime(final long serverTime) {
		if (hasSyncServerTime) {
			return;
		}
		hasSyncServerTime = true;
		SystemVariables.SERVER_TIME.setTime(serverTime);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				SystemVariables.SERVER_TIME.setTime(SystemVariables.SERVER_TIME.getTime() + 50);
			}
		}, 0, 50);
	}

	/**
	 * 登录时间记时器
	 */
	public void loginTimeCounter() {
		long now = System.currentTimeMillis();
		long stopTime = now + 2*24*60*60*1000;  // 2天后的毫秒数
//		long stopTime = now + 5*1000;
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Message message = new Message();
				message.what = 100;
				message.setTarget(handler);
				message.sendToTarget();
			}
		}, new Date(stopTime));
	}


    /**
     * 同步上次巡更信息, 同步成功删除本地记录
     */
    public void syncPatrolData(Context context) {
		Gson gson = new Gson();

	    // 1, 同步巡查信息

	    List<PatrolView> pvList = pvDao.getAllNotSync();
	    if (pvList != null && pvList.size() > 0) {
		    RequestParams requestParams = new RequestParams();
		    requestParams.put("data", gson.toJson(pvList));
		    httpClient.post(context, Constants.UPLOAD_PATROL_VIEW_URL, requestParams, new JsonHttpResponseHandler() {
			    @Override
			    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
				    try {
					    String errorCode = jsonObject.getString("errorCode");
					    if (Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
						    pvDao.sync();
					    }
				    } catch (JSONException e) {
					    Log.e(TAG, e.toString());
					    e.printStackTrace();
				    }
			    }
		    });
	    }

	    // 2, 同步巡更打卡信息
	    List<UserPatrol> upList = upDao.getAllNotSync();
	    if (upList != null && upList.size() > 0) {
		    RequestParams requestParams = new RequestParams();
		    requestParams.put("data", gson.toJson(upList));
		    httpClient.post(context, Constants.UPLOAD_PARTOL_RECORD_URL, requestParams, new JsonHttpResponseHandler() {
			    @Override
			    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
				    try {
					    String errorCode = jsonObject.getString("errorCode");
					    if (Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
						    upDao.sync();
							prDao.sync();
					    }
				    } catch (JSONException e) {
					    Log.e(TAG, e.toString());
					    e.printStackTrace();
				    }
			    }
		    });
	    }

	    // 3, 同步交接班信息
	    List<ShiftRecord> srList = srDao.getAllNotSync();
	    if (srList != null && srList.size() > 0) {
		    RequestParams requestParams = new RequestParams();
		    requestParams.put("data", gson.toJson(srList));
		    httpClient.post(context, Constants.UPLOAD_SHIFT_INFO_URL, requestParams, new JsonHttpResponseHandler() {
			    @Override
			    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
				    try {
					    String errorCode = jsonObject.getString("errorCode");
					    if (Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
						    srDao.sync();
					    }
				    } catch (JSONException e) {
					    Log.e(TAG, e.toString());
					    e.printStackTrace();
				    }
			    }
	        });
	    }
    }

	/**
	 * 解析数据
	 * @param jsonObject
	 * @throws JSONException
	 */
	public void parseData(JSONObject jsonObject) throws JSONException {
		// 登录用户信息
		JSONObject userJson = jsonObject.getJSONObject("userInfo");
		String userId = userJson.getString("userId");
		SystemVariables.user.setId(userId);
		SystemVariables.user.setUserName(userJson.getString("userName"));
		SystemVariables.user.setLoginName(userJson.getString("loginName"));
		SystemVariables.user.setPyShort(userJson.getString("pyShort"));
		SystemVariables.user.setPatrolStationTypeId(userJson.getString("patrolStationTypeId"));
		SystemVariables.user.setHasPatrolViewPrivilege(userJson.getBoolean("hasPatrolViewPrivilege"));

		// 超级密码
		Constants.SUPER_PASSWORD = jsonObject.getString("super_password");

		// 替岗用户信息(默认为登录用户)
		SystemVariables.tUser.setId(userId);
		SystemVariables.tUser.setUserName(userJson.getString("userName"));
		SystemVariables.tUser.setLoginName(userJson.getString("loginName"));
		SystemVariables.tUser.setPyShort(userJson.getString("pyShort"));
		SystemVariables.tUser.setPatrolStationTypeId(userJson.getString("patrolStationTypeId"));
		SystemVariables.tUser.setHasPatrolViewPrivilege(userJson.getBoolean("hasPatrolViewPrivilege"));
		SystemVariables.USER_ID = userId;
		SystemVariables.T_USER_ID = userId;

		// 所有用户
		JSONArray userList = jsonObject.getJSONArray("userList");
		if (userList != null && userList.length() > 0) {
			User user = null;
			for (int i = 0; i < userList.length(); ++i) {
				JSONObject userObj = userList.getJSONObject(i);
				user = new User();
				user.setId(userObj.getString("userId"));
				String loginName = userObj.getString("loginName");
				user.setLoginName(loginName);
				user.setUserName(userObj.getString("userName"));
				user.setPatrolStationTypeId(userObj.getString("patrolStationTypeId"));
				user.setPassword(userObj.getString("password"));

				SystemVariables.ALL_USERS_MAP.put(loginName, user);
			}
		}

		// 岗位列表
		JSONArray gangWeiArray = jsonObject.getJSONArray("stationList");
		if (gangWeiArray != null && gangWeiArray.length() > 0) {
			Station station = null;
			for (int i = 0; i < gangWeiArray.length(); ++i) {
				JSONObject gangWeiObj = gangWeiArray.getJSONObject(i);
				station = new Station(gangWeiObj.getString("stationId"), gangWeiObj.getString("stationName"));
				// 班次
				JSONArray banCiArray = gangWeiObj.getJSONArray("scheduleList");
				if (banCiArray != null && banCiArray.length() > 0) {
					Schedule schedule = null;
					for (int j = 0; j < banCiArray.length(); ++j) {
						schedule = new Schedule();
						JSONObject banCiObj = banCiArray.getJSONObject(j);
						String scheduleId = banCiObj.getString("scheduleId");
						String name = banCiObj.getString("name");
						String scheduleTypeName = banCiObj.getString("scheduleTypeName");
						schedule.setName(name);
						schedule.setScheduleTypeName(scheduleTypeName);
						schedule.setScheduleId(scheduleId);
						schedule.setStationId(banCiObj.getString("stationId"));
						int exception = banCiObj.getInt("exception");
						int frequency = banCiObj.getInt("frequency");
						schedule.setFrequency(frequency);
						schedule.setException(exception);
						String beginTime = banCiObj.getString("beginTime");
						String endTime = banCiObj.getString("endTime");
						schedule.setBeginTime(beginTime);
						schedule.setEndTime(endTime);
						int shouldPatrolTimes = banCiObj.getInt("shouldPatrolTimes");
						schedule.setShouldPatrolTimes(shouldPatrolTimes);
						// 路线
						JSONArray luXianArray = banCiObj.getJSONArray("patrolLines");
						if (luXianArray != null && luXianArray.length() > 0) {
							PatrolLine patrolLine = null;
							for (int k = 0; k < luXianArray.length(); ++k) {
								patrolLine = new PatrolLine();

								JSONObject luXianObj = luXianArray.getJSONObject(k);
								patrolLine.setName(name);
								patrolLine.setScheduleTypeName(scheduleTypeName);
								patrolLine.setId(luXianObj.getString("lineId"));
								patrolLine.setLineName(luXianObj.getString("lineName"));
								patrolLine.setBeginTime(beginTime);
								patrolLine.setEndTime(endTime);
								patrolLine.setScheduleId(scheduleId);
								patrolLine.setFrequency(frequency);
								patrolLine.setException(exception);
								patrolLine.setShouldPatrolTimes(shouldPatrolTimes);
								// 节点
								JSONArray jieDianArray = luXianObj.getJSONArray("nodes");
								if (jieDianArray != null && jieDianArray.length() > 0) {
									LineNode lineNode = null;
									for (int m = 0; m < jieDianArray.length(); ++m) {
										lineNode = new LineNode();
										JSONObject jieDianObj = jieDianArray.getJSONObject(m);
										String nodeId = jieDianObj.getString("nodeId");
										String nfcCode = jieDianObj.getString("nfcCode");
										lineNode.setId(nodeId);
										lineNode.setNodeName(jieDianObj.getString("name"));
										lineNode.setLineId(jieDianObj.getString("lineId"));
										lineNode.setNfcCode(nfcCode);

										patrolLine.getLineNodes().add(lineNode);
										SystemVariables.ALL_LINE_NODES.add(lineNode);
										SystemVariables.ALL_LINE_NODES_MAP.put(nfcCode, lineNode);
										SystemVariables.NODEID_NODE_MAP.put(nodeId, lineNode);
									}
								}
								station.getLines().add(new KeyValuePair(luXianObj.getString("lineId"), luXianObj.getString("lineName")));
								schedule.getPatrolLines().add(patrolLine);
								SystemVariables.PATROL_LINES.add(patrolLine);
							}
						}

						station.getSchedules().add(schedule);
					}
				}
				SystemVariables.STATION_LIST.add(station);
			}
		}

		// 班次
		JSONArray scheduleList = jsonObject.getJSONArray("scheduleDictList");
		if (scheduleList != null && scheduleList.length() > 0) {
			KeyValuePair shift = null;
			for (int i = 0; i < scheduleList.length(); ++i) {
				JSONObject shiftJson = scheduleList.getJSONObject(i);
				shift = new KeyValuePair(shiftJson.getString("scheduleId"), shiftJson.get("scheduleName"));
				SystemVariables.SHIFT_LIST.add(shift);
			}
		}
	}
}
