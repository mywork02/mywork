package com.suime.common.service;

/**
 * 通用短信相关服务,不需要与数据库交互
 * @author ice
 */
public interface CommonSmsService {
	/**
	 * 发送验证码
	 * @param cellphone 手机号
	 * @param reason 发送理由
	 * @param remoteAddr 请求ip
	 * @return 是否发送成功
	 */
	Boolean sendVerificationCode(String cellphone, String reason, String remoteAddr);
}
