package com.suime.library.dto.pack;

/**
 * Created by chenqy on 2016/4/20.
 */
public class FavoriteBean {

	/**
	 * id
	 */
	private Long id;

	/**
	 * name
	 */
	private String name;

	/**
	 * student id
	 */
	private Long studentId;

	/**
	 * intro
	 */
	private String intro;

	/**
	 *学生名字
	 * */
	private String studentName;
	/**
	 *文档数量
	 * */
	private String document_count;
	/**
	 *关注人数
	 * */
	private String concern_count;

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getDocument_count() {
		return document_count;
	}

	public void setDocument_count(String document_count) {
		this.document_count = document_count;
	}

	public String getConcern_count() {
		return concern_count;
	}

	public void setConcern_count(String concern_count) {
		this.concern_count = concern_count;
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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

}
