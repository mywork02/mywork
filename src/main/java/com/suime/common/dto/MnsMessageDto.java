package com.suime.common.dto;

/**
 * aliyun mns message
 * @author ice
 */
public class MnsMessageDto {

	/**
	 * 计算页数
	 */
	public static final String GET_PAGE_NUMBER = "get_page_no";

	/**
	 * 转码成pdf
	 */
	public static final String CONVERT_TO_PDF = "convert_to_pdf";

	/**
	 * 文件id
	 */
	private Long id;

	/**
	 * 文件key aliyun md5key-sha1key-extension-length
	 */
	private String key;
	/**
	 * 消息类型
	 */
	private String type;

	/**
	 * 版式
	 */
	private String handouts;

	/**
	 * 是否需要预览:1:需要预览;0:不需要预览
	 */
	private Byte ndpre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHandouts() {
		return handouts;
	}

	public void setHandouts(String handouts) {
		this.handouts = handouts;
	}

	public Byte getNdpre() {
		return ndpre;
	}

	public void setNdpre(Byte ndpre) {
		this.ndpre = ndpre;
	}

}
