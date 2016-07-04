package com.suime.library.dto;

import java.io.Serializable;

/**
 * Created by origin on 2015/12/29.
 */
public class StudentDocumentStatisticDto implements Serializable{

    private static final long serialVersionUID = -2099508155553120754L;
    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类code
     */
    private String categoryCode;

    /**
     * 分类层级
     */
    private Byte categoryLevel;

    /**
     * 分类文档数
     */
    private Long count;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Byte getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Byte categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
