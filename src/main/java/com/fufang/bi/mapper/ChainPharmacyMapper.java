package com.fufang.bi.mapper;

import java.util.List;
import java.util.Map;

import com.fufang.bi.entity.ChainPharmacy;

public interface ChainPharmacyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChainPharmacy record);

    int insertSelective(ChainPharmacy record);

    ChainPharmacy selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChainPharmacy record);

    int updateByPrimaryKey(ChainPharmacy record);
    
    List findListCpharmacy(Map map);
}