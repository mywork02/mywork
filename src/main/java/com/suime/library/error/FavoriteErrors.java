package com.suime.library.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * Created by origin on 2016/1/21.
 */
public class FavoriteErrors extends BusinessException {
    private static FavoriteErrors instance = new FavoriteErrors();

    public static FavoriteErrors getInstance() {
        return instance;
    }
    public BusinessException favoriteNotExistsError(){
        return new BusinessException("19011","favorite not exists", SpringContext.getText("errors.favorite.not_found"),new Object[0]);
    }
    public BusinessException allreadyExistError(String name){
        return new BusinessException("19012","favorite already exist", SpringContext.getText("errors.favorite.already_exist", new Object[]{name}),new Object[0]);
    }

}