package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_banner 实体类
 * Created by ice 05/05/2016.
 */
public class Banner implements Serializable {
    /**
     * sid
     */
    private static final long serialVersionUID = 7085312012697053982L;

    /**
     * id
     */
    private Long id;

    /**
     * 扩展key,用于移动端获取extra数据使用
     */
    private String extraKey;

    /**
     * 扩展内容
     */
    private String extra;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    private Timestamp updatedAt;

    /**
     * 类型.如web，wenkuSearch等
     */
    private String type;

    /**
     * 版本
     */
    private String version;

    /**
     * 目标：1为web端，2为移动端，默认为1
     */
    private Integer target;

    /**
     * 是否有效(用于软删除),1:有效 0:无效.默认为0
     */
    private Byte actived;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setExtraKey(String extraKey) {
        this.extraKey = extraKey;
    }

    public String getExtraKey() {
        return extraKey;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getExtra() {
        return extra;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getTarget() {
        return target;
    }

    public Byte getActived() {
        return actived;
    }

    public void setActived(Byte actived) {
        this.actived = actived;
    }
}