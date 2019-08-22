package com.spro.dao;

import com.spro.entity.material.Inventory;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface InventoryMapper extends BaseMapper<Inventory>{
    Map<String,Object> queryCountInventory(Map<String,Object> paramMap);

    //查询总条数
    Integer queryInventoryTotalCount(Map<String,Object> paramMap);

    //根据产品查询产品可用总数
    Map<String,Object> queryInventoryByProductId(@Param(value = "productId") Integer productId);
}