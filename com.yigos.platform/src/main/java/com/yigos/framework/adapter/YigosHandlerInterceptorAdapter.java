package com.yigos.framework.adapter;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class YigosHandlerInterceptorAdapter extends HandlerInterceptorAdapter {
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	protected HttpSession session;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
	        HttpServletResponse response, Object handler) throws Exception {
		Map<String, String[]> paraMap = request.getParameterMap();
		logger.info(paraMap);
		Device device = DeviceUtils.getCurrentDevice(request);
		String deviceDisplay = device.isMobile()?"mobile":device.isTablet()?"tablet":"pc";
		request.setAttribute("deviceDisplay", deviceDisplay);
		String hostPath = request.getScheme()+"://"+request.getServerName();
		String contextPath = hostPath+":"+request.getServerPort()+request.getContextPath();
		request.setAttribute("hostPath", hostPath);
		request.setAttribute("contextPath", contextPath);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
	        HttpServletResponse response, Object handler,
	        ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
	        HttpServletResponse response, Object handler, Exception ex)
	        throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
}
