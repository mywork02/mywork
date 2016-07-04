package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenji_printfile_printshop_ref 实体类
 * Created by ice 19/02/2016.
 */
public class PrintfilePrintshopRef implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -4361959634858023332L;

	/**
	 * id
	 */
	private Integer id;

	/**
	 * printshopId
	 */
	private Long printshopId;

	/**
	 * name
	 */
	private String name;

	/**
	 * fileId
	 */
	private Long fileId;

	/**
	 * privately
	 */
	private Byte privately;

	/**
	 * active
	 */
	private Byte active;

	/**
	 * updateTime
	 */
	private Timestamp updateTime;

	/**
	 * printTime
	 */
	private Integer printTime;

	/**
	 * priceSingle
	 */
	private Integer priceSingle;

	/**
	 * priceDouble
	 */
	private Integer priceDouble;

	/**
	 * copiesAllow
	 */
	private Integer copiesAllow;

	/**
	 * allowDelete
	 */
	private Byte allowDelete;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setPrintshopId(Long printshopId) {
		this.printshopId = printshopId;
	}

	public Long getPrintshopId() {
		return printshopId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setPrivately(Byte privately) {
		this.privately = privately;
	}

	public Byte getPrivately() {
		return privately;
	}

	public void setActive(Byte active) {
		this.active = active;
	}

	public Byte getActive() {
		return active;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setPrintTime(Integer printTime) {
		this.printTime = printTime;
	}

	public Integer getPrintTime() {
		return printTime;
	}

	public void setPriceSingle(Integer priceSingle) {
		this.priceSingle = priceSingle;
	}

	public Integer getPriceSingle() {
		return priceSingle;
	}

	public void setPriceDouble(Integer priceDouble) {
		this.priceDouble = priceDouble;
	}

	public Integer getPriceDouble() {
		return priceDouble;
	}

	public void setCopiesAllow(Integer copiesAllow) {
		this.copiesAllow = copiesAllow;
	}

	public Integer getCopiesAllow() {
		return copiesAllow;
	}

	public void setAllowDelete(Byte allowDelete) {
		this.allowDelete = allowDelete;
	}

	public Byte getAllowDelete() {
		return allowDelete;
	}

}