package com.suime.context.model;
import java.util.Date;
import java.io.Serializable;

/**
 * wenji_school_number 实体类
 * Created by ice 18/05/2016
 * lsw
 */ 
public class SchoolNumber implements Serializable {
	/**
	 * id
	 */
	private Long id;  

	/**
	 * schoolId   学校ID
	 */
	private Long schoolId;

	/**
	 * number    人数
	 */
	private Integer number;
	
	/**
	 *docNumber 文档数量
	 */
	private Integer docNumber;
	
	
	

	public Integer getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(Integer docNumber) {
		this.docNumber = docNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	

}