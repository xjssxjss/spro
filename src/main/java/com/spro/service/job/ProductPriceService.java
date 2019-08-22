package com.spro.service.job;

import com.spro.common.GlobalConstant;
import com.spro.dao.ProductPriceMapper;
import com.spro.entity.job.ProductPrice;
import com.spro.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ProductPriceService extends BaseService<ProductPrice>{
    private Logger logger = LoggerFactory.getLogger(ProductPriceService.class);

    @Autowired
    private ProductPriceMapper productPriceMapper;


    /**
     * 根据产品Id和代理级别查询相关价格
     * @param proxyLevelId
     * @param productId
     * @return
     */
    public Map<String,Object> queryPriceByProxyLevelAndProduct(Integer proxyLevelId,Integer productId){
        try {
            BigDecimal price = productPriceMapper.queryPriceByProxyLevelAndProduct(proxyLevelId,productId);
            if(null == price){
                success = false;
                message = GlobalConstant.FAIL_PRICE_NOT_COMPLATE;
            } else {
                data = price;
                success = true;
                message = GlobalConstant.SUCCESS_MESSAGE;
            }
        } catch (Exception e){
            e.printStackTrace();
            success = false;
            message = e.getMessage();
        }

        return result();
    }
}
