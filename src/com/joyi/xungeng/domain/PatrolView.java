package com.joyi.xungeng.domain;

import com.joyi.xungeng.SystemVariables;

import java.io.Serializable;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡查打卡】 实体类
 */
public class PatrolView implements Serializable {
	private int id;
	private String nodeId;
	private String userId;
	private String patrolTime;
	private String patrolPhoneTime;
	private String nodeName;
	private String status;
	private String imei = SystemVariables.IMEI;


	public String getPatrolPhoneTime() {
		return patrolPhoneTime;
	}

	public void setPatrolPhoneTime(String patrolPhoneTime) {
		this.patrolPhoneTime = patrolPhoneTime;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public String getPatrolTime() {
		return patrolTime;
	}

	public void setPatrolTime(String patrolTime) {
		this.patrolTime = patrolTime;
	}
}
