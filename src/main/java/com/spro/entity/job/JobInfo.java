package com.spro.entity.job;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spro.entity.finance.FiscalMonth;

import java.util.Date;

public class JobInfo {
    private Integer id;

    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long fisMonId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date submitTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long submiter;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String waybillNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String expressCompany;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date sendTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remark;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String qrCode;

    private FiscalMonth fisMon;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getFisMonId() {
        return fisMonId;
    }

    public void setFisMonId(Long fisMonId) {
        this.fisMonId = fisMonId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Long getSubmiter() {
        return submiter;
    }

    public void setSubmiter(Long submiter) {
        this.submiter = submiter;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo == null ? null : waybillNo.trim();
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }

    public FiscalMonth getFisMon() {
        return fisMon;
    }

    public void setFisMon(FiscalMonth fisMon) {
        this.fisMon = fisMon;
    }
}