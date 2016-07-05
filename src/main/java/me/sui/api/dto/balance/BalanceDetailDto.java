package me.sui.api.dto.balance;

import java.io.Serializable;
import java.util.Date;

public class BalanceDetailDto implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 余额类型 1:会员余额 2:会员积分
     */
    private Integer cashType;

    /**
     * 余额变化方向 1:+ 2:-
     */
    private Integer way;

    /**
     * 操作前的余额
     */
    private Integer oldCash;

    /**
     * 操作后的余额
     */
    private Integer newCash;

    /**
     * 余额变化数量
     */
    private Integer changeCash;

    /**
     * 1:会员充值 2:创建订单 3:取消订单 4:支付宝支付 5:微信支付 6:快捷支付 7:退款 8:付费操作
     */
    private Integer type;

    /**
     * 对应的数据表id
     */
    private Long relateId;

    /**
     * 
     */
    private Date createdAt;

    /**
     * 
     */
    private Date updatedAt;

	/**
	 * @see #id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @see #id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @see #studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @see #studentId
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * @see #cashType
	 */
	public Integer getCashType() {
		return cashType;
	}

	/**
	 * @see #cashType
	 */
	public void setCashType(Integer cashType) {
		this.cashType = cashType;
	}

	/**
	 * @see #way
	 */
	public Integer getWay() {
		return way;
	}

	/**
	 * @see #way
	 */
	public void setWay(Integer way) {
		this.way = way;
	}

	/**
	 * @see #oldCash
	 */
	public Integer getOldCash() {
		return oldCash;
	}

	/**
	 * @see #oldCash
	 */
	public void setOldCash(Integer oldCash) {
		this.oldCash = oldCash;
	}

	/**
	 * @see #newCash
	 */
	public Integer getNewCash() {
		return newCash;
	}

	/**
	 * @see #newCash
	 */
	public void setNewCash(Integer newCash) {
		this.newCash = newCash;
	}

	/**
	 * @see #changeCash
	 */
	public Integer getChangeCash() {
		return changeCash;
	}

	/**
	 * @see #changeCash
	 */
	public void setChangeCash(Integer changeCash) {
		this.changeCash = changeCash;
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
	 * @see #relateId
	 */
	public Long getRelateId() {
		return relateId;
	}

	/**
	 * @see #relateId
	 */
	public void setRelateId(Long relateId) {
		this.relateId = relateId;
	}

	/**
	 * @see #createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @see #createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @see #updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @see #updatedAt
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}