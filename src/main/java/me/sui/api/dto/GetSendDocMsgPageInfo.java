package me.sui.api.dto;

import java.io.Serializable;

public class GetSendDocMsgPageInfo implements Serializable {
	private int pageNum;// 当前页码
	private int pageSize;// 每页记录数
	private long total;// 总记录数
	private int pages;// 总页数

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

}
