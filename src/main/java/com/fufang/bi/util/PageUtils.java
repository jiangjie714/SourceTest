package com.fufang.bi.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.fufang.bi.entity.AutoPage;
import com.fufang.bi.jqGrid.JqGridParams;

public class PageUtils {
	public static Map<String, Object> getModelParams(Object obj)
			throws Exception {
		Map<String, Object> paramMap = PropertyUtils.describe(obj);
		return paramMap;
	}
	
	/**
	 *  传入查询数据的数量 records 、JqGridParams对象 、sidx 、sord 默认排序 设置默认后 JqGrid参数排序失效
	 * @param records
	 * @param params
	 * @param sidx
	 * @param sord
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Map<String, Object> getPagedata(int records,
			JqGridParams params ,String sidx,String sord ) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Integer rows =params.getRows();if(rows==null){rows=10;}//默认一页10条
		Integer pages =params.getPage();if(pages==null){pages=1;}//当前页
		// 总页数
		int total = records / rows+ (records % rows == 0 ? 0 : 1);
		// 启始数
		int start = (params.getPage() - 1) *rows;//起始数 0 
		//结束数
		int end = params.getPage() * rows;//结束数 10  用  0< a <=10 
		
		AutoPage page = new AutoPage();
		page.setRecords(records);
		page.setTotal(total);
		
		page.setStart(start);
		page.setEnd(end);
		page.setPage(pages);
		if(sidx!=null && !sidx.equals("")){
			page.setDefaut(sidx, sord);
		}else{
			page.setSidx(params.getSidx());
			page.setSord(params.getSord());
		}
		
		Map<String, Object> paramMap = PropertyUtils.describe(page);
		return paramMap;
	}
}
