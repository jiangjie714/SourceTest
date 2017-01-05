package com.fufang.bi.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.fufang.bi.apiutil.BaseController;
import com.fufang.bi.apiutil.FFApiResponse;
import com.fufang.bi.entity.ChainPharmacy;
import com.fufang.bi.entity.StorageDetail;
import com.fufang.bi.entity.StorageMilde;
import com.fufang.bi.entity.StorageTotal;
import com.fufang.bi.jqGrid.JqGridReturnedData;
import com.fufang.bi.jqGrid.JqGridUtils;
import com.fufang.bi.service.chain.ChainService;
import com.fufang.bi.util.Converter;
import com.fufang.bi.util.PageUtils;
import com.fufang.yd.entity.User;

@Api(value = "ChainReport-api", description = "连锁报表-API")
@RestController
@RequestMapping(value = "/chain/reports")
public class ChainReportController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(ChainReportController.class);

	@Autowired
	@Qualifier("chainServiceImpl")
	private ChainService chainService;

	private static final String CYCLEPARAMJSON = "{\"page\":\"1\",\"rows\":\"10,\"sidx\":\"id\",\"sord\":\"asc\"}";
	protected static final String RETURNMESSAGE = "返回结果 -- code:000000(成功),code:100000(失败)";


	@ApiOperation(value = "由菜单进入经销存台账主界面", notes = "由菜单进入经销存台账主界面")
	@ApiResponses(value = { @ApiResponse(code = 200, message = RETURNMESSAGE) })
	@RequestMapping(value = "/intoIndex", method = RequestMethod.GET)
	@ResponseBody
	public FFApiResponse<Map> intoIndex(HttpServletRequest request) {
		String msg = "";
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			logger.error("/jxctzh/intoIndex user==null error");
			msg="error 未获取到登录信息";
			return error(msg);
		}
		int id = user.getPharmacyId();//200010
		Integer tmp=null;
		
				//if( user.getPharmacyId()!=tmp){id=user.getPharmacyId();}
		Map map = new HashMap();
		map.put("pharmacyid", id);
		ChainPharmacy cph = chainService.findCpharmacy(id);
		if (cph != null) {
			if (cph.getParentid() == user.getPharmacyId()) {
				logger.debug("总店");
				map.put("select", 3);
			} else {
				logger.debug("分店或者独立经营");
				map.put("select", 2);
			}
		} else {
			msg = "请您一天后再查询数据！";
		}
		return success(map, msg);
	}

	private static final String queryByOrganization = "{\"id\":200010,\"start_time\": \"2016-08-20\" ,\"end_time\": \"2016-11-23\" }";

	@ApiOperation(value = " 废弃  --由菜单进入经销存台账主界面", notes = "废弃  -- 由菜单进入经销存台账主界面")
	@ApiResponses(value = { @ApiResponse(code = 200, message = RETURNMESSAGE) })
	@RequestMapping(value = "/queryByOrganization", method = RequestMethod.POST)
	@ResponseBody
	public FFApiResponse<Map> queryByOrganization(
			HttpServletRequest request,
			@ApiParam(value = CYCLEPARAMJSON) @ModelAttribute com.fufang.bi.jqGrid.JqGridParams params,
			@ApiParam(required = false, value = queryByOrganization) @RequestParam String query) {
		chainService.findListByPage(null);
		User user = (User) request.getSession().getAttribute("user");
		String role = "";
		role = (String) request.getSession().getAttribute("mark");
		Map datamap = new HashMap();
		Map map = new HashMap();

		String msg = "";
		try {
			JSONObject object = JSONObject.fromObject(query);
			map = (Map) object;
			map.put("mark", role);

			chainService.findListCpharmacy(map, params);
			/*
			 * //解析jqGrid的参数 JqGridParams params = JqGridUtils.send(request);
			 */
			// 总行数
			// int records=chainService.findListCpharmacyCount(map);
			// 总页数
			// int total = records / params.getRows() + (records %
			// params.getRows() == 0 ? 0 : 1);
			// 启始数
			// int start = (params.getPage() - 1) * params.getRows();
			// 排序规则

			// PageInfo info = new PageInfo(chainService.findListCpharmacy(map,
			// params));

			// datamap.put("list", info.getList());
			// Long record = info.getTotal();
			JqGridReturnedData returnedData = new JqGridReturnedData();
			/*
			 * returnedData.setPage(params.getPage());
			 * returnedData.setRecords(record.intValue());
			 * returnedData.setTotal(params.getTotal(record.intValue()));
			 * returnedData.setRows(info.getList());
			 */
			JqGridUtils.returnData(datamap, returnedData);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return error("sss");
		}
		return success(datamap, msg);
	}
	
	private static final String excels = "{\"id\":200010,\"start_time\": \"2015-08-20\" ,\"end_time\": \"2016-11-23\",\"select\":\"1\",\"way\":\"0\" }";

	@ApiOperation(value = "导出Excel", notes = "导出Excel")
	@ApiResponses(value = { @ApiResponse(code = 200, message = RETURNMESSAGE) })
	@RequestMapping(value = "/jxctzh/downExcel" , method = RequestMethod.POST)//, method = RequestMethod.POST
	public void downExcel(HttpServletRequest request,HttpServletResponse response,
			@ApiParam(required = false, value = excels) @RequestParam String query){
		String path = request.getSession().getServletContext().getRealPath("/");
	try {
		User user = (User) request.getSession().getAttribute("user");
		String role = "";
		role = (String) request.getSession().getAttribute("mark");
		Integer chaintype= user.getChainType();
		logger.debug("chaintype:"+chaintype);

		String data = request.getParameter("query");
		System.out.println("query:"+data);
		JSONObject object = JSONObject.fromObject(data);
		Map<String, Comparable> querymap = new HashMap<String, Comparable>();
		querymap = (Map) object;
		Integer select=(Integer)querymap.get("select");//判断读取哪个模板
		String way="";
		if(null!=querymap.get("way")){
			 way=(String)querymap.get("way").toString().trim();
		}else{
			return ;
		}
		Integer type=(Integer)querymap.get("type");
		String sourcefilePath = path + "/"+ "down/taizhang"+select+".xls";
		List<?> list  = new ArrayList();
		querymap.put("mark", role);
		if(type==0){
			querymap.put("amount", "t.notaxAmount");//无税
		}else{
			querymap.put("amount", "t.amount");
		}
		InputStream inputStream;
		inputStream = new FileInputStream(sourcefilePath);
		String fileName = "tmp.xls";
		
		Object obj = null;
		
		if(select==1){
			fileName="进销存台账_按组织机构.xls";
			Integer	id =user.getPharmacyId();
			querymap.put("id", id);
			list = chainService.findAllCpharmacyExcel(querymap);
			StorageTotal sumData = new StorageTotal();
			sumData = chainService.findAllCpharmacySum(querymap);
			obj=sumData;
		}else if(select==2){
			fileName ="进销存台账_按商品.xls";
			if(way.equals("0")){//点击进入
				Integer id = (Integer)querymap.get("id");
				if(id==null){
				logger.error("/jxctzh/downExcel id==null error");
				return ;
				}
				querymap.put("needs", 0);
				querymap.put("parent", 8);
			}else if(way.equals("1")) {//直接查询
				Integer	id=null ;
				if(user!=null && (querymap.get("id")==null || "".equals(querymap.get("id")))){
					id=user.getPharmacyId();
					querymap.put("id", id);
				}
				String pharmacyCode="";String name="";String pinyin="";
				pharmacyCode= (String )querymap.get("pharmacyCode");
				name=(String)querymap.get("name");
				pinyin=(String)querymap.get("pinyin");
				if((pharmacyCode==null && name==null && pinyin==null)|| ("".equals(pharmacyCode)&&"".equals(name) &&"".equals(pinyin))){
					querymap.put("parent", 9);//代表 是按照查询条件进入的
					if(0==chaintype){
						querymap.put("needs", 2);
					}else{
						querymap.put("needs", 3);
					}
				}else{
					querymap.put("needs", 1);//可以确定 旗下唯一 一家药店
				}
			}
			StorageMilde sumData = new StorageMilde();
			list = chainService.findAllCpharmacyMildeExcel(querymap);
			sumData =chainService.findAllCpharmacyMildeSum(querymap);
			obj=sumData;
		}else if(select==3){
			fileName ="进销存台账_按出入库.xls";
			if(way.equals("0")){
				querymap.put("needs", 0);
				querymap.put("parent", 8);
			}else if(way.equals("1")){
				Integer id=null;
				if(user!=null && (querymap.get("id")==null || "".equals(querymap.get("id")))){
					id=user.getPharmacyId();
					querymap.put("pharmacyid", id);
				}
				String pharmacyCode="";String name="";String pinyin="";
				pharmacyCode= (String )querymap.get("pharmacyCode");
				name=(String)querymap.get("name");
				pinyin=(String)querymap.get("pinyin");
				if((pharmacyCode==null && name==null && pinyin==null)|| ("".equals(pharmacyCode)&&"".equals(name) &&"".equals(pinyin))){
					querymap.put("parent", 9);//代表 是按照查询条件进入的
					if(0==chaintype){
						querymap.put("needs", 2);
					}else{
						querymap.put("needs", 3);
					}
				}else{
					querymap.put("needs", 1);//可以确定 旗下唯一 一家药店
				}
				querymap.put("pharmacyid", id);
			}
			StorageDetail sumData = new StorageDetail();
			list = chainService.findAllCpharmacyDetailExcel(querymap);
			sumData = chainService.findAllCpharmacyDetailSum(querymap);
			obj=sumData;
		}
		fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
		HSSFWorkbook workbook = createUploadSplitExcel(list,inputStream,select,obj);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename="+fileName );
		// 新建一输出文件流
		ServletOutputStream fOut = response.getOutputStream();
		workbook.write(fOut);
	    fOut.flush();
		fOut.close();
			
		} catch (FileNotFoundException e) {
			logger.error("/jxctzh/downExcel FileNotFoundException error");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.error("/jxctzh/downExcel UnsupportedEncodingException error");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("/jxctzh/downExcel IOException error");
			e.printStackTrace();
		}
		
		
	}
	
	/***
	 * 生成excel
	 * @param orderType
	 * @param list
	 * @param inputStream
	 * @return
	 */
	public HSSFWorkbook createUploadSplitExcel(List< ? > list,InputStream inputStream,Integer select,Object obj){
		try {
			HSSFWorkbook workbook=null;
			workbook = new HSSFWorkbook(inputStream);
			HSSFSheet sheet = null;//工作表
			sheet = workbook.getSheetAt(0);//获得第一个工作表
			
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setLocked(false);
			HSSFRow row = null;
			HSSFCell cell =null;
            if(list!=null && list.size()>0){
				for(int i=0,size=list.size();i<size;i++){
					HSSFRow rows = null;	rows =sheet.createRow(i+5);
					if(select==3){
						row = sheet.createRow(i+3);
					}else{
						row = sheet.createRow(i+2);
					}
					
					if(select==1){
							StorageTotal  data = (StorageTotal )list.get(i);
							Converter.setCellText(row, cell, 0,Converter.toBlank(data.getId()), cellStyle);
							Converter.setCellText(row, cell, 1,Converter.toBlank(data.getPharmacycode()), cellStyle);
							Converter.setCellText(row, cell, 2,Converter.toBlank(data.getName()), cellStyle);
							Converter.setCellText(row, cell, 3,Converter.toBlank(data.getQckc()), cellStyle);
							Converter.setCellText(row, cell, 4,Converter.toBlank(data.getQckcje()), cellStyle);
							Converter.setCellText(row, cell, 5,Converter.toBlank(data.getQjrk()), cellStyle);
							Converter.setCellText(row, cell, 6,Converter.toBlank(data.getQjrkje()), cellStyle);
							Converter.setCellText(row, cell, 7,Converter.toBlank(data.getQjck()), cellStyle);
							Converter.setCellText(row, cell, 8,Converter.toBlank(data.getQjckje()), cellStyle);
							Converter.setCellText(row, cell, 9,Converter.toBlank(data.getQmkc()), cellStyle);
							Converter.setCellText(row, cell, 10,Converter.toBlank(data.getQmkcje()), cellStyle);
						}
					if(select==2){
						StorageMilde  data =(StorageMilde) list.get(i); 
						Converter.setCellText(row, cell, 0,Converter.toBlank(data.getId()), cellStyle);
						Converter.setCellText(row, cell, 1,Converter.toBlank(data.getPharmacycode()), cellStyle);
						Converter.setCellText(row, cell, 2,Converter.toBlank(data.getName()), cellStyle);
						Converter.setCellText(row, cell, 3,Converter.toBlank(data.getMatcode()), cellStyle);
						Converter.setCellText(row, cell, 4,Converter.toBlank(data.getBarcode()), cellStyle);
						Converter.setCellText(row, cell, 5,Converter.toBlank(data.getMatname()), cellStyle);
						Converter.setCellText(row, cell, 6,Converter.toBlank(data.getCommonname()), cellStyle);
						Converter.setCellText(row, cell, 7,Converter.toBlank(data.getDosage()), cellStyle);
						Converter.setCellText(row, cell, 8,Converter.toBlank(data.getSpec()), cellStyle);
						Converter.setCellText(row, cell, 9,Converter.toBlank(data.getUnitname()), cellStyle);
						Converter.setCellText(row, cell, 10,Converter.toBlank(data.getManufname()), cellStyle);
						Converter.setCellText(row, cell,11,Converter.toBlank(data.getProductarea()), cellStyle);
						Converter.setCellText(row, cell,12,Converter.toBlank(data.getLicensenum()), cellStyle);
						Converter.setCellText(row, cell, 13,Converter.toBlank(data.getRetail()), cellStyle);
						
						Converter.setCellText(row, cell, 14,Converter.toBlank(data.getQckc()), cellStyle);
						Converter.setCellText(row, cell, 15,Converter.toBlank(data.getQckcje()), cellStyle);
						Converter.setCellText(row, cell, 16,Converter.toBlank(data.getQjrk()), cellStyle);
						Converter.setCellText(row, cell, 17,Converter.toBlank(data.getQjrkje()), cellStyle);
						Converter.setCellText(row, cell, 18,Converter.toBlank(data.getQjck()), cellStyle);
						Converter.setCellText(row, cell, 19,Converter.toBlank(data.getQjckje()), cellStyle);
						Converter.setCellText(row, cell, 20,Converter.toBlank(data.getQmkc()), cellStyle);
						Converter.setCellText(row, cell, 21,Converter.toBlank(data.getQmkcje()), cellStyle);
						
					}
					if(select==3){
						StorageDetail   data =(StorageDetail)list.get(i); 
						Converter.setCellText(row, cell, 0,Converter.toBlank(data.getId()), cellStyle);
						Converter.setCellText(row, cell, 1,Converter.toBlank(data.getPharmacycode()), cellStyle);
						Converter.setCellText(row, cell, 2,Converter.toBlank(data.getName()), cellStyle);
						Converter.setCellText(row, cell, 3,Converter.toBlank(data.getMatcode()), cellStyle);
						Converter.setCellText(row, cell, 4,Converter.toBlank(data.getBarcode()), cellStyle);
						Converter.setCellText(row, cell, 5,Converter.toBlank(data.getMatname()), cellStyle);
						Converter.setCellText(row, cell, 6,Converter.toBlank(data.getCommonname()), cellStyle);
						Converter.setCellText(row, cell, 7,Converter.toBlank(data.getDosage()), cellStyle);
						Converter.setCellText(row, cell, 8,Converter.toBlank(data.getSpec()), cellStyle);
						Converter.setCellText(row, cell, 9,Converter.toBlank(data.getUnitname()), cellStyle);
						Converter.setCellText(row, cell, 10,Converter.toBlank(data.getManufname()), cellStyle);
						Converter.setCellText(row, cell, 11,Converter.toBlank(data.getProductarea()), cellStyle);
						Converter.setCellText(row, cell, 12,Converter.toBlank(data.getLicensenum()), cellStyle);
						Converter.setCellText(row, cell, 13,Converter.toBlank(data.getRetail()), cellStyle);
						
						Converter.setCellText(row, cell, 14,Converter.toBlank(data.getQckc()), cellStyle);
						Converter.setCellText(row, cell, 15,Converter.toBlank(data.getQckcje()), cellStyle);
						Converter.setCellText(row, cell, 16,Converter.toBlank(data.getCg()), cellStyle);
						Converter.setCellText(row, cell, 17,Converter.toBlank(data.getCgje()), cellStyle);
						Converter.setCellText(row, cell, 18,Converter.toBlank(data.getPyrk()), cellStyle);
						Converter.setCellText(row, cell, 19,Converter.toBlank(data.getPyrkje()), cellStyle);
						Converter.setCellText(row, cell, 20,Converter.toBlank(data.getQtrk()), cellStyle);
						Converter.setCellText(row, cell, 21,Converter.toBlank(data.getQtrkje()), cellStyle);
						Converter.setCellText(row, cell, 22,Converter.toBlank(data.getClrk()), cellStyle);
						Converter.setCellText(row, cell, 23,Converter.toBlank(data.getClrkje()), cellStyle);
						Converter.setCellText(row, cell, 24,Converter.toBlank(data.getPsrk()), cellStyle);
						Converter.setCellText(row, cell, 25,Converter.toBlank(data.getPsrkje()), cellStyle);
						Converter.setCellText(row, cell, 26,Converter.toBlank(data.getQcrk()), cellStyle);
						Converter.setCellText(row, cell, 27,Converter.toBlank(data.getQcrkje()), cellStyle);
						
						Converter.setCellText(row, cell, 28,Converter.toBlank(data.getXsck()), cellStyle);
						Converter.setCellText(row, cell, 29,Converter.toBlank(data.getXsckje()), cellStyle);
						Converter.setCellText(row, cell, 30,Converter.toBlank(data.getPkck()), cellStyle);
						Converter.setCellText(row, cell, 31,Converter.toBlank(data.getPkckje()), cellStyle);
						Converter.setCellText(row, cell, 32,Converter.toBlank(data.getQtck()), cellStyle);
						Converter.setCellText(row, cell, 33,Converter.toBlank(data.getQtckje()), cellStyle);
						Converter.setCellText(row, cell, 34,Converter.toBlank(data.getClck()), cellStyle);
						Converter.setCellText(row, cell, 35,Converter.toBlank(data.getClckje()), cellStyle);
						Converter.setCellText(row, cell, 36,Converter.toBlank(data.getPsck()), cellStyle);
						Converter.setCellText(row, cell, 37,Converter.toBlank(data.getPsckje()), cellStyle);
						Converter.setCellText(row, cell, 38,Converter.toBlank(data.getQdck()), cellStyle);
						Converter.setCellText(row, cell, 39,Converter.toBlank(data.getQdckje()), cellStyle);
						Converter.setCellText(row, cell, 40,Converter.toBlank(data.getXhck()), cellStyle);
						Converter.setCellText(row, cell, 41,Converter.toBlank(data.getXhckje()), cellStyle);
						
						
						Converter.setCellText(row, cell, 42,Converter.toBlank(data.getQmkc()), cellStyle);
						Converter.setCellText(row, cell, 43,Converter.toBlank(data.getQmkcje()), cellStyle);
					}
						
					if(i==size-1){
						if(select==1){
							StorageTotal  sum =(StorageTotal) obj; 
							
							Converter.setCellText(rows, cell, 0,"合 计", cellStyle);
							Converter.setCellText(rows, cell, 3,Converter.toBlank(sum.getQckc()), cellStyle);
							Converter.setCellText(rows, cell, 4,Converter.toBlank(sum.getQckcje()), cellStyle);
							Converter.setCellText(rows, cell, 5,Converter.toBlank(sum.getQjrk()), cellStyle);
							Converter.setCellText(rows, cell, 6,Converter.toBlank(sum.getQjrkje()), cellStyle);
							Converter.setCellText(rows, cell, 7,Converter.toBlank(sum.getQjck()), cellStyle);
							Converter.setCellText(rows, cell, 8,Converter.toBlank(sum.getQjckje()), cellStyle);
							Converter.setCellText(rows, cell, 9,Converter.toBlank(sum.getQmkc()), cellStyle);
							Converter.setCellText(rows, cell, 10,Converter.toBlank(sum.getQmkcje()), cellStyle);
							}
						if(select==2){
							StorageMilde  sum =(StorageMilde) obj; 
							Converter.setCellText(rows, cell, 0,Converter.toBlank("合 计"), cellStyle);
							Converter.setCellText(rows, cell, 14,Converter.toBlank(sum.getQckc()), cellStyle);
							Converter.setCellText(rows, cell, 15,Converter.toBlank(sum.getQckcje()), cellStyle);
							Converter.setCellText(rows, cell, 16,Converter.toBlank(sum.getQjrk()), cellStyle);
							Converter.setCellText(rows, cell, 17,Converter.toBlank(sum.getQjrkje()), cellStyle);
							Converter.setCellText(rows, cell, 18,Converter.toBlank(sum.getQjck()), cellStyle);
							Converter.setCellText(rows, cell, 19,Converter.toBlank(sum.getQjckje()), cellStyle);
							Converter.setCellText(rows, cell, 20,Converter.toBlank(sum.getQmkc()), cellStyle);
							Converter.setCellText(rows, cell, 21,Converter.toBlank(sum.getQmkcje()), cellStyle);
							}
						if(select==3){
							StorageDetail  sum =(StorageDetail) obj; 
							Converter.setCellText(rows, cell, 0,Converter.toBlank("合 计"), cellStyle);
//							Converter.setCellText(rows, cell, 1,Converter.toBlank(sum.getPharmacycode()), cellStyle);
//							Converter.setCellText(rows, cell, 2,Converter.toBlank(sum.getName()), cellStyle);
//							Converter.setCellText(rows, cell, 3,Converter.toBlank(sum.getMatcode()), cellStyle);
//							Converter.setCellText(rows, cell, 4,Converter.toBlank(sum.getBarcode()), cellStyle);
//							Converter.setCellText(rows, cell, 5,Converter.toBlank(sum.getMatname()), cellStyle);
//							Converter.setCellText(rows, cell, 6,Converter.toBlank(sum.getCommonname()), cellStyle);
//							Converter.setCellText(rows, cell, 7,Converter.toBlank(sum.getDosage()), cellStyle);
//							Converter.setCellText(rows, cell, 8,Converter.toBlank(sum.getSpec()), cellStyle);
//							Converter.setCellText(rows, cell, 9,Converter.toBlank(sum.getUnitname()), cellStyle);
//							Converter.setCellText(rows, cell, 10,Converter.toBlank(sum.getManufname()), cellStyle);
//							Converter.setCellText(rows, cell, 11,Converter.toBlank(sum.getProductarea()), cellStyle);
//							Converter.setCellText(rows, cell, 12,Converter.toBlank(sum.getLicensenum()), cellStyle);
//							Converter.setCellText(rows, cell, 13,Converter.toBlank(sum.getRetail()), cellStyle);
							
							Converter.setCellText(rows, cell, 14,Converter.toBlank(sum.getQckc()), cellStyle);
							Converter.setCellText(rows, cell, 15,Converter.toBlank(sum.getQckcje()), cellStyle);
							Converter.setCellText(rows, cell, 16,Converter.toBlank(sum.getCg()), cellStyle);
							Converter.setCellText(rows, cell, 17,Converter.toBlank(sum.getCgje()), cellStyle);
							Converter.setCellText(rows, cell, 18,Converter.toBlank(sum.getPyrk()), cellStyle);
							Converter.setCellText(rows, cell, 19,Converter.toBlank(sum.getPyrkje()), cellStyle);
							Converter.setCellText(rows, cell, 20,Converter.toBlank(sum.getQtrk()), cellStyle);
							Converter.setCellText(rows, cell, 21,Converter.toBlank(sum.getQtrkje()), cellStyle);
							Converter.setCellText(rows, cell, 22,Converter.toBlank(sum.getClrk()), cellStyle);
							Converter.setCellText(rows, cell, 23,Converter.toBlank(sum.getClrkje()), cellStyle);
							Converter.setCellText(rows, cell, 24,Converter.toBlank(sum.getPsrk()), cellStyle);
							Converter.setCellText(rows, cell, 25,Converter.toBlank(sum.getPsrkje()), cellStyle);
							Converter.setCellText(rows, cell, 26,Converter.toBlank(sum.getQcrk()), cellStyle);
							Converter.setCellText(rows, cell, 27,Converter.toBlank(sum.getQcrkje()), cellStyle);
							
							Converter.setCellText(rows, cell, 28,Converter.toBlank(sum.getXsck()), cellStyle);
							Converter.setCellText(rows, cell, 29,Converter.toBlank(sum.getXsckje()), cellStyle);
							Converter.setCellText(rows, cell, 30,Converter.toBlank(sum.getPkck()), cellStyle);
							Converter.setCellText(rows, cell, 31,Converter.toBlank(sum.getPkckje()), cellStyle);
							Converter.setCellText(rows, cell, 32,Converter.toBlank(sum.getQtck()), cellStyle);
							Converter.setCellText(rows, cell, 33,Converter.toBlank(sum.getQtckje()), cellStyle);
							Converter.setCellText(rows, cell, 34,Converter.toBlank(sum.getClck()), cellStyle);
							Converter.setCellText(rows, cell, 35,Converter.toBlank(sum.getClckje()), cellStyle);
							Converter.setCellText(rows, cell, 36,Converter.toBlank(sum.getPsck()), cellStyle);
							Converter.setCellText(rows, cell, 37,Converter.toBlank(sum.getPsckje()), cellStyle);
							Converter.setCellText(rows, cell, 38,Converter.toBlank(sum.getQdck()), cellStyle);
							Converter.setCellText(rows, cell, 39,Converter.toBlank(sum.getQdckje()), cellStyle);
							Converter.setCellText(rows, cell, 40,Converter.toBlank(sum.getXhck()), cellStyle);
							Converter.setCellText(rows, cell, 41,Converter.toBlank(sum.getXhckje()), cellStyle);
							
							
							Converter.setCellText(rows, cell, 42,Converter.toBlank(sum.getQmkc()), cellStyle);
							Converter.setCellText(rows, cell, 43,Converter.toBlank(sum.getQmkcje()), cellStyle);
							}
						
					}
				}
            }
            return  workbook;
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static final String queryChianMainData
	= "{\"start_time\": \"2015-08-20\" ,\"end_time\": \"2016-11-23\" ,\"select\":1,<br/>"
	+ " \"type\":0,\"pharmacyCode\":\"\",\"name\":\"\",\"pinyin\":\"\"}";

	@ApiOperation(value = "第1层条件查询  总店查询 进销存台账旗下药店维度接口", notes = "第1层条件查询 总店查询 进销存台账旗下药店维度接口")
	@ApiResponses(value = { @ApiResponse(code = 200, message = RETURNMESSAGE) })
	@RequestMapping(value = "/jxctzh/queryChianMainData", method = RequestMethod.POST)
	@ResponseBody
	public FFApiResponse<Map> queryChianMainData(
			HttpServletRequest request,
			@ApiParam(value = CYCLEPARAMJSON) @ModelAttribute com.fufang.bi.jqGrid.JqGridParams params,
			@ApiParam(required = false, value = queryChianMainData) @RequestParam String query) {
		Map datamap = new HashMap();
		Map querymap = new HashMap();//查询map
		String msg = "error";
		try {
			User user = (User) request.getSession().getAttribute("user");
			String role = "";
			role = (String) request.getSession().getAttribute("mark");
			JSONObject object = JSONObject.fromObject(query);
			if(user==null){
				logger.error("/jxctzh/queryChianMainData user==null error");
				return error(msg);
			}
			querymap = (Map) object;
			querymap.put("id", user.getPharmacyId());
			int type=(Integer)querymap.get("type");//含税和无税标志 数据库中字段不同
			if(type==0){
				querymap.put("amount", "t.notaxAmount");//无税
			}else{
				querymap.put("amount", "t.amount");
			}
			List<StorageTotal> list = new ArrayList();
			// 总行数
			int records = chainService.findAllCpharmacyCount(querymap);
			int total = records / params.getRows() + (records % params.getRows() == 0 ? 0 : 1);
			querymap.putAll(PageUtils.getPagedata(records, params, "pharmacyId", "asc"));
			StorageTotal sumData = new StorageTotal();
			list = chainService.findAllCpharmacy(querymap);
			sumData = chainService.findAllCpharmacySum(querymap);
			JqGridReturnedData returnedData = new JqGridReturnedData();
			returnedData.setPage(params.getPage());
			returnedData.setRecords(records);
			returnedData.setTotal(total);
			returnedData.setRows(list);
			returnedData.setUserdata(sumData);
			JqGridUtils.returnData(datamap, returnedData);
			msg = "success";
		} catch (Exception e) {
			logger.error("/jxctzh/queryChianMainData error");
			e.printStackTrace();
			return error("error");
		}

		return success(datamap, msg);
	}
	
	
	private static final String queryOnClickGoodsData = "{\"id\":200026,\"type\":0,\"start_time\":\"2015-08-20\",\"end_time\":\"2016-11-23\"}";
	
	@ApiOperation(value = "第1层点击进入第2层  总店点击进入某家药店查询该店所有药品信息", notes = "第1层点击进入第2层  总店点击进入某家药店查询该店所有药品信息")
	@ApiResponses(value = { @ApiResponse(code = 200, message = RETURNMESSAGE) })
	@RequestMapping(value = "/jxctzh/queryOnClickGoodsData", method = RequestMethod.POST)
	@ResponseBody
	public FFApiResponse<Map> queryOnClickGoodsData(HttpServletRequest request,
			@ApiParam(value = queryOnClickGoodsData) @ModelAttribute com.fufang.bi.jqGrid.JqGridParams params,
			@ApiParam(required = false, value = queryOnClickGoodsData) @RequestParam String query){
				Map datamap = new HashMap();
				Map querymap = new HashMap();
				String msg = "error";
		try {     
			User user = (User) request.getSession().getAttribute("user");
			String role = "";
			role = (String) request.getSession().getAttribute("mark");
			JSONObject object = JSONObject.fromObject(query);
			querymap = (Map) object;
			querymap.put("mark", role);
			Integer id = (Integer)querymap.get("id");
			if(id==null){
			logger.error("/jxctzh/queryOnClickGoodsData id==null error");
			return error(msg);
			}
			int type=(Integer)querymap.get("type");//含税和无税标志 数据库中字段不同
			if(type==0){
				querymap.put("amount", "t.notaxAmount");//无税
			}else{
				querymap.put("amount", "t.amount");
			}
			querymap.put("needs", 0);
			querymap.put("parent", 8);//C 代表Onclick 进入的
			List<StorageMilde> list = new ArrayList();
			// 总行数
			int records = chainService.findAllCpharmacyMildeCount(querymap);
			int total = records / params.getRows() + (records % params.getRows() == 0 ? 0 : 1);
			querymap.putAll(PageUtils.getPagedata(records, params, "pharmacyId", "asc"));
			StorageMilde sumData = new StorageMilde();
			
			list = chainService.findAllCpharmacyMilde(querymap);
			sumData =chainService.findAllCpharmacyMildeSum(querymap);
			JqGridReturnedData returnedData = new JqGridReturnedData();
			returnedData.setPage(params.getPage());
			returnedData.setRecords(records);
			returnedData.setTotal(total);
			returnedData.setRows(list);
			returnedData.setUserdata(sumData);
			JqGridUtils.returnData(datamap, returnedData);
			msg = "success";
		} catch (Exception e) {
			logger.error("/jxctzh/queryOnClickGoodsData error");
			e.printStackTrace();
			return error("error");
		}
		return success(datamap, msg);
	}
	
	private static final String queryGoodsData = "{\"type\":0,\"start_time\": \"2015-08-20\" ,\"end_time\": \"2016-11-23\",\"matcode\":\"\",<br/>"
	+ "\"barcode\":\"\",\"py\":\"\",\"licensenum\":\"\",\"materialname\":\"\",<br/>\"commonname\":\"\",\"pharmacyCode\":\"016\",\"name\":\"\",\"pinyin\":\"\"}";


	@ApiOperation(value = "第2层条件查询 进入药店查看药店所有药品期初到期末状况", notes = "第2层条件查询 进入药店查看药店所有药品期初到期末状况")
	@ApiResponses(value = { @ApiResponse(code = 200, message = RETURNMESSAGE) })
	@RequestMapping(value = "/jxctzh/queryGoodsData", method = RequestMethod.POST)
	@ResponseBody
	public FFApiResponse<Map> queryGoodsData(HttpServletRequest request,
			@ApiParam(value = CYCLEPARAMJSON) @ModelAttribute com.fufang.bi.jqGrid.JqGridParams params,
			@ApiParam(required = false, value = queryGoodsData) @RequestParam String query){
		Map datamap = new HashMap();
		Map querymap = new HashMap();
		String msg = "error";
		try {
			User user = (User) request.getSession().getAttribute("user");
			String role = "";
			role = (String) request.getSession().getAttribute("mark");
			Integer chaintype= user.getChainType();
			logger.debug("query:"+query);
			logger.debug("chaintype:"+chaintype);
			JSONObject object = JSONObject.fromObject(query);
			querymap = (Map) object;
			Integer id=null;
			if(user==null){
				logger.error("/jxctzh/queryGoodsData user==null error");
				msg="error 未获取到登录信息";
				return error(msg);
			}
			if(user!=null && (querymap.get("id")==null || "".equals(querymap.get("id")))){
				id=user.getPharmacyId();
				querymap.put("id", id);
			}
			String pharmacyCode="";String name="";String pinyin="";
			pharmacyCode= (String )querymap.get("pharmacyCode");
			name=(String)querymap.get("name");
			pinyin=(String)querymap.get("pinyin");
			System.out.println("pinyin ------>>>>>:" +querymap.get("pinyin") );
			if((pharmacyCode==null && name==null && pinyin==null)|| ("".equals(pharmacyCode)&&"".equals(name) &&"".equals(pinyin))){
				querymap.put("parent", 9);//代表 是按照查询条件进入的
				if(0==chaintype){
					querymap.put("needs", 2);
				}else{
					querymap.put("needs", 3);
				}
			}else{
				   querymap.put("needs", 1);//可以确定 旗下唯一 一家药店
			}
			querymap.put("mark", role);

			int type=(Integer)querymap.get("type");//含税和无税标志 数据库中字段不同
			if(type==0){
				querymap.put("amount", "t.notaxAmount");//无税
			}else{
				querymap.put("amount", "t.amount");     
			}
			List<StorageMilde> list = new ArrayList();
			// 总行数
			int records = chainService.findAllCpharmacyMildeCount(querymap);
			int total = records / params.getRows() + (records % params.getRows() == 0 ? 0 : 1);
			querymap.putAll(PageUtils.getPagedata(records, params, "pharmacyId", "asc"));
			StorageMilde  sumData =new StorageMilde();
			list = chainService.findAllCpharmacyMilde(querymap);
			sumData =chainService.findAllCpharmacyMildeSum(querymap);

			System.out.println("is  finished..............");
			JqGridReturnedData returnedData = new JqGridReturnedData();
			returnedData.setPage(params.getPage());
			returnedData.setRecords(records);
			returnedData.setTotal(total);
			returnedData.setRows(list);
			returnedData.setUserdata(sumData);
			JqGridUtils.returnData(datamap, returnedData);
			msg = "success";
		} catch (Exception e) {
			logger.error("/jxctzh/queryGoodsData error");
			e.printStackTrace();
			return error("error");
		}
		return success(datamap, msg);
	}

	
	private static final String queryOnClickGoodsDetailData = "{\"pharmacyid\":200026, \"materialid\":1355,\"type\":0,\"start_time\":\"2015-08-20\",\"end_time\":\"2016-11-23\"}";
	
	@ApiOperation(value = "第2层进入第3层 点击查看药品生命周期详情", notes = "第2层进入第3层  点击查看药品生命周期详情")
	@ApiResponses(value = { @ApiResponse(code = 200, message = RETURNMESSAGE) })
	@RequestMapping(value = "/jxctzh/queryOnClickGoodsDetailData", method = RequestMethod.POST)
	@ResponseBody
	public FFApiResponse<Map> queryOnClickGoodsDetailData(HttpServletRequest request,
			@ApiParam(value = CYCLEPARAMJSON) @ModelAttribute com.fufang.bi.jqGrid.JqGridParams params,
			@ApiParam(required = false, value = queryOnClickGoodsDetailData) @RequestParam String query) {

		Map datamap = new HashMap();
		Map querymap = new HashMap();
		String msg = "error";
		try {
			User user = (User) request.getSession().getAttribute("user");
			String role = "";
			role = (String) request.getSession().getAttribute("mark");
			JSONObject object = JSONObject.fromObject(query);
			querymap = (Map) object;
			querymap.put("mark", role);
			Integer pharmacyid=null;
			if(user==null){
				logger.error("/jxctzh/queryOnClickGoodsDetailData user==null error");
				return error(msg);
			}
			int type = (Integer) querymap.get("type");// 含税和无税标志 数据库中字段不同
			if (type == 0) {
				querymap.put("amount", "t.notaxAmount");// 无税
			} else {
				querymap.put("amount", "t.amount");
			}
			querymap.put("needs", 0);
			querymap.put("parent", 8);//C 代表Onclick 进入的
			  
			List<StorageDetail> list = new ArrayList();
			// 总行数
			int records = chainService.findAllCpharmacyDetailCount(querymap);
			logger.debug("。。。。。。。。。"+records);
			int total = records / params.getRows()
					+ (records % params.getRows() == 0 ? 0 : 1);
			querymap.putAll(PageUtils.getPagedata(records, params,"pharmacyId", "asc"));
			StorageDetail sumData = new StorageDetail();

			list = chainService.findAllCpharmacyDetail(querymap);
			sumData = chainService.findAllCpharmacyDetailSum(querymap);
			JqGridReturnedData returnedData = new JqGridReturnedData();
			returnedData.setPage(params.getPage());
			returnedData.setRecords(records);
			returnedData.setTotal(total);
			returnedData.setRows(list);
			returnedData.setUserdata(sumData);
			JqGridUtils.returnData(datamap, returnedData);
			msg = "success";
		} catch (Exception e) {
			logger.error("/jxctzh/queryOnClickGoodsDetailData error");
			e.printStackTrace();
			return error("error");
		}
		return success(datamap, msg);

	}
	
	private static final String queryGoodsDetailData = "{\"type\":0,\"start_time\": \"2016-08-20\" ,\"end_time\": \"2016-11-23\",\"matcode\":\"\","
			+ "\"barcode\":\"\",\"py\":\"TYLXJN\",\"licensenum\":\"\",\"matname\":\"\",<br/>\"commonname\":\"\"}";

	@ApiOperation(value = "第3层 查看药品生命周期详情", notes = "第3层 查看药品生命周期详情")
	@ApiResponses(value = { @ApiResponse(code = 200, message = RETURNMESSAGE) })
	@RequestMapping(value = "/jxctzh/queryGoodsDetailData", method = RequestMethod.POST)
	@ResponseBody
	public FFApiResponse<Map> queryGoodsDetailData(HttpServletRequest request,
			@ApiParam(value = CYCLEPARAMJSON) @ModelAttribute com.fufang.bi.jqGrid.JqGridParams params,
			@ApiParam(required = false, value = queryGoodsDetailData) @RequestParam String query) {

		Map datamap = new HashMap();
		Map querymap = new HashMap();
		String msg = "error";
		try {
			User user = (User) request.getSession().getAttribute("user");
			String role = "";
			role = (String) request.getSession().getAttribute("mark");
			Integer chaintype= user.getChainType();
			logger.debug("query:"+query);
			logger.debug("chaintype:"+chaintype);
			JSONObject object = JSONObject.fromObject(query);
			Integer id=null;
			querymap = (Map) object;
			if(user==null){
				logger.error("/jxctzh/queryGoodsDetailData user==null error");
				msg="error 未获取到登录信息";
				return error(msg);
			}
			if(user!=null && (querymap.get("id")==null || "".equals(querymap.get("id")))){
				id=user.getPharmacyId();
				querymap.put("pharmacyid", id);
			}
			String pharmacyCode="";String name="";String pinyin="";
			pharmacyCode= (String )querymap.get("pharmacyCode");
			name=(String)querymap.get("name");
			pinyin=(String)querymap.get("pinyin");
			if((pharmacyCode==null && name==null && pinyin==null)|| ("".equals(pharmacyCode)&&"".equals(name) &&"".equals(pinyin))){
				querymap.put("parent", 9);//代表 是按照查询条件进入的
				if(0==chaintype){
					querymap.put("needs", 2);
				}else{
					querymap.put("needs", 3);
				}
			}else{
				querymap.put("needs", 1);//可以确定 旗下唯一 一家药店
			}
			
			
			querymap.put("mark", role);
			querymap.put("pharmacyid", id);
			int type = (Integer) querymap.get("type");// 含税和无税标志 数据库中字段不同
			if (type == 0) {
				querymap.put("amount", "t.notaxAmount");// 无税
			} else {
				querymap.put("amount", "t.amount");
			}
			//querymap.put("falg", 0); 
			List<StorageDetail> list = new ArrayList();
			// 总行数
			int records = chainService.findAllCpharmacyDetailCount(querymap);
			logger.debug("。。。。。。。。。"+records);
			int total = records / params.getRows()
					+ (records % params.getRows() == 0 ? 0 : 1);
			querymap.putAll(PageUtils.getPagedata(records, params,"pharmacyId", "asc"));
			StorageDetail sumData = new StorageDetail();

			list = chainService.findAllCpharmacyDetail(querymap);
			sumData = chainService.findAllCpharmacyDetailSum(querymap);
			JqGridReturnedData returnedData = new JqGridReturnedData();
			returnedData.setPage(params.getPage());
			returnedData.setRecords(records);
			returnedData.setTotal(total);
			returnedData.setRows(list);
			returnedData.setUserdata(sumData);
			JqGridUtils.returnData(datamap, returnedData);
			System.out.println("is  finished..............");
			msg = "success";
		} catch (Exception e) {
			logger.error("/jxctzh/queryOnClickGoodsDetailData error");
			e.printStackTrace();
			return error("error");
		}
		return success(datamap, msg);

	}
}
