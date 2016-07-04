package com.suime.library.dto;

import com.suime.context.model.Banner;
import com.suime.library.helper.CompareHelper;

import net.sf.json.JSONObject;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by origin on 2016/1/15.
 */
public class BannerDto implements Serializable {

	/**
	 * sid
	 */
	private static final long serialVersionUID = -5193136161863944502L;

	/**
	 * 图片地址
	 */
	private String picUrl;

	/**
	 * 类型.如web，wenkuSearch等
	 */
	private String type;

	/**
	 * 扩展key,用于移动端获取extra数据使用
	 */
	private String extraKey;

	/**
	 * 扩展内容
	 */
	private String extra;

	/**
	 * constructor
	 */
	public BannerDto() {

	}

	/**
	 * constructor
	 */
	public BannerDto(Banner banner) {
		if (banner != null) {
			this.setType(banner.getType());
			this.setPicUrl(banner.getPicUrl());
			this.setExtraKey(banner.getExtraKey());
			this.setExtra(banner.getExtra());
		}
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setExtraKey(String extraKey) {
		this.extraKey = extraKey;
	}

	/**
	 * 封装 extra 为json对象返回 
	 * @return json
	 */
	public Object getExtra() {
		JSONObject result = new JSONObject();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if (StringUtils.startsWith(request.getServletPath(), "/m2")) {
			String version = request.getHeader("SUI_Version");
			int compareVersion = CompareHelper.compareVersion(version, "1.1.4");
			if (compareVersion < 0) {
				if ("wenkuSearch".equals(this.type)) {
					result.put("url", this.extra);
				}
			}
		}
		result.put(this.extraKey, this.extra);
		return result;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}
