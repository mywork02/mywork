package com.suime.common.util;

import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.StringUtils;
import com.suime.common.support.Configure;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ice on 29/3/2016.
 */
public final class SmsLyHelper {

	/*
     企业spId：  4832 
	企业名称：  上海随印信息科技有限公司（营销）
	企业账号：  syxx_yx 
	企业密码：  syxx_yx123

    平台入口：http://120.26.66.24/msg/index.jsp
    (测试
    帐号：suiyinxinxi_yx
    密码：syxx_yx123
    产品id：683050
    )
    账户：syxx_hy
    密码：syxx_hy123
    产品id：94634003
    http://120.26.66.24/msg/HttpBatchSendSM。
	 */
    /**
     * url
     */
    private static final String SEND_URL = Configure.getPropertyValue("sys.sms.ly.send_url", "http://120.26.66.24/msg/HttpBatchSendSM");

    /**
     * product
     */
    private static final String product = Configure.getPropertyValue("sys.sms.ly.product", "94634003");

    /**
     * loginName
     */
    private static final String ACCOUNT = Configure.getPropertyValue("sys.sms.ly.account", "syxx_hy");

    /**
     * password
     */
    private static final String PASSWORD = Configure.getPropertyValue("sys.sms.ly.pswd", "syxx_hy123");

    /**
     * charset
     */
    private static final String CHARSET = "UTF-8";

    /**
     * send
     *
     * @param text
     * @param mobiles
     * @return response
     */
    public static String sendSms(String text, List<String> mobiles) {
        String mobile = StringUtils.join(mobiles, Constants.VALUE_SIMPLE_SPLIT_CHAR);
        return sendSms(text, mobile);
    }

    public static String sendSms(String text, String mobile) {
        List<NameValuePair> paramList = new ArrayList<>();
        paramList.add(new BasicNameValuePair("product", product));
        paramList.add(new BasicNameValuePair("account", ACCOUNT));
        paramList.add(new BasicNameValuePair("pswd", PASSWORD));
        paramList.add(new BasicNameValuePair("needstatus", "true"));
        paramList.add(new BasicNameValuePair("mobile", mobile));

        try {
            String context = text;
            paramList.add(new BasicNameValuePair("msg", context));
            return post(SEND_URL, paramList, CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramList paramList
     * @param charset   charset
     * @return 提交响应
     */
    private static String post(String url, List<NameValuePair> paramList, String charset) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramList != null) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, charset);
                method.setEntity(entity);
            }

            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}
