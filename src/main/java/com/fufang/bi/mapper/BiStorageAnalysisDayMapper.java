package com.fufang.bi.mapper;

import java.util.List;
import java.util.Map;

import com.fufang.bi.entity.BiStorageAnalysisDay;
import com.fufang.bi.entity.StorageDetail;
import com.fufang.bi.entity.StorageMilde;
import com.fufang.bi.entity.StorageTotal;

public interface BiStorageAnalysisDayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BiStorageAnalysisDay record);

    int insertSelective(BiStorageAnalysisDay record);

    BiStorageAnalysisDay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BiStorageAnalysisDay record);

    int updateByPrimaryKey(BiStorageAnalysisDay record);
    
    List <StorageTotal> findAllCpharmacy(Map map);
    
    StorageTotal findAllCpharmacySum (Map map);
    
    List <StorageTotal> findAllCpharmacyExcel(Map map);
    
    List <StorageTotal> findAllCpharmacyDemo();
    
    int  findAllCpharmacyCount(Map map);
    
    int findAllCpharmacyMildeCount(Map map) ;
    
    List<StorageMilde> findAllCpharmacyMilde(Map map);
    
    StorageMilde findAllCpharmacyMildeSum(Map map);
    
    List<StorageMilde> findAllCpharmacyMildeExcel(Map map);
    
    int findAllCpharmacyDetailCount(Map map);
    
    StorageDetail findAllCpharmacyDetailSum(Map map);
    
    List<StorageDetail> findAllCpharmacyDetail(Map map);
    
    List<StorageDetail> findAllCpharmacyDetailExcel(Map map);
    
    
    
    
}