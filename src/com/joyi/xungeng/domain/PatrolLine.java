package com.joyi.xungeng.domain;

import com.joyi.xungeng.activity.XunGengDaKaActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更路线】 实体类
 */
public class PatrolLine implements Serializable {

	private String id;
	private String lineName;
	private String stationId;                           // 岗位ID
	private int frequency;                              // 频次
	private int exception;                              // 偏差(分钟)
	private String beginTime;
	private String endTime;
	private String scheduleId;                          // 班次id
	private List<LineNode> lineNodes = new ArrayList<>();// 路线上的节点


	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public List<LineNode> getLineNodes() {
		return lineNodes;
	}

	public String getName() {
		return lineName;
	}

	public void setName(String lineName) {
		this.lineName = lineName;
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


	@Override
	public String toString() {
		Integer integer = XunGengDaKaActivity.luXianLunCiMap.get(id);

		if (integer == null || integer == 0) {
			integer = 1;
		}
		return lineName+ "[第"+integer+"轮]\n"+beginTime+"~"+endTime;
	}
}
