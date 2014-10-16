package com.joyi.xungeng.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangyong on 2014/10/14.
 * 【交接班记录】实体类
 */
public class ShiftRecord implements Serializable {
	private int id;
	private String uid;
	private String stationId;       // 岗位ID
	private String scheduleTypeId;  // 巡更班次ID
	private Date submitTime;      // 交班时间
	private Date submitPhoneTime;   // 交班手机时间
	private Date receiveTime;     // 接班时间
	private Date recivePhoneTime;     // 接班手机时间


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

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getScheduleTypeId() {
		return scheduleTypeId;
	}

	public void setScheduleTypeId(String scheduleTypeId) {
		this.scheduleTypeId = scheduleTypeId;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getSubmitPhoneTime() {
		return submitPhoneTime;
	}

	public void setSubmitPhoneTime(Date submitPhoneTime) {
		this.submitPhoneTime = submitPhoneTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getRecivePhoneTime() {
		return recivePhoneTime;
	}

	public void setRecivePhoneTime(Date recivePhoneTime) {
		this.recivePhoneTime = recivePhoneTime;
	}
}
