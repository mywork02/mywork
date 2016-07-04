package com.suime.library.dto;

import java.io.Serializable;

/**
 * Created by origin on 2015/12/15.
 */
public class AdminDto implements Serializable {

	/**
	 * sid
	 */
	private static final long serialVersionUID = -2717024362462466475L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 移动电话
	 */
	private String cellphone;

	/**
	 * 用户名称
	 */
	private String name;

	/**
	 * 性别
	 */
	private Byte sex;

	/**
	 * 账号状态，0:删除状态,1:未启用状态,2:启用状态
	 */
	private Byte status;

	/**
	 * 角色名
	 */
	private String roleName;

	/**
	 * 角色code
	 */
	private String roleCode;

	/**
	 * 角色id
	 */
	private Long roleId;

	/**
	 * constructor
	 */
	public AdminDto() {
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
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

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
