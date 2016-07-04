package com.suime.context.model;

import java.io.Serializable;

/**
 * wenji_file_state 实体类
 * Created by ice 15/02/2016.
 */
public class FileState implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -6279555010722675823L;

	/**
	 * state
	 */
	private Long state;

	/**
	 * annotation
	 */
	private String annotation;

	/**
	 * id
	 */
	private Long id;

	public void setState(Long state) {
		this.state = state;
	}

	public Long getState() {
		return state;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}