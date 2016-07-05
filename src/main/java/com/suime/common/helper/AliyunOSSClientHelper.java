package com.suime.common.helper;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Date;

import com.aliyun.oss.model.*;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.IOUtils;
import com.confucian.framework.utils.DateUtil;
import com.suime.common.dto.pack.AliyunOSSBean;
import com.suime.common.support.Configure;

/**
 * aliyun oss helper
 * @author ice
 */
public final class AliyunOSSClientHelper {

	/**
	 * 单例 instance
	 */
	private static AliyunOSSClientHelper instance;

	/**
	 * aliyun oss client accessKeyId 
	 */
	private final String accessKeyId;

	/**
	 * aliyun oss client secretAccessKey 
	 */
	private final String accessKeySecret;

	/**
	 * aliyun oss client bucketName 
	 */
	private final String bucketName;

	/**
	 * aliyun oss client endpoint 内网
	 */
	private final String internalEndpoint;

	/**
	 * aliyun oss client endpoint 外网
	 */
	private final String externalEndpoint;

	/**
	 * 是否开发模式
	 */
	private final boolean isDebug;

	/**
	 * conturctor
	 */
	private AliyunOSSClientHelper() {
		accessKeyId = Configure.getPropertyValue("aliyun.oss.accessKeyId");
		accessKeySecret = Configure.getPropertyValue("aliyun.oss.accessKeySecret");
		bucketName = Configure.getPropertyValue("aliyun.oss.buckets.file");
		internalEndpoint = Configure.getPropertyValue("aliyun.oss.endpoints.internal");
		externalEndpoint = Configure.getPropertyValue("aliyun.oss.endpoints.external");
		isDebug = BooleanUtils.toBoolean(Configure.getPropertyValue("aliyun.oss.debug"));

	}

	/**
	 * doesObjectExist
	 * @param sourceKey
	 * @return boolean
	 */
	public boolean doesObjectExist(String sourceKey) {
		OSSClient client = this.getOSSClient();
		return client.doesObjectExist(bucketName, sourceKey);
	}

	/**
	 * 根据key获取oss文件,如果返回null则表示读取OSS文件出错了
	 * @param sourceKey 文件key
	 * @param extension 文件扩展名(后缀名)
	 * @return aliyunOSSBean
	 */
	public AliyunOSSBean getObjectFromOSSByKey(String sourceKey, String extension) {
		AliyunOSSBean bean = null;
		// OSSClient ossClient = this.getInternalOSSClient();
		OSSClient client = this.getOSSClient();
		OSSObject object = client.getObject(bucketName, sourceKey);
		try {
			byte[] data = IOUtils.readStreamAsByteArray(object.getObjectContent());
			bean = new AliyunOSSBean();
			bean.setLength(data.length);
			bean.setMd5key(DigestHelper.md5(ByteBuffer.wrap(data)));
			bean.setSha1key(DigestHelper.sha1(ByteBuffer.wrap(data)));
			bean.setExtension(extension);
		} catch (IOException e) {
			bean = null;
			e.printStackTrace();
		} finally {
			IOUtils.safeClose(object.getObjectContent());
		}
		return bean;
	}

	/**
	 * 复制阿里云oss中的打印文档
	 * @param sourceKey
	 * @param destinationKey
	 * @return CopyObjectResult
	 */
	public CopyObjectResult copyObject(String sourceKey, String destinationKey, String contentType) {
		OSSClient client = this.getOSSClient();
		String sourceBucketName = this.bucketName;
		String destinationBucketName = bucketName;
		CopyObjectRequest copyObjectRequest = new CopyObjectRequest(sourceBucketName, sourceKey, destinationBucketName, destinationKey);

		if(org.apache.commons.lang3.StringUtils.isNotBlank(contentType)) {
			ObjectMetadata newObjectMetadata = new ObjectMetadata();
			newObjectMetadata.setContentType(contentType);
			copyObjectRequest.setNewObjectMetadata(newObjectMetadata);
		}

//		CopyObjectResult copyObject = client.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
		CopyObjectResult copyObject = client.copyObject(copyObjectRequest);
		// client.deleteObject(destinationBucketName, sourceKey);
		return copyObject;
	}

	/**
	 * 通过Key删除阿里云中的打印文档
	 * @param sourceKey
	 */
	public void deleteObject(String sourceKey) {
		OSSClient client = this.getOSSClient();
		client.deleteObject(bucketName, sourceKey);
	}

	/**
	 * 根据key获取OSSObject
	 * @param key upload key
	 * @return OSSObject
	 */
	public OSSObject getOSSObject(String key) {
		// OSSClient ossClient = this.getInternalOSSClient();
		OSSClient ossClient = this.getOSSClient();
		return ossClient.getObject(bucketName, key);
	}

	/**
	 * 获取文件上传url
	 * @param key key
	 * @param minutes 过期分钟数
	 * @return presignedUrl
	 */
	public URL generatePutPresignedUrl(String key, int minutes) {
		OSSClient ossClient = getExternalOSSClient();
		Date expiration = DateUtil.addMinute(new Date(), minutes);
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.PUT);
		// 设置Content-Type
		request.setContentType("application/octet-stream");
		request.setExpiration(expiration);
		URL generatePresignedUrl = ossClient.generatePresignedUrl(request);
		return generatePresignedUrl;
	}

	/**
	 * 获取文件上传url
	 * @param key key
	 * @param minutes 过期分钟数
	 * @return presignedUrl
	 */
	public URL generateGetPresignedUrl(String key, String fileName, int minutes) {
		OSSClient ossClient = getExternalOSSClient();
		Date expiration = DateUtil.addMinute(new Date(), minutes);

		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
		request.setExpiration(expiration);
		if (StringUtils.isNotBlank(fileName)) {
			ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
			responseHeaders.setContentDisposition("attachment;filename=" + fileName);
			request.setResponseHeaders(responseHeaders);
		}
		return ossClient.generatePresignedUrl(request);
	}

	/**
	 * 获取 OSSClient
	 * @return OSSClient
	 */
	public OSSClient getOSSClient() {
		if (isDebug) {
			return getExternalOSSClient();
		}
		return getInternalOSSClient();
	}

	/**
	 * 获取外网访问 OSSClient
	 * @return OSSClient
	 */
	public OSSClient getExternalOSSClient() {
		return initOSSClient(externalEndpoint);
	}

	/**
	 * 获取内网访问 OSSClient
	 * @return OSSClient
	 */
	public OSSClient getInternalOSSClient() {
		return initOSSClient(internalEndpoint);
	}

	/**
	 * 获取OssClient
	 * @param endpoint endpoint
	 * @return OSSClient
	 */
	private OSSClient initOSSClient(String endpoint) {
		return new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}

	/**
	 * getInstance
	 * @return AliyunOssHelper 实例
	 */
	public static synchronized AliyunOSSClientHelper getInstance() {
		if (instance == null) {
			instance = new AliyunOSSClientHelper();
		}
		return instance;
	}
}
