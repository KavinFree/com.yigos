package com.yigos.framework.common.util.mapping;

import java.util.Properties;

public class PagesMapping {
	private static final PropertiesConfig configProp = new PropertiesConfig();
	
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
