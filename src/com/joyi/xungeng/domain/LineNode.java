package com.joyi.xungeng.domain;

import java.io.Serializable;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更路线节点】 实体类
 */
public class LineNode implements Serializable {

	private String id;
	private String lineid;
	private String nodename;
	private String nfcCode;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLineid() {
		return lineid;
	}

	public void setLineid(String lineid) {
		this.lineid = lineid;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getNfcCode() {
		return nfcCode;
	}

	public void setNfcCode(String nfcCode) {
		this.nfcCode = nfcCode;
	}
}
