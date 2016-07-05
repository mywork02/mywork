package com.confucian.framework.ioc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 获取spring ApplicationContext。该类需放在spring ioc里后自动初始化
 * @author ice
 */
public class SpringContext implements ApplicationContextAware {

	/**
	 * spring ApplicationContext
	 */
	private static ApplicationContext context;

	/**
	 * spring MessageSourceAccessor
	 */
	private static MessageSourceAccessor messageSourceAccessor;

	/**
	 * spring messageSource
	 */
	private static ResourceBundleMessageSource messageSource;
	/**
	 * messageSourceName<br>
	 * 默认值messageSource
	 */
	private String messageSourceName = "messageSource";

	/**
	 * 设置messageSourceName值
	 * @param messageSourceName messageSourceName
	 */
	public void setMessageSourceName(String messageSourceName) {
		this.messageSourceName = messageSourceName;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		messageSource = (ResourceBundleMessageSource) context.getBean(messageSourceName);
		messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	/**
	 * 获取ioc bean
	 * @param name bean id
	 * @return bean
	 */
	public static Object getBean(String name) {
		return context.getBean(name);
	}

	/**
	 * 获取ioc bean
	 * @param requiredType requiredType
	 * @param <T> classType
	 * @return bean
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return context.getBean(requiredType);
	}

	/**
	 * spring i18n资源文件获取key内容 用数组填充资源文<br>
	 * 如果没有对应的local文件,ResourceBundleMessageSource则使用历史defaultLocale获取text。
	 * @param msgKey mskKey
	 * @param args 填充信息,可变参数
	 * @return 对应文本
	 */
	public static String getText(String msgKey, Object... args) {
		return messageSourceAccessor.getMessage(msgKey, args, LocaleContextHolder.getLocale());
	}
}
