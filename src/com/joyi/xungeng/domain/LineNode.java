package com.joyi.xungeng.domain;

import java.io.Serializable;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更路线节点】 实体类
 */
public class LineNode implements Serializable {

	private String id;
	private String lineId;
	private String nodeName;
	private String nfcCode;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNfcCode() {
		return nfcCode;
	}

	public void setNfcCode(String nfcCode) {
		this.nfcCode = nfcCode;
	}
}
