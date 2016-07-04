package com.suime.library.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * Created by origin on 2016/1/7.
 */
public final class CategoryErrors extends BusinessException {
    private static CategoryErrors instance = new CategoryErrors();

    public static CategoryErrors getInstance() {
        return instance;
    }

    public BusinessException categoryAlreadyExistsError() {
        return new BusinessException("08001", "category already exists ", SpringContext.getText("errors.category.exists"), new Object[0]);
    }

    public BusinessException codeAlreadyExistsError(String code) {
        return new BusinessException("08002", "code already exists", SpringContext.getText("errors.category.code_exists", new Object[]{code}), new Object[0]);
    }

    public BusinessException categoryNotFoundError() {
        return new BusinessException("08003", "category not found", SpringContext.getText("errors.category.not_found"), new Object[0]);
    }
}
