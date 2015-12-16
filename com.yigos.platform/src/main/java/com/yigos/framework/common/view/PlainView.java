package com.yigos.framework.common.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonEncoding;
/*
 * import org.codehaus.jackson.JsonEncoding;
 * import org.codehaus.jackson.JsonGenerator;
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.yigos.framework.common.util.ObjectMappingCustomer;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

public class PlainView extends AbstractView {
	public static final String DEFAULT_CONTENT_TYPE = "text/plain";
	private ObjectMappingCustomer objectMapper = new ObjectMappingCustomer();
	private JsonEncoding encoding = JsonEncoding.UTF8;

	public PlainView() {
		setContentType(DEFAULT_CONTENT_TYPE);
		setExposePathVariables(false);
	}

	protected void prepareResponse(HttpServletRequest request,
			HttpServletResponse response) {
		setResponseContentType(request, response);
		response.setCharacterEncoding(this.encoding.getJavaName());
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
	}

	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Set<String> renderedAttributes = model.keySet();
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			if ((!(entry.getValue() instanceof BindingResult))
					&& (renderedAttributes.contains(entry.getKey()))) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		@SuppressWarnings("deprecation")
		JsonGenerator generator = this.objectMapper.getJsonFactory()
				.createJsonGenerator(response.getOutputStream(), this.encoding);

		this.objectMapper.writeValue(generator, result.size() == 1 ? result
				.values().iterator().next() : result);
	}
	
	/*text/plain    扩展    .c
	text/plain    扩展    .c++
	text/plain    扩展    .cc
	text/plain    扩展    .com
	text/plain    扩展    .conf
	text/plain    扩展    .cxx
	text/plain    扩展    .def
	text/plain    扩展    .f
	text/plain    扩展    .f90
	text/plain    扩展    .for
	text/plain    扩展    .g
	text/plain    扩展    .h
	text/plain    扩展    .hh
	text/plain    扩展    .idc
	text/plain    扩展    .jav
	text/plain    扩展    .java
	text/plain    扩展    .list
	text/plain    扩展    .log
	text/plain    扩展    .lst
	text/plain    扩展    .m
	text/plain    扩展    .mar
	text/plain    扩展    .pl
	text/plain    扩展    .sdml
	text/plain    扩展    .text
	text/plain    扩展    .txt*/
}
