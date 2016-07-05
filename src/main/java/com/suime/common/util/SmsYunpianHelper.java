package com.suime.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信发送
 *
 * @author ice
 */
public class SmsYunpianHelper {

    /**
     * 查账户信息的http地址
     */
    private static final String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";

    /**
     * 智能匹配模版发送接口的http地址
     */
    private static final String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";

    /**
     * 模板发送接口的http地址
     */
    private static final String URI_TPL_SEND_SMS = "http://yunpian.com/v1/sms/tpl_send.json";

    /**
     * 发送语音验证码接口的http地址
     */
    private static final String URI_SEND_VOICE = "http://yunpian.com/v1/voice/send.json";

    /**
     * 编码格式。发送编码格式统一用UTF-8
     */
    private static final String ENCODING = "UTF-8";
    /**
     * API key
     */
    private static final String API_KEY = "apikey";

    /**
     * 随米短信发送apikey
     */
    private static final String SUIME_SMS_API_KEY = "98f6827737373926cf07a3e0ce0c4263";

    // public static void main(String[] args) throws IOException, URISyntaxException {
    //
    // // 修改为您的apikey.apikey可在官网（http://www.yuanpian.com)登录后用户中心首页看到
    // String apikey = SUIME_SMS_API_KEY;
    //
    // // 修改为您要发送的手机号
    // String mobile = "18210134513";
    //
    // /**************** 查账户信息调用示例 *****************/
    // System.out.println(SmsHelper.getUserInfo(apikey));
    //
    // /**************** 使用智能匹配模版接口发短信(推荐) *****************/
    // // 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
    // String text = "【云片网】您的验证码是1234";
    // // 发短信调用示例
    // System.out.println(SmsHelper.sendSms(apikey, text, mobile));
    //
    // /**************** 使用指定模板接口发短信(不推荐，建议使用智能匹配模版接口) *****************/
    // // 设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
    // long tpl_id = 1;
    // // 设置对应的模板变量值
    // // 如果变量名或者变量值中带有#&=%中的任意一个特殊符号，需要先分别进行urlencode编码
    // // 如code值是#1234#,需作如下编码转换
    // String codeValue = URLEncoder.encode("#1234#", ENCODING);
    // String tpl_value = "#code#=" + codeValue + "&#company#=云片网";
    // // 模板发送的调用示例
    // System.out.println(SmsHelper.tplSendSms(apikey, tpl_id, tpl_value, mobile));
    //
    // /**************** 使用接口发语音验证码 *****************/
    // String code = "1234";
    // System.out.println(SmsHelper.sendVoice(apikey, mobile, code));
    // }

    /**
     * 取账户信息
     *
     * @return json格式字符串
     * @throws IOException
     */

    public static String getUserInfo() throws IOException, URISyntaxException {
        String apikey = SUIME_SMS_API_KEY;
        Map<String, String> params = new HashMap<String, String>();
        params.put(API_KEY, apikey);
        return post(URI_GET_USER_INFO, params);
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
        String apikey = SUIME_SMS_API_KEY;
        Map<String, String> params = new HashMap<String, String>();
        params.put(API_KEY, apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        Logger logger = LoggerFactory.getLogger(SmsYunpianHelper.class);
        logger.info("mobile:" + mobile + ",text:" + text);
        return post(URI_SEND_SMS, params);
    }

    /**
     * 通过模板发送短信(不推荐)
     *
     * @param tplId    　模板id
     * @param tplValue 　模板变量值
     * @param mobile   　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */

    public static String tplSendSms(long tplId, String tplValue, String mobile) throws IOException {
        String apikey = SUIME_SMS_API_KEY;
        Map<String, String> params = new HashMap<String, String>();
        params.put(API_KEY, apikey);
        params.put("tpl_id", String.valueOf(tplId));
        params.put("tpl_value", tplValue);
        params.put("mobile", mobile);
        return post(URI_TPL_SEND_SMS, params);
    }

    /**
     * 通过接口发送语音验证码
     *
     * @param mobile 接收的手机号
     * @param code   验证码
     * @return 提交响应
     */
    public static String sendVoice(String mobile, String code) {
        String apikey = SUIME_SMS_API_KEY;
        Map<String, String> params = new HashMap<String, String>();
        params.put(API_KEY, apikey);
        params.put("mobile", mobile);
        params.put("code", code);
        return post(URI_SEND_VOICE, params);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交 [参数，值] Map
     * @return 提交响应
     */
    public static String post(String url, Map<String, String> paramsMap) {
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
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
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
