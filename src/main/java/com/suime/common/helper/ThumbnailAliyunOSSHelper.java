package com.suime.common.helper;

/**
 * imageAliyunOSSHelper
 *
 * @author ice
 */
public final class ThumbnailAliyunOSSHelper extends AbstractAliyunOSSHelper {

    /**
     * 单例 instance
     */
    private static ThumbnailAliyunOSSHelper instance;

    /**
     * constructor
     */
    public ThumbnailAliyunOSSHelper() {
        super("aliyun.oss.buckets.thumbnail_pic");
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
    public static synchronized ThumbnailAliyunOSSHelper getInstance() {
        if (instance == null) {
            instance = new ThumbnailAliyunOSSHelper();
        }
        return instance;
    }
}
