package com.joyi.xungeng.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangyong on 2014/10/14.
 * 【交接班记录】实体类
 */
public class ShiftRecord implements Serializable {
	private String uid;
	private String stationId;       // 岗位ID
	private String scheduleTypeId;  // 巡更班次ID
	private Date submitTime;      // 交班时间
	private Date submitAppTime;   // 交班手机时间
	private Date receiveTime;     // 接班时间
	private Date reciveAppTime;     // 接班手机时间



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

	public Date getSubmitAppTime() {
		return submitAppTime;
	}

	public void setSubmitAppTime(Date submitAppTime) {
		this.submitAppTime = submitAppTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getReciveAppTime() {
		return reciveAppTime;
	}

	public void setReciveAppTime(Date reciveAppTime) {
		this.reciveAppTime = reciveAppTime;
	}
}
