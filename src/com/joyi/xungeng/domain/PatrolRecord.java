package com.joyi.xungeng.domain;

import com.joyi.xungeng.SystemVariables;

import java.io.Serializable;

/**
 * Created by zhangyong on 2014/10/14.
 * 【巡更打卡记录】实体类
 */
public class PatrolRecord implements Serializable {

	private int id;
	private String nodeId;
	private String userPatrolId;
	private String patrolTime;
	private String patrolPhoneTime;
	private String error = "";
    private int sequence;              // 轮次
    private String lineId;
	private String imei = SystemVariables.IMEI;
	private int sync;                   // 是否已同步到服务器
	private String userId;              // 用户ID
	private String tuserId;             // 替班用户ID


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTuserId() {
		return tuserId;
	}

	public void setTuserId(String tuserId) {
		this.tuserId = tuserId;
	}

	public int getSync() {
		return sync;
	}

	public void setSync(int sync) {
		this.sync = sync;
	}



	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
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
		return patrolPhoneTime;
	}

	public void setPatrolPhoneTime(String patrolPhoneTime) {
		this.patrolPhoneTime = patrolPhoneTime;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
