package com.joyi.xungeng.domain;

/**
 * Created by zhangyong on 2014/10/28.
 * 路线<->第几轮<->该轮的ID号
 */
public class LuXianLunCiId {
	private String userId;
	private String lineId;
	private int lunCi;
	private long lunId;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public int getLunCi() {
		return lunCi;
	}

	public void setLunCi(int lunCi) {
		this.lunCi = lunCi;
	}

	public long getLunId() {
		return lunId;
	}

	public void setLunId(long lunId) {
		this.lunId = lunId;
	}
}
