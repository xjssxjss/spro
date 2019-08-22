package com.spro.entity.job;

import java.math.BigDecimal;

public class ProductPrice {
    private Integer id;

    private Integer proxyLevelId;

    private Integer productId;

    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProxyLevelId() {
        return proxyLevelId;
    }

    public void setProxyLevelId(Integer proxyLevelId) {
        this.proxyLevelId = proxyLevelId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}