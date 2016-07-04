package com.suime.library.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.suime.context.model.Category;

/**
 * 目录 dto
 *
 * @author zhibaokang
 */
public class CategoryDto implements Serializable {

    /**
     * sid
     */
    private static final long serialVersionUID = 8054468594614633234L;

    /**
     * id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    private Timestamp updatedAt;

    /**
     * 编码
     */
    private String code;

    /**
     * 等级
     */
    private Byte level;

    /**
     * 目录名
     */
    private String name;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 父目录
     */
    private Long parentId;

    /**
     * 是否有效(用于软删除),1:有效 0:无效.默认为0
     */
    private Byte actived;

    /**
     * children
     */
    private List<CategoryDto> children;

    /**
     * 默认构造函数
     */
    public CategoryDto() {

    }

    /**
     * dto 转换构造函数
     *
     * @param category
     */
    public CategoryDto(Category category) {
        if (category != null) {
            this.id = category.getId();
            this.createdAt = category.getCreatedAt();
            this.updatedAt = category.getUpdatedAt();
            this.code = category.getCode();
            this.level = category.getLevel();
            this.name = category.getName();
            this.remarks = category.getRemarks();
            this.actived = category.getActived();
            this.parentId = category.getParentId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Byte getActived() {
        return actived;
    }

    public void setActived(Byte actived) {
        this.actived = actived;
    }

    public List<CategoryDto> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryDto> children) {
        this.children = children;
    }

}
