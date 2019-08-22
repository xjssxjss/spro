package com.spro.entity.sys;

import java.util.Date;

public class SequenceCounter {
    private Long id;

    private String name;

    private Integer counter;

    private Date changeDate;

    private Integer resetMode;

    private Integer initValue;

    private Integer increment;

    private Integer seqLength;

    private String seperator;

    private String prefix;

    private String suffix;

    private String dateFormat;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Integer getResetMode() {
        return resetMode;
    }

    public void setResetMode(Integer resetMode) {
        this.resetMode = resetMode;
    }

    public Integer getInitValue() {
        return initValue;
    }

    public void setInitValue(Integer initValue) {
        this.initValue = initValue;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public Integer getSeqLength() {
        return seqLength;
    }

    public void setSeqLength(Integer seqLength) {
        this.seqLength = seqLength;
    }

    public String getSeperator() {
        return seperator;
    }

    public void setSeperator(String seperator) {
        this.seperator = seperator == null ? null : seperator.trim();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix == null ? null : prefix.trim();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}