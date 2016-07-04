package com.suime.library.dto;

import java.io.Serializable;

/**
 * Created by ice on 1/4/2016.
 */
public class TabBadgeDto implements Serializable{

    private static final long serialVersionUID = 3925505723316771652L;
    /**
     * 收藏
     */
    private Integer favorite = 0;

    /**
     * 文档
     */
    private Integer wenku = 0;

    /**
     * 消息
     */
    private Integer message = 0;

    /**
     * 关注
     */
    private Integer profile = 0;

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public Integer getWenku() {
        return wenku;
    }

    public void setWenku(Integer wenku) {
        this.wenku = wenku;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getProfile() {
        return profile;
    }

    public void setProfile(Integer profile) {
        this.profile = profile;
    }
}
