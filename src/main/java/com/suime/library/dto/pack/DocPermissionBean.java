package com.suime.library.dto.pack;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * permissionBean
 * @author ice
 */
public class DocPermissionBean {

	/**
	 * 数组ID 需要修改权限的数据ID
	 */
	private Long[] ids;

	/**
	 * studentId
	 */
	@JsonIgnore
	private Long studentId;

	/**
	 * permission
	 */
	@JsonIgnore
	private Byte permission;

	public Byte getPermission() {
		return permission;
	}

	public void setPermission(Byte permission) {
		this.permission = permission;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

}
