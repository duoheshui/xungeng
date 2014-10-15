package com.joyi.xungeng.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更路线】 实体类
 */
public class PatrolLine implements Serializable {

	private String id;
	private String stationId;   // 岗位ID
	private int frequency;      // 频次
	private int offset;         // 偏差(分钟)
	private Date beginTime;
	private Date endTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
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
}
