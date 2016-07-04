/**
 * StudentDto.java
 * 2015年12月4日
 */
package com.suime.library.dto;

import java.io.Serializable;

import com.confucian.framework.utils.StringUtils;
import com.suime.common.support.Configure;
import com.suime.context.model.Student;

/**
 * StudentDto
 *
 * @author origin
 */
public class StudentDto implements Serializable {

	/**
	 * sid
	 */
	private static final long serialVersionUID = -4667501431656762362L;
	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * id
	 */
	private Long id;

	/**
	 * student头像 存放url
	 */
	private String avatar;

	/**
	 * cellphone
	 */
	private String cellphone;

	/**
	 * constructor
	 */
	public StudentDto() {

	}

	/**
	 * constructor
	 */
	public StudentDto(Student student) {
		this.setAvatar(student.getAvatar());
		this.setCellphone(student.getCellphone());
		this.setId(student.getId());
		this.setNickName(student.getNickName());
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 如果用户没有头像则返回默认头像
	 * @return 处理过后的用户头像
	 */
	public String getAvatar() {
		if (StringUtils.isBlank(avatar)) {
			return Configure.getPropertyValue("student.avatar.default");
		}
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

}
