package com.suime.library.dto.pack;

import java.util.List;

/**
 * 接收文档bean
 * @author ice
 */
public class AcceptRecordBean {

	/**
	 * 别名
	 */
	private String alias;

	/**
	 * 发送记录ids
	 */
	private List<Long> recordIds;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<Long> getRecordIds() {
		return recordIds;
	}

	public void setRecordIds(List<Long> recordIds) {
		this.recordIds = recordIds;
	}

}
