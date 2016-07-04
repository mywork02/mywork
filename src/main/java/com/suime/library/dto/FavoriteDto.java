package com.suime.library.dto;

import com.suime.context.model.Favorite;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by chenqy on 2016/4/20.
 */
public class FavoriteDto implements Serializable {

	/**
	 * sid
	 */
	private static final long serialVersionUID = -2115072972017392448L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 创建时间
	 */
	private Timestamp createdAt;

	/**
	 * 更新时间
	 */
	private Timestamp updatedAt;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * intro
	 */
	private String intro;
	/**
	 * 学生名字
	 */
	private String studentName;
	/**
	 * 文档数量
	 */
	private String documentCount;
	/**
	 *关注人数
	 */
	private String concernCount;
	
	

	/**
	 * constructor
	 */
	public FavoriteDto() {

	}

	/**
	 * constructor
	 */
	public FavoriteDto(Favorite favorite) {
		if (favorite != null) {
			setName(favorite.getName());
			setCreatedAt(favorite.getCreatedAt());
			setUpdatedAt(favorite.getUpdatedAt());
			setId(favorite.getId());
			this.setIntro(favorite.getIntro());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getDocumentCount() {
		return documentCount;
	}

	public void setDocumentCount(String documentCount) {
		this.documentCount = documentCount;
	}

	public String getConcernCount() {
		return concernCount;
	}

	public void setConcernCount(String concernCount) {
		this.concernCount = concernCount;
	}


}
