package com.spro.entity.sys;

import com.spro.entity.DictionaryEntries;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("attachment")
public class Attachment {
    private Integer id;

    private Long slipId;

    private String slipType;

    private String fileName;

    private String fileSaveName;

    private String filePath;

    private Integer fileType;

    private Date uploadTime;

    private String uploadUser;

    private String fileSize;

    private String remark;

    private Boolean isValid;

    private DictionaryEntries attach;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSlipId() {
        return slipId;
    }

    public void setSlipId(Long slipId) {
        this.slipId = slipId;
    }

    public String getSlipType() {
        return slipType;
    }

    public void setSlipType(String slipType) {
        this.slipType = slipType == null ? null : slipType.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName == null ? null : fileSaveName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser == null ? null : uploadUser.trim();
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize == null ? null : fileSize.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public DictionaryEntries getAttach() {
        return attach;
    }

    public void setAttach(DictionaryEntries attach) {
        this.attach = attach;
    }
}