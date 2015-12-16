package com.yigos.auth.role.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yigos.auth.entity.SysRole;
import com.yigos.auth.role.service.itf.RoleService;
import com.yigos.auth.role.view.RoleQueryView;
import com.yigos.auth.role.view.RoleView;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.controller.BaseControllerImpl;

@Controller
public class RoleController extends BaseControllerImpl{
	@Autowired
	private RoleService service;
	
	@RequestMapping
	public void page(Model model) throws Exception {
		
	}
	
	@RequestMapping
	public void list(Model model, RoleQueryView view) throws Exception {
		this.service.list(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, view.getItems(),
				view.getCounts());
	}
	
	@RequestMapping
	public void findRole(Model model,
			RoleView view) throws Exception {
		SysRole role = this.service.findRoleByPK(view.getId());
		if(role!=null){
			BeanUtils.copyProperties(role, view);
			this.writeJsonToResponse(model, true, DataDict.NORMAL, view, 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}

	@RequestMapping
	public void saveRole(Model model,
			RoleView view) throws Exception {
		this.service.saveRole(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, "保存成功", 1);
	}

	@RequestMapping
	public void updateRole(Model model,
			RoleView view) throws Exception {
		if(StringUtils.isNotEmpty(view.getId())){
			this.service.updateRole(view);
			this.writeJsonToResponse(model, true, DataDict.NORMAL, "更新成功", 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}

	@RequestMapping
	public void deleteRole(Model model,
			RoleView view) throws Exception {
		if(CollectionUtils.isNotEmpty(view.getIds())){
			for(String id:view.getIds()){
				this.service.deleteRole(id);
			}
			this.writeJsonToResponse(model, true, DataDict.NORMAL, "操作成功", 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}
}
