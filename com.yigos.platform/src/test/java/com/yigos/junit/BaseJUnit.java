package com.yigos.junit;

import java.lang.reflect.Field;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.GenericXmlContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.SessionScope;

import com.yigos.auth.user.view.SysUserView;

@ContextConfiguration(locations = { 
		"classpath:spring/applicationContext.xml",
		"classpath:spring/yigos-servlet.xml",
	    "classpath:log4j.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager",
		defaultRollback = false)
public class BaseJUnit extends AbstractTransactionalJUnit4SpringContextTests{

	static class ContextLoader extends GenericXmlContextLoader {
        @Override
        protected void customizeBeanFactory(
            DefaultListableBeanFactory beanFactory) {
            beanFactory.registerScope("request", new RequestScope());
            beanFactory.registerScope("session", new SessionScope());
            super.customizeBeanFactory(beanFactory);
        }
    }
    
	/**
	 * static MockHttpServletRequest request
	 */
	protected static MockHttpServletRequest request;
	/**
	 * static MockHttpServletResponse response
	 */
	protected static MockHttpServletResponse response;
	
	protected static Model model ;
	
	@Before
	public void setUp() {
		initMockData();
	}
	
	protected void initMockData() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		model = new ExtendedModelMap();
		HttpSession session = request.getSession();
		SysUserView view = new SysUserView();
		view.setCode("001");
        session.setAttribute("yigosSession", view);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}
	
	protected static final Logger LOGGER = Logger.getLogger(BaseJUnit.class);

	protected final void assertEquals(Object expected, Object actual) {
        LOGGER.trace("expected [" + expected + "]");
        LOGGER.trace("actual   [" + actual + "]");
        org.junit.Assert.assertEquals(expected, actual);
    }
	
	protected final void setField(Object target, String fieldName, Object source) {
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        field.setAccessible(true);
        ReflectionUtils.setField(field, target, source);
    }
	
}
