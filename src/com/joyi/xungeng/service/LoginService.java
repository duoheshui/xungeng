package com.joyi.xungeng.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.util.Log;
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
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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
	private Context context;
	private boolean hasSyncServerTime;
	private AsyncHttpClient httpClient = new AsyncHttpClient();



	private LoginService(Context context) {this.context = context;}

	public static LoginService getInstance(Context context) {
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
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				SystemVariables.SERVER_TIME.setTime(SystemVariables.SERVER_TIME.getTime() + 100);
			}
		}, 0, 100);
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

			}
		}).show();
	}

    /**
     * 同步上次巡更信息, 同步成功删除本地记录
     * TODO
     */
    public void syncPatrolData(Context context) {
	    // 1, 同步巡查信息
	    final PatrolViewDao pvDao = new PatrolViewDao();
	    List<PatrolView> pvList = pvDao.getAll();
        if (pvList != null && pvList.size() > 0) {
	        final JSONArray jsonArray = new JSONArray(pvList);
	        RequestParams requestParams = new RequestParams();
	        requestParams.put("data", jsonArray.toString());
	        httpClient.post(context, Constants.UPLOAD_PATROL_VIEW_URL, requestParams, new JsonHttpResponseHandler(){
		        @Override
		        public void onSuccess(JSONObject jsonObject) {
			        super.onSuccess(jsonObject);
			        try {
				        String errorCode = jsonObject.getString("errorCode");
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
	    if (pvList != null && pvList.size() > 0) {
		    final JSONArray jsonArray = new JSONArray(upList);
		    RequestParams requestParams = new RequestParams();
		    requestParams.put("data", jsonArray.toString());
		    httpClient.post(context, Constants.UPLOAD_PARTOL_RECORD_URL, requestParams, new JsonHttpResponseHandler(){
			    @Override
			    public void onSuccess(JSONObject jsonObject) {
				    super.onSuccess(jsonObject);
				    try {
					    String errorCode = jsonObject.getString("errorCode");
					    if (Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
						    upDao.deleteAll();
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
	    List<ShiftRecord> srList = srDao.getList();
	    if (srList != null && srList.size() > 0) {
		    final JSONArray jsonArray = new JSONArray(srList);
		    RequestParams requestParams = new RequestParams();
		    requestParams.put("data", jsonArray.toString());
		    httpClient.post(context, Constants.UPLOAD_SHIFT_INFO_URL, requestParams, new JsonHttpResponseHandler(){
			    @Override
			    public void onSuccess(JSONObject jsonObject) {
				    super.onSuccess(jsonObject);
				    try {
					    String errorCode = jsonObject.getString("errorCode");
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
