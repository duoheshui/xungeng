package com.joyi.xungeng.domain;

/**
 * Created by zhangyong on 2014/10/28.
 * 路线轮次对应表
 */
public class LuXianLunCi {
	private String userId;
	private String lineId;
	private int lunCi;


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
}
