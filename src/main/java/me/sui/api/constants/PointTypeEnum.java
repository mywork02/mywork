package me.sui.api.constants;

/**
 * Created by ice on 24/3/2016.
 */
public enum PointTypeEnum {
	/**
	 * 注册
	 */
	REGISTER(1),
	/**
	 * 完善信息
	 */
	PERFECT(2),
	/**
	 * 签到
	 */
	SIGN_IN(3),
	/**
	 * 点击广告
	 */
	CLICK_ADS(4),
	/**
	 * 分享
	 */
	SHARE(5),
	/**
	 * 收藏
	 */
	MARK(6),
	/**
	 * 回复
	 */
	REPLY(7),
	/**
	 * 打印
	 */
	PRINT(8),
	/**
	 * 充值
	 */
	RECHARGE(9),
	/**
	 * 上传文档
	 */
	UPLOAD_DOCUMENT(10),
	/**
	 * 等级提升
	 */
	LEVEL_UP(11),
	/**
	 * 订单取消退还抵扣
	 */
	ORDER_CANCEL_RETURN(12),
	/**
	 * 订单退款退还
	 */
	ORDER_REFUND_RETURN(13),
	/**
	 * 积分抵扣(打印抵扣)
	 */
	ORDER_DEDUCT(21),
	/**
	 * 积分消费(积分商城)
	 */
	CONSUME(22);
	// 1:注册,2:完善信息,3:签到,4:点击广告,5:分享,6:收藏,7:回复,8:打印,9:充值,10:上传文档,11:等级提升,21:积分抵扣,22:积分消费

	private int value;

	PointTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
