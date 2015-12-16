package com.yigos.framework.adapter;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import com.yigos.framework.constant.DataDict;
import com.yigos.test.service.AsyncServiceImpl;

public class ManagerHandlerInterceptorAdapter extends YigosHandlerInterceptorAdapter {
	
	@Autowired
	private AsyncServiceImpl asyncService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
	        HttpServletResponse response, Object handler) throws Exception {
		String userId = (String) session.getAttribute(DataDict.SESSION_ID);
		if(null == userId) {
			String loginUrl = "/workspace/verify/login/index.html";
			request.setAttribute("contextPath", request.getContextPath());
			RequestDispatcher rd = request.getRequestDispatcher(loginUrl);
			rd.forward(request, response);
			return false;
		}
		
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
		Map<String, String[]> paraMap = request.getParameterMap();
		logger.info(paraMap);
		asyncService.loadDataToCache(paraMap);
		super.afterCompletion(request, response, handler, ex);
	}

}
