package com.joyi.xungeng.domain;

/**
 * Created by zhangyong on 2014/10/28.
 * 交接班状态表
 */
public class JiaoJieBanStatus {
	public static final int TYPE_JIAO_BAN = 0;  // 交班
	public static final int TYPE_JIE_BAN = 1;   // 接班

	public JiaoJieBanStatus() { }

	public JiaoJieBanStatus(String userId, String time, int type) {
		this.userId = userId;
		this.time = time;
		this.type = type;
	}

	private String userId;
	private int type;                           // 交接班类型, TYPE_JIAO_BAN:交班, TYPE_JIE_BAN:接班
	private String time;                        // 时间


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
