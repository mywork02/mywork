package me.sui.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDetailDto implements Serializable {

	private Long id;// 订单ID
	private Long studentId;// 学生ID
	private String studentCellPhone;// 学生
	private Long printshopId;// 楼长ID
	private String shopCellPhome;// 楼长电话
	private Long defaultPrinterId;// 默认打印机ID
	private Integer price;
	private Integer cashUsed;
	private Integer activityCashUsed;
	private Integer actualPrice;
	private Integer priceForShop;
	private Integer orderRealPrice;
	private Integer deliveryFee;
	private Integer hongbaoUsed;// 用了多少红包
	private Integer actualHongbaoAmount;// 实际上红包有多大
	private Integer hongbaoId;// 红包的 ID。
	private Integer needAds;
	private Integer state;// 1未支付2支付3取消4打印5打印完成6退款
	private String buyer;
	private String tradeNo;
	private String notifyId;
	private Integer pages;// 总面数，一页两面记为 2 面。
	private Integer paperCount;// 纸张数。
	private String deliveryRoom;
	private Long deliveryBuildingId;
	private String deliveryTime;
	private String deliveryNote;
	private Date createdAt;
	private Date paidAt;
	private Date refundedAt;
	private Date startedAt;
	private Date printedAt;
	private Date lastPrintedAt;// 最后一次打印时间，可能涉及重打
	private Date scanErrorAt;
	private Date updatedAt;
	private Date deletedAt;

	private List<OrderTaskDto> tasks;

	public void addTask(OrderTaskDto otDto) {
		if (tasks == null) {
			tasks = new ArrayList<OrderTaskDto>();
		}
		tasks.add(otDto);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getPrintshopId() {
		return printshopId;
	}

	public void setPrintshopId(Long printshopId) {
		this.printshopId = printshopId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCashUsed() {
		return cashUsed;
	}

	public void setCashUsed(Integer cashUsed) {
		this.cashUsed = cashUsed;
	}

	public Integer getActivityCashUsed() {
		return activityCashUsed;
	}

	public void setActivityCashUsed(Integer activityCashUsed) {
		this.activityCashUsed = activityCashUsed;
	}

	public Integer getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(Integer actualPrice) {
		this.actualPrice = actualPrice;
	}

	public Integer getPriceForShop() {
		return priceForShop;
	}

	public void setPriceForShop(Integer priceForShop) {
		this.priceForShop = priceForShop;
	}

	public Integer getOrderRealPrice() {
		return orderRealPrice;
	}

	public void setOrderRealPrice(Integer orderRealPrice) {
		this.orderRealPrice = orderRealPrice;
	}

	public Integer getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Integer deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Integer getHongbaoUsed() {
		return hongbaoUsed;
	}

	public void setHongbaoUsed(Integer hongbaoUsed) {
		this.hongbaoUsed = hongbaoUsed;
	}

	public Integer getActualHongbaoAmount() {
		return actualHongbaoAmount;
	}

	public void setActualHongbaoAmount(Integer actualHongbaoAmount) {
		this.actualHongbaoAmount = actualHongbaoAmount;
	}

	public Integer getHongbaoId() {
		return hongbaoId;
	}

	public void setHongbaoId(Integer hongbaoId) {
		this.hongbaoId = hongbaoId;
	}

	public Integer getNeedAds() {
		return needAds;
	}

	public void setNeedAds(Integer needAds) {
		this.needAds = needAds;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getPaperCount() {
		return paperCount;
	}

	public void setPaperCount(Integer paperCount) {
		this.paperCount = paperCount;
	}

	public String getDeliveryRoom() {
		return deliveryRoom;
	}

	public void setDeliveryRoom(String deliveryRoom) {
		this.deliveryRoom = deliveryRoom;
	}

	public Long getDeliveryBuildingId() {
		return deliveryBuildingId;
	}

	public void setDeliveryBuildingId(Long deliveryBuildingId) {
		this.deliveryBuildingId = deliveryBuildingId;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryNote() {
		return deliveryNote;
	}

	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(Date paidAt) {
		this.paidAt = paidAt;
	}

	public Date getRefundedAt() {
		return refundedAt;
	}

	public void setRefundedAt(Date refundedAt) {
		this.refundedAt = refundedAt;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public Date getPrintedAt() {
		return printedAt;
	}

	public void setPrintedAt(Date printedAt) {
		this.printedAt = printedAt;
	}

	public Date getLastPrintedAt() {
		return lastPrintedAt;
	}

	public void setLastPrintedAt(Date lastPrintedAt) {
		this.lastPrintedAt = lastPrintedAt;
	}

	public Date getScanErrorAt() {
		return scanErrorAt;
	}

	public void setScanErrorAt(Date scanErrorAt) {
		this.scanErrorAt = scanErrorAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public List<OrderTaskDto> getTasks() {
		return tasks;
	}

	public void setTasks(List<OrderTaskDto> tasks) {
		this.tasks = tasks;
	}

	public String getStudentCellPhone() {
		return studentCellPhone;
	}

	public void setStudentCellPhone(String studentCellPhone) {
		this.studentCellPhone = studentCellPhone;
	}

	public String getShopCellPhome() {
		return shopCellPhome;
	}

	public void setShopCellPhome(String shopCellPhome) {
		this.shopCellPhome = shopCellPhome;
	}

	public Long getDefaultPrinterId() {
		return defaultPrinterId;
	}

	public void setDefaultPrinterId(Long defaultPrinterId) {
		this.defaultPrinterId = defaultPrinterId;
	}

}
