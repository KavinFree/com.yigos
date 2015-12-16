package com.yigos.framework.exception.msg;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

/**
 * 
 * 按國際化方式獲取錯誤碼對應的內容
 * @author Jason Ling
 * @since 2013年5月6日 
 */
@Component
public class ErrorMsgSrvImpl implements ErrorMsgSrv {
	/** 
	 * messageSource:
	 */
	@Autowired
	protected ResourceBundleMessageSource messageSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.biz.sky.b2c.framework.exception.ErrorMsgSrv#getErrorMsg(javax.servlet
	 * .http.HttpServletRequest, java.lang.String, java.lang.Object[])
	 */
	@Override
    public String getErrorMsg(HttpServletRequest request, String errorCode,
			Object[] arg) {
		// 語言
		Locale language = getLocale(request);
		String errorContent = messageSource.getMessage(
				"exception." + errorCode, arg, language);
		return errorContent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.biz.sky.b2c.framework.exception.ErrorMsgSrv#getErrorMsg(javax.servlet
	 * .http.HttpServletRequest, java.lang.String)
	 */
    @Override
	public String getErrorMsg(HttpServletRequest request, String errorCode) {
		// 語言
		Locale language = getLocale(request);
		String errorContent = messageSource.getMessage(
				"exception." + errorCode, null, language);
		return errorContent;
	}
    
	/**
	 * 獲取語種
	 * @param request 請求對象
	 * @return Locale
	 * @author Jason Ling
	 * @since 2013年5月6日  
	*/ 
	private Locale getLocale(HttpServletRequest request) {
		Locale locale = ((Locale) WebUtils.getSessionAttribute(request,
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
		if (locale == null) {
			locale = Locale.TRADITIONAL_CHINESE;
		}
		return locale;
	}
}
