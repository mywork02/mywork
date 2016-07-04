package com.suime.library.dto.pack;

import com.confucian.mybatis.support.scope.OrderType;
import com.suime.context.model.support.StudentStatusEnum;

/**
 * Created by origin on 2015/12/24.
 */
public class SearchAdminBean {

    /**
     * 搜索文字
     */
    private String text;

    /**
     * categoryId
     */
    private Long categoryId;

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
     * 文档名称
     */
    private String studentDocumentName;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 文档标签内容
     */
    private String tag;

    /**
     * 用户状态
     */
    private StudentStatusEnum studentStatus;

    /**
     * 用户手机号
     */
    private String cellphone;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色code
     */
    private String roleCode;

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

    public StudentStatusEnum getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(StudentStatusEnum studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStudentDocumentName() {
        return studentDocumentName;
    }

    public void setStudentDocumentName(String studentDocumentName) {
        this.studentDocumentName = studentDocumentName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
