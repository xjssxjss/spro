 package com.spro.entity.material;

import com.spro.entity.DictionaryEntries;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "库存信息")
public class Inventory {
    private Integer id;

    private Integer productId;

    @ApiModelProperty(value = "库存价格")
    private BigDecimal price;

    private Integer proxyLevelId;

    private String dnCode;

    private Integer dnQty;

    private Integer totalQty;

    private Integer lockedQty;

    private Date checkInTime;

    private String remark;

    private Product product;        //产品对象

    private DictionaryEntries proxyLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getProxyLevelId() {
        return proxyLevelId;
    }

    public void setProxyLevelId(Integer proxyLevelId) {
        this.proxyLevelId = proxyLevelId;
    }

    public String getDnCode() {
        return dnCode;
    }

    public void setDnCode(String dnCode) {
        this.dnCode = dnCode == null ? null : dnCode.trim();
    }

    public Integer getDnQty() {
        return dnQty;
    }

    public void setDnQty(Integer dnQty) {
        this.dnQty = dnQty;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getLockedQty() {
        return lockedQty;
    }

    public void setLockedQty(Integer lockedQty) {
        this.lockedQty = lockedQty;
    }

    public Date getCheckInTime() {
        if(null != checkInTime){
            return checkInTime;
        }
        return null;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public DictionaryEntries getProxyLevel() {
        return proxyLevel;
    }

    public void setProxyLevel(DictionaryEntries proxyLevel) {
        this.proxyLevel = proxyLevel;
    }
}