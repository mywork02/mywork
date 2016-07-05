package me.sui.api.dto.balance;

import java.io.Serializable;
import java.util.Date;

public class GetBalanceDetailReqDto implements Serializable {

	private String sourceCode;// 请求源
	private String requestId;// 请求源的ID
	private String requestTime;// 请求时间YYYY-MM-DD HH:mm:ss
	private Long studentId;// 学生ID
	private Integer type;//1:会员充值 2:创建订单 3:取消订单 4:支付宝支付 5:微信支付 6:快捷支付 7:退款 8:付费操作
	private Integer cashType;// 1:会员余额 2:会员积分
	private Date bDate;//查询时间下限(大于等于)
	private Date eDate;//查询时间上限(小于等于)
	private int pageNum;// 当前页
	private int pageSize;// 每页条数

	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Date getbDate() {
		return bDate;
	}

	public void setbDate(Date bDate) {
		this.bDate = bDate;
	}

	public Date geteDate() {
		return eDate;
	}

	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}

	/**
	 * @see #type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @see #type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @see #cashType
	 */
	public Integer getCashType() {
		if (cashType == null)
			return Integer.valueOf(1);
		return cashType;
	}

	/**
	 * @see #cashType
	 */
	public void setCashType(Integer cashType) {
		this.cashType = cashType;
	}

}
