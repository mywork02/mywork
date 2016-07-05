package com.confucian.framework.web;

import com.confucian.framework.ioc.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * controller 抽象类
 *
 * @author ice
 */
public abstract class AbstractController {
    /**
     * logger
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * spring i18n资源文件获取key内容 用数组填充资源文<br>
     * 如果没有对应的local文件,ResourceBundleMessageSource则使用历史defaultLocale获取text。
     *
     * @param msgKey mskKey
     * @param args   填充信息,可变参数
     * @return 对应文本
     */
    protected String getText(String msgKey, Object... args) {
        return SpringContext.getText(msgKey, args);
    }

}
