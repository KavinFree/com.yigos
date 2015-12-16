package com.yigos.framework.web.context.generator;

import java.beans.Introspector;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.ClassUtils;

/**
 * BeanName 解析
 * @author kavin
 *
 */
public class YigosAnnotationBeanNameGenerator extends
		AnnotationBeanNameGenerator implements BeanNameGenerator {
	
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String shortClassName = ClassUtils.getShortName(definition
				.getBeanClassName());
		shortClassName = definition.getBeanClassName();
		return Introspector.decapitalize(shortClassName);
	}

}
