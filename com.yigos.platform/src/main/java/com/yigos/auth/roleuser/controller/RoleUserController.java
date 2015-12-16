package com.yigos.auth.roleuser.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yigos.auth.entity.SysRoleUser;
import com.yigos.auth.role.service.itf.RoleService;
import com.yigos.auth.roleuser.service.itf.RoleUserService;
import com.yigos.auth.roleuser.view.RoleUserQueryView;
import com.yigos.auth.roleuser.view.RoleUserView;
import com.yigos.auth.user.service.itf.UserService;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.controller.BaseControllerImpl;

@Controller
public class RoleUserController extends BaseControllerImpl {
	
	@Autowired
	private RoleUserService service;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping
	public void page(Model model) throws Exception {
		
	}

	@RequestMapping
	public void list(Model model, RoleUserQueryView view) throws Exception {
		this.service.list(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, view.getItems(),
				view.getCounts());
	}
	
	@RequestMapping
	public void find(Model model, RoleUserView view) throws Exception {
		SysRoleUser roleUser = this.service.findRoleUserByPK(view.getId());
		if(roleUser!=null){
			BeanUtils.copyProperties(roleUser, view);
			this.writeJsonToResponse(model, true, DataDict.NORMAL, view, 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}

	@RequestMapping
	public void save(Model model, RoleUserView view) throws Exception {
		this.service.saveRoleUser(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, "保存成功", 1);
	}

	@RequestMapping
	public void update(Model model, RoleUserView view) throws Exception {
		if(StringUtils.isNotEmpty(view.getId())){
			this.service.updateRoleUser(view);
			this.writeJsonToResponse(model, true, DataDict.NORMAL, "更新成功", 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}

	@RequestMapping
	public void delete(Model model, RoleUserView view) throws Exception {
		if(StringUtils.isNotEmpty(view.getId())){
			this.service.deleteRoleUser(view);
			this.writeJsonToResponse(model, true, DataDict.NORMAL, "禁用成功", 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}
	
	@RequestMapping
	public void loadComboboxData(Model model){
		List<?> roleList = this.roleService.loadRoleList();
		List<?> userList = this.userService.loadUserList();
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		map.put("roleList", roleList);
		map.put("userList", userList);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, map, 1);
	}
}
