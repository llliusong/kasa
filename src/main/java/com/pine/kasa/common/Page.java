package com.pine.kasa.common;

import com.github.pagehelper.PageInfo;

/**
 * 向前台统一返回分页相关
 */
public class Page {
    private Integer pre_page = 1;       //上一页
    private Integer current_page;       //当前页
    private Integer next_page;          //下一页
    private Integer total_page;         //总页数
    private Long total;

    public Page(PageInfo pageInfo) {
        this.pre_page = pageInfo.getPrePage();
        this.current_page = pageInfo.getPageNum();
        this.next_page = pageInfo.getNextPage();
        this.total_page = pageInfo.getNavigateLastPage();
        this.total = pageInfo.getTotal();
    }

    public Page(Integer pre_page, Integer current_page, Integer next_page, Integer total_page, Long total) {
        this.pre_page = pre_page;
        this.current_page = current_page;
        this.next_page = next_page;
        this.total_page = total_page;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pre_page=" + pre_page +
                ", current_page=" + current_page +
                ", next_page=" + next_page +
                ", total_page=" + total_page +
                ", total=" + total +
                '}';
    }

    public Integer getPre_page() {
        return pre_page;
    }

    public void setPre_page(Integer pre_page) {
        this.pre_page = pre_page;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public Integer getNext_page() {
        return next_page;
    }

    public void setNext_page(Integer next_page) {
        this.next_page = next_page;
    }

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
