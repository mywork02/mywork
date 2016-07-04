package com.suime.library.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * Created by origin on 2016/1/7.
 */
public final class CommentErrors extends BusinessException {
    private static CommentErrors instance = new CommentErrors();

    public static CommentErrors getInstance() {
        return instance;
    }

    public BusinessException commentNotFoundError() {
        return new BusinessException("09001", "comment not found", SpringContext.getText("errors.comment.not_found"), new Object[0]);
    }

}
