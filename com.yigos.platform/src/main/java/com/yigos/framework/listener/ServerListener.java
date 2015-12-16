package com.yigos.framework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.google.gson.Gson;
import com.yigos.test.service.AsyncServiceImpl;

public class ServerListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(ServerListener.class);

	private AsyncServiceImpl asyncServiceImpl;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		/*
		ApplicationContext applicationContext = (ApplicationContext)event.getServletContext()
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		if (applicationContext != null) {
			asyncServiceImpl = applicationContext.getBean(
			"com.yigos.test.AsyncServiceImpl", 
			AsyncServiceImpl.class);
		}
*/
		WebApplicationContext webApplicationContext = WebApplicationContextUtils. 
				 getWebApplicationContext(event.getServletContext()); 
		if(webApplicationContext!=null){
			asyncServiceImpl = webApplicationContext.getBean("com.yigos.test.service.AsyncServiceImpl",
					AsyncServiceImpl.class);
		}

		if (asyncServiceImpl != null) {
			asyncServiceImpl.loadDataForInit();
			logger.info(new Gson().toJson(asyncServiceImpl.getCache()));
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (asyncServiceImpl != null) {
			asyncServiceImpl.saveDataForDestroy();
			logger.info(new Gson().toJson(asyncServiceImpl.getCache()));
		}
	}

}
