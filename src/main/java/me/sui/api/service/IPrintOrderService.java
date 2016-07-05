package me.sui.api.service;

import me.sui.api.dto.CancelPrintOrderResDto;
import me.sui.api.dto.OrderDetailDto;
import me.sui.api.dto.balance.AddBalanceDetailReqDto;
import me.sui.api.dto.balance.AddBalanceDetailResDto;
import me.sui.api.dto.balance.GetBalanceDetailReqDto;
import me.sui.api.dto.balance.GetBalanceDetailResDto;
import me.sui.api.dto.base.BaseResDto;

public interface IPrintOrderService {

	/**
	 * 取消订单
	 * @param userId
	 * @param printOrderId
	 * @return
	 */
	public CancelPrintOrderResDto cancelPrintOrder(Long userId, Long printOrderId);

	/**
	 * 获取余额流水明细
	 * @param req
	 * @return
	 */
	public GetBalanceDetailResDto getBalanceDetail(GetBalanceDetailReqDto req);

	/**
	 * 更新余额流水
	 * @param req
	 * @return
	 */
	public AddBalanceDetailResDto addBalanceDetail(AddBalanceDetailReqDto req);


	/**
	 * 查询订单详情
	 * @param orderId
	 * @return
	 */
	public OrderDetailDto getOrderDetail(Long orderId);

	/**
	 * 打印店暂停营业  (把打印店状态改成0)
	 * 状态 1:开业,0:歇业,3:停业
	 * @param printshopId
	 * @return
	 */
	public BaseResDto suspendPrintshop(Long printshopId);

	/**
	 * 自动确认订单
	 * @param orderId
	 * @return
	 */
	public BaseResDto autoConfirmOrder(Long orderId);

	/**
	 * 手动确认订单
	 * @param orderId
	 * @return
	 */
	public BaseResDto manualConfirmOrder(Long orderId);

}
