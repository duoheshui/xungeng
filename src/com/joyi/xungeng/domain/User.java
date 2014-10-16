package com.joyi.xungeng.domain;

import java.io.Serializable;

/**
 * Created by zhangyong on 2014/10/14.
 * 用户表
 */
public class User implements Serializable {
	private String id;
	private String userName;
	private String password;
	private String name;                    // 用户名拼音
	private String patrolStationTypeId;     // 巡更岗位性质




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatrolStationTypeId() {
		return patrolStationTypeId;
	}

	public void setPatrolStationTypeId(String patrolStationTypeId) {
		this.patrolStationTypeId = patrolStationTypeId;
	}
}
