package com.suime.common.helper;

import org.apache.commons.lang.StringUtils;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.suime.common.support.Configure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * aliyun mns helper
 *
 * @author ice
 */
public final class AliyunMNSClientHelper {

    /**
     * instance
     */
    private static AliyunMNSClientHelper instance;

    /**
     * mnsClient
     */
    private final MNSClient mnsClient;

    /**
     * constructor
     */
    private AliyunMNSClientHelper() {
        CloudAccount account = new CloudAccount(Configure.getPropertyValue("aliyun.mns.accessKeyId"), Configure.getPropertyValue("aliyun.mns.accessKeySecret"),
                Configure.getPropertyValue("aliyun.mns.accountEndpoint"));
        mnsClient = account.getMNSClient();
    }

    /**
     * 发送消息
     *
     * @param mnsQueues   mnsQueues
     * @param messageBoby messageBoby
     * @return message
     */
    public Message messageSend(MnsQueues mnsQueues, String messageBoby) {
        return messageSend(mnsQueues, messageBoby, 8);
    }

    /**
     * 发送消息
     *
     * @param mnsQueues   mnsQueues
     * @param messageBoby messageBoby
     * @param priority    优先级
     * @return message
     */
    public Message messageSend(MnsQueues mnsQueues, String messageBoby, int priority) {
        CloudQueue queue = mnsClient.getQueueRef(mnsQueues.getValue());
        // queue.create();
        // 发送消息
        Message message = new Message();
        message.setMessageBody(messageBoby);
        message.setPriority(priority);
        try {
            Class<Message> clazz = Message.class;
            Field field = clazz.getDeclaredField("rawBodyType");
            field.setAccessible(true);
            field.set(message, clazz.getDeclaredClasses()[0].getEnumConstants()[2]);
            field.setAccessible(false);
        } catch (Exception ex) {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        Message putMsg = queue.putMessage(message);
        return putMsg;
    }

    /**
     * 重新生成key
     *
     * @param fileKey  fileKey
     * @param handouts 版式
     * @return 带有版式的key
     */
    public String generateKey(String fileKey, String handouts) {
        String newKey = "";
        if (fileKey.indexOf("pdf") > 0) {
            if (StringUtils.equalsIgnoreCase("x11", handouts)) {
                newKey = fileKey;
            } else {
                newKey = fileKey + "-" + handouts;
            }
        } else {
            if (StringUtils.equalsIgnoreCase("x11", handouts)) {

                newKey = fileKey + "-pdf";
            } else {
                newKey = fileKey + "-" + handouts + "-pdf";
            }
        }
        return newKey;
    }

    /**
     * getInstance
     *
     * @return aliyunMNSClientHelper
     */
    public static synchronized AliyunMNSClientHelper getInstance() {
        if (instance == null) {
            instance = new AliyunMNSClientHelper();
        }
        return instance;
    }

    public MNSClient getMnsClient() {
        return mnsClient;
    }

}
