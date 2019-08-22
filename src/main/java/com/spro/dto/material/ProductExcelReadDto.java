package com.spro.dto.material;

import com.spro.entity.DictionaryEntries;

import java.math.BigDecimal;

public class ProductExcelReadDto {
    private Integer id;

    private String code;

    private String productName;

    private String productAliasName;

    private String productEnglishName;

    private Integer brandId;

    private Integer productPictureId;

    private BigDecimal stanardPrice;

    private Boolean isSample;

    private Boolean isValid;

    private String remark;

    private Integer count;       //出错行数

    private String errMsg;          //错误信息

    private String brandName;

    private DictionaryEntries brand;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductAliasName() {
        return productAliasName;
    }

    public void setProductAliasName(String productAliasName) {
        this.productAliasName = productAliasName == null ? null : productAliasName.trim();
    }

    public String getProductEnglishName() {
        return productEnglishName;
    }

    public void setProductEnglishName(String productEnglishName) {
        this.productEnglishName = productEnglishName == null ? null : productEnglishName.trim();
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getProductPictureId() {
        return productPictureId;
    }

    public void setProductPictureId(Integer productPictureId) {
        this.productPictureId = productPictureId;
    }

    public BigDecimal getStanardPrice() {
        return stanardPrice;
    }

    public void setStanardPrice(BigDecimal stanardPrice) {
        this.stanardPrice = stanardPrice;
    }

    public Boolean getIsSample() {
        return isSample;
    }

    public void setIsSample(Boolean isSample) {
        this.isSample = isSample;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public DictionaryEntries getBrand() {
        return brand;
    }

    public void setBrand(DictionaryEntries brand) {
        this.brand = brand;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
