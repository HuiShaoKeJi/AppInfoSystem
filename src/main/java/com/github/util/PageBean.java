package com.github.util;


import java.util.List;

/**
 * 工具类   -> 分页查询
 */
public class PageBean<E> {
    /**起始下标*/
    private int startIndex;
    /**当前页*/
    private int currentPage;
    /**总记录数*/
    private int totalCount;
    /**总页数*/
    private int totalPage;
    /**每页显示的数量*/
    private int pageSize;
    /**本页查询到的数据 E:代表任意类型的数据*/
    private List<E> result;

    public int getStartIndex() {
        /*计算起始下标*/
        return (currentPage-1)*pageSize;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        /*计算总页数*/
        return totalCount % pageSize != 0 ? (totalCount / pageSize)+1 : totalCount / pageSize;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<E> getResult() {
        return result;
    }

    public void setResult(List<E> result) {
        this.result = result;
    }
}
