package com.suime.common.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * 业务异常
 * @author ice
 */
public final class BusinessErrors {

	/**
	 * instance
	 */
	private static BusinessErrors instance = new BusinessErrors();

	/**
	 * constructor
	 */
	private BusinessErrors() {

	}

	/**
	 * 未知错误
	 * @return BusinessException
	 */
	public BusinessException unknowError() {
		return new BusinessException(ErrorConstants.UNKNOWN_ERROR, "unkown error", SpringContext.getText("unkown_error"));
	}

	/**
	 * 参数错误
	 * @return BusinessException
	 */
	public BusinessException paramsError() {
		return new BusinessException(ErrorConstants.PARAMS_ERROR, "unkown params", SpringContext.getText("params_error"));
	}

	/**
	 * 无效账号
	 * @return BusinessException
	 */
	public BusinessException accountInvalid() {
		return new BusinessException(ErrorConstants.ACCOUNT_INVALID, "account invalid", SpringContext.getText("errors.account.invalid"));
	}

	/**
	 * 密码格式错误
	 * @return BusinessException
	 */
	public BusinessException passwordError() {
		return new BusinessException(ErrorConstants.PASSWORD_ERROR, "errors password", SpringContext.getText("errors.password"));
	}

	/**
	 * 无效token
	 * @return BusinessException
	 */
	public BusinessException tokenInvalidError() {
		return new BusinessException(ErrorConstants.TOKEN_INVALID, "token invalid", SpringContext.getText("errors.token.invalid"));
	}

	/**
	 * 无效token
	 * @return BusinessException
	 */
	public BusinessException tokenInvalidError(String token) {
		return new BusinessException(ErrorConstants.TOKEN_INVALID, token, SpringContext.getText("errors.token.invalid"));
	}

	/**
	 * 验证码不匹配错误
	 * @return BusinessException
	 */
	public BusinessException vcodeNotMatchError() {
		return new BusinessException(ErrorConstants.VCODE_NOTMATCH_ERROR, "verification code not match", SpringContext.getText("verification.code.not_match"));
	}

	/**
	 * 手机号格式不正确错误
	 * @param cellphone 手机号
	 * @return BusinessException
	 */
	public BusinessException mobileError(String cellphone) {
		return new BusinessException(ErrorConstants.MOBILE_ERROR, "errors mobile", SpringContext.getText("errors.mobile", cellphone));
	}

	/**
	 * 手机号已经注册过错误
	 * @param cellphone 手机号
	 * @return BusinessException
	 */
	public BusinessException alreadyRegisteredError(String cellphone) {
		return new BusinessException(ErrorConstants.MOBILE_ALREADY_REGISTERED, "errors mobile registered",
				SpringContext.getText("errors.mobile.registered", cellphone));
	}

	/**
	 * 等待片刻才能发送短消息
	 * @param minute 秒数
	 * @return BusinessException
	 */
	public BusinessException smsWaitMinuteError(int minute) {
		return new BusinessException(ErrorConstants.SMS_WAIT_MINUTE, "wait a minute", SpringContext.getText("errors.mobile.wait", minute));
	}

	/**
	 * 不能发送短信息
	 * @return BusinessException
	 */
	public BusinessException smsCanNotSendError() {
		return new BusinessException(ErrorConstants.SMS_CANNOT_SEND, "can not send sms", SpringContext.getText("errors.sms.can_not.send"));
	}

	/**
	 * 用户不存在
	 * @return BusinessException
	 */
	public BusinessException userNotExistsError() {
		return new BusinessException(ErrorConstants.USER_NOT_EXISTS, "user not exists", SpringContext.getText("errors.user.not.exists"));
	}

	/**
	 * 无效用户
	 * @return BusinessException
	 */
	public BusinessException userInvalidError() {
		return new BusinessException(ErrorConstants.USER_INVALID, "user invalid", SpringContext.getText("errors.user.invalid"));
	}

	/**
	 * 用户已下线
	 * @return BusinessException
	 */
	public BusinessException userOfflineError() {
		return new BusinessException(ErrorConstants.USER_OFFLINE, "user offline", SpringContext.getText("errors.user.offline"));
	}

