package com.joyi.xungeng.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡查打卡】 实体类
 */
public class PatrolView implements Serializable {
	private int id;
	private String lineid;
	private String uid;
	private Date patrolTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLineid() {
		return lineid;
	}

	public void setLineid(String lineid) {
		this.lineid = lineid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getPatrolTime() {
		return patrolTime;
	}

	public void setPatrolTime(Date patrolTime) {
		this.patrolTime = patrolTime;
	}
}
