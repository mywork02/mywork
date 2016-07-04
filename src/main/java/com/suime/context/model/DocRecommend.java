package com.suime.context.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * wenku_doc_recommend 实体类
 * Created by ice 29/04/2016.
 */
public class DocRecommend implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 8965538305666122583L;

	/**
	 * id
	 */
	private Integer id;

	/**
	 * docIds
	 */
	private String docIds;

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