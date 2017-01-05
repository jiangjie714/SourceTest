package com.fufang.bi.util;

import java.util.List;
import java.util.Map;

public class JsonBeans {

	//消息状态码
		private Integer state;
		//消息内容
		private String msg;
		//List 集合
		private List list;
		
		
		//Map 形式
		private Map<Object ,Object> map;
		
		
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
		public List getList() {
			return list;
		}
		public void setList(List list) {
			this.list = list;
		}
		public Map<Object, Object> getMap() {
			return map;
		}
		public void setMap(Map<Object, Object> map) {
			this.map = map;
		}
		
		
}
