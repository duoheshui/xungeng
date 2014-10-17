package com.joyi.xungeng.domain;

import com.joyi.xungeng.SystemVariables;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡查打卡】 实体类
 */
public class PatrolView implements Serializable {
	private int id;
	private String nodeId;
	private String userId;
	private Date patrolTime;
	private String imei = SystemVariables.IMEI;


	public String getImei() {
		return imei;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getPatrolTime() {
		return patrolTime;
	}

	public void setPatrolTime(Date patrolTime) {
		this.patrolTime = patrolTime;
	}
}
