package com.suime.common.helper;

/**
 * imageAliyunOSSHelper
 * @author ice
 */
public final class IdentityCardAliyunOSSHelper extends AbstractAliyunOSSHelper {

	/**
	* 单例 instance
	*/
	private static IdentityCardAliyunOSSHelper instance;

	/**
	 * constructor
	 */
	public IdentityCardAliyunOSSHelper() {
		super("aliyun.oss.buckets.idcard_pic");
//		this.contentType = "image/jpeg";
	}

	/**
	 * getInstance
	 * @return AliyunOssHelper 实例
	 */
	public static synchronized IdentityCardAliyunOSSHelper getInstance() {
		if (instance == null) {
			instance = new IdentityCardAliyunOSSHelper();
		}
		return instance;
	}
}
