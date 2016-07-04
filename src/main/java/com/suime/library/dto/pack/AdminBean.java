package com.suime.library.dto.pack;

/**
 * Created by origin on 2015/12/15.
 */
public class AdminBean {

    /**
     * 唯一id
     */
    private Long id;

    /**
     * 移动电话
     */
    private String cellphone;

    /**
     * 密码
     */
    private String password;

    /**
     * 管理员名称
     */
    private String name;

    /**
     * 性别
     */
    private Byte sex;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 账号状态,0:删除状态,1:未启用状态,2:启用状态
     */
    private Byte status;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
