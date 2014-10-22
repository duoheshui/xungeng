package com.joyi.xungeng.domain;

import com.joyi.xungeng.SystemVariables;

import java.io.Serializable;

/**
 * Created by zhangyong on 2014/10/14.
 * 【交接班记录】实体类
 */
public class ShiftRecord implements Serializable {
	private int id;
	private String userId;
	private String stationId;       // 岗位ID
	private String scheduleTypeId;  // 巡更班次ID
	private String lineId;          // 路线ID
	private String submitTime;      // 交班时间
	private String submitPhoneTime;   // 交班手机时间
	private String receiveTime;     // 接班时间
	private String recivePhoneTime;     // 接班手机时间
	private String imei = SystemVariables.IMEI;

	public String getImei() {
		return imei;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
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

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getSubmitPhoneTime() {
		return submitPhoneTime;
	}

	public void setSubmitPhoneTime(String submitPhoneTime) {
		this.submitPhoneTime = submitPhoneTime;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getRecivePhoneTime() {
		return recivePhoneTime;
	}

	public void setRecivePhoneTime(String recivePhoneTime) {
		this.recivePhoneTime = recivePhoneTime;
	}
}
