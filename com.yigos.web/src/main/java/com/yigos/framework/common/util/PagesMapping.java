package com.yigos.framework.common.util;

import java.util.Properties;

public class PagesMapping {
	private static final ConfigProp configProp = new ConfigProp();
	
	public static String getProperty(String key) {
		String property = configProp.getProp().getProperty(key.trim());
		if (property != null) {
			return property.trim();
		}
		return property;
	}

	public static void setProperty(String key, String value) {
		configProp.getProp().setProperty(key, value);
	}

	public static Properties getProperties() {
		return configProp.getProp();
	}

	static {
		configProp.registerProp("config.PagesMapping");
	}
}
