package com.joyi.xungeng.domain;

import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.LuXianLunCiDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更路线】 实体类
 */
public class PatrolLine implements Serializable {
	private transient LuXianLunCiDao llDao = new LuXianLunCiDao();
	private String id;
	private String lineName;
	private String stationId;                           // 岗位ID
	private int frequency;                              // 频次
	private int exception;                              // 偏差(分钟)
	private String beginTime;
	private String endTime;
	private String scheduleId;                          // 班次id
	private List<LineNode> lineNodes = new ArrayList<>();// 路线上的节点
	private String name;                                        // 班次名称
	private String scheduleTypeName;                            // '早班｜中班｜晚班'
	private int shouldPatrolTimes;                          // 根据开始结束时间及频次动态计算的


	public int getShouldPatrolTimes() {
		return shouldPatrolTimes;
	}

	public void setShouldPatrolTimes(int shouldPatrolTimes) {
		this.shouldPatrolTimes = shouldPatrolTimes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScheduleTypeName() {
		return scheduleTypeName;
	}

	public void setScheduleTypeName(String scheduleTypeName) {
		this.scheduleTypeName = scheduleTypeName;
	}


	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public List<LineNode> getLineNodes() {
		return lineNodes;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
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
		int lunCi = llDao.getLunCi(SystemVariables.USER_ID, id);
		lunCi = lunCi<0 ? 1 : lunCi;
		return name+"["+scheduleTypeName+"]\n"+lineName+ "\t\t[第"+lunCi+"轮]\n"+beginTime+"~"+endTime;
	}
}
