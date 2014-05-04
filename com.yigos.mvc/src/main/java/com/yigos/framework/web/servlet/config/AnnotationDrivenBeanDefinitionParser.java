package com.yigos.framework.web.servlet.config;

import java.util.List;
import java.util.Properties;

import com.yigos.framework.web.mvc.request.mapping.YigosRequestMappingHandlerMapping;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.util.xml.DomUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.servlet.handler.ConversionServiceExposingInterceptor;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.w3c.dom.Element;

class AnnotationDrivenBeanDefinitionParser implements
		BeanDefinitionParser {
	private static final boolean jsr303Present = ClassUtils.isPresent(
			"javax.validation.Validator",
			AnnotationDrivenBeanDefinitionParser.class.getClassLoader());

	private static final boolean jaxb2Present = ClassUtils.isPresent(
			"javax.xml.bind.Binder",
			AnnotationDrivenBeanDefinitionParser.class.getClassLoader());

	private static final boolean jackson2Present = (ClassUtils.isPresent(
			"com.fasterxml.jackson.databind.ObjectMapper",
			AnnotationDrivenBeanDefinitionParser.class.getClassLoader()))
			&& (ClassUtils
					.isPresent("com.fasterxml.jackson.core.JsonGenerator",
							AnnotationDrivenBeanDefinitionParser.class
									.getClassLoader()));

	private static final boolean jacksonPresent = (ClassUtils.isPresent(
			"org.codehaus.jackson.map.ObjectMapper",
			AnnotationDrivenBeanDefinitionParser.class.getClassLoader()))
			&& (ClassUtils
					.isPresent("org.codehaus.jackson.JsonGenerator",
							AnnotationDrivenBeanDefinitionParser.class
									.getClassLoader()));

	private static boolean romePresent = ClassUtils.isPresent(
			"com.sun.syndication.feed.WireFeed",
			AnnotationDrivenBeanDefinitionParser.class.getClassLoader());

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		Object source = parserContext.extractSource(element);

		CompositeComponentDefinition compDefinition = new CompositeComponentDefinition(
				element.getTagName(), source);
		parserContext.pushContainingComponent(compDefinition);

		RuntimeBeanReference contentNegotiationManager = getContentNegotiationManager(
				element, source, parserContext);

		RootBeanDefinition handlerMappingDef = new RootBeanDefinition(
				YigosRequestMappingHandlerMapping.class);
		handlerMappingDef.setSource(source);
		handlerMappingDef.setRole(2);
		handlerMappingDef.getPropertyValues().add("order", Integer.valueOf(0));
		handlerMappingDef.getPropertyValues().add("removeSemicolonContent",
				Boolean.valueOf(false));
		handlerMappingDef.getPropertyValues().add("basePackage",
				element.getAttribute("base-package"));
		handlerMappingDef.getPropertyValues().add("contentNegotiationManager",
				contentNegotiationManager);
		String methodMappingName = parserContext.getReaderContext()
				.registerWithGeneratedName(handlerMappingDef);

		RuntimeBeanReference conversionService = getConversionService(element,
				source, parserContext);
		RuntimeBeanReference validator = getValidator(element, source,
				parserContext);
		RuntimeBeanReference messageCodesResolver = getMessageCodesResolver(
				element, source, parserContext);

		RootBeanDefinition bindingDef = new RootBeanDefinition(
				ConfigurableWebBindingInitializer.class);
		bindingDef.setSource(source);
		bindingDef.setRole(2);
		bindingDef.getPropertyValues().add("conversionService",
				conversionService);
		bindingDef.getPropertyValues().add("validator", validator);
		bindingDef.getPropertyValues().add("messageCodesResolver",
				messageCodesResolver);

		ManagedList<?> messageConverters = getMessageConverters(element,
				source, parserContext);
		ManagedList<?> argumentResolvers = getArgumentResolvers(element,
				source, parserContext);
		ManagedList<?> returnValueHandlers = getReturnValueHandlers(element,
				source, parserContext);
		String asyncTimeout = getAsyncTimeout(element, source, parserContext);
		RuntimeBeanReference asyncExecutor = getAsyncExecutor(element, source,
				parserContext);
		ManagedList<?> callableInterceptors = getCallableInterceptors(element,
				source, parserContext);
		ManagedList<?> deferredResultInterceptors = getDeferredResultInterceptors(
				element, source, parserContext);

		RootBeanDefinition handlerAdapterDef = new RootBeanDefinition(
				RequestMappingHandlerAdapter.class);
		handlerAdapterDef.setSource(source);
		handlerAdapterDef.setRole(2);
		handlerAdapterDef.getPropertyValues().add("contentNegotiationManager",
				contentNegotiationManager);
		handlerAdapterDef.getPropertyValues().add("webBindingInitializer",
				bindingDef);
		handlerAdapterDef.getPropertyValues().add("messageConverters",
				messageConverters);
		if (element.hasAttribute("ignoreDefaultModelOnRedirect")) {
			Boolean ignoreDefaultModel = Boolean.valueOf(element
					.getAttribute("ignoreDefaultModelOnRedirect"));
			handlerAdapterDef.getPropertyValues().add(
					"ignoreDefaultModelOnRedirect", ignoreDefaultModel);
		}
		if (argumentResolvers != null) {
			handlerAdapterDef.getPropertyValues().add(
					"customArgumentResolvers", argumentResolvers);
		}
		if (returnValueHandlers != null) {
			handlerAdapterDef.getPropertyValues().add(
					"customReturnValueHandlers", returnValueHandlers);
		}
		if (asyncTimeout != null) {
			handlerAdapterDef.getPropertyValues().add("asyncRequestTimeout",
					asyncTimeout);
		}
		if (asyncExecutor != null) {
			handlerAdapterDef.getPropertyValues().add("taskExecutor",
					asyncExecutor);
		}
		handlerAdapterDef.getPropertyValues().add("callableInterceptors",
				callableInterceptors);
		handlerAdapterDef.getPropertyValues().add("deferredResultInterceptors",
				deferredResultInterceptors);
		String handlerAdapterName = parserContext.getReaderContext()
				.registerWithGeneratedName(handlerAdapterDef);

		RootBeanDefinition csInterceptorDef = new RootBeanDefinition(
				ConversionServiceExposingInterceptor.class);
		csInterceptorDef.setSource(source);
		csInterceptorDef.getConstructorArgumentValues()
				.addIndexedArgumentValue(0, conversionService);
		RootBeanDefinition mappedCsInterceptorDef = new RootBeanDefinition(
				MappedInterceptor.class);
		mappedCsInterceptorDef.setSource(source);
		mappedCsInterceptorDef.setRole(2);
		mappedCsInterceptorDef.getConstructorArgumentValues()
				.addIndexedArgumentValue(0, null);
		mappedCsInterceptorDef.getConstructorArgumentValues()
				.addIndexedArgumentValue(1, csInterceptorDef);
		String mappedInterceptorName = parserContext.getReaderContext()
				.registerWithGeneratedName(mappedCsInterceptorDef);

		RootBeanDefinition exceptionHandlerExceptionResolver = new RootBeanDefinition(
				ExceptionHandlerExceptionResolver.class);
		exceptionHandlerExceptionResolver.setSource(source);
		exceptionHandlerExceptionResolver.setRole(2);
		exceptionHandlerExceptionResolver.getPropertyValues().add(
				"contentNegotiationManager", contentNegotiationManager);
		exceptionHandlerExceptionResolver.getPropertyValues().add(
				"messageConverters", messageConverters);
		exceptionHandlerExceptionResolver.getPropertyValues().add("order",
				Integer.valueOf(0));
		String methodExceptionResolverName = parserContext.getReaderContext()
				.registerWithGeneratedName(exceptionHandlerExceptionResolver);

		RootBeanDefinition responseStatusExceptionResolver = new RootBeanDefinition(
				ResponseStatusExceptionResolver.class);
		responseStatusExceptionResolver.setSource(source);
		responseStatusExceptionResolver.setRole(2);
		responseStatusExceptionResolver.getPropertyValues().add("order",
				Integer.valueOf(1));
		String responseStatusExceptionResolverName = parserContext
				.getReaderContext().registerWithGeneratedName(
						responseStatusExceptionResolver);

		RootBeanDefinition defaultExceptionResolver = new RootBeanDefinition(
				DefaultHandlerExceptionResolver.class);
		defaultExceptionResolver.setSource(source);
		defaultExceptionResolver.setRole(2);
		defaultExceptionResolver.getPropertyValues().add("order",
				Integer.valueOf(2));
		String defaultExceptionResolverName = parserContext.getReaderContext()
				.registerWithGeneratedName(defaultExceptionResolver);

		parserContext.registerComponent(new BeanComponentDefinition(
				handlerMappingDef, methodMappingName));
		parserContext.registerComponent(new BeanComponentDefinition(
				handlerAdapterDef, handlerAdapterName));
		parserContext
				.registerComponent(new BeanComponentDefinition(
						exceptionHandlerExceptionResolver,
						methodExceptionResolverName));
		parserContext.registerComponent(new BeanComponentDefinition(
				responseStatusExceptionResolver,
				responseStatusExceptionResolverName));
		parserContext.registerComponent(new BeanComponentDefinition(
				defaultExceptionResolver, defaultExceptionResolverName));
		parserContext.registerComponent(new BeanComponentDefinition(
				mappedCsInterceptorDef, mappedInterceptorName));

		MvcNamespaceUtils.registerDefaultComponents(parserContext, source);

		parserContext.popAndRegisterContainingComponent();

		return null;
	}

	private RuntimeBeanReference getConversionService(Element element,
			Object source, ParserContext parserContext) {
		RuntimeBeanReference conversionServiceRef;
		if (element.hasAttribute("conversion-service")) {
			conversionServiceRef = new RuntimeBeanReference(
					element.getAttribute("conversion-service"));
		} else {
			RootBeanDefinition conversionDef = new RootBeanDefinition(
					FormattingConversionServiceFactoryBean.class);
			conversionDef.setSource(source);
			conversionDef.setRole(2);
			String conversionName = parserContext.getReaderContext()
					.registerWithGeneratedName(conversionDef);
			parserContext.registerComponent(new BeanComponentDefinition(
					conversionDef, conversionName));
			conversionServiceRef = new RuntimeBeanReference(conversionName);
		}
		return conversionServiceRef;
	}

	private RuntimeBeanReference getValidator(Element element, Object source,
			ParserContext parserContext) {
		if (element.hasAttribute("validator")) {
			return new RuntimeBeanReference(element.getAttribute("validator"));
		}
		if (jsr303Present) {
			RootBeanDefinition validatorDef = new RootBeanDefinition(
					LocalValidatorFactoryBean.class);
			validatorDef.setSource(source);
			validatorDef.setRole(2);
			String validatorName = parserContext.getReaderContext()
					.registerWithGeneratedName(validatorDef);
			parserContext.registerComponent(new BeanComponentDefinition(
					validatorDef, validatorName));
			return new RuntimeBeanReference(validatorName);
		}

		return null;
	}

	private RuntimeBeanReference getContentNegotiationManager(Element element,
			Object source, ParserContext parserContext) {
		RuntimeBeanReference contentNegotiationManagerRef;
		if (element.hasAttribute("content-negotiation-manager")) {
			contentNegotiationManagerRef = new RuntimeBeanReference(
					element.getAttribute("content-negotiation-manager"));
		} else {
			RootBeanDefinition factoryBeanDef = new RootBeanDefinition(
					ContentNegotiationManagerFactoryBean.class);
			factoryBeanDef.setSource(source);
			factoryBeanDef.setRole(2);
			factoryBeanDef.getPropertyValues().add("mediaTypes",
					getDefaultMediaTypes());

			String beanName = "mvcContentNegotiationManager";
			parserContext.getReaderContext().getRegistry()
					.registerBeanDefinition(beanName, factoryBeanDef);
			parserContext.registerComponent(new BeanComponentDefinition(
					factoryBeanDef, beanName));
			contentNegotiationManagerRef = new RuntimeBeanReference(beanName);
		}
		return contentNegotiationManagerRef;
	}

	private Properties getDefaultMediaTypes() {
		Properties props = new Properties();
		if (romePresent) {
			props.put("atom", "application/atom+xml");
			props.put("rss", "application/rss+xml");
		}
		if ((jackson2Present) || (jacksonPresent)) {
			props.put("json", "application/json");
		}
		if (jaxb2Present) {
			props.put("xml", "application/xml");
		}
		return props;
	}

	private RuntimeBeanReference getMessageCodesResolver(Element element,
			Object source, ParserContext parserContext) {
		if (element.hasAttribute("message-codes-resolver")) {
			return new RuntimeBeanReference(
					element.getAttribute("message-codes-resolver"));
		}
		return null;
	}

	private String getAsyncTimeout(Element element, Object source,
			ParserContext parserContext) {
		Element asyncElement = DomUtils.getChildElementByTagName(element,
				"async-support");
		return asyncElement != null ? asyncElement
				.getAttribute("default-timeout") : null;
	}

	private RuntimeBeanReference getAsyncExecutor(Element element,
			Object source, ParserContext parserContext) {
		Element asyncElement = DomUtils.getChildElementByTagName(element,
				"async-support");
		if ((asyncElement != null)
				&& (asyncElement.hasAttribute("task-executor"))) {
			return new RuntimeBeanReference(
					asyncElement.getAttribute("task-executor"));
		}

		return null;
	}

	private ManagedList<?> getCallableInterceptors(Element element,
			Object source, ParserContext parserContext) {
		ManagedList<Object> interceptors = new ManagedList<Object>();
		Element asyncElement = DomUtils.getChildElementByTagName(element,
				"async-support");
		if (asyncElement != null) {
			Element interceptorsElement = DomUtils.getChildElementByTagName(
					asyncElement, "callable-interceptors");
			if (interceptorsElement != null) {
				interceptors.setSource(source);
				for (Element converter : DomUtils.getChildElementsByTagName(
						interceptorsElement, "bean")) {
					BeanDefinitionHolder beanDef = parserContext.getDelegate()
							.parseBeanDefinitionElement(converter);
					beanDef = parserContext.getDelegate()
							.decorateBeanDefinitionIfRequired(converter,
									beanDef);
					interceptors.add(beanDef);
				}
			}
		}
		return interceptors;
	}

	private ManagedList<?> getDeferredResultInterceptors(Element element,
			Object source, ParserContext parserContext) {
		ManagedList<Object> interceptors = new ManagedList<Object>();
		Element asyncElement = DomUtils.getChildElementByTagName(element,
				"async-support");
		if (asyncElement != null) {
			Element interceptorsElement = DomUtils.getChildElementByTagName(
					asyncElement, "deferred-result-interceptors");
			if (interceptorsElement != null) {
				interceptors.setSource(source);
				for (Element converter : DomUtils.getChildElementsByTagName(
						interceptorsElement, "bean")) {
					BeanDefinitionHolder beanDef = parserContext.getDelegate()
							.parseBeanDefinitionElement(converter);
					beanDef = parserContext.getDelegate()
							.decorateBeanDefinitionIfRequired(converter,
									beanDef);
					interceptors.add(beanDef);
				}
			}
		}
		return interceptors;
	}

	private ManagedList<?> getArgumentResolvers(Element element, Object source,
			ParserContext parserContext) {
		Element resolversElement = DomUtils.getChildElementByTagName(element,
				"argument-resolvers");
		if (resolversElement != null) {
			ManagedList<BeanDefinitionHolder> argumentResolvers = extractBeanSubElements(
					resolversElement, parserContext);
			return wrapWebArgumentResolverBeanDefs(argumentResolvers);
		}
		return null;
	}

	private ManagedList<?> getReturnValueHandlers(Element element,
			Object source, ParserContext parserContext) {
		Element handlersElement = DomUtils.getChildElementByTagName(element,
				"return-value-handlers");
		if (handlersElement != null) {
			return extractBeanSubElements(handlersElement, parserContext);
		}
		return null;
	}

	private ManagedList<?> getMessageConverters(Element element, Object source,
			ParserContext parserContext) {
		Element convertersElement = DomUtils.getChildElementByTagName(element,
				"message-converters");
		ManagedList<Object> messageConverters = new ManagedList<Object>();
		if (convertersElement != null) {
			messageConverters.setSource(source);
			for (Element beanElement : DomUtils.getChildElementsByTagName(
					convertersElement, new String[] { "bean", "ref" })) {
				Object object = parserContext.getDelegate()
						.parsePropertySubElement(beanElement, null);
				messageConverters.add(object);
			}
		}

		if ((convertersElement == null)
				|| (Boolean.valueOf(convertersElement
						.getAttribute("register-defaults")).booleanValue())) {
			messageConverters.setSource(source);
			messageConverters.add(createConverterBeanDefinition(
					ByteArrayHttpMessageConverter.class, source));

			RootBeanDefinition stringConverterDef = createConverterBeanDefinition(
					StringHttpMessageConverter.class, source);
			stringConverterDef.getPropertyValues().add("writeAcceptCharset",
					Boolean.valueOf(false));
			messageConverters.add(stringConverterDef);

			messageConverters.add(createConverterBeanDefinition(
					ResourceHttpMessageConverter.class, source));
			messageConverters.add(createConverterBeanDefinition(
					SourceHttpMessageConverter.class, source));
			messageConverters.add(createConverterBeanDefinition(
					AllEncompassingFormHttpMessageConverter.class, source));
			if (romePresent) {
				messageConverters.add(createConverterBeanDefinition(
						AtomFeedHttpMessageConverter.class, source));
				messageConverters.add(createConverterBeanDefinition(
						RssChannelHttpMessageConverter.class, source));
			}
			if (jaxb2Present) {
				messageConverters.add(createConverterBeanDefinition(
						Jaxb2RootElementHttpMessageConverter.class, source));
			}
			if (jackson2Present) {
				messageConverters.add(createConverterBeanDefinition(
						MappingJackson2HttpMessageConverter.class, source));
			} else if (jacksonPresent) {
				messageConverters.add(createConverterBeanDefinition(
						MappingJacksonHttpMessageConverter.class, source));
			}
		}
		return messageConverters;
	}

	private RootBeanDefinition createConverterBeanDefinition(
			@SuppressWarnings("rawtypes") Class<? extends HttpMessageConverter> converterClass,
			Object source) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition(
				converterClass);
		beanDefinition.setSource(source);
		beanDefinition.setRole(2);

		return beanDefinition;
	}

	private ManagedList<BeanDefinitionHolder> extractBeanSubElements(
			Element parentElement, ParserContext parserContext) {
		ManagedList<BeanDefinitionHolder> list = new ManagedList<BeanDefinitionHolder>();
		list.setSource(parserContext.extractSource(parentElement));
		for (Element beanElement : DomUtils.getChildElementsByTagName(
				parentElement, "bean")) {
			BeanDefinitionHolder beanDef = parserContext.getDelegate()
					.parseBeanDefinitionElement(beanElement);
			beanDef = parserContext.getDelegate()
					.decorateBeanDefinitionIfRequired(beanElement, beanDef);
			list.add(beanDef);
		}
		return list;
	}

	private ManagedList<BeanDefinitionHolder> wrapWebArgumentResolverBeanDefs(
			List<BeanDefinitionHolder> beanDefs) {
		ManagedList<BeanDefinitionHolder> result = new ManagedList<BeanDefinitionHolder>();

		for (BeanDefinitionHolder beanDef : beanDefs) {
			String className = beanDef.getBeanDefinition().getBeanClassName();
			Class<?> clazz = ClassUtils.resolveClassName(className,
					ClassUtils.getDefaultClassLoader());

			if (WebArgumentResolver.class.isAssignableFrom(clazz)) {
				RootBeanDefinition adapter = new RootBeanDefinition(
						ServletWebArgumentResolverAdapter.class);
				adapter.getConstructorArgumentValues().addIndexedArgumentValue(
						0, beanDef);
				result.add(new BeanDefinitionHolder(adapter, beanDef
						.getBeanName() + "Adapter"));
			} else {
				result.add(beanDef);
			}
		}

		return result;
	}
}