package com.fufang.bi.service.chain;

import java.util.List;
import java.util.Map;

import com.fufang.bi.entity.ChainPharmacy;
import com.fufang.bi.entity.StorageDetail;
import com.fufang.bi.entity.StorageItem;
import com.fufang.bi.entity.StorageMilde;
import com.fufang.bi.entity.StorageTotal;
import com.fufang.bi.jqGrid.JqGridParams;


public interface ChainService {
	
	public String Demo();

	/**
	 * 保存对象
	 */
	public int saveObject(StorageItem si);
	/**
	 * 获取药品详细信息
	 * @param si
	 * @return
	 */
	public StorageItem getMatidDetail(StorageItem si);
	
	/**
	 * 获取List
	 * @param si
	 * @return
	 */
	public List<StorageItem> findList(StorageItem si);
	

	public void findListByPage(StorageItem si);
	
	/**
	 * 获取用户所在 连锁 药店信息
	 * @param id
	 * @return
	 */
	public ChainPharmacy findCpharmacy(Integer id);
	
	/**
	 * 查询总店组织机构下的店铺进销存 台账信息
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<StorageTotal > findListCpharmacy(Map map,JqGridParams params) throws Exception;
	/**
	 * 查询总店组织机构下的店铺进销存 台账信息 有多少条信息
	 * @param map
	 * @return
	 */
//	public int findListCpharmacyCount(Map map);
	
	/**
	 * 查询所有药店的 期初 -到-期末的信息
	 * @param map
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public List<StorageTotal> findAllCpharmacy(Map map) ;
	/**
	 * 查询所有药店的 期初 -到-期末的信息全量 无分页
	 * @param map
	 * @return
	 */
	public List<StorageTotal> findAllCpharmacyExcel(Map map) ;
	
	
	public int findAllCpharmacyCount(Map map);
	
	/**
	 * 查询所有药店的 期初 -到-期末的总计
	 * @param map
	 * @return
	 */
	public StorageTotal findAllCpharmacySum(Map map) ;
	
	/**
	 * 获取各个药店 各个药品的总条数
	 * @param map
	 * @return
	 */
	public int findAllCpharmacyMildeCount(Map map);
	
	
	/**
	 * 查询各个药店 各个药品的信息
	 * @param map
	 * @return
	 */
	public List<StorageMilde> findAllCpharmacyMilde(Map map);
	/**
	 * 求相应的合计
	 * @param map
	 * @return
	 */
	public StorageMilde  findAllCpharmacyMildeSum(Map map);
	
	/**
	 * 导出 各个药店 各个药品的信息
	 * @param map
	 * @return
	 */
	public List<StorageMilde> findAllCpharmacyMildeExcel(Map map);
	
	/**
	 * 查询药品台账明细总和
	 * @param map
	 * @return
	 */
	public int findAllCpharmacyDetailCount(Map map);
	
	/**
	 * 查询药品台账明细合计
	 * @param map
	 * @return
	 */
	public StorageDetail findAllCpharmacyDetailSum(Map map);
	/**
	 * 查询药品台账明细
	 * @param map
	 * @return
	 */
	public List<StorageDetail> findAllCpharmacyDetail(Map map);
	
	/**
	 * 导出Excel
	 * @param map
	 * @return
	 */
	public List<StorageDetail> findAllCpharmacyDetailExcel(Map map);
}

