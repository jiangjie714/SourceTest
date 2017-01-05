/**
 *
 */

package com.fufang.bi.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fufang.bi.dao.BiReportsDao;

/**
 * @author jiakai.chen
 *
 */
@RestController
@RequestMapping("/api/reports")
public class BiReportsController {

	@Autowired
	private BiReportsDao dao;

	@RequestMapping("/mapdata")
	public List<Map<String, Object>> getMapData(@RequestParam String region, @RequestParam String circle,
			HttpServletResponse response) {

		if ((!region.equals(BiReportsDao.REGION_STATE) && !region.equals(BiReportsDao.REGION_CITY))
				|| (!circle.equals(BiReportsDao.CIRCLE_MONTH) && !circle.equals(BiReportsDao.CIRCLE_WEEK))) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		return dao.getMapData(region, circle);
	}

	@RequestMapping("/linedata1")
	public List<Map<String, Object>> getLineData1(@RequestParam String circle, HttpServletResponse response) {

		if (!circle.equals(BiReportsDao.CIRCLE_MONTH) && !circle.equals(BiReportsDao.CIRCLE_WEEK)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		return dao.getLineData1(circle);
	}

	@RequestMapping("/linedata2")
	public List<Map<String, Object>> getLineData2(@RequestParam String circle, HttpServletResponse response) {

		if (!circle.equals(BiReportsDao.CIRCLE_MONTH) && !circle.equals(BiReportsDao.CIRCLE_WEEK)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		return dao.getLineData2(circle);
	}
	
	@RequestMapping("/salesForMap")
	public List<Map<String, Object>> getSalesForMap() {
		return dao.getSalesByMonth();
	}
}
