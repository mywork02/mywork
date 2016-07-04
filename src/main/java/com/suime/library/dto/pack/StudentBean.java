package com.suime.library.dto.pack;

/**
 * studentBean
 * @author ice
 */
public class StudentBean {

	/**
	 * 唯一id
	 */
	private Long id;
	/**
	 * 移动电话
	 */
	private String cellphone;

	/**
	 * 密码
	 */
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
