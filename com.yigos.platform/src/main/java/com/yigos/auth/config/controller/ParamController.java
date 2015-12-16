package com.yigos.auth.config.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yigos.auth.config.service.itf.ParamService;
import com.yigos.auth.config.service.itf.TypeService;
import com.yigos.auth.config.view.SysConfigParamQueryView;
import com.yigos.auth.config.view.SysConfigParamView;
import com.yigos.auth.entity.config.SysConfigParam;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.controller.BaseControllerImpl;

@Controller
public class ParamController extends BaseControllerImpl{

	@Autowired
	private ParamService service;
	@Autowired
	private TypeService typeService;
	
	@RequestMapping
	public void page(Model model) throws Exception {
		
	}

	@RequestMapping
	public void list(Model model,
			SysConfigParamQueryView view) throws Exception {
		this.service.list(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, view.getItems(),
				view.getCounts());
	}
	
	@RequestMapping
	public void findParam(Model model,
			SysConfigParamView view) throws Exception {
		SysConfigParam sysConfigParam = this.service.findByPK(view.getId());
		if(sysConfigParam!=null){
			BeanUtils.copyProperties(sysConfigParam, view);
			this.writeJsonToResponse(model, true, DataDict.NORMAL, view, 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}

	@RequestMapping
	public void saveParam(Model model,
			SysConfigParamView view) throws Exception {
		this.service.save(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, "保存成功", 1);
	}

	@RequestMapping
	public void updateParam(Model model,
			SysConfigParamView view) throws Exception {
		if(StringUtils.isNotEmpty(view.getId())){
			this.service.update(view);
			this.writeJsonToResponse(model, true, DataDict.NORMAL, "更新成功", 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}

	@RequestMapping
	public void deleteParam(Model model,
			SysConfigParamView view) throws Exception {
		if(CollectionUtils.isNotEmpty(view.getIds())){
			for(String id:view.getIds()){
				this.service.delete(id);
			}
			this.writeJsonToResponse(model, true, DataDict.NORMAL, "禁用成功", 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}
}
