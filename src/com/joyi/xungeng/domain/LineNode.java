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
		if (nfcCode != null) {
			this.nfcCode = nfcCode.toUpperCase();
			return;
		}
		this.nfcCode = nfcCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof LineNode)) {
			return false;
		}

		LineNode lineNode = (LineNode) o;

		if (!id.equals(lineNode.id)) {
			return false;
		}
		if (!lineId.equals(lineNode.lineId)) {
			return false;
		}
		if (!nfcCode.equals(lineNode.nfcCode)) {
			return false;
		}
		if (!nodeName.equals(lineNode.nodeName)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + lineId.hashCode();
		result = 31 * result + nodeName.hashCode();
		result = 31 * result + nfcCode.hashCode();
		return result;
	}
}
