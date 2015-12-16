package com.yigos.framework.common.util;

import java.io.IOException;
/*
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.web.util.HtmlUtils;
*/
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMappingCustomer extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	public ObjectMappingCustomer() {
		super();
		// 空值处理为空串
		this.getSerializerProvider().setNullValueSerializer(
			new JsonSerializer<Object>() {
				@Override
				public void serialize(Object value, JsonGenerator jg,
						SerializerProvider sp) throws IOException,
						JsonProcessingException {
					jg.writeString("");
				}
			});
	}
	
}