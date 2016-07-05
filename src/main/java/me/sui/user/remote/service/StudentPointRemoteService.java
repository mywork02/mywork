package me.sui.user.remote.service;

import me.sui.context.support.PointTypeEnum;

/**
 * Created by ice on 11/4/2016.
 */
public interface StudentPointRemoteService {

    /**
     * 添加学生积分log
     *
     * @param pointTypeEnum 积分类型
     * @param studentId     学生id
     * @param relateId      关联id
     * @param amount        变化数,如连续登录天数,积分变化数量等
     * @return studentPointLog
     */
	void addStudentPointLog(PointTypeEnum pointTypeEnum, Long studentId, Long relateId, Integer amount);
}
