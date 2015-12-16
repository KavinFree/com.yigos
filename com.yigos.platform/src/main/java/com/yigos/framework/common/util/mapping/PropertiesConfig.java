package com.yigos.framework.common.util.mapping;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class PropertiesConfig {
	private Properties prop = new Properties();
	private static final Logger logger = Logger.getLogger(PropertiesConfig.class);

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

