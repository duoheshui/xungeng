package com.joyi.xungeng.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 加过盐的MD5，最终结果以hex字符串形式返回，且为小写字母
 * 
 * @author 刘军海
 * @version May 2, 2012 10:35:00 AM
 */
public final class MD5Util {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 盐值
	 */
	private static final String salt = "3!38@8094$B978EC%3&9#F*13FA+2";

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	private static String encodeMd5(String key) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(key.getBytes());
			byte[] bytes = md.digest();
			String result = byteArrayToHexString(bytes);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String GetMD5(String origin) {
		String resultString = salt + "_" + origin;
		resultString = encodeMd5(resultString);
		resultString = origin + resultString + "_" + salt;
		resultString = encodeMd5(resultString);
		return resultString;
	}

	public static String GetMD5(String origin, String salt) {
		String resultString = MD5Util.salt + "_" + origin + "_" + salt;
		resultString = encodeMd5(resultString);
		resultString = origin + "{" + salt + resultString + "}_" + MD5Util.salt;
		resultString = encodeMd5(resultString);
		return resultString;
	}
	
	/**
	 * 对于InputStream，来获取其所对应的md5值
	 * @param in
	 * @return
	 */
	public final static String getInputStreamMd5(final InputStream in){
		byte buffer[] = new byte[1024];
		int len;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
			byte[] b = digest.digest();
			return byteArrayToHexString(b);
		} catch (Exception e) {
			return null;
		}
	}
	
	public final static String getMd5ForBytes(final byte[] bytes){
		try{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte buffer[] = new byte[1024];
			int len;
			ByteArrayInputStream bIn = new ByteArrayInputStream(bytes);
			while ((len = bIn.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			bIn.close();
			return byteArrayToHexString(digest.digest());
		}catch (Exception e) {
			return null;
		}
	}

	public final static String getFileMd5(final File file) {
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
			byte[] b = digest.digest();
			return byteArrayToHexString(b);
		} catch (Exception e) {
			return null;
		}
	}
}
