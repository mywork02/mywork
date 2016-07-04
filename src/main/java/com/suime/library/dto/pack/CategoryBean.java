package com.suime.library.dto.pack;

import com.suime.context.model.Category;

import java.sql.Timestamp;

/**
 * category bean
 * @author zhibaokang
 *
 */
public class CategoryBean {
	
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
	 * 编码
	 */
	private String code;
	
	/**
	 * 等级
	 */
	private Byte level;
	
	/**
	 * 目录名
	 */
	private String name;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 父目录
	 */
	private Long parentId;
	
	/**
	 * 是否有效(用于软删除),1:有效 0:无效.默认为0
	 */
	private Byte actived;
	
	/**
	 * bean/model 转换
	 * @return model
	 */
	public Category transToCategory(){
		Category category = new Category();
		category.setCode(this.code);
		category.setCreatedAt(this.createdAt);
		category.setId(this.id);
		category.setLevel(this.level);
		category.setName(this.name);
		category.setRemarks(this.remarks);
		category.setUpdatedAt(this.updatedAt);
		category.setActived(this.actived);
		return category;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Byte getActived() {
		return actived;
	}

	public void setActived(Byte actived) {
		this.actived = actived;
	}
	
	

}
