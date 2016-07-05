package com.suime.common.helper;

import com.suime.common.support.Configure;

/**
 * 消息
 * @author ice
 */
public enum MnsQueues {
	/*
	aliyun.mns.queues.transform=sui-core
	aliyun.mns.queues.transform_ppt=sui-ppt
	aliyun.mns.queues.transform_doc=sui-word-com
	aliyun.mns.queues.count_page=sui-count
	aliyun.mns.queues.count_page_ppt=sui-count-ppt
	 */
	/**
	 * sui-core
	 */
	TRANSFORM("transform"),
	/**
	 * sui-ppt
	 */
	TRANSFORM_PPT("transform_ppt"),
	/**
	 * sui-ppt-com
	 */
	TRANSFORM_PPT_COM("transform_ppt_com"),
	/**
	 * sui-word-com
	 */
	TRANSFORM_DOC("transform_doc"),
	/**
	 * sui-word-printer
	 */
	TRANSFORM_PRINTER("transform_printer"),
	/**
	 * wenku-batch-upload
	 */
	TRANSFORM_WENKU_BATCH_UPLOAD("wenku_batch_upload"),
	/**
	 * sui-count
	 */
	COUNT_PAGE("count_page"),
	/**
	 * sui-count-ppt
	 */
	COUNT_PAGE_PPT("count_page_ppt");

	/**
	 * value
	 */
	private String value;

	/**
	 * constructor
	 * @param value value
	 */
	MnsQueues(String value) {
		this.value = Configure.getPropertyValue("aliyun.mns.queues." + value);
	}

	public String getValue() {
		return value;
	}

}
