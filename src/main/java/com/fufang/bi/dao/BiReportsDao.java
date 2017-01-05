/**
 *
 */

package com.fufang.bi.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author jiakai.chen
 *
 */
@Repository
public class BiReportsDao {

	public static final String REGION_STATE = "state";
	public static final String REGION_CITY = "city";
	public static final String CIRCLE_WEEK = "week";
	public static final String CIRCLE_MONTH = "month";

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate db;
	
	@Resource(name = "jdbcTemplateSqlserver")
	private JdbcTemplate dbSqlserver;

	public List<Map<String, Object>> getMapData(String region, String circle) {

		StringBuilder sql = new StringBuilder();
		if (region.equals(REGION_STATE)) {
			sql.append(
					"select sum(累计注册药店数量) as 累计注册药店数量,sum(新增注册药店数量) as 新增注册药店数量,sum(新增使用药店数量) as 新增使用药店数量,sum(活跃药店数量) as 活跃药店数量,sum(上传单据药店数) as 上传单据药店数,sum(销售总额) as 销售总额,sum(销售单据量) as 销售单据量,");
			sql.append("省,");
		} else {
			sql.append("select 累计注册药店数量,新增注册药店数量,新增使用药店数量,活跃药店数量,上传单据药店数,销售总额,销售单据量,");
			sql.append("市,");
		}
		sql.append("日期 from report_region_");
		sql.append(circle);
		sql.append(" where 日期 = ");
		if (circle.equals(CIRCLE_MONTH)) {
			sql.append("LAST_DAY(date_add(NOW(), interval -1 month))");
		} else {
			sql.append(
					"subdate(date_add(curdate(), interval -7 day),date_format(date_add(curdate(), interval -7 day),'%w')-7)");
		}
		if (region.equals(REGION_STATE)) {
			sql.append(" group by 省,日期");
		}

		return db.queryForList(sql.toString());
	}

	public List<Map<String, Object>> getLineData1(String circle) {

		StringBuilder sql = new StringBuilder("select ");
		if (circle.equals(CIRCLE_WEEK)) {
			sql.append("累积注册药店数量");
		} else {
			sql.append("累计注册药店数量");
		}
		sql.append(",新增注册药店数量,新增使用药店数量,活跃药店数量,上传单据药店数,日期 from report_country_");
		sql.append(circle);
		sql.append(" where 日期 ");
		if (circle.equals(CIRCLE_MONTH)) {
			sql.append("BETWEEN date_add(NOW(), interval -12 MONTH) and  date_add(NOW(), interval -1 day)");
		} else {
			sql.append(
					"BETWEEN date_add(NOW(), interval -336 day) and  date_add(NOW(), interval -1 day)");
		}

		return db.queryForList(sql.toString());
	}

	public List<Map<String, Object>> getLineData2(String circle) {

		StringBuilder sql = new StringBuilder("SELECT 销售总额,销售单据量,日期 from report_country_");
		sql.append(circle);
		sql.append(" where 日期 ");
		if (circle.equals(CIRCLE_MONTH)) {
			sql.append("BETWEEN date_add(NOW(), interval -12 MONTH) and  date_add(NOW(), interval -1 day)");
		} else {
			sql.append(
					"BETWEEN date_add(NOW(), interval -336 day) and  date_add(NOW(), interval -1 day)");
		}

		return db.queryForList(sql.toString());
	}
	
	public List<Map<String, Object>> getSalesByMonth() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(new Date());
		String endDate = format.format(cal.getTime());
		
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
		String startDate = format.format(cal.getTime());
		
		int filterTotal = 300000;
		String filterSql = 
				"select id, total "
				+ "	from ("
				+ "		select op.id, rpp.total "
				+ "		from ods_pharmacy op join ("
				+ "			SELECT SUM(total) total,pharmacyId"
				+ "			FROM report_pharmacy_period rpp"
				+ "			where day_time >= '" + startDate + "' and day_time <= '" + endDate + "' "
				+ "			group by pharmacyId"
				+ "		) rpp ON op.provinceId = 110000 AND rpp.pharmacyid = op.id"
				+ ") a "
				+ "where total > " + filterTotal;
		
		List<Map<String, Object>> filterList = dbSqlserver.queryForList(filterSql.toString());
		Set<String> pharmacyIdSet = new HashSet<String>();
		for(Map<String, Object> m : filterList) {
			pharmacyIdSet.add(m.get("id").toString());
		}
		
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		startDate = format.format(cal.getTime());
		
//		startDate = "2015-09-01";
//		endDate = "2015-09-30";
		String sql = 
				"select op.id, op.name, op.registAddress, rpp.total"
				+ "	from ods_pharmacy op join ("
				+ "		select sum(total) total, pharmacyId"
				+ "		from report_pharmacy_period rpp "
				+ "		where day_time >= '" + startDate + "' and day_time <= '" + endDate + "' "
				+ "		group by pharmacyId"
				+ ") rpp on op.provinceId = 110000 and rpp.pharmacyid = op.id";
		
		List<Map<String, Object>> list = dbSqlserver.queryForList(sql.toString());
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>(); 
		for(Map<String, Object> m : list) {
			if(pharmacyIdSet.contains(m.get("id").toString()) && m.get("registAddress").toString().contains("北京")) {
				resultList.add(m);
			}
		}
		
		return resultList;
	}

}
