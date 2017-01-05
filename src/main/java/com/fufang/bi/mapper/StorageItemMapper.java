package com.fufang.bi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.fufang.bi.entity.StorageItem;
import com.fufang.bi.entity.StorageTotal;

public interface StorageItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StorageItem record);

    int insertSelective(StorageItem record);

    StorageItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StorageItem record);

    int updateByPrimaryKey(StorageItem record);
    
    StorageItem Demo(Integer id);
    
    List <StorageItem> findList(StorageItem map);
    
    List <StorageTotal> findListCpharmacy(Map map);

    List <StorageTotal> findListCpharmacy(Map map, RowBounds rowBounds);
    
//    int findListCpharmacyCount(Map map);
    
    
}