package com.fufang.bi.service.impl.chain;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fufang.bi.entity.ChainPharmacy;
import com.fufang.bi.entity.StorageDetail;
import com.fufang.bi.entity.StorageItem;
import com.fufang.bi.entity.StorageMilde;
import com.fufang.bi.entity.StorageTotal;
import com.fufang.bi.jqGrid.JqGridParams;
import com.fufang.bi.mapper.BiStorageAnalysisDayMapper;
import com.fufang.bi.mapper.ChainPharmacyMapper;
import com.fufang.bi.mapper.StorageItemMapper;
import com.fufang.bi.service.chain.ChainService;
import com.fufang.bi.util.MapConvertObject;
import com.fufang.bi.util.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service (value = "chainServiceImpl") 
public class ChainServiceImpl implements ChainService {
	private static Logger logger = LoggerFactory.getLogger(ChainServiceImpl.class);
	
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate db;
	
	@Resource(name = "jdbcTemplateSqlserver")
	private JdbcTemplate dbSqlserver;
	
	@Autowired
	private StorageItemMapper storageItemMapper;
	
	@Autowired
	private ChainPharmacyMapper chainPharmacyMapper;
	
	@Autowired
	private BiStorageAnalysisDayMapper biStorageAnalysisDayMapper;
	
	public String Demo(){
		
		return  "test";
	}


//	public List<StorageItem> getMatidDetail(StorageItem si) {
//		//StorageItem st =storageItemMapper.selectByPrimaryKey(1);
//		 List<StorageItem> list = new ArrayList<StorageItem>();
//		 //list= storageItemMapper.addtest();
//		 //list.add(st);   
//		return list;
//	}


	@Override
	public StorageItem getMatidDetail(StorageItem si) {
		Integer id =10;
		StorageItem sm =storageItemMapper.Demo(id);
		return sm;
	}


	@Override
	public List<StorageItem> findList(StorageItem si) {
		System.out.println("jin lai");
		Map map = new HashMap();
		try {
			map=MapConvertObject.convertBean(si);
			logger.debug( map.toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		List <StorageItem> list=storageItemMapper.findList(si);
		return list;
	}
	
	public void findListByPage(StorageItem si) {
		/*PageHelper.startPage(1, 10);
		PageInfo info = new PageInfo(storageItemMapper.findList(null));
		System.out.println(info.getTotal());
		List<StorageItem> list = info.getList();
		for(StorageItem s : list) {
			System.out.println(s.getId());
		}*/
	}


	@Override
	public int saveObject(StorageItem si) {
		int  tmp=storageItemMapper.insertSelective(si);
		return tmp;
	}


	@Override
	public ChainPharmacy findCpharmacy(Integer id) {
		
		return chainPharmacyMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<StorageTotal> findListCpharmacy(Map map,JqGridParams params) throws Exception {
		params.setSord("desc");
		params.setSidx("a.pharmacyid");
		map.putAll(PageUtils.getModelParams(params));
		PageHelper.startPage(params.getPage(), params.getRows());
		PageInfo info = new PageInfo(storageItemMapper.findListCpharmacy(map));
		System.out.println(info.getTotal());
		List<StorageTotal> list = info.getList();
		for(StorageTotal s : list) {
			System.out.println(s.getName() + "--");
		}
		return info.getList();
	}


	@Override
	public List<StorageTotal> findAllCpharmacy(Map map)  {
		
		return biStorageAnalysisDayMapper.findAllCpharmacy(map);
	}


	@Override
	public int findAllCpharmacyCount(Map map) {
		return biStorageAnalysisDayMapper.findAllCpharmacyCount(map);
	}


	@Override
	public List<StorageTotal> findAllCpharmacyExcel(Map querymap) {
		
		return biStorageAnalysisDayMapper.findAllCpharmacyExcel(querymap);
	}


	@Override
	public int findAllCpharmacyMildeCount(Map map) {
		
		return biStorageAnalysisDayMapper.findAllCpharmacyMildeCount(map);
	}


	@Override
	public List<StorageMilde> findAllCpharmacyMilde(Map map) {
		return biStorageAnalysisDayMapper.findAllCpharmacyMilde(map);
	}


	@Override
	public List<StorageMilde> findAllCpharmacyMildeExcel(Map map) {
		return biStorageAnalysisDayMapper.findAllCpharmacyMildeExcel(map);
	}


	@Override
	public StorageTotal findAllCpharmacySum(Map map) {
		return biStorageAnalysisDayMapper.findAllCpharmacySum(map);
	}


	@Override
	public StorageMilde findAllCpharmacyMildeSum(Map map) {
		return biStorageAnalysisDayMapper.findAllCpharmacyMildeSum(map);
	}


	@Override
	public int findAllCpharmacyDetailCount(Map map) {
		return biStorageAnalysisDayMapper.findAllCpharmacyDetailCount(map);
	}


	@Override
	public StorageDetail findAllCpharmacyDetailSum(Map map) {
		return biStorageAnalysisDayMapper.findAllCpharmacyDetailSum(map);
	}


	@Override
	public List<StorageDetail> findAllCpharmacyDetail(Map map) {
		return biStorageAnalysisDayMapper.findAllCpharmacyDetail(map);
	}


	@Override
	public List<StorageDetail> findAllCpharmacyDetailExcel(Map map) {
		return biStorageAnalysisDayMapper.findAllCpharmacyDetailExcel(map);
	}


//	@Override
//	public int findListCpharmacyCount(Map map) {
//		return storageItemMapper.findListCpharmacyCount(map);
//	}
}
