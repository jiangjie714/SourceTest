package com.fufang.bi.util;

import java.util.List;

public class JsonBean {
	//消息状态码
	private Integer state;
	//消息内容
	private String msg;
	//对象数据
	private Object obj;
	//集合数据
	private List<?> list;
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	
}
