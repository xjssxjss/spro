package com.spro.entity.finance;

import java.util.Date;

public class FiscalMonth {
    private Long id;

    private String fiscalMonth;

    private Long preFisMonId;

    private Date beginTime;

    private Date endTime;

    private String creator;

    private Boolean isCurrent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFiscalMonth() {
        return fiscalMonth;
    }

    public void setFiscalMonth(String fiscalMonth) {
        this.fiscalMonth = fiscalMonth == null ? null : fiscalMonth.trim();
    }

    public Long getPreFisMonId() {
        return preFisMonId;
    }

    public void setPreFisMonId(Long preFisMonId) {
        this.preFisMonId = preFisMonId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }
}