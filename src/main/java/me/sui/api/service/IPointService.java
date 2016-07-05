package me.sui.api.service;

import me.sui.api.constants.PointTypeEnum;
import me.sui.api.dto.PointResDto;

public interface IPointService {

	/**
	 * 添加学生积分log
	 *
	 * @param pointTypeEnum 积分类型
	 * @param studentId     学生id
	 * @param relateId      关联id
	 * @param amount        变化数,如连续登录天数,积分变化数量等
	 */

	PointResDto point(PointTypeEnum pointTypeEnum, Long studentId, Long relateId, Integer amount);
}
