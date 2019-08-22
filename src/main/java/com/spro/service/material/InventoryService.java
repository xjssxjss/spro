package com.spro.service.material;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.spro.common.GlobalConstant;
import com.spro.dao.InventoryMapper;
import com.spro.dto.material.InventoryDto;
import com.spro.entity.material.Inventory;
import com.spro.service.BaseService;
import com.spro.service.finance.FiscalMonthService;
import com.spro.util.GenerateDnCode;
import com.spro.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InventoryService extends BaseService<Inventory>{

    @Autowired
    private FiscalMonthService fiscalMonthService;

    private Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private InventoryMapper inventoryMapper;

    /**
     * 根据查询条件，查询所有库存信息
     * @param params
     * @return
     */
    public Map<String,Object> queryInventoryByParams(Map<String,Object> params) {
        if(null != params){
            String productName = (StringUtil.likeParam((String)params.get("productName")));
            params.put("productName",productName);
            params.put("proxyLevel",StringUtil.mulSelectParam(params.get("proxyLevel")));
        }
        //声明传回的结果集
        List<InventoryDto> inventoryDtoList = new ArrayList();
        try{
            //开启分页
            startPage(params);
            List<Inventory> inventoryList = queryByParams(params);

            InventoryDto inventoryDto = null;
            for (Inventory inventory : inventoryList) {
                inventoryDto = new InventoryDto(inventory);
                inventoryDtoList.add(inventoryDto);
            }
            result.put("dataList",inventoryDtoList);
            result.put("totalCount",inventoryMapper.queryInventoryTotalCount(params));
            //统计库存信息
            Map<String,Object> countMap = inventoryMapper.queryCountInventory(params);
            result.put("totalQty",null == countMap ? 0 : countMap.get("totalQty"));
            result.put("lockedQty",null == countMap ? 0 : countMap.get("lockedQty"));
            result.put("canUseTotalQty",null == countMap ? 0 : countMap.get("canUseTotalQty"));
            result.put("totalPrice",null == countMap ? 0.0 : countMap.get("totalPrice"));
            result.put("canUseTotalPrice",null == countMap ? 0.0 : countMap.get("canUseTotalPrice"));

            success = true;
            message = GlobalConstant.SUCCESS_MESSAGE;
            data = result;
        }catch (Exception e){
            success = false;
            message = e.getMessage();
        }
        return result();
    }

    /**
     * 修改或保存库存信息
     * @return
     */
    public Map<String,Object> saveOrUpdateInventory(String inventryForm){
        //获取对象参数
        Inventory product = JSON.parseObject(inventryForm, new TypeReference<Inventory>() {});
        if(null == product.getId()){
            if(null != product){
                product.setCheckInTime(new Date());
                product.setDnQty(product.getTotalQty());
                product.setLockedQty(0);
                //设置入库单号
                product.setDnCode(GlobalConstant.DN_SUFFIX + GenerateDnCode.getCode());
                try {
                    insert(product);
                    message = GlobalConstant.SUCCESS_INSERT_MESSAGE;
                    success = true;
                } catch (Exception e){
                    message = e.getMessage();
                    success = false;
                }
            }
        } else {
            try {
                product.setDnQty(product.getTotalQty());
                update(product);
                success  = true;
                message = GlobalConstant.SUCCESS_UPDATE_MESSAGE;
            } catch (Exception e){
                success = false;
                message = e.getMessage();
            }
        }
        logger.info("product>>>>>>>>>>" + product);
        return result();
    }

    /**
     * 根据产品Id，查询此产品所有库存总数
     * @param productId
     * @return
     */
    public Map<String,Object> queryInventoryByProductId(Integer productId){
        Map<String,Object> invMap = new HashMap<>();
        try{
            Map<String,Object> productMap = inventoryMapper.queryInventoryByProductId(productId);
            if(null != productMap){
                if(null != productMap.get("canUseQty")){
                    invMap.put("canUseQty",productMap.get("canUseQty"));
                    success = true;
                    message = GlobalConstant.SUCCESS_MESSAGE;
                } else {
                    invMap.put("canUseQty",0);
                    success = false;
                    message = GlobalConstant.FAIL_INV_IS_NOT_ENOUGNN;
                }
            } else {
                invMap.put("canUseQty",0);
                success = false;
                message = GlobalConstant.FAIL_INV_IS_NOT_ENOUGNN;
            }
            data = invMap;
        }catch (Exception e){
            e.printStackTrace();
            success = false;
            message = e.getMessage();
        }
        return result();
    }
}
