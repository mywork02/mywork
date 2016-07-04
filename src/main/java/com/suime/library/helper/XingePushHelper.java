package com.suime.library.helper;

import com.alibaba.fastjson.JSONObject;
import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.utils.DateUtil;
import com.confucian.framework.utils.JsonUtil;
import com.suime.common.support.Configure;
import com.suime.context.model.support.LibraryConstants;
import com.suime.library.dto.pack.CommentPushBean;
import com.suime.library.dto.pack.PushMessageBean;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.Style;
import com.tencent.xinge.XingeApp;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ice on 23/2/2016.
 */
public class XingePushHelper {
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(XingePushHelper.class);

    public static void pushMessage(Byte deviceType, Long studentDocumentId, Long commentId, String body, String deviceToken, String type) {
        if (LibraryConstants.DEVICE_TYPE_ANDROID.equals(deviceType)) {
            androidPushMessage(studentDocumentId, commentId, body, deviceToken, type);
        } else {
            iosPushMessage(studentDocumentId, commentId, body, deviceToken, type);
        }
    }

    public static void iosPushMessage(Long studentDocumentId, Long commentId, String body, String deviceToken, String type) {
        MessageIOS message = new MessageIOS();
        message.setExpireTime(86400);
        message.setAlert(body);
        message.setBadge(1);
        message.setSound("n.mp3");
        message.setCategory("B");
        JSONObject documentJson = new JSONObject();
        documentJson.put("id", studentDocumentId);

        JSONObject tempObject = new JSONObject();
        tempObject.put("wenku", documentJson);

        if (commentId != null) {
            JSONObject commentJson = new JSONObject();
            commentJson.put("id", commentId);
            tempObject.put("comment", commentJson);
        }
        Map<String, Object> custom = new HashMap<String, Object>();
        custom.put("type", type);
        custom.put("extra", tempObject);
        message.setCustom(custom);

        Long accessId = NumberUtils.toLong(Configure.getPropertyValue("ios.push.access_id"), 0L);
        String secretKey = Configure.getPropertyValue("ios.push.secret_key");
        Boolean isDebug = "1".equals(Configure.getPropertyValue("ios.push.is_debug"));
        XingeApp xinge = new XingeApp(accessId, secretKey);
        logger.info("custom:" + JsonUtil.toJsonString(custom));
        org.json.JSONObject jsonObject = xinge.pushSingleAccount(0, deviceToken, message, isDebug ? XingeApp.IOSENV_DEV : XingeApp.IOSENV_PROD);
        logger.info(jsonObject.toString());
    }

    public static void androidPushMessage(Long studentDocumentId, Long commentId, String body, String deviceToken, String type) {
        Message message = new Message();
        message.setExpireTime(86400);
        message.setType(Message.TYPE_MESSAGE);//透传消息
        message.setTitle(SpringContext.getText("message.android.title"));
        message.setContent(body);
        Style style = new Style(1, 1, 1, 1, 1);
        style.setRingRaw("n.mp3");
        message.setStyle(style);
        JSONObject documentJson = new JSONObject();
        documentJson.put("id", studentDocumentId);

        JSONObject tempObject = new JSONObject();
        tempObject.put("wenku", documentJson);

        if (commentId != null) {
            JSONObject commentJson = new JSONObject();
            commentJson.put("id", commentId);
            tempObject.put("comment", commentJson);
        }
        Map<String, Object> custom = new HashMap<String, Object>();
        custom.put("type", type);
        custom.put("extra", tempObject);
        message.setCustom(custom);

        Long accessId = NumberUtils.toLong(Configure.getPropertyValue("android.push.access_id"), 0L);
        String secretKey = Configure.getPropertyValue("android.push.secret_key");
        XingeApp xinge = new XingeApp(accessId, secretKey);
        logger.info("custom:" + JsonUtil.toJsonString(custom));
        org.json.JSONObject jsonObject = xinge.pushSingleDevice(deviceToken, message);
        logger.info(jsonObject.toString());
    }

    public static void pushIos(CommentPushBean commentPushBean, String body, String deviceToken, String type) {
        MessageIOS message = new MessageIOS();
        message.setExpireTime(86400);
        message.setAlert(body);
        message.setBadge(1);
        message.setSound("n.mp3");
        message.setCategory("B");
        JSONObject json = JsonUtil.toJsonObject(commentPushBean);
        JSONObject tempObject = new JSONObject();
        tempObject.put("wenku", json);
        Map<String, Object> custom = new HashMap<String, Object>();
        custom.put("type", type);
        custom.put("extra", tempObject);
        message.setCustom(custom);

        Long accessId = NumberUtils.toLong(Configure.getPropertyValue("ios.push.access_id"), 0L);
        String secretKey = Configure.getPropertyValue("ios.push.secret_key");
        Boolean isDebug = "1".equals(Configure.getPropertyValue("ios.push.is_debug"));
        XingeApp xinge = new XingeApp(accessId, secretKey);
        logger.info(JsonUtil.toJsonString(message));
        org.json.JSONObject jsonObject = xinge.pushSingleAccount(0, deviceToken, message, isDebug ? XingeApp.IOSENV_DEV : XingeApp.IOSENV_PROD);
        logger.info(jsonObject.toString());
    }

    public static void pushIos(PushMessageBean pushMessageBean) {
        MessageIOS message = new MessageIOS();
        message.setExpireTime(86400);
        message.setAlert(pushMessageBean.getBody());
        message.setBadge(1);
        message.setSound("n.mp3");
        message.setCategory("B");
        Map<String, Object> custom = new HashMap<String, Object>();
        custom.put("type", pushMessageBean.getType());
        custom.put("extra", pushMessageBean.getExtra());
        message.setCustom(custom);

        String deviceToken = "e11d1ed08fa3c98472f9a6b09cebf8566be215f1";
        if (StringUtils.isNotBlank(pushMessageBean.getDeviceToken())) {
            deviceToken = pushMessageBean.getDeviceToken();
        }

        Long accessId = NumberUtils.toLong(Configure.getPropertyValue("ios.push.access_id"), 0L);
        String secretKey = Configure.getPropertyValue("ios.push.secret_key");
        Boolean isDebug = "1".equals(Configure.getPropertyValue("ios.push.is_debug"));
        XingeApp xinge = new XingeApp(accessId, secretKey);
        org.json.JSONObject jsonObject = xinge.pushSingleAccount(0, deviceToken, message, isDebug ? XingeApp.IOSENV_DEV : XingeApp.IOSENV_PROD);
        logger.info(jsonObject.toString());
    }
}
