package com.yigos.auth.button.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yigos.auth.button.service.itf.ButtonService;
import com.yigos.auth.button.view.SysButtonQueryView;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.controller.BaseControllerImpl;

@Controller
public class ButtonController extends BaseControllerImpl{
	
	@Autowired
	private ButtonService service;
	
	@RequestMapping
	public void list(Model model, SysButtonQueryView view) throws Exception {
		this.service.list(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, null, 0);
	}
	
}
