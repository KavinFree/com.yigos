package com.yigos.framework.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

public class PojoUtil {

	protected void buildWhereParam(Map<String, Object> condition, String tableAlias,
			Class<?> clazz, Object view) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		org.apache.commons.beanutils.BeanUtils bu = new org.apache.commons.beanutils.BeanUtils();
		PropertyDescriptor[] proArray = BeanUtils.getPropertyDescriptors(clazz);
		
		for(PropertyDescriptor pro:proArray){
			String name=pro.getName();
			Class<?> type = pro.getPropertyType();
			@SuppressWarnings("static-access")
			String value=bu.getProperty(view, name);
			System.out.println(name+"," +type+value);
		}
		if (StringUtils.isNotBlank("")) {
			StringUtils.trim("");
			condition.put("", "");
		}
	}
}
