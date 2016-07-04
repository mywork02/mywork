package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_user_recommend 实体类
 * Created by ice 13/05/2016.
 */
public class UserRecommend implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -4975406442133853986L;

	/**
	 * id
	 */
	private Integer id;

	/**
	 * docIds
	 */
	private String docIds;

	/**
	 * adIds
	 */
	private String adIds;

	/**
	 * createdAt
	 */
	private Timestamp createdAt;

	/**
	 * updatedAt
	 */
	private Timestamp updatedAt;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setDocIds(String docIds) {
		this.docIds = docIds;
	}

	public String getDocIds() {
		return docIds;
	}

	public void setAdIds(String adIds) {
		this.adIds = adIds;
	}

	public String getAdIds() {
		return adIds;
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

}