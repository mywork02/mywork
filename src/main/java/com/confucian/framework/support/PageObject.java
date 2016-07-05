package com.confucian.framework.support;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ice on 19/1/2016.
 */
public class PageObject<T> implements Serializable {

    /**
     * sid
     */
    private static final long serialVersionUID = 6841985745328822721L;

    /**
     * 起始位置,从0开始
     */
    private int start;

    /**
     * 偏移量
     */
    private int pageSize;

    /**
     * 数据
     */
    private List<T> data;

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 总页数
     */
    private int totalPageCount;

    /**
     * 当前页码
     */
    private int currentPageNo;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    /**
     * 是否有前一页
     */
    private boolean hasPreviousPage;

    /**
     * 是否是第一页
     */
    private boolean isStartPage;

    /**
     * 是否是最后页
     */
    private boolean isEndPage;

    /**
     * 构造方法，构造空页
     *
     * @param pageSize 偏移量
     */
    public PageObject(int pageSize) {
        this(0, 0, pageSize, Collections.<T>emptyList());
    }

    /**
     * 默认构造方法
     *
     * @param start     起始位置
     * @param totalSize 总记录数
     * @param pageSize  偏移量
     * @param data      数据
     */
    public PageObject(int start, int totalSize, int pageSize, List<T> data) {
        this.pageSize = pageSize;
        this.start = start;
        this.totalCount = totalSize;
        this.data = data;
        init();
    }

    /**
     * 取总记录数
     *
     * @return 总记录数
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 取偏移量
     *
     * @return 偏移量
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 取数据
     *
     * @return 数据
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 设置数据
     *
     * @param data 数据
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 取当前页码
     *
     * @return 当前页码
     */
    public int getCurrentPageNo() {
        return currentPageNo;
    }

    /**
     * 取是否有下一页
     *
     * @return 是否有下一页
     */
    @JsonIgnore
    public boolean hasNextPage() {
        return hasNextPage;
    }

    /**
     * 取是否有前一页
     *
     * @return 是否有前一页
     */
    @JsonIgnore
    public boolean hasPreviousPage() {
        return hasPreviousPage;
    }

    /**
     * 取起始位置
     *
     * @return 起始位置
     */
    @JsonIgnore
    public int getStart() {
        return start;
    }

    /**
     * 取总页数
     *
     * @return 总页数
     */
    public int getTotalPageCount() {
        return totalPageCount;
    }

    /**
     * 取是否是最后一页
     *
     * @return 是否是最后一页
     */
    @JsonIgnore
    public boolean isEndPage() {
        return isEndPage;
    }

    /**
     * 取是否是第一页
     *
     * @return 是否是第一页
     */
    @JsonIgnore
    public boolean isStartPage() {
        return isStartPage;
    }

    /**
     * 计算扩展属性
     */
    protected void init() {
        if (totalCount % pageSize == 0) { // 计算总页数
            this.totalPageCount = totalCount / pageSize;
        } else {
            this.totalPageCount = totalCount / pageSize + 1;
        }
        this.currentPageNo = (start / pageSize) + 1; // 计算当前页码
        this.hasNextPage = this.currentPageNo < this.totalPageCount; // 计算是否有下一页
        this.hasPreviousPage = this.currentPageNo > 1; // 计算是否有前页
        this.isStartPage = this.currentPageNo == 1; // 计算是否是第一页
        this.isEndPage = (this.currentPageNo == this.totalPageCount); // 计算是否是最后一页
    }

    /**
     * 静态方法,获取任一页第一条数据的位置,startIndex从0开始
     *
     * @param pageNo   页码
     * @param pageSize 偏移量
     * @return 对应起始位
     */
    public static int calculateStart(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }

    /**
     * 静态方法,根据起始位置和偏移量,获取对应页码
     *
     * @param start    起始位
     * @param pageSize 偏移量
     * @return 对应页码
     */
    public static int calculatePageNo(int start, int pageSize) {
        return start / pageSize + 1;
    }
}
