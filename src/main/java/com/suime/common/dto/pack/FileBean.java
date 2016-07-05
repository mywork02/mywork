package com.suime.common.dto.pack;

/**
 * fileBean
 * @author ice
 */
public class FileBean {

	/**
	 * 文件key aliyun md5key-sha1key-extension-length
	 */
	private String key;

	/**
	 * 文件长度
	 */
	private Long length;

	/**
	 * 文件状态码
	 */
	private Byte state;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	* 表示文件是否可预览 默认不可以,0:不可预览,1:可预览
	*/
	private Byte preview;

	/**
	 * 扩展名(后缀名),不带.号
	 */
	private String extension;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Byte getPreview() {
		return preview;
	}

	public void setPreview(Byte preview) {
		this.preview = preview;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
