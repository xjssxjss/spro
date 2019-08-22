package com.spro.entity.au;

import com.spro.entity.DictionaryEntries;

import java.util.Date;

/**
 * 用户
 */
public class User {
    private Integer id;

    private String loginName;           //用户登录名

    private String username;            //用户名

    private String userMd5Pwd;          //MD5加密密码

    private String userPwd;

    private String phone;               //手机号

    private String email;

    private Integer reportTo;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Date createTime;

    private Integer proxyLevelId;

    private Boolean isValid;

    private String remark;

    private Integer userPictureId;

    private DictionaryEntries proxyLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserMd5Pwd() {
        return userMd5Pwd;
    }

    public void setUserMd5Pwd(String userMd5Pwd) {
        this.userMd5Pwd = userMd5Pwd == null ? null : userMd5Pwd.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getReportTo() {
        return reportTo;
    }

    public void setReportTo(Integer reportTo) {
        this.reportTo = reportTo;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getProxyLevelId() {
        return proxyLevelId;
    }

    public void setProxyLevelId(Integer proxyLevelId) {
        this.proxyLevelId = proxyLevelId;
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

    public Integer getUserPictureId() {
        return userPictureId;
    }

    public void setUserPictureId(Integer userPictureId) {
        this.userPictureId = userPictureId;
    }

    public DictionaryEntries getProxyLevel() {
        return proxyLevel;
    }

    public void setProxyLevel(DictionaryEntries proxyLevel) {
        this.proxyLevel = proxyLevel;
    }
}