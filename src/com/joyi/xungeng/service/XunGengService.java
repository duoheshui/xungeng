package com.joyi.xungeng.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public static String byteArray2HexString(byte[] arr) {
        String str = null;


        return str;
    }
}