	/**
	 * 用户已下线
	 * @return BusinessException
	 */
	public BusinessException userOfflineError(String token) {
		return new BusinessException(ErrorConstants.USER_OFFLINE, token, SpringContext.getText("errors.user.offline"));
	}

	/**
	 * 用户无权限进行操作
	 * @return BusinessException
	 */
	public BusinessException userNoAuthError() {
		return new BusinessException(ErrorConstants.USER_NO_AUTH, "user no auth", SpringContext.getText("errors.user.no.auth"));
	}

	/**
	 * 文件未找到(不存在)
	 * @return BusinessException
	 */
	public BusinessException fileNotFound() {
		return new BusinessException(ErrorConstants.FILE_NOT_FOUND, "file not found", SpringContext.getText("errors.file_link.not_found"));
	}

	/**
	 * 文件无权限访问
	 * @return BusinessException
	 */
	public BusinessException fileNoAuth() {
		return new BusinessException(ErrorConstants.FILE_NO_AUTH, "file no auth", SpringContext.getText("errors.file.no_auth"));
	}

	/**
	 * 文件获取失败
	 * @return BusinessException
	 */
	public BusinessException fileFetchFailure() {
		return new BusinessException(ErrorConstants.FILE_NOT_FOUND, "file fetch failure", SpringContext.getText("errors.file.fetch_failure"));
	}

	/**
	 * 文件上传key为空
	 * @return BusinessException
	 */
	public BusinessException fileKeyBlank() {
		return new BusinessException(ErrorConstants.FILE_NOT_FOUND, "file key blank", SpringContext.getText("errors.file.key_blank"));
	}

	/**
	 * 文件名为空
	 * @return BusinessException
	 */
	public BusinessException fileNameBlank() {
		return new BusinessException(ErrorConstants.FILE_NOT_FOUND, "file not found", SpringContext.getText("errors.file.name_blank"));
	}

	/**
	 * 文件扩展名不能识别
	 * @return BusinessException
	 */
	public BusinessException fileExtensionError(String extension) {
		return new BusinessException(ErrorConstants.FILE_EXTENSION_ERROR, "file extension error",
				SpringContext.getText("errors.file.extension_error", extension));
	}

	/**
	 * 余额不足
	 * @return BusinessException
	 */
	public BusinessException balanceLackError() {
		return new BusinessException(ErrorConstants.BALANCE_LACK, "balance lack", SpringContext.getText("errors.partner.balance_lack"));
	}

	/**
	 * printOrder不存在
	 * @return BusinessException
	 */
	public BusinessException printOrderNotFoundError(String printOrderId) {
		return new BusinessException(ErrorConstants.PRINT_ORDER_NOT_FOUND, "order not found",
				SpringContext.getText("errors.printorder.not_found", printOrderId));
	}

	/**
	 * printOrder 不能取消
	 * @return BusinessException
	 */
	public BusinessException printOrderCanNotCancelError(String printOrderId) {
		return new BusinessException(ErrorConstants.PRINT_ORDER_CAN_NOT_CANCEL, "order can not cancel",
				SpringContext.getText("errors.printorder.can_not_cancel", printOrderId + ""));
	}

	/**
	 * building 不存在
	 * @return BusinessException
	 */
	public BusinessException buildingNotFoundError() {
		return new BusinessException("04003", "error building not found", SpringContext.getText("errors.building.not_found"));
	}

	/**
	 * 打印店(printshop)歇业
	 * @return BusinessException
	 */
	public BusinessException printshopPauseError() {
		throw new BusinessException("04004", "error shop pause", SpringContext.getText("errors.printshop.pause"));
	}

	/**
	 * printTask 未创建
	 * @return BusinessException
	 */
	public BusinessException taskNotCreatedError() {
		throw new BusinessException("04005", "error task not created", SpringContext.getText("errors.printtask.not_created"));
	}

	public static BusinessErrors getInstance() {
		return instance;
	}

}
