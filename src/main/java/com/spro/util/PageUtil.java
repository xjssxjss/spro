package com.spro.util;

/**
 * 分页util类
 * create by sean
 */
public class PageUtil {
    /**
     * 分页页码
     */
    private Integer currentPage = 1;

    /**
     * 每页记录数
     */
    private Integer pageSize = 10;

    public PageUtil(Integer currentPage,Integer pageSize){
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
