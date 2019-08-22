package com.spro.dto.material;

import com.spro.entity.DictionaryEntries;
import com.spro.entity.material.Inventory;
import com.spro.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

import static java.math.RoundingMode.HALF_UP;

public class InventoryDto {

    private Integer id;

    private Integer productId;

    private String productName;

    private String proxyLevelName;

    private BigDecimal price;

    private Integer proxyLevelId;

    private String dnCode;

    private Integer dnQty;

    private Integer totalQty;

    private Integer lockedQty;

    private Date checkInTime;

    private String remark;

    private Long checkInTimeQty; //入库天数

    private DictionaryEntries proxyLevel;

    public InventoryDto(Inventory inv){
        this.id = inv.getId();
        this.checkInTime = inv.getCheckInTime();
        this.dnCode = inv.getDnCode();
        this.lockedQty = inv.getLockedQty();
        this.dnQty = inv.getDnQty();
        this.price = inv.getPrice();
        this.remark = inv.getRemark();
        this.totalQty = inv.getTotalQty();
        this.proxyLevel = inv.getProxyLevel();
        this.proxyLevelId = inv.getProxyLevel().getId();
        this.productId = inv.getProduct().getId();
        this.productName = inv.getProduct() == null ? "" : inv.getProduct().getProductName();

        this.proxyLevelName = inv.getProxyLevel() == null ? "" : inv.getProxyLevel().getChineseName();

        String nowDateStr = DateUtil.parseDateToStr(new Date(),DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        String checkInTIme = DateUtil.parseDateToStr(inv.getCheckInTime(),DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        try{
            this.setCheckInTimeQty(DateUtil.getDistanceDays(nowDateStr,inv.getCheckInTime() == null?"2019-01-01 10:10:10":checkInTIme));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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
        if(null != price){
           return price.setScale(2,HALF_UP);
        }
        System.out.println("ceshi   jrebal");
        return null;
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
        return checkInTime;
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

    public Long getCheckInTimeQty() {
        return checkInTimeQty;
    }

    public void setCheckInTimeQty(Long checkInTimeQty) {
        this.checkInTimeQty = checkInTimeQty;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProxyLevelName() {
        return proxyLevelName;
    }

    public void setProxyLevelName(String proxyLevelName) {
        this.proxyLevelName = proxyLevelName;
    }

    public DictionaryEntries getProxyLevel() {
        return proxyLevel;
    }

    public void setProxyLevel(DictionaryEntries proxyLevel) {
        this.proxyLevel = proxyLevel;
    }
}
