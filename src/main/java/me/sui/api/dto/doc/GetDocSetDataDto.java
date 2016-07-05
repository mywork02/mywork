/**
 * 
 */
package me.sui.api.dto.doc;

import java.io.Serializable;
import java.util.Date;

/**
 * @author davidclj
 *
 */
public class GetDocSetDataDto implements Serializable {
	/**
	 * 文集ID
	 */
	private Long id;

	/**
	 * 文集名称
	 */
	private String setName;

	/**
	 * 文集说明
	 */
	private String setDesc;

	/**
	 * 文集封面
	 */
	private String setCover;

	/**
	 * 学生ID
	 */
	private Long studentId;

	/**
	 * 是否有效(用于软删除),1:有效 0:无效.默认为0
	 */
	private Integer actived;

	/**
	 * 创建时间
	 */
	private Date createdAt;

	/**
	 * 
	 */
	private Date updatedAt;

	/**
	 * @see #id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @see #id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @see #setName
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * @see #setName
	 */
	public void setSetName(String setName) {
		this.setName = setName;
	}

	/**
	 * @see #setDesc
	 */
	public String getSetDesc() {
		return setDesc;
	}

	/**
	 * @see #setDesc
	 */
	public void setSetDesc(String setDesc) {
		this.setDesc = setDesc;
	}

	/**
	 * @see #setCover
	 */
	public String getSetCover() {
		return setCover;
	}

	/**
	 * @see #setCover
	 */
	public void setSetCover(String setCover) {
		this.setCover = setCover;
	}

	/**
	 * @see #studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @see #studentId
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * @see #actived
	 */
	public Integer getActived() {
		return actived;
	}

	/**
	 * @see #actived
	 */
	public void setActived(Integer actived) {
		this.actived = actived;
	}

	/**
	 * @see #createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @see #createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @see #updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @see #updatedAt
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
