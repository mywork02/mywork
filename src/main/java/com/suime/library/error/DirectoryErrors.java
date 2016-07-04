package com.suime.library.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * Created by origin on 2016/1/21.
 */
public class DirectoryErrors extends BusinessException {
    private static DirectoryErrors instance = new DirectoryErrors();

    public static DirectoryErrors getInstance() {
        return instance;
    }
    public BusinessException directoryNotExistsError(){
        return new BusinessException("12001","directory not exists", SpringContext.getText("errors.directory.not_found"),new Object[0]);
    }
    public BusinessException allreadyExistError(String name){
        return new BusinessException("12002","directory already exist", SpringContext.getText("errors.directory.already_exist", new Object[]{name}),new Object[0]);
    }
    public BusinessException directoryNotEmptyError(){
        return new BusinessException("12003","directory not empty", SpringContext.getText("errors.directory.not_empty"),new Object[0]);
    }

}