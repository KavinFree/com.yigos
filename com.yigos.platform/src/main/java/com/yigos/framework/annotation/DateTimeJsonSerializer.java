package com.yigos.framework.annotation;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yigos.framework.common.util.datetime.DateTimeUtil;

/**
 * 日期序列化
 * @author kavin
 * @since 2013年11月10日
 */
public class DateTimeJsonSerializer extends JsonSerializer<Timestamp>{

	/*
	 *(non-Javadoc)
	 * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
	 */
	@Override
	public void serialize(Timestamp value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtil.yyyy_MM_dd_HH_mm_ss);
		String formattedDate = dateFormat.format(value);
		jgen.writeString(formattedDate);
	}

}