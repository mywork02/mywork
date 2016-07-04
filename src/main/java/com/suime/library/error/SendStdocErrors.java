package com.suime.library.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * Created by chenqy on 2016/4/12.
 */
public final class SendStdocErrors extends BusinessException{

    private static SendStdocErrors instance = new SendStdocErrors();

    public static SendStdocErrors getInstance() {
        return instance;
    }

    public BusinessException alreadyProcessedError(){
        return new BusinessException("18001","already processed", SpringContext.getText("errors.send_stdoc.already_processed"));
    }
}
