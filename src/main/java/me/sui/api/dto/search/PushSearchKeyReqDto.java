package me.sui.api.dto.search;

import me.sui.api.dto.base.BaseReqDto;

public class PushSearchKeyReqDto extends BaseReqDto {

	private Long studentId;// 学生ID
	private String text;//搜索文本
	private String categoryName;//分类名称
	private Long categoryId;//suime分类ID
	private String tagName;//tag名称
	private Long tagId;//suime tagId

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

}
