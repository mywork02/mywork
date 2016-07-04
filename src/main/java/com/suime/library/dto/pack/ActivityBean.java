package com.suime.library.dto.pack;

import com.suime.context.model.Activity;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by origin on 2015/12/23.
 */
public class ActivityBean {

	/**
	 * 唯一id
	 */
	private Long id;
	/**
	 * 背景
	 */
	private String background;

	/**
	 * 活动code
	 */
	private String code;

	/**
	 * 关键词 ，分号隔开
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
	 * 活动标题
	 */
	private String title;

	/**
	 * 活动子标题
	 */
	private String subTitles;

	/**
	 * 顺序,值越大越靠前
	 */
	private Integer orderNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setPcBanner(String pcBanner) {
		this.pcBanner = pcBanner;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * convert to activity
	 * @return activity
	 */
	public Activity convertToActivity() {
		Activity activity = new Activity();
		activity.setCode(this.code);
		activity.setBackground(this.background);
		String tempWord = this.keyWords;
		if (StringUtils.isNotBlank(tempWord)) {
			tempWord = tempWord.replace("，", ",");
		}
		activity.setKeyWords(tempWord);
		activity.setMobileBanner(this.mobileBanner);
		activity.setPcBanner(this.pcBanner);
		activity.setTitle(this.title);
		String tempSubTitles = this.subTitles;
		if (StringUtils.isNotBlank(tempSubTitles)) {
			tempSubTitles = tempSubTitles.replace("，", ",");
		}
		activity.setSubTitles(tempSubTitles);
		activity.setId(this.id);
		if (this.orderNum != null) {
			activity.setOrderNum(this.orderNum);
		} else {
			activity.setOrderNum(0);
		}
		return activity;
	}
}
