package com.whyyu.indoormanagementserver.util;

/**
 * @author WhyYu
 * @Description 分页请求参数包装
 * @Date 2021/7/26 17:55
 */
public class PageParam {
    private Integer page;
    private Integer size;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageParam{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
