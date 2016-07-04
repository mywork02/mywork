package com.suime.library.dto.pack;

/**
 * Created by origin on 2015/12/25.
 */
public class ResetPasswordBean {

    /**
     * 验证码
     */
    private String verficationCode;

    /**
     * 手机号
     */
    private String cellphone;

    /**
     * 新密码
     */
    private String password;

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerficationCode() {
        return verficationCode;
    }

    public void setVerficationCode(String verficationCode) {
        this.verficationCode = verficationCode;
    }
}
