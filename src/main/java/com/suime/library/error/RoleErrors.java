package com.suime.library.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * Created by origin on 2016/1/7.
 */
public final class RoleErrors extends BusinessException {

    private static RoleErrors instance = new RoleErrors();

    public static RoleErrors getInstance() {
        return instance;
    }

    public BusinessException roleNotFoundError(Long id) {
        return new BusinessException("07001", "role not found ", SpringContext.getText("errors.role.not_found", new Object[]{id}), new Object[0]);
    }

}
