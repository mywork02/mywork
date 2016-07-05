package com.suime.common.util;

import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.DateUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 百悟通短信发送
 *
 * @author ice
 */
public class SmsBaiwutongHelper {

    /**
     * md5加密
     *
     * @param source 待加密对象
     * @return md5加密后的字符串
     */
    public static String md5(String source) {
        String value = source;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(source.getBytes());
            BigInteger bi = new BigInteger(1, digest.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交 [参数，值] Map
     * @return 提交响应
     */
    private static String post(String url, Map<String, String> paramsMap, final String charset) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, charset));
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

    /**
     * snedSms
     *
     * @return string
     */
    protected static String getBlance() {
        String url = "http://cloud.baiwutong.com:8080/get_balance.do";
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("id", "wj6598");
        String tdCode = "5688rj";
        paramsMap.put("MD5_td_code", tdCode);
        // paramsMap.put("mobile", "18210134513,17701325906");
        // paramsMap.put("msg_content", "测试短信发送内容");
        // paramsMap.put("msg_id", "123");
        // paramsMap.put("ext", "");
        String charset = "gbk";
        String post = post(url, paramsMap, charset);
        return post;
    }

    /**
     * snedSms
     *
     * @return string
     */
    protected static String sendSms() {
        Date date = new Date();
        String url = "http://cloud.baiwutong.com:8080/post_sms.do";
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("id", "wj6598");
        // String tdCode = "5688rj1069014806598";
        // String md5TdCode = md5(tdCode);
        String md5TdCode = "3f30637c3580367f73570a07aabe95e3";
        paramsMap.put("MD5_td_code", md5TdCode);
        // paramsMap.put("mobile", "17701325906,18210134513,15121027816");
//		paramsMap.put("mobile", "17701325906,18210134513");
        paramsMap.put("mobile", "18610378833");
        paramsMap.put("msg_content",
                "欢迎您访问我们的网站 http://sui.me，开启云打印新时代。" + DateUtil.format(date, Constants.VALUE_DATE_TIME_PATTERN) + "(" + date.getTime() + ")");
        paramsMap.put("msg_id", "123");
        paramsMap.put("ext", "");
        String charset = "gbk";
        String post = post(url, paramsMap, charset);
        return post;
    }

    /**
     * 智能匹配模版接口发短信
     *
     * @param text   　短信内容
     * @param mobile 　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */

    public static String sendSms(String text, String mobile) throws IOException {
        Date date = new Date();
        String url = "http://cloud.baiwutong.com:8080/post_sms.do";
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("id", "wj6598");
        // String tdCode = "5688rj1069014806598";
        // String md5TdCode = md5(tdCode);
        String md5TdCode = "3f30637c3580367f73570a07aabe95e3";
        paramsMap.put("MD5_td_code", md5TdCode);
        // paramsMap.put("mobile", "17701325906,18210134513,15121027816");
//		paramsMap.put("mobile", "17701325906,18210134513");
        paramsMap.put("mobile", mobile);
        paramsMap.put("msg_content", text);
        paramsMap.put("msg_id", "123");
        paramsMap.put("ext", "");
        String charset = "gbk";
        return post(url, paramsMap, charset);
//		return post(URI_SEND_SMS, params);
    }

}
