package com.suime.library.dto.pack;

import com.suime.context.model.Category;

/**
 * TagsBean
 *
 * @author zhibaokang
 */
public class TagsBean {

    /**
     * id
     */
    private Long id;

    /**
     * 标签文本
     */
    private String text;

    /**
     * 标签类型,1:为普通类型,此时 category 为标签所属分类;9:为搜索类型,此时的标签是用来搜索的,category 为搜索分类
     */
    private Byte type;

    /**
     * 来源,1:管理员添加,2:用户添加
     */
    private Byte source;

    /**
     * 所属categoryId
     */
    private Long categoryId;

    /**
     * 如果存在category 则使用category
     */
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
