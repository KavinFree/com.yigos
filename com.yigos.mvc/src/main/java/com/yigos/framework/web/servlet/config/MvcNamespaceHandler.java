package com.yigos.framework.web.servlet.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MvcNamespaceHandler extends NamespaceHandlerSupport {
	public void init() {
		registerBeanDefinitionParser("annotation-driven",
				new SpringAnnotationDrivenBeanDefinitionParser());
	}
}
