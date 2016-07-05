package me.sui.context.support;

/**
 * Created by ice on 24/3/2016.
 */
public enum PointTypeEnum {
    /**
     * 注册
     */
    REGISTER((byte) 1),
    /**
     * 完善信息
     */
    PERFECT((byte) 2),
    /**
     * 签到
     */
    SIGN_IN((byte) 3),
    /**
     * 点击广告
     */
    CLICK_ADS((byte) 4),
    /**
     * 分享
     */
    SHARE((byte) 5),
    /**
     * 收藏
     */
    MARK((byte) 6),
    /**
     * 回复
     */
    REPLY((byte) 7),
    /**
     * 打印
     */
    PRINT((byte) 8),
    /**
     * 充值
     */
    RECHARGE((byte) 9),
    /**
     * 上传文档
     */
    UPLOAD_DOCUMENT((byte) 10),
    /**
     * 等级提升
     */
    LEVEL_UP((byte) 11),
    /**
     * 订单取消退还抵扣
     */
    ORDER_CANCEL_RETURN((byte) 12),
    /**
     * 订单退款退还
     */
    ORDER_REFUND_RETURN((byte) 13),
    /**
     * 积分兑换失败返还
     */
    CONSUME_FAILURE_RETURN((byte) 14),
    /**
     * 积分抵扣(打印抵扣)
     */
    ORDER_DEDUCT((byte) 21),
    /**
     * 积分消费(积分商城)
     */
    CONSUME((byte) 22);
    // 1:注册,2:完善信息,3:签到,4:点击广告,5:分享,6:收藏,7:回复,8:打印,9:充值,
    // 10:上传文档,11:等级提升,12:订单取消退还抵扣,13:订单退款退还,14:积分兑换失败返还,
    // 21:积分抵扣,22:积分消费

    private Byte value;

    PointTypeEnum(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }
}
