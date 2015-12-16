package com.yigos.framework.web.mvc.request.mapping;

import java.lang.reflect.Method;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * RequestMapping 注解的解析
 * @author kavin
 *
 */
public class YigosRequestMappingHandlerMapping extends
		RequestMappingHandlerMapping {
	private String basePackage;

	public String getBasePackage() {
		return this.basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	protected RequestMappingInfo getMappingForMethod(Method method,
			Class<?> handlerType) {
		if (!StringUtils.isEmpty(this.basePackage)
				&& handlerType.getName().startsWith(this.basePackage)) {
			RequestMappingInfo info = null;
			RequestMapping methodAnnotation = (RequestMapping) AnnotationUtils
					.findAnnotation(method, RequestMapping.class);
			if (methodAnnotation != null) {
				RequestCondition<?> methodCondition = getCustomMethodCondition(method);
				info = createDefaultRequestMappingInfo(method, handlerType,
						methodAnnotation, methodCondition);
			}
			return info;
		}
		return super.getMappingForMethod(method, handlerType);
	}

	private RequestMappingInfo createDefaultRequestMappingInfo(Method method,
			Class<?> handlerType, RequestMapping annotation,
			RequestCondition<?> customCondition) {
		String[] path = { defaultMappingPath(method, handlerType) };
		return new RequestMappingInfo(new PatternsRequestCondition(path,
				getUrlPathHelper(), getPathMatcher(), useSuffixPatternMatch(),
				useTrailingSlashMatch(), getFileExtensions()),
				new RequestMethodsRequestCondition(annotation.method()),
				new ParamsRequestCondition(annotation.params()),
				new HeadersRequestCondition(annotation.headers()),
				new ConsumesRequestCondition(annotation.consumes(), annotation
						.headers()), new ProducesRequestCondition(
						annotation.produces(), annotation.headers(),
						getContentNegotiationManager()), customCondition);
	}

	private String defaultMappingPath(Method method, Class<?> handlerType) {
		StringBuffer path = new StringBuffer("");
		path.append(basePath(handlerType))
		.append("/")
		.append(classToUrl(handlerType))
		.append("/")
		.append(upperToLower(method.getName()));
		return path.toString();
	}
	private String basePath(Class<?> handlerType){
		String basePath = ClassUtils.getPackageName(handlerType)
		.replace(this.basePackage, "").replace(".controller", "").replace('.', '/');
		return basePath;
	}
	
	private String classToUrl(Class<?> handlerType){
		String clazz = ClassUtils.getShortName(handlerType).replace("Controller", "");
		return upperToLower(clazz);
	}

	private String upperToLower(String name) {
		if (!StringUtils.isEmpty(name)) {
			StringBuilder sb = new StringBuilder(name.length() + 4);
			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);
				if (Character.isUpperCase(c)) {
					if (i != 0) {
						sb.append('-');
					}
					sb.append(Character.toLowerCase(c));
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		}
		return null;
	}
}