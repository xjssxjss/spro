package com.spro.entity.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spro.entity.DictionaryEntries;

import java.util.Date;

/**
 * @Description 发送邮件实体
 * @Created Date:2019/3/27
 * @Author yahui.xie
 */
public class Email {
    private Long id;

    private Long slipId;           //单号id

    private String slipCode;       //单号Code

    private String slipType;       //单号类型

    private String type;           //邮件类型

    @JsonIgnore
    private Integer status;        //邮件状态

    private DictionaryEntries statusEntries;

    @JsonFormat(pattern = "yyyy-MM-DD hh:mm:ss",locale = "zh")
    private Date createTime;        //邮件生成时间

    @JsonFormat(pattern = "yyyy-MM-DD hh:mm:ss",locale = "zh")
    private Date sendTime;          //邮件发送时间

    private String subject;         //邮件主题

    private String body;            //邮件内容

    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private String addr;            //邮件发送地址

    private String cc;              //抄送人员

    private String bcc;             //邮件密送人员

    private String errorMsg;        //错误信息

    private Integer tryCount;       //重试次数

    private String remark;          //备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSlipId() {
        return slipId;
    }

    public void setSlipId(Long slipId) {
        this.slipId = slipId;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode == null ? null : slipCode.trim();
    }

    public String getSlipType() {
        return slipType;
    }

    public void setSlipType(String slipType) {
        this.slipType = slipType == null ? null : slipType.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc == null ? null : cc.trim();
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc == null ? null : bcc.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    public Integer getTryCount() {
        return null == tryCount ? 0 : tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public DictionaryEntries getStatusEntries() {
        return statusEntries;
    }

    public void setStatusEntries(DictionaryEntries statusEntries) {
        this.statusEntries = statusEntries;
    }
}
