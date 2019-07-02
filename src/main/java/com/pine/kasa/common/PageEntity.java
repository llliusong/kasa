package com.pine.kasa.common;


/**
 * 用于接收传来的分页信息在程序内中传值使用
 */
public class PageEntity {

    /**
     * 当前页
     */
    private Integer pageNo;        //当前页
    /**
     * 每页大小 默认10条
     */
    private Integer pageSize;       //每页大小

    /**
     * 删除的条数
     */
    private Integer deleCounts;
    /**
     * 排序字段
     */
    private String orderField;            //排序字段
    /**
     * 排序方式 （asc/desc）可为空
     */
    private String order;           //排序方式 （asc/desc）
    /**
     * 搜索条件（本字段仅为传值使用，不代表表字段）可为空
     */
    private String search;          //搜索条件

    public PageEntity() {
        this.pageNo = pageNo == null ? 1:pageNo;
        this.pageSize = pageSize == null ? 10 :pageSize;
    }

    public PageEntity(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo == null || pageNo<0 ? 1 : pageNo;
    }

    public Integer getDeleCounts() {
        return deleCounts == null ? 0: deleCounts;
    }

    public void setDeleCounts(Integer deleCounts) {
        this.deleCounts = deleCounts;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo == null || pageNo<0 ? 1 : pageNo;
    }

    public Integer getPageSize() {
        return pageSize == null || pageSize<0 ? 10 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize == null || pageSize<0 ? 10 : pageSize;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "PageEntity{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", orderField='" + orderField + '\'' +
                ", order='" + order + '\'' +
                ", search='" + search + '\'' +
                '}';
    }
}

