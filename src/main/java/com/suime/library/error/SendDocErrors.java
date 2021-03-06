package com.suime.library.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * Created by origin on 2015/12/23.
 */
public final class SendDocErrors extends BusinessException {
	private static SendDocErrors instance = new SendDocErrors();

	public static SendDocErrors getInstance() {
		return instance;
	}

	public BusinessException userConflict() {
		return new BusinessException("DOC001", "user conflicting", SpringContext.getText("errors.senddoc.user.conflict", new Object[] {}), new Object[0]);
	}

	public BusinessException docTypeNotFound() {
		return new BusinessException("DOC002", "doctype not found", SpringContext.getText("errors.senddoc.doctype.not_found", new Object[] {}), new Object[0]);
	}

	public BusinessException sendError(String errorInfo) {
		return new BusinessException("DOC991", "send error", errorInfo, new Object[0]);
	}

	public BusinessException test(String code) {
		return new BusinessException("06001", "code already exists", SpringContext.getText("errors.activity.exists", new Object[] { code }), new Object[0]);
	}

}
