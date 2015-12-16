package com.yigos.framework.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yigos.framework.common.util.ObjectMappingCustomer;

public final class JsonUtils {
    
	private final static ObjectMappingCustomer MAPPER = new ObjectMappingCustomer();

	private  JsonUtils(){
	}
	/**
	 * 對象轉換為json字串
	 * @param bean
	 * @return String
	 * @throws Exception
	 * @since 2013年5月6日  
	*/ 
	public static String toJsonString(Object bean) throws Exception{
			return MAPPER.writeValueAsString(bean);
	}

	/**
	 * json字串轉換為java對象
	 * @param jsonString
	 * @param clazz
	 * @return <T>
	 * @throws Exception
	 * @since 2013年5月6日  
	*/ 
	public static <T> T fromJsonString(String jsonString, Class<T> clazz) throws Exception{
			return MAPPER.readValue(jsonString, clazz);
	}
	
    /**
     * json字符串轉換為java指定類型 
     * @param json json格式的字符串
     * @param valueTypeRef 完整的泛型类型  TypeReference ref = new TypeReference<List<Integer>>() { };
     * @return Object 返回List<Integer>的Object類型 需要轉換為指定需要的類型
     * @throws Exception
     * @since 2013-4-22
     */
    public static Object fromJsonString(String json, TypeReference<?> valueTypeRef) throws Exception{
	    return MAPPER.readValue(json, valueTypeRef);
	}

}