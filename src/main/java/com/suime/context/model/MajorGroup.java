package com.suime.context.model;

import java.io.Serializable;

/**
 * wenji_major_group_1 实体类
 * Created by ice 15/02/2016.
 */
public class MajorGroup implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 6180063777155114091L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * code
	 */
	private String code;

	/**
	 * name
	 */
	private String name;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}