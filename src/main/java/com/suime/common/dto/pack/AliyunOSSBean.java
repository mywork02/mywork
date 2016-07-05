package com.suime.common.dto.pack;

/**
 * aliyun oss bean
 * @author ice
 */
public class AliyunOSSBean {

	/**
	 * 文件md5编码
	 */
	private String md5key;

	/**
	 * 文件sha1编码
	 */
	private String sha1key;

	/**
	 * 文件长度
	 */
	private long length;

	/**
	 * 文件扩展名(后缀名)
	 */
	private String extension;

	public String getMd5key() {
		return md5key;
	}

	public void setMd5key(String md5key) {
		this.md5key = md5key;
	}

	public String getSha1key() {
		return sha1key;
	}

	public void setSha1key(String sha1key) {
		this.sha1key = sha1key;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return md5key + "-" + sha1key + "-" + extension + "-" + length;
	}
}
