package com.joyi.xungeng.domain;

import com.joyi.xungeng.SystemVariables;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【用户巡更】 实体类
 */
public class UserPatrol implements Serializable {

	private long id;
	private String userId;              // 用户ID
	private String lineId;              // 线路ID
	private int sequence;               // 轮次
	private String scheduleId;          // 巡更班次ID
	private String beginTime;
	private String endTime;
	private String beginPhoneTime;
	private String endPhoneTime;
	private String imei = SystemVariables.IMEI;


	public String getImei() {
		return imei;
	}

	private List<PatrolRecord> patrolRecords = new ArrayList<>();


    public List<PatrolRecord> getPatrolRecords() {
        return patrolRecords;
    }

    public void setPatrolRecords(List<PatrolRecord> patrolRecords) {
        this.patrolRecords = patrolRecords;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBeginPhoneTime() {
		return beginPhoneTime;
	}

	public void setBeginPhoneTime(String beginPhoneTime) {
		this.beginPhoneTime = beginPhoneTime;
	}

	public String getEndPhoneTime() {
		return endPhoneTime;
	}

	public void setEndPhoneTime(String endPhoneTime) {
		this.endPhoneTime = endPhoneTime;
	}
}
