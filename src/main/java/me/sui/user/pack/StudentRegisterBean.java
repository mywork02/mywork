package me.sui.user.pack;

import java.io.Serializable;

/**
 * 学生注册
 *
 * @author ice
 */
public class StudentRegisterBean implements Serializable {

    /**
     * sid
     */
    private static final long serialVersionUID = 7468906660738228834L;
    
    /**
     * 注册手机
     */
    private String cellphone;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 专业
     */
    private Long majorGroupId;

    /**
     * 专业id
     */
    private String majorGroupCode;

    /**
     * 入学年份
     */
    private Integer startYear;

    /**
     * 验证码
     */
    private String verificationCode;

    /**
     * 用户来源,(第三方客户来源值为:partner-customer-{code})
     */
    private String source;

    /**
     * 关联的printshop_id
     */
    private Long linkedPrintshopId;

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getMajorGroupId() {
        return majorGroupId;
    }

    public void setMajorGroupId(Long majorGroupId) {
        this.majorGroupId = majorGroupId;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getMajorGroupCode() {
        return majorGroupCode;
    }

    public void setMajorGroupCode(String majorGroupCode) {
        this.majorGroupCode = majorGroupCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getLinkedPrintshopId() {
        return linkedPrintshopId;
    }

    public void setLinkedPrintshopId(Long linkedPrintshopId) {
        this.linkedPrintshopId = linkedPrintshopId;
    }
}
