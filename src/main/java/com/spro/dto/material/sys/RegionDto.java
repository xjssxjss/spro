package com.spro.dto.material.sys;

import java.util.List;

public class RegionDto {

    private String label;
    private String value;
    private List<RegionDto> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<RegionDto> getChildren() {
        return children;
    }

    public void setChildren(List<RegionDto> children) {
        this.children = children;
    }
}
