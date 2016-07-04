package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenji_file 实体类
 * Created by ice 15/02/2016.
 */
public class File implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 4395416378926137015L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * key
	 */
	private String key;

	/**
	 * length
	 */
	private Long length;

	/**
	 * pageCount
	 */
	private Integer pageCount;

	/**
	 * state
	 */
	private Long state;

	/**
	 * createdAt
	 */
	private Timestamp createdAt;

	/**
	 * updatedAt
	 */
	private Timestamp updatedAt;

	/**
	 * preview
	 */
	private Byte preview;

	/**
	 * hasThumbnail
	 */
	private Byte hasThumbnail;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public Long getLength() {
		return length;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Long getState() {
		return state;
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

	public void setPreview(Byte preview) {
		this.preview = preview;
	}

	public Byte getPreview() {
		return preview;
	}

	public void setHasThumbnail(Byte hasThumbnail) {
		this.hasThumbnail = hasThumbnail;
	}

	public Byte getHasThumbnail() {
		return hasThumbnail;
	}

}