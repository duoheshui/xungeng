package com.joyi.xungeng.domain;

import com.joyi.xungeng.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangyong on 2014/10/14.
 * 【巡更打卡记录】实体类
 */
public class PatrolRecord implements Serializable {

	private int id;
	private String nodeId;
	private String userPatrolId;
	private String patrolTime;
	private Date patrolPhoneTime;
	private String error;


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

	public String getUserPatrolId() {
		return userPatrolId;
	}

	public void setUserPatrolId(String userPatrolId) {
		this.userPatrolId = userPatrolId;
	}

	public String getPatrolTime() {
		return patrolTime;
	}

	public void setPatrolTime(String patrolTime) {
		this.patrolTime = patrolTime;
	}

	public String getPatrolPhoneTime() {
		return DateUtil.getHumanReadStr(patrolPhoneTime);
	}

	public void setPatrolPhoneTime(Date patrolPhoneTime) {
		this.patrolPhoneTime = patrolPhoneTime;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
