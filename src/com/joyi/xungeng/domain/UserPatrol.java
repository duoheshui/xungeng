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

	private int id;
	private String userId;              // 用户ID
	private String lineId;              // 线路ID
	private int sequence;               // 轮次
	private String scheduleTypeId;      // 巡更班次ID
	private Date beginTime;
	private Date endTime;
	private Date beginPhoneTime;
	private Date endPhoneTime;
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

    public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getScheduleTypeId() {
		return scheduleTypeId;
	}

	public void setScheduleTypeId(String scheduleTypeId) {
		this.scheduleTypeId = scheduleTypeId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getBeginPhoneTime() {
		return beginPhoneTime;
	}

	public void setBeginPhoneTime(Date beginPhoneTime) {
		this.beginPhoneTime = beginPhoneTime;
	}

	public Date getEndPhoneTime() {
		return endPhoneTime;
	}

	public void setEndPhoneTime(Date endPhoneTime) {
		this.endPhoneTime = endPhoneTime;
	}
}
