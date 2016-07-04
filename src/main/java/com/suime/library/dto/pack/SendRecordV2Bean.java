package com.suime.library.dto.pack;

import java.util.List;

/**
 * Created by chenqy on 2016/4/7.
 */
public class SendRecordV2Bean {

	/**
	 * 接收人id
	 */
	private List<Long> receiverStudentIds;
	/**
	 * 文档ids
	 */
	private List<Long> docIds;
	/**
	 * 目录ids
	 */
	private List<Long> dirIds;

	public List<Long> getDocIds() {
		return docIds;
	}

	public void setDocIds(List<Long> docIds) {
		this.docIds = docIds;
	}

	public List<Long> getDirIds() {
		return dirIds;
	}

	public void setDirIds(List<Long> dirIds) {
		this.dirIds = dirIds;
	}

	/**
	 * @see #receiverStudentIds
	 */
	public List<Long> getReceiverStudentIds() {
		return receiverStudentIds;
	}

	/**
	 * @see #receiverStudentIds
	 */
	public void setReceiverStudentIds(List<Long> receiverStudentIds) {
		this.receiverStudentIds = receiverStudentIds;
	}
}
