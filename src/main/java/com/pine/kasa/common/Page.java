package com.pine.kasa.common;

import com.github.pagehelper.PageInfo;
import lombok.Data;

/**
 * 向前台统一返回分页相关
 */
@Data
public class Page {
    /**
     * 上一页
     */
    private Integer prePage = 1;
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 下一页
     */
    private Integer nextPage;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 总条数
     */
    private Long total;

    public Page(PageInfo pageInfo) {
        this.prePage = pageInfo.getPrePage();
        this.currentPage = pageInfo.getPageNum();
        this.nextPage = pageInfo.getNextPage();
        this.totalPage = pageInfo.getNavigateLastPage();
        this.total = pageInfo.getTotal();
    }

    public Page(Integer prePage, Integer currentPage, Integer nextPage, Integer totalPage, Long total) {
        this.prePage = prePage;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
        this.totalPage = totalPage;
        this.total = total;
    }
}
