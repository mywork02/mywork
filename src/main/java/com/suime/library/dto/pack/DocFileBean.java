package com.suime.library.dto.pack;

import com.suime.common.dto.pack.CommonBean;

/**
 * 文库上传文件的封装类,数据载体
 * @author ice
 */
public class DocFileBean extends CommonBean {

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * aliyun oss 文件key md5
	 */
	private String key;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
