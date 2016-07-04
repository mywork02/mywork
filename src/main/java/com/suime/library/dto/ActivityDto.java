package com.suime.library.dto;

import com.alibaba.fastjson.JSONObject;
import com.suime.context.model.Activity;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by origin on 2015/12/23.
 */
@ApiModel
public class ActivityDto implements Serializable {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 8506784308845255216L;

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
	 * 背景
	 */
	private String background;

	/**
	 * 活动code
	 */
	private String code;

	/**
	 * 关键词
	 */
	private String keyWords;

	/**
	 * 移动端banner
	 */
	private String mobileBanner;

	/**
	 * pc端banner
	 */
	private String pcBanner;

	/**
	 * 活动子标题
	 */
	private String subTitles;

	/**
	 * 活动标题
	 */
	private String title;

	/**
	 * 顺序,值越大越靠前
	 */
	private Integer orderNum;

	/**
	 * 文档列表
	 */
	private List<JSONObject> datas;

	/**
	 * constructor
	 */
	public ActivityDto() {

	}

	/**
	 * constructor
	 */
	public ActivityDto(Activity activity) {
		this.background = activity.getBackground();
		this.code = activity.getCode();
		this.keyWords = activity.getKeyWords();
		this.mobileBanner = activity.getMobileBanner();
		this.pcBanner = activity.getPcBanner();
		this.id = activity.getId();
		this.createdAt = activity.getCreatedAt();
		this.updatedAt = activity.getUpdatedAt();
		this.title = activity.getTitle();
		this.subTitles = activity.getSubTitles();
		this.orderNum = activity.getOrderNum();
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

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getMobileBanner() {
		return mobileBanner;
	}

	public void setMobileBanner(String mobileBanner) {
		this.mobileBanner = mobileBanner;
	}

	public String getPcBanner() {
		return pcBanner;
	}

	public void setPcBanner(String pcBanner) {
		this.pcBanner = pcBanner;
	}

	public String getSubTitles() {
		return subTitles;
	}

	public void setSubTitles(String subTitles) {
		this.subTitles = subTitles;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public List<JSONObject> getDatas() {
		return datas;
	}

	public void setDatas(List<JSONObject> datas) {
		this.datas = datas;
	}
}
