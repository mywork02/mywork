package com.confucian.framework.config;

import com.confucian.framework.encode.SpringPropertyCoder;
import com.confucian.framework.utils.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Properties;
import java.util.Set;

/**
 * 资源文件字符串解密
 * Created by ice on 11/5/16.
 */
public class PropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

    /**
     * 待解密的字符串前缀
     */
    private static final String ENC_STR = "ENC:";

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {

        Set<String> propertyKeys = props.stringPropertyNames();
        for (String propertyKey : propertyKeys) {
            String value = props.getProperty(propertyKey);
            if (StringUtils.startsWith(value, ENC_STR)) {
                value = StringUtils.substringAfter(value, ENC_STR);
                value = SpringPropertyCoder.decode(value);
                props.setProperty(propertyKey, value);
            }
        }
        super.processProperties(beanFactoryToProcess, props);
    }
}
