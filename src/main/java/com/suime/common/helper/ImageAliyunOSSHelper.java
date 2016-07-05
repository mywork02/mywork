package com.suime.common.helper;

/**
 * imageAliyunOSSHelper
 *
 * @author ice
 */
public final class ImageAliyunOSSHelper extends AbstractAliyunOSSHelper {

    /**
     * 单例 instance
     */
    private static ImageAliyunOSSHelper instance;

    /**
     * constructor
     */
    public ImageAliyunOSSHelper() {
        super("aliyun.oss.buckets.front_pic");
//		this.contentType = "image/jpeg";
    }

    /**
     * getVisitorKey
     *
     * @param key
     * @return url
     */
    public String getVisitUrl(String key) {
        return this.externalEndpoint.replace("http://", "http://" + bucketName + ".") + "/" + key;
    }

    /**
     * getInstance
     *
     * @return AliyunOssHelper 实例
     */
    public static synchronized ImageAliyunOSSHelper getInstance() {
        if (instance == null) {
            instance = new ImageAliyunOSSHelper();
        }
        return instance;
    }
}
