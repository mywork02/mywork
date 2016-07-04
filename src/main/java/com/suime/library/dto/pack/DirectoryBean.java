package com.suime.library.dto.pack;

/**
 * directory 封装 bean
 * Created by origin on 2016/1/21.
 */
/**
 * @author lishiwei
 *
 */
public class DirectoryBean {

	/**
	 * id
	 */
	private Long id;

	/**
	 * name
	 */
	private String name;

	/**
	 * parent id
	 */
	private Long parentId;

	/**
	 * student id
	 */
	private Long studentId;

	/**
	 * source
	 */
	private String intro;
	
	private Byte source;
	
	/**
	 * 权限
	 * @return
	 */
	private Byte permission;
	
	

	public Byte getPermission() {
		return permission;
	}

	public void setPermission(Byte permission) {
		this.permission = permission;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Byte getSource() {
		return source;
	}

	public void setSource(Byte source) {
		this.source = source;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
}
