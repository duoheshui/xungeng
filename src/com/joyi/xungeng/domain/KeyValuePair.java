package com.joyi.xungeng.domain;

/**
 * Created by zhangyong on 2014/10/16.
 * 表示所有<键, 值>结构的数据,
 * 目前用于表示
 * <li>岗位: id, name</li>
 * <li>班次: id, name</li>
 *
 */
public class KeyValuePair {
	public KeyValuePair() {

	}
	public KeyValuePair(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	private String key;
	private Object value;



	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
