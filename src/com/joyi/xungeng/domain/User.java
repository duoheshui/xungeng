package com.joyi.xungeng.domain;

import java.io.Serializable;

/**
 * Created by zhangyong on 2014/10/14.
 * 用户表
 */
public class User implements Serializable {
	private String id;
	private String loginName;               // 登录名
	private String userName;
	private String password;
	private String pyShort;                    // 用户名拼音
	private String patrolStationTypeId;     // 巡更岗位性质
	private boolean hasPatrolViewPrivilege;


	public boolean isHasPatrolViewPrivilege() {
		return hasPatrolViewPrivilege;
	}

	public void setHasPatrolViewPrivilege(boolean hasPatrolViewPrivilege) {
		this.hasPatrolViewPrivilege = hasPatrolViewPrivilege;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

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

	public String getPyShort() {
		return pyShort;
	}

	public void setPyShort(String pyShort) {
		this.pyShort = pyShort;
	}

	public String getPatrolStationTypeId() {
		return patrolStationTypeId;
	}

	public void setPatrolStationTypeId(String patrolStationTypeId) {
		this.patrolStationTypeId = patrolStationTypeId;
	}
}
