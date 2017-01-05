package com.fufang.bi.entity;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class AutoPage implements Serializable {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 5700928560191316767L;

	private int records;//总条数

	private int total;//总页数
	
	private int page=1;//当前页数 默认1
	
	private int start;//起始数
	
	private int end;//结束数
	
	private String sidx;//排序列名
	
	private String sord;//排序规则 asc or desc
	
	private String defaut;// 默认排序
	
	
	public String getDefaut() {
		return defaut;
	}

	public void setDefaut(String sidx ,String sord) {
		if (StringUtils.isEmpty(this.sidx)) {
			this.sidx = sidx;
			this.sord = sord;
		}
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	
	
	
}
