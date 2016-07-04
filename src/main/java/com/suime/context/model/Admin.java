package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenji_admin 实体类
 * Created by ice 15/02/2016.
 */
public class Admin implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -3463847643859418349L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * cellphone
	 */
	private String cellphone;

	/**
	 * password
	 */
	private String password;

	/**
	 * name
	 */
	private String name;

	/**
	 * sex
	 */
	private Byte sex;

	/**
	 * status
	 */
	private Byte status;

	/**
	 * roleId
	 */
	private Long roleId;

	/**
	 * createdAt
	 */
	private Timestamp createdAt;

	/**
	 * updatedAt
	 */
	private Timestamp updatedAt;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public Byte getSex() {
		return sex;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getStatus() {
		return status;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getRoleId() {
		return roleId;
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