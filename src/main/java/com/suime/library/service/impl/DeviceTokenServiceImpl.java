package com.suime.library.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.utils.DateUtil;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.context.model.DeviceToken;
import com.suime.context.model.support.LibraryConstants;
import com.suime.library.dao.DeviceTokenMapper;
import com.suime.library.service.DeviceTokenService;

/**
 * deviceTokenService
 * Created by ice 22/03/2016.
 */
@Service("deviceTokenService")
public class DeviceTokenServiceImpl extends GenericServiceImpl<DeviceToken> implements DeviceTokenService {

    /**
     * deviceTokenMapper
     */
    @Autowired
    @Qualifier("deviceTokenMapper")
    private DeviceTokenMapper deviceTokenMapper;

    @Override
    public GenericMapper<DeviceToken> getGenericMapper() {
        return deviceTokenMapper;
    }

    @Override
    public void addUserDeviceToken(Long userId, String token, Byte deviceType) {

        Conds userConds = new Conds();
        userConds.equal("device_type", deviceType);
        userConds.equal("user_id", userId);

        Conds tokenConds = new Conds();
        tokenConds.equal("device_type", deviceType);
        tokenConds.equal("token", token);

        DeviceToken deviceTokenByUser = this.fetchSearchByConds(userConds);

        DeviceToken deviceTokenByToken = this.fetchSearchByConds(tokenConds);
        if (deviceTokenByUser != null) {
            if (deviceTokenByToken != null && !deviceTokenByUser.getId().equals(deviceTokenByToken.getId())) {
                this.delete(deviceTokenByToken.getId());
            }
        } else {
            deviceTokenByUser = deviceTokenByToken;
        }
        Timestamp sqlTimestamp = DateUtil.getSqlTimestamp();
        if (deviceTokenByUser == null) {
            deviceTokenByUser = new DeviceToken();
            deviceTokenByUser.setCreatedAt(sqlTimestamp);
        }
        deviceTokenByUser.setUserType(LibraryConstants.USER_TYPE_STUDENT);
        deviceTokenByUser.setUpdatedAt(sqlTimestamp);
        deviceTokenByUser.setUserId(userId);
        deviceTokenByUser.setToken(token);
        deviceTokenByUser.setDeviceType(deviceType);
        if (deviceTokenByUser.getId() == null) {
            this.save(deviceTokenByUser);
        } else {
            this.update(deviceTokenByUser);
        }
    }
}
