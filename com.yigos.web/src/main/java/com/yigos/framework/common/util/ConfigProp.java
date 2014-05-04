/*
 * Copyright 2012-2013 DSAT
 */
package com.yigos.framework.common.util;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author Jason Ling
 * @since 2013年5月8日 
 */
public class ConfigProp {
	private Properties prop = new Properties();
	private static final Logger logger = Logger.getLogger(ConfigProp.class);

	public void registerProp(String bundle) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle(bundle);
			@SuppressWarnings("rawtypes")
			Enumeration enu = rb.getKeys();
			while (enu.hasMoreElements()) {
				Object objKey = enu.nextElement();
				if (objKey instanceof String) {
					String strKey = (String) objKey;
					String strValue = null;
					if (strKey != null) {
						strValue = rb.getString(strKey);
						if (strValue != null)
							prop.setProperty(strKey, strValue);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public Properties getProp() {
		return prop;
	}
}

