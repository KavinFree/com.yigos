package com.yigos.xml.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yigos.framework.controller.BaseController;
import com.yigos.xml.TestXml;

@Controller
public class XmlController extends BaseController {
	
	@RequestMapping
	@ResponseBody
	public TestXml index(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		TestXml test = new TestXml();
		test.setName("bbbbb");
		test.setQuanlity(111111111);
		return test;
	}
	
}
