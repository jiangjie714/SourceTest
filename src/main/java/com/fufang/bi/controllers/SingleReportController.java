package com.fufang.bi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fufang.bi.apiutil.FFApiResponse;
import com.fufang.bi.service.single.SingleService;

@Api(value = "SingleReport-api", description = "单体报表-API")
@RestController
//@RequestMapping("/single/reports")
public class SingleReportController {
	private static Logger logger = LoggerFactory.getLogger(SingleReportController.class);
//	
//	@Autowired
//	private SingleService  singleService ;
//	
//	@RequestMapping("/singleDemo/{pharmacyId}")
//	public FFApiResponse<Map<String,Object>> SingleDemo(@PathVariable("pharmacyId") Integer pharmacyId ){
//		Map<String,Object> map = new HashMap<String,Object>();
//		List<BUSINESS_WEEK> list= singleService.QueryList(pharmacyId);
//		map.put("data", list);
//		return FFApiResponse.success(map);
//	}
	
}
