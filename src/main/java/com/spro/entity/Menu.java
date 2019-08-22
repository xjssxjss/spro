package com.spro.entity;

import com.spro.util.SerializeUtil;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Menu implements Serializable{
    private Integer id;

    private String code;

    private String name;

    private String type;

    private String path;

    private String icon;

    private Boolean valid;

    private Integer parentId;

    private String remark;

    private String level;

    private List<Menu> children;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Menu menu = new Menu();
        menu.setCode("234");
        Object a = menu;
        String s = SerializeUtil.serializeToString(a);
        // Menu unserialize = (Menu) SerializeUtil.unserialize(new String(s).getBytes());
        Menu unserialize1 = (Menu) SerializeUtil.unserialize(s);
        System.out.println(unserialize1);
        System.out.println(s);
        Menu unserialize = (Menu) SerializeUtil.unserialize(s);
        System.out.println(unserialize);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", path='" + path + '\'' +
                ", icon='" + icon + '\'' +
                ", valid=" + valid +
                ", parentId=" + parentId +
                ", remark='" + remark + '\'' +
                ", level='" + level + '\'' +
                ", children=" + children +
                '}';
    }
}