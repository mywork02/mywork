package com.suime.context.model;

import java.util.Date;
import java.io.Serializable;

/**
 * wenji_school 实体类
 * Created by ice 15/02/2016.
 */
public class School implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 1508022448904319112L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * name
	 */
	private String name;

	/**
	 * spell
	 */
	private String spell;

	/**
	 * cityId
	 */
	private Long cityId;

	/**
	 * provinceId
	 */
	private Long provinceId;

	/**
	 * actived
	 */
	private Byte actived;

	/**
	 * createdAt
	 */
	private Date createdAt;

	/**
	 * updatedAt
	 */
	private Date updatedAt;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getSpell() {
		return spell;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setActived(Byte actived) {
		this.actived = actived;
	}

	public Byte getActived() {
		return actived;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

}