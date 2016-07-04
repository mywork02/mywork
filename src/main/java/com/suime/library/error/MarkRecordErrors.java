package com.suime.library.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * Created by origin on 2016/1/7.
 */
public final class MarkRecordErrors extends BusinessException {
    private static MarkRecordErrors instance = new MarkRecordErrors();

    public static MarkRecordErrors getInstance() {
        return instance;
    }

    public BusinessException markRecordNotFoundError() {
        return new BusinessException("10001", "markRecord not found ", SpringContext.getText("errors.mark_record.not_found"), new Object[0]);
    }

    public BusinessException alreadyRemovedError() {
        return new BusinessException("10002", "markRecord already removed ", SpringContext.getText("errors.mark_record.already_removed"), new Object[0]);
    }

    public BusinessException canNotOwnFileError() {
        return new BusinessException("10002", "can't mark own file ", SpringContext.getText("errors.mark_own_file"), new Object[0]);
    }
}
