package com.suime.library.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * Created by origin on 2016/1/7.
 */
public final class TagsErrors extends BusinessException {
    private static TagsErrors instance = new TagsErrors();

    public static TagsErrors getInstance() {
        return instance;
    }

    public BusinessException tagsNotFoundError(){
        return  new BusinessException("11001","tags not found", SpringContext.getText("errors.tags.not_found"),new Object[0]);
    }

    public BusinessException textIsEmptyError(){
        return new BusinessException("11002","tag's text is empty", SpringContext.getText("errors.tags.text_empty"),new Object[0]);
    }

    public BusinessException textTooLongError(){
        return new BusinessException("11003","tag's text is too long", SpringContext.getText("errors.tags.text_too_long"),new Object[0]);
    }

    public BusinessException alreadyExistsError(String text){
        return new BusinessException("11004","already exists", SpringContext.getText("errors.tags.already_exists", new Object[]{text}),new Object[0]);
    }


}
