package com.suime.library.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * Created by origin on 2015/12/23.
 */
public final class ActivityErrors extends BusinessException {
    private static ActivityErrors instance = new ActivityErrors();

    public static ActivityErrors getInstance() {
        return instance;
    }

    public BusinessException codeAlreadyExistsError(String code) {
        return new BusinessException("06001", "code already exists", SpringContext.getText("errors.activity.exists", new Object[]{code}), new Object[0]);
    }

    public BusinessException activityNotFoundError(Long id) {
        return new BusinessException("06002", "activity not found", SpringContext.getText("errors.activity.not_found", new Object[]{id}), new Object[0]);
    }

    public BusinessException activityNotFoundError(String code) {
        return new BusinessException("06002", "activity not found", SpringContext.getText("errors.activity.not_found", new Object[]{code}), new Object[0]);
    }

    public BusinessException keyWordsNumberNotEqualSubTitlesNumberError(String keyWords, String subTitles) {
        return new BusinessException("06003", " keyWords number is not equal subTitles number", SpringContext.getText("errors.activity.keyWordsNumNotEqualSubTitleNum"
                , new Object[]{keyWords, subTitles}), new Object[0]);
    }

}
