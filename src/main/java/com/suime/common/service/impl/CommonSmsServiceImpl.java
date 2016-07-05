package com.suime.common.service.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.ValidatorUtil;
import com.confucian.framework.web.exception.BusinessException;
import com.suime.common.cache.CacheService;
import com.suime.common.error.ErrorConstants;
import com.suime.common.error.MobileValiditionException;
import com.suime.common.service.CommonSmsService;
import com.suime.common.support.Configure;
import com.suime.common.util.RandomUtil;
import com.suime.common.util.SmsHelper;

/**
 * CommonSmsService 实现类
 *
 * @author ice
 */
@Service("commonSmsService")
public class CommonSmsServiceImpl extends AbstractServiceImpl implements CommonSmsService {

    /**
     * cacheService
     */
    @Autowired
    private CacheService cacheService;

    @Override
    public Boolean sendVerificationCode(String cellphone, String reason, String remoteAddr) {
        Boolean flag = false;
        if (!ValidatorUtil.validateMobile(cellphone)) {
            throw new MobileValiditionException(cellphone);
        }
        if (!this.checkSmsSendReason(reason)) {
            throw new BusinessException(ErrorConstants.SMS_CANNOT_SEND, "can not send sms", SpringContext.getText("errors.sms.can_not.send"));
        }
        int minute = 60;
        String phoneKey = Configure.getPropertyValue("cache.phone.prefix") + reason + StringUtils.trim(cellphone);
        if (this.cacheService.get(phoneKey) != null) {
            // 您需要等待一段时间才能再次发送验证码。
            logger.info("Same cellphone came up in 1 minute {}", cellphone);
            throw new BusinessException(ErrorConstants.SMS_WAIT_MINUTE, "wait a minute", SpringContext.getText("errors.mobile.wait", minute));
        }

        String ipAddrKey = Configure.getPropertyValue("cache.ipaddr.prefix") + reason + remoteAddr;
        if (this.cacheService.get(ipAddrKey) != null) {
            // 您需要等待一段时间才能再次发送验证码。
            logger.info("Same remoteAddr came up in 1 minute {}", remoteAddr);
            throw new BusinessException(ErrorConstants.SMS_WAIT_MINUTE, "wait a minute", SpringContext.getText("errors.mobile.wait", minute));
        }

        String verificationCode = RandomUtil.genrateDigits(6);
        String verificationCodeKey = Configure.getPropertyValue("cache.verification.code.prefix") + reason + cellphone;
        final int exp = 300;// 300秒,5分钟
        CommonResultBean result = new CommonResultBean();
        try {
            Boolean isDebug = Boolean.valueOf(Configure.getPropertyValue("sys.debug", "false"));
            if (isDebug) {
                verificationCode = Configure.getPropertyValue("sys.verification_code.debug", "123456");
            } else {
                this.logger.info(SpringContext.getText("sms.message.verification_code", verificationCode));
                SmsHelper.sendSms(SpringContext.getText("sms.message.verification_code", verificationCode), cellphone);
            }
            this.cacheService.set(verificationCodeKey, exp, verificationCode);
            this.cacheService.set(phoneKey, minute, minute);
            this.cacheService.set(ipAddrKey, minute, minute);
            result.setResult(Constants.NORMAL_RESULT_RIGHT);
            flag = true;
        } catch (IOException e) {
            result.setResult(Constants.NORMAL_RESULT_ERROR);
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 检测短信发送理由
     *
     * @param reason 理由
     * @return 是否可以发送
     */
    private Boolean checkSmsSendReason(String reason) {
        String reasons = Configure.getPropertyValue("sms.send.reason");
        return StringUtils.contains(reasons, Constants.VALUE_SIMPLE_SPLIT_CHAR + reason + Constants.VALUE_SIMPLE_SPLIT_CHAR);
    }

}
