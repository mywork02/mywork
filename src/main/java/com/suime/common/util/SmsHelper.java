package com.suime.common.util;

import com.suime.common.support.Configure;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 短信发送
 *
 * @author ice
 */
public class SmsHelper {

    /**
     * 短信平台
     */
    private static final String PLATFORM = Configure.getPropertyValue("sys.sms.platform", "LY");

    private static Logger logger = LoggerFactory.getLogger(SmsHelper.class);

    /**
     * 智能匹配模版接口发短信
     *
     * @param text   　短信内容
     * @param mobile 　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */

    public static String sendSms(String text, String mobile) throws IOException {
        String smsResponse;
        if (StringUtils.equals("LY", PLATFORM)) {
            smsResponse = SmsLyHelper.sendSms(text, mobile);
        } else if (StringUtils.equals("YUNPIAN", PLATFORM)) {
            smsResponse = SmsYunpianHelper.sendSms(text, mobile);
        } else if (StringUtils.equals("BAIWU", PLATFORM)) {
            smsResponse = SmsBaiwutongHelper.sendSms(text, mobile);
        } else {
            smsResponse = SmsYunpianHelper.sendSms(text, mobile);
        }
        logger.info("--smsResponse:" + smsResponse);
        return smsResponse;
    }

    public static String getPLATFORM() {
        return PLATFORM;
    }
}
