package com.suime.library.service;

import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.DeviceToken;

/**
 * deviceTokenService
 * Created by ice 22/03/2016.
 */
public interface DeviceTokenService extends GenericService<DeviceToken> {

    /**
     * 添加用户deviceToken
     *
     * @param userId     学生id
     * @param token      deviceTOken
     * @param deviceType ios or android
     */
    void addUserDeviceToken(Long userId, String token, Byte deviceType);

}
