package com.yigos.framework.common.view;

import java.util.Map;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JsonView extends MappingJackson2JsonView {

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
