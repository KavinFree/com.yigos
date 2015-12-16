package com.yigos.template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerUtil {
	public static void main(String[] args) throws Exception {
		String where = "<#if queryView.name ??> and o.name >= '%${queryView.name}%'</#if>";
		
		Map<String, Object> data = new HashMap<String, Object>();
		TestVo vo = new TestVo();
		vo.setName("dddd");
		
		data.put("queryView", vo);
		
		Configuration cfg = new Configuration();
		StringTemplateLoader stl = new StringTemplateLoader();
		stl.putTemplate("whereTemplate", where);
		cfg.setTemplateLoader(stl);
		Template temp = cfg.getTemplate("whereTemplate");
		StringWriter queryStringWriter = new StringWriter();
		temp.process(data, queryStringWriter);
		String queryString = queryStringWriter.toString();
		System.out.println(queryString);
	}

}
