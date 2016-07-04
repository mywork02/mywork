package com.suime.library.dto;

import java.io.Serializable;

import com.suime.context.model.Role;

/**
 * Created by origin on 2015/12/16.
 */
public class RoleModelDto implements Serializable {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 4546074143435287241L;

	/**
	 * 唯一id
	 */
	private Long id;

	/**
	 * 角色code
	 */
	private String code;

	/**
	 * 角色名
	 */
	private String name;

	/**
	 * constructor
	 */
	public RoleModelDto() {
	}

	/**
	 * constructor
	 */
	public RoleModelDto(Role role) {
		if (role != null) {
			this.id = role.getId();
			this.code = role.getCode();
			this.name = role.getName();
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
