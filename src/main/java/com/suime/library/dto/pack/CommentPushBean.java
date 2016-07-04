package com.suime.library.dto.pack;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 评论推送
 * Created by ice on 23/2/2016.
 */
public class CommentPushBean {

	/**
	 * id
	 */
	private Long id;

	/**
	 * name
	 */
	private String name;

	/**
	 * extension
	 */
	private String extension;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
