package com.suime.library.dto;

import com.suime.context.model.Tags;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * tag dto
 */
public class TagsDto implements Serializable{


    private static final long serialVersionUID = 2898658506562032852L;
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
     * 是否有效(用于软删除),1:有效 0:无效.默认为0
     */
    private Byte actived;

    /**
     * 来源,1:管理员添加,2:用户添加
     */
    private Byte source;

    /**
     * 标签文本
     */
    private String text;

    /**
     * 使用次数
     */
    private Integer useCount;

    /**
     * 添加用户
     */
    private Long userId;

    /**
     * 推荐标签对应的photo,for移动端使用
     */
    private String photoUrl;

    /**
     * 默认构造器
     */
    public TagsDto() {

    }

    /**
     * model 转 dto
     */
    public TagsDto(Tags tag) {
        if (tag != null) {
            this.id = tag.getId();
            this.createdAt = tag.getCreatedAt();
            this.updatedAt = tag.getUpdatedAt();
            this.actived = tag.getActived();
            this.source = tag.getSource();
            this.text = tag.getText();
            this.useCount = tag.getUseCount();
            this.userId = tag.getUserId();
            this.setPhotoUrl(tag.getPhotoUrl());
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

    public Byte getActived() {
        return actived;
    }

    public void setActived(Byte actived) {
        this.actived = actived;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
