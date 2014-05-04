package com.yigos.framework.model;

import java.util.Map;

import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class JsonView extends MappingJacksonJsonView {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.view.json.MappingJacksonJsonView#filterModel(java.util.Map)
	 */
	@Override
	protected Object filterModel(Map<String, Object> arg0) {
		Map<?, ?> result = (Map<?, ?>) super.filterModel(arg0);
		if (result.size() == 1) {
			return result.values().iterator().next();
		} else {
			return result;
		}
	}

}
