package com.joyi.xungeng.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.joyi.xungeng.domain.PatrolRecord;
import com.joyi.xungeng.domain.PatrolView;
import com.joyi.xungeng.domain.ShiftRecord;
import com.joyi.xungeng.domain.UserPatrol;
import com.joyi.xungeng.util.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.GZIPOutputStream;

/**
 * Created by zhangyong on 2014/10/14.
 */
public class LoginService {
	private String TAG = "LoginService";
	private static LoginService loginService;
	private MainActivity context;
	private boolean hasSyncServerTime;

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
	 * @return
	 */
	public String getNewVersionUrl() {
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
				SystemVariables.SERVER_TIME.setTime(SystemVariables.SERVER_TIME.getTime() + 100);
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
	 * 新版本弹出层
	 */
	public void showAlert(){
		new AlertDialog.Builder(context).setTitle("").setMessage("检测到有新版本, 请升级...").setPositiveButton("升级", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

			}
		}).setNegativeButton("退出", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				context.finish();
				System.exit(1);
			}
		}).show();
	}

    /**
     * 同步上次巡更信息, 同步成功删除本地记录
     */
    public void syncPatrolData(Context context) {
Log.e("login", "syncPatrolData");
Gson gson = new Gson();

	    // 1, 同步巡查信息
	    final PatrolViewDao pvDao = new PatrolViewDao();
	    List<PatrolView> pvList = pvDao.getAll();
Log.e("pv", gson.toJson(pvList));
	    if (pvList != null && pvList.size() > 0) {
		    RequestParams requestParams = new RequestParams();
		    requestParams.put("data", gson.toJson(pvList));
		    httpClient.post(context, Constants.UPLOAD_PATROL_VIEW_URL, requestParams, new JsonHttpResponseHandler() {
			    @Override
			    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
				    try {
					    String errorCode = jsonObject.getString("errorCode");
Log.e("pvcode", errorCode);
					    if (Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
						    pvDao.deleteAll();
					    }
				    } catch (JSONException e) {
					    Log.e(TAG, e.toString());
					    e.printStackTrace();
				    }
			    }
		    });
	    }

	    // 2, 同步巡更打卡信息
	    final UserPatrolDao upDao = new UserPatrolDao();
	    List<UserPatrol> upList = upDao.getAll();
Log.e("up", gson.toJson(upList));
	    if (upList != null && upList.size() > 0) {
		    RequestParams requestParams = new RequestParams();
		    requestParams.put("data", gson.toJson(upList));
		    httpClient.post(context, Constants.UPLOAD_PARTOL_RECORD_URL, requestParams, new JsonHttpResponseHandler() {
			    @Override
			    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
				    try {
					    String errorCode = jsonObject.getString("errorCode");
Log.e("upcode", errorCode);
					    if (Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
						    upDao.deleteAll();
						    PatrolRecordDao prDao = new PatrolRecordDao();
						    prDao.deleteAll();
					    }
				    } catch (JSONException e) {
					    Log.e(TAG, e.toString());
					    e.printStackTrace();
				    }
			    }
		    });
	    }

	    // 3, 同步交接班信息
	    final ShiftRecordDao srDao = new ShiftRecordDao();
	    List<ShiftRecord> srList = srDao.getAll();
Log.e("sr", gson.toJson(srList));
	    if (srList != null && srList.size() > 0) {
		    RequestParams requestParams = new RequestParams();
		    requestParams.put("data", gson.toJson(srList));
		    httpClient.post(context, Constants.UPLOAD_SHIFT_INFO_URL, requestParams, new JsonHttpResponseHandler() {
			    @Override
			    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
				    try {
					    String errorCode = jsonObject.getString("errorCode");
Log.e("srcode", errorCode);
					    if (Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
						    srDao.deleteAll();
					    }
				    } catch (JSONException e) {
					    Log.e(TAG, e.toString());
					    e.printStackTrace();
				    }
			    }
		    });
	    }
    }
}
