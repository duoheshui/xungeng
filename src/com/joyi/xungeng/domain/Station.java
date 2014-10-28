package com.joyi.xungeng.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/16.
 * 【岗位】
 */
public class Station {

	public Station() {

	}

	public Station(String id, String name) {
		this.id = id;
		this.name = name;
	}
	private String id;
	private String name;
	private List<KeyValuePair> lines = new ArrayList<KeyValuePair>();
	private List<Schedule> schedules = new ArrayList<>();   // 班次


	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<KeyValuePair> getLines() {
		return lines;
	}

	public void setLines(List<KeyValuePair> lines) {
		this.lines = lines;
	}

	@Override
	public String toString() {
		return name;
	}
}
