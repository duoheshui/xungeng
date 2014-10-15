package com.joyi.xungeng.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangyong on 2014/10/15.
 * 【用户巡更】 实体类
 */
public class UserPatrol implements Serializable {

	private int id;
	private String  uid;            // 用户ID
	private String lineid;          // 线路ID
	private int sequence;              // 轮次
	private String scheduleTypeId;  // 巡更班次ID
	private Date beginTime;
	private Date endTime;
	private Date beginPhoneTime;
	private Date endPhoneTime;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLineid() {
		return lineid;
	}

	public void setLineid(String lineid) {
		this.lineid = lineid;
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
