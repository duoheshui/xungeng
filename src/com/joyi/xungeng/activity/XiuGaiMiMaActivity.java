package com.joyi.xungeng.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.PatrolRecordDao;
import com.joyi.xungeng.dao.PatrolViewDao;
import com.joyi.xungeng.dao.ShiftRecordDao;
import com.joyi.xungeng.dao.UserPatrolDao;
import com.joyi.xungeng.domain.UserPatrol;
import com.joyi.xungeng.service.XunGengService;
import com.joyi.xungeng.util.Constants;
import com.joyi.xungeng.util.StringUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangyong on 2014/10/15.
 * 【修改密码】Activity
 */
public class XiuGaiMiMaActivity extends BaseActivity {

	private EditText oldPwd;
	private EditText newPwd;
	private EditText newPwd2;
    private Handler handler;
	XunGengService xgService = new XunGengService();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiu_gai_mi_ma);

		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText(SystemVariables.USER_NAME);

		oldPwd = (EditText) findViewById(R.id.old_pwd);
		newPwd = (EditText) findViewById(R.id.new_pwd);
		newPwd2 = (EditText) findViewById(R.id.new_pwd2);

		// 安装apk文件
//		handler = new Handler(){
//			@Override
//			public void handleMessage(Message msg) {
//				super.handleMessage(msg);
//				File file = (File) msg.getData().get("file");
//				Log.e("file", file == null ? "null" : file.getAbsolutePath());
//				xgService.openFile(XiuGaiMiMaActivity.this, file);
//			}
//		};

	}


	/**
	 * 修改密码
	 * @param view
	 */
	public void changePassword(View view) {
//		String path = Environment.getExternalStorageDirectory()+"/update/";
//						String fileName = "/storage/sdcard/nfc.apk";
//			xgService.openFile(this, new File(fileName));


		AsyncHttpClient httpClient = new AsyncHttpClient();
//
//		String[] mAllowedContentTypes = new String[] {"application/vnd.android.package-archive"};
//		httpClient.get("http://192.16.8.176:8080/wuye/nfc.apk", new BinaryHttpResponseHandler(mAllowedContentTypes){
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
//				Log.e(TAG, "onSuccess->" + Arrays.toString(binaryData));
//				String path = Environment.getExternalStorageDirectory()+"/update/";
//				String fileName = "wuye.apk";
//				File file = XunGengService.getFileFromBytes(binaryData, path, fileName);
//				Message message = new Message();
//				message.what = 100;
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("file", file);
//				message.setData(bundle);
//				message.setTarget(handler);
//				message.sendToTarget();
//			}

//			@Override
//			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//
//			}
//		});

		PatrolRecordDao prDao = new PatrolRecordDao();
		UserPatrolDao upDao = new UserPatrolDao();
		ShiftRecordDao srDao = new ShiftRecordDao();
		PatrolViewDao pvDao = new PatrolViewDao();
		upDao.deleteAll();
		srDao.deleteAll();
		pvDao.deleteAll();
		prDao.deleteAll();
		if (2 > 1) {
			return;
		}

		String newPassword = newPwd.getText().toString();
		String newPassword2 = newPwd2.getText().toString();
		if (StringUtils.isNullOrEmpty(oldPwd.getText().toString())) {
			showToast("请输入旧密码");
			return;
		}
		if (StringUtils.isNullOrEmpty(newPassword)) {
			showToast("请输入新密码");
			return;
		}
		if (newPassword.length() < Constants.MIN_PASSWORD_LENGTH) {
			showToast("新密码过短");
			return;
		}
		if (!newPassword.equals(newPassword2)) {
			showToast("两次密码输入不一致");
			return;
		}

		RequestParams requestParams = new RequestParams();
		requestParams.put("userId", SystemVariables.user.getId());
		requestParams.put("oldPassword", oldPwd.getText().toString());
		requestParams.put("newPassword", newPassword);
		httpClient.post(this, Constants.CHANGE_PASSWORD_URL, requestParams, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
				try {
					String errorCode = jsonObject.getString("errorCode");
					if (Constants.HTTP_SUCCESS_CODE.equals(errorCode)) {
						showToast("修改成功");
						finish();
					}else{
						showToast("修改失败...");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}