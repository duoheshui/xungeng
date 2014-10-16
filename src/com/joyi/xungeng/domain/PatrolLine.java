package com.joyi.xungeng.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更路线】 实体类
 */
public class PatrolLine implements Serializable {

	private String id;
	private String stationId;           // 岗位ID
	private int frequency;              // 频次
	private int exception;              // 偏差(分钟)
	private Date beginTime;
	private Date endTime;
	private List<LineNode> lineNodes;   // 路线上的节点


	public List<LineNode> getLineNodes() {
		return lineNodes;
	}

	public void setLineNodes(List<LineNode> lineNodes) {
		this.lineNodes = lineNodes;
	}

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

	public int getException() {
		return exception;
	}

	public void setException(int exception) {
		this.exception = exception;
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
