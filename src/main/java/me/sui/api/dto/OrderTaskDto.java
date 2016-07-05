package me.sui.api.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrderTaskDto implements Serializable {

	/**
	 * id
	 */
	private Long id;

	/**
	 * studentId
	 */
	private Long studentId;

	/**
	 * visitorId
	 */
	private String visitorId;

	/**
	 * filelinkId
	 */
	private Long filelinkId;

	/**
	 * fileKey
	 */
	private String fileKey;

	/**
	 * fileName
	 */
	private String fileName;

	/**
	 * printOrderId
	 */
	private Long printOrderId;

	/**
	 * printshopId
	 */
	private Long printshopId;

	/**
	 * printerId
	 */
	private Integer printerId;

	/**
	 * bothside
	 */
	private Byte bothside;

	/**
	 * colorful
	 */
	private Byte colorful;

	/**
	 * copies
	 */
	private Integer copies;

	/**
	 * handouts
	 */
	private String handouts;

	/**
	 * startPage
	 */
	private Integer startPage;

	/**
	 * countPage
	 */
	private Integer countPage;

	/**
	 * pages
	 */
	private Integer pages;

	/**
	 * pagesAdjusted
	 */
	private Integer pagesAdjusted;

	/**
	 * priceForShop
	 */
	private Integer priceForShop;

	/**
	 * state
	 */
	private Integer state;

	/**
	 * downloadedAt
	 */
	private Timestamp downloadedAt;

	/**
	 * printedAt
	 */
	private Timestamp printedAt;

	/**
	 * lastPrintedAt
	 */
	private Timestamp lastPrintedAt;

	/**
	 * createdAt
	 */
	private Timestamp createdAt;

	/**
	 * updatedAt
	 */
	private Timestamp updatedAt;

	/**
	 * deletedAt
	 */
	private Timestamp deletedAt;

	/**
	 * lib
	 */
	private Byte lib;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setFilelinkId(Long filelinkId) {
		this.filelinkId = filelinkId;
	}

	public Long getFilelinkId() {
		return filelinkId;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setPrintOrderId(Long printOrderId) {
		this.printOrderId = printOrderId;
	}

	public Long getPrintOrderId() {
		return printOrderId;
	}

	public void setPrintshopId(Long printshopId) {
		this.printshopId = printshopId;
	}

	public Long getPrintshopId() {
		return printshopId;
	}

	public void setPrinterId(Integer printerId) {
		this.printerId = printerId;
	}

	public Integer getPrinterId() {
		return printerId;
	}

	public void setBothside(Byte bothside) {
		this.bothside = bothside;
	}

	public Byte getBothside() {
		return bothside;
	}

	public void setColorful(Byte colorful) {
		this.colorful = colorful;
	}

	public Byte getColorful() {
		return colorful;
	}

	public void setCopies(Integer copies) {
		this.copies = copies;
	}

	public Integer getCopies() {
		return copies;
	}

	public void setHandouts(String handouts) {
		this.handouts = handouts;
	}

	public String getHandouts() {
		return handouts;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public Integer getStartPage() {
		return startPage;
	}

	public void setCountPage(Integer countPage) {
		this.countPage = countPage;
	}

	public Integer getCountPage() {
		return countPage;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPagesAdjusted(Integer pagesAdjusted) {
		this.pagesAdjusted = pagesAdjusted;
	}

	public Integer getPagesAdjusted() {
		return pagesAdjusted;
	}

	public void setPriceForShop(Integer priceForShop) {
		this.priceForShop = priceForShop;
	}

	public Integer getPriceForShop() {
		return priceForShop;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getState() {
		return state;
	}

	public void setDownloadedAt(Timestamp downloadedAt) {
		this.downloadedAt = downloadedAt;
	}

	public Timestamp getDownloadedAt() {
		return downloadedAt;
	}

	public void setPrintedAt(Timestamp printedAt) {
		this.printedAt = printedAt;
	}

	public Timestamp getPrintedAt() {
		return printedAt;
	}

	public void setLastPrintedAt(Timestamp lastPrintedAt) {
		this.lastPrintedAt = lastPrintedAt;
	}

	public Timestamp getLastPrintedAt() {
		return lastPrintedAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setLib(Byte lib) {
		this.lib = lib;
	}

	public Byte getLib() {
		return lib;
	}

}
