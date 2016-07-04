package com.suime.library.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * 文库errors
 * @author ice
 */
public final class LibraryErrors {

	/**
	 * instance
	 */
	private static LibraryErrors instance = new LibraryErrors();

	/**
	 * constructor
	 */
	private LibraryErrors() {

	}

	public static LibraryErrors getInstance() {
		return instance;
	}

	/**
	 * 文集未找到
	 * @return BusinessException
	 */
	public BusinessException collectedWorksNotFound() {
		throw new BusinessException("", "collected works not found", SpringContext.getText("errors.collected_works.not_found"));
	}

	/**
	 * 不能收藏自己的文集
	 * @return BusinessException
	 */
	public BusinessException canNotMarkOwnCollectedWorks() {
		throw new BusinessException("", "can not mark own collected works", SpringContext.getText("errors.collected_works.can_not_mark_own"));
	}

}
