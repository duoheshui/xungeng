package com.joyi.xungeng.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/28.
 * 班次,
 */
public class Schedule {

	private String scheduleId;                                  // 班次id
	private String stationId;
	private int frequency;                                      // 频次 [分钟]
	private int exception;                                      // 偏差 [分钟]
	private String beginTime;
	private String endTime;
	private int shouldPatrolTimes;                              // 需要巡更的轮次,根据开始结束时间及频次动态计算的
	private List<PatrolLine> patrolLines = new ArrayList<>();   // 巡更路线




	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
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

	public int getShouldPatrolTimes() {
		return shouldPatrolTimes;
	}

	public void setShouldPatrolTimes(int shouldPatrolTimes) {
		this.shouldPatrolTimes = shouldPatrolTimes;
	}

	public List<PatrolLine> getPatrolLines() {
		return patrolLines;
	}

	public void setPatrolLines(List<PatrolLine> patrolLines) {
		this.patrolLines = patrolLines;
	}
}
