package com.spro.dao;

import com.spro.entity.job.ProductPrice;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface ProductPriceMapper extends BaseMapper<ProductPrice>{
    BigDecimal queryPriceByProxyLevelAndProduct(@Param("proxyLevelId") Integer proxyLevelId,
                                                @Param("productId") Integer productId);
}