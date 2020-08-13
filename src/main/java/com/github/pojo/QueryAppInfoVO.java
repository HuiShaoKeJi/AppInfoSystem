package com.github.pojo;

/**
 * 查询App基础信息VO
 */
public class QueryAppInfoVO {

    private String querySoftwareName;//软件名称
    private Integer queryStatus;//app状态
    private Integer queryFlatformId;//所属平台id
    private Integer queryCategoryLevel1;//一级分类
    private Integer queryCategoryLevel2;//二级分类
    private Integer queryCategoryLevel3;//三级分类
    private Integer pageIndex;//当前页
    private Integer startIndex;//起始页
    private Integer pageSize;//每页显示的条数

    public String getQuerySoftwareName() {
        return querySoftwareName;
    }

    public void setQuerySoftwareName(String querySoftwareName) {
        this.querySoftwareName = querySoftwareName;
    }

    public Integer getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(Integer queryStatus) {
        this.queryStatus = queryStatus;
    }

    public Integer getQueryFlatformId() {
        return queryFlatformId;
    }

    public void setQueryFlatformId(Integer queryFlatformId) {
        this.queryFlatformId = queryFlatformId;
    }

    public Integer getQueryCategoryLevel1() {
        return queryCategoryLevel1;
    }

    public void setQueryCategoryLevel1(Integer queryCategoryLevel1) {
        this.queryCategoryLevel1 = queryCategoryLevel1;
    }

    public Integer getQueryCategoryLevel2() {
        return queryCategoryLevel2;
    }

    public void setQueryCategoryLevel2(Integer queryCategoryLevel2) {
        this.queryCategoryLevel2 = queryCategoryLevel2;
    }

    public Integer getQueryCategoryLevel3() {
        return queryCategoryLevel3;
    }

    public void setQueryCategoryLevel3(Integer queryCategoryLevel3) {
        this.queryCategoryLevel3 = queryCategoryLevel3;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
