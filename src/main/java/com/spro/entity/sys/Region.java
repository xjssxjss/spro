package com.spro.entity.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Region {

    @JsonIgnore
    private String regionId;

    @JsonIgnore
    private String regionName;

    @JsonIgnore
    private String regionShortName;

    @JsonIgnore
    private String regionCode;

    @JsonIgnore
    private String regionParentId;

    @JsonIgnore
    private Integer regionLevel;

    private String label;

    private String value;

    private List<Region> children;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public String getRegionShortName() {
        return regionShortName;
    }

    public void setRegionShortName(String regionShortName) {
        this.regionShortName = regionShortName == null ? null : regionShortName.trim();
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode == null ? null : regionCode.trim();
    }

    public String getRegionParentId() {
        return regionParentId;
    }

    public void setRegionParentId(String regionParentId) {
        this.regionParentId = regionParentId == null ? null : regionParentId.trim();
    }

    public Integer getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }

    public List<Region> getChildren() {
        return children;
    }

    public void setChildren(List<Region> children) {
        this.children = children;
    }

    public String getLabel() {
        return regionName;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return regionId;
    }

    public void setValue(String value) {
        this.value = value;
    }
}