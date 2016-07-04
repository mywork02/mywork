package com.suime.library.dto.pack;

import com.confucian.mybatis.support.scope.OrderType;
import com.suime.context.model.support.FileType;

import java.util.List;

/**
 * searchBean
 *
 * @author ice
 */
public class SearchBean {

    /**
     * 搜索文字
     */
    private String text;

    /**
     * categoryId
     */
    private Long categoryId;

    /**
     * fileTypes,预留文档类型,如后期 需求不需要则可完全删除
     */
    private List<FileType> fileTypes;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序方式
     */
    private OrderType sort;

    /**
     * 专业code,该条件 用于 默认推荐文档,如果categoryId存在时,该条件不起作用
     */
    private String majorGroupCode;

    /**
     * school id
     */
    private Long schoolId;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 关键词
     */
    private String keyWord;

    /**
     * 关键词两端是否需要添加分隔符，根据活动关键词搜索文档时使用
     */
    private boolean isSplit;

    /**
     * 文档id列表
     */
    private List<Long> ids;

    /**
     * 目录
     * */
    private Long directoryId;


    /**
     * 权限
     * @return
     */
    private Byte permission;
    
    /**
     * 学校数组
     * @return
     */
    private Long[] schoolIds;
    
    
    
    

    public Long[] getSchoolIds() {
		return schoolIds;
	}

	public void setSchoolIds(Long[] schoolIds) {
		this.schoolIds = schoolIds;
	}

	public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<FileType> getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(List<FileType> fileTypes) {
        this.fileTypes = fileTypes;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public OrderType getSort() {
        return sort;
    }

    public void setSort(OrderType sort) {
        this.sort = sort;
    }

    public String getMajorGroupCode() {
        return majorGroupCode;
    }

    public void setMajorGroupCode(String majorGroupCode) {
        this.majorGroupCode = majorGroupCode;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public boolean getIsSplit() {
        return isSplit;
    }

    public void setIsSplit(boolean isSplit) {
        this.isSplit = isSplit;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Long directoryId) {
        this.directoryId = directoryId;
    }

    public Byte getPermission() {
        return permission;
    }

    public void setPermission(Byte permission) {
        this.permission = permission;
    }
    

}
