package com.yigos.framework.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class BaseServiceImpl {
	public HttpServletRequest getRequest() {
        if (RequestContextHolder.getRequestAttributes() == null)
            return null;
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
}
