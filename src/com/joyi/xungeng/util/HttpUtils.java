package com.joyi.xungeng.util;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by zhangyong on 2014/10/14.
 */
public class HttpUtils {

	static String url = "http://192.16.8.176:8080/wuye/login.jsp";

	public static String post(Map<String, String> params) {
		String TAG = "HttpUtils";
		HttpClient httpClient = new DefaultHttpClient();
		//和GET方式一样，先将参数放入List
		LinkedList paramList = new LinkedList<BasicNameValuePair>();
		Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		try {
			HttpPost postMethod = new HttpPost(url);
			postMethod.setEntity(new UrlEncodedFormEntity(paramList, "utf-8")); //将参数填入POST Entity中

			HttpResponse response = httpClient.execute(postMethod); //执行POST方法
			Log.i(TAG, "resCode = " + response.getStatusLine().getStatusCode()); //获取响应码
			String responseStr = EntityUtils.toString(response.getEntity(), "utf-8");
			Log.i(TAG, "result = " + responseStr); //获取响应内容
			return responseStr;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
