package com.yigos.framework.service;

import javax.servlet.http.HttpServletRequest;
import com.yigos.framework.dao.jpa.itf.JpaBaseDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.yigos.framework.dao.itf.BaseDao;

public class BaseServiceImpl {
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	protected BaseDao dao;

    @Autowired
    protected JpaBaseDao jpaBaseDao;

    @Autowired
    private SqlSession mybatis;
	
	protected HttpServletRequest currentRequest() {
        if (RequestContextHolder.getRequestAttributes() == null)
            return null;
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
	
}
