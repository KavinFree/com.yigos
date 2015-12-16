package com.yigos.auth.menu.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yigos.auth.entity.SysMenu;
import com.yigos.auth.menu.service.itf.MenuService;
import com.yigos.auth.menu.view.MenuQueryView;
import com.yigos.auth.menu.view.MenuView;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.controller.BaseControllerImpl;

@Controller
public class MenuController extends BaseControllerImpl{

	@Autowired
	private MenuService service;
	
	@RequestMapping
	public void page(Model model) throws Exception {
		
	}
	
	@RequestMapping
	public void list(Model model, MenuQueryView view) throws Exception {
		this.service.list(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, view.getItems(),
				view.getCounts());
	}
	
	@RequestMapping
	public void roleMenuList(Model model, MenuQueryView view) throws Exception {
		this.service.roleMenuList(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, view.getItems(),
				view.getCounts());
	}
	
	@RequestMapping
	public void findMenu(Model model, MenuView view) throws Exception {
		SysMenu sysMenu = this.service.findByPK(view.getId());
		if(sysMenu!=null){
			BeanUtils.copyProperties(sysMenu, view);
			this.writeJsonToResponse(model, true, DataDict.NORMAL, view, 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}
	
	@RequestMapping
	public void saveMenu(Model model, MenuView view) throws Exception {
		this.service.save(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, "保存成功", 1);
	}
	
	@RequestMapping
	public void updateMenu(Model model, MenuView view) throws Exception {
		if(StringUtils.isNotEmpty(view.getId())){
			this.service.update(view);
			this.writeJsonToResponse(model, true, DataDict.NORMAL, "更新成功", 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}

	@RequestMapping
	public void deleteMenu(Model model, MenuView view) throws Exception {
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
