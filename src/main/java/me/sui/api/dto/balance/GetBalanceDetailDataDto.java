package me.sui.api.dto.balance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GetBalanceDetailDataDto implements Serializable {
	private Long balanceDetailId;//流水号
	private Date tradeDate;//交易时间
	private BigDecimal balanceBefore;//交易前的余额
	private BigDecimal balanceAfter;//交易后的余额
	private BigDecimal balanceChange;//变化金额
	private String tradeSign;//交易方向 +/-
	private String tradeDesc;//交易说明
	private Long relateId;//相关ID

	public Long getBalanceDetailId() {
		return balanceDetailId;
	}

	public void setBalanceDetailId(Long balanceDetailId) {
		this.balanceDetailId = balanceDetailId;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public BigDecimal getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(BigDecimal balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(BigDecimal balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public BigDecimal getBalanceChange() {
		return balanceChange;
	}

	public void setBalanceChange(BigDecimal balanceChange) {
		this.balanceChange = balanceChange;
	}

	public String getTradeSign() {
		return tradeSign;
	}

	public void setTradeSign(String tradeSign) {
		this.tradeSign = tradeSign;
	}

	public String getTradeDesc() {
		return tradeDesc;
	}

	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}

	public Long getRelateId() {
		return relateId;
	}

	public void setRelateId(Long relateId) {
		this.relateId = relateId;
	}
}