package com.yigos.framework.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.ui.Model;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import com.yigos.framework.common.util.PagesMapping;
import com.yigos.framework.model.JsonModel;

public class BaseController extends MultiActionController implements Controller {
	/**
	 * 國際化資源匹配
	 */
	@Autowired
	protected ResourceBundleMessageSource messageSource;

	/**
	 * 國際化資源語言注入
	 */
	@Autowired
	protected LocaleResolver localeResolver;
	
	protected Locale getLocale(HttpServletRequest request) {
		Locale locale = ((Locale) WebUtils.getSessionAttribute(request,
		SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
		if (locale == null) {
			locale = Locale.TRADITIONAL_CHINESE;
		}
		return locale;
	}
	
	protected String getLocaleMessage(HttpServletRequest request,String key) {
		Locale locale = getLocale(request);
		return messageSource.getMessage(key, null, key, locale);
	}
	
	protected void writeJsonToResponse(Model model, boolean success, Object root) {
        model.asMap().clear();
        model.addAttribute(new JsonModel(success, root));
    }
	
    protected void writeJsonToResponse(Model model, boolean success,
        Object root, int count) {
        model.asMap().clear();
        model.addAttribute(new JsonModel(success, root, count));
    }
    
    protected String pagePath(String pageKey) {
		return PagesMapping.getProperty(pageKey);
	}
}
