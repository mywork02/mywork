package com.suime.library.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ice on 18/5/16.
 */
public class DirectoryDto {
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
     * 是否有效(用于软删除),1:有效 0:无效.默认为0
     */
    private Byte actived;

    /**
     * name
     */
    private String name;

    /**
     * parentId
     */
    private Long parentId;

    /**
     * studentId
     */
    private Long studentId;

    /**
     * 权限
     */
    private Byte permission;

    /**
     * 来源，1：用户创建，2：接收
     */
    private Byte source;
    
    /**
     * 描述
     */
    private String intro;
    
    /**
     * 子目录
     * */
    private List<DirectoryDto> subDirectorys;
    
    /**
     * 此目录下面的文档
     * */
    private List<StudentDocumentDto> studentDocuments;
    
    /**
     * 父级目录Name
     * @return
     */
    private String parentName;
    
    
    
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setActived(Byte actived) {
        this.actived = actived;
    }

    public Byte getActived() {
        return actived;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setPermission(Byte permission) {
        this.permission = permission;
    }

    public Byte getPermission() {
        return permission;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Byte getSource() {
        return source;
    }

	public List<DirectoryDto> getSubDirectorys() {
		return subDirectorys;
	}

	public void setSubDirectorys(List<DirectoryDto> subDirectorys) {
		this.subDirectorys = subDirectorys;
	}

	public List<StudentDocumentDto> getStudentDocuments() {
		return studentDocuments;
	}

	public void setStudentDocuments(List<StudentDocumentDto> studentDocuments) {
		this.studentDocuments = studentDocuments;
	}
    
}
