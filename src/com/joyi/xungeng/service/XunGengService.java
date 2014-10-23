package com.joyi.xungeng.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.BidiFormatter;
import android.util.Log;
import android.widget.Toast;
import com.joyi.xungeng.domain.LineNode;
import com.joyi.xungeng.domain.PatrolRecord;

import javax.crypto.ShortBufferException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 2014/10/20 0020.
 */
public class XunGengService {
    //下载apk程序代码
    public File downLoadFile(Context context, String httpUrl) {
        final String fileName = "new_version.apk";
        File tmpFile = new File("/sdcard/update");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        final File file = new File("/sdcard/update/" + fileName);

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[256];
            conn.connect();
            double count = 0;
            if (conn.getResponseCode() >= 400) {
                Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
            } else {
                while (count <= 100) {
                    if (is != null) {
                        int numRead = is.read(buf);
                        if (numRead <= 0) {
                            break;
                        } else {
                            fos.write(buf, 0, numRead);
                        }
                    } else {
                        break;
                    }
                }
            }
            conn.disconnect();
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * 打开APK程序代码
     * @param file
     */
    public void openFile(Context context, File file) {
	    Intent intent = new Intent();
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.setAction(android.content.Intent.ACTION_VIEW);
	    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
	    context.startActivity(intent);
    }

	/**
	 * 把字节数组保存为一个文件
	 *
	 * @param b
	 * @param outputFile
	 * @return
	 */
	public static File getFileFromBytes(byte[] b, String outputFile, String fileName) {
		File ret = null;
		BufferedOutputStream stream = null;
		try {
			File file = new File(outputFile);
			if (!file.exists()) {
				file.mkdirs();
			}
			ret = new File(outputFile+fileName);
			FileOutputStream fstream = new FileOutputStream(ret);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
			stream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	/**
	 * 字节数组转16进制字符串
	 * @param src
	 * @return
	 */
    public static String byteArray2HexString(byte[] src) {
	    StringBuilder stringBuilder = new StringBuilder("0X");
	    if (src == null || src.length <= 0) {
		    return null;
	    }

	    char[] buffer = new char[2];
	    for (int i = 0; i < src.length; i++) {
		    buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
		    buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
		    System.out.println(buffer);
		    stringBuilder.append(buffer);
	    }

	    return stringBuilder.toString().toUpperCase();
    }

	/**
	 * 判断网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断集合是否为空
	 * @param list
	 * @return
	 */
	public static boolean isNullList(List<?> list) {
		return list==null || list.size()<=0;
	}


	/**
	 * 获取漏巡情况
	 * @param shouldPatrols 需要打卡的节点
	 * @param records   该轮次的打卡记录
	 * @param sequence  轮次
	 * @param buffer    结果保存在此StringBuffer中
	 * @return
	 */
	public static StringBuffer getLouXunList(List<LineNode> shouldPatrols, List<PatrolRecord> records, int sequence, StringBuffer buffer) {

		if (shouldPatrols==null || shouldPatrols.size()==0) {
			return buffer;
		}
		Map<String, PatrolRecord> nodeIdRecordMap = new HashMap<>();
		for (PatrolRecord record : records) {
			nodeIdRecordMap.put(record.getNodeId(), record);
		}
		for (LineNode node : shouldPatrols) {
			String nodeid = node.getId();
			if (nodeIdRecordMap.get(nodeid) == null) {
				buffer.append("第" + sequence + "轮" + node.getNodeName()+"\n");
			}
		}
		return buffer;
	}

}
