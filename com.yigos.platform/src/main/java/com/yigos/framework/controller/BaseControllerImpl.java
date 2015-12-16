package com.yigos.framework.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;
import com.yigos.framework.common.util.datetime.ConvertUtil;
import com.yigos.framework.common.util.datetime.DateTimeUtil;
import com.yigos.framework.common.util.mapping.PagesMapping;
import com.yigos.framework.model.JsonModel;

public class BaseControllerImpl extends MultiActionController {
	protected Logger logger = Logger.getLogger(getClass());
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
	
	@Autowired
	protected HttpSession session;

	@Autowired
	protected HttpServletRequest request;
	
	protected HttpServletRequest getRequest() {
        if (RequestContextHolder.getRequestAttributes() == null)
            return null;
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
	/*
	@ModelAttribute  
    protected void setResponse(HttpServletResponse response){
    	response.setHeader("Access-Control-Allow-Origin", "*");
    }
	*/
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
	
    protected void writeJsonToResponse(Model model, boolean success,
        int code, Object result, long totalCount) {
        model.asMap().clear();
        model.addAttribute(new JsonModel(success, code, result, totalCount));
    }
    
    protected String pagePath(String pageKey) {
		return PagesMapping.getProperty(pageKey);
	}
    
    /**
     * 日期參數綁定
     * @param binder
     * @author kavin
     * @since 2013年11月19日
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
    	binder.setIgnoreInvalidFields(false);
    	
        binder.registerCustomEditor(java.sql.Timestamp.class, new PropertyEditorSupport() {
			public void setAsText(String text) throws IllegalArgumentException {
				if (StringUtils.isBlank(text)) {
					super.setValue(null);
				} else {
					try {
						if(DateTimeUtil.isFormat(text, DateTimeUtil.yyyy_MM_dd)){
							super.setValue(new Timestamp(DateTimeUtil.toDate(text, DateTimeUtil.yyyy_MM_dd).getTime()));
						}else if(DateTimeUtil.isFormat(text, DateTimeUtil.yyyy_MM_dd_HH_mm_ss)){
							super.setValue(new Timestamp(DateTimeUtil.toDate(text, DateTimeUtil.yyyy_MM_dd_HH_mm_ss).getTime()));
						}
						
					} catch (Exception e) {
						super.setValue(Timestamp.valueOf(text));
					}
				}
			}
		});
        /*
		binder.registerCustomEditor(java.sql.Clob.class, new PropertyEditorSupport() {
			public void setAsText(String text) throws IllegalArgumentException {
				if (StringUtils.isBlank(text)) {
					super.setValue(null);
				} else {
					try {
						super.setValue(new ClobImpl(text));
					} catch (Exception e) {
						super.setValue(new ClobImpl(""));
					}
				}
			}
		});
		*/
		binder.registerCustomEditor(java.sql.Date.class, new PropertyEditorSupport() {
			public void setAsText(String text) throws IllegalArgumentException {
				if (StringUtils.isBlank(text)) {
					super.setValue(null);
				} else {
					try {
						super.setValue(new Timestamp(ConvertUtil.str2Date(text,
								"yyyy-MM-dd HH:mm:ss").getTime()));
					} catch (Exception e) {
						super.setValue(Timestamp.valueOf(text));
					}
				}
			}
		});
    	super.initBinder(request, binder);
    }
}
