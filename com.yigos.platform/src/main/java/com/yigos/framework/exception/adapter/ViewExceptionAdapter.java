package com.yigos.framework.exception.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 * ModelAndView的適配器
 */
public interface ViewExceptionAdapter {
    /**
     * 針對不同的異常類型，不同的請求類型，返回相應的異常展示視圖
     * @param request 請求對象
     * @param response
     * @param handler
     * @param ex
     * @return ModelAndView
     * @throws Exception
    */ 
    ModelAndView createModelAndView(HttpServletRequest request,
        HttpServletResponse response, Object handler, Exception ex)
        throws Exception;
}
