package com.yigos.auth.user.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yigos.auth.entity.SysUser;
import com.yigos.auth.user.service.itf.UserService;
import com.yigos.auth.user.view.UserQueryView;
import com.yigos.auth.user.view.SysUserView;
import com.yigos.framework.annotation.MethodLog;
import com.yigos.framework.constant.DataDict;
import com.yigos.framework.controller.BaseControllerImpl;

@Controller
public class UserController extends BaseControllerImpl {

	@Autowired
	private UserService service;

	@RequestMapping
	public void page(Model model) throws Exception {
		
	}

	@RequestMapping
	public void list(Model model, 
			UserQueryView view) throws Exception {
		this.service.list(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, view.getItems(),
				view.getCounts());
	}

	@RequestMapping
	public void findUser(Model model, SysUserView view) throws Exception {
		SysUser user = this.service.findUserByPK(view.getId());
		if(user!=null){
			BeanUtils.copyProperties(user, view);
			this.writeJsonToResponse(model, true, DataDict.NORMAL, view, 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}

	@RequestMapping
	public void saveUser(Model model, SysUserView view) throws Exception {
		this.service.saveUser(view);
		this.writeJsonToResponse(model, true, DataDict.NORMAL, "保存成功", 1);
	}

	@RequestMapping
	@MethodLog(remark="updateUser")
	public void updateUser(Model model, SysUserView view) throws Exception {
		if(StringUtils.isNotEmpty(view.getId())){
			this.service.updateUser(view);
			this.writeJsonToResponse(model, true, DataDict.NORMAL, "更新成功", 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}

	@RequestMapping
	public void deleteUser(Model model, SysUserView view) throws Exception {
		if(CollectionUtils.isNotEmpty(view.getIds())){
			for(String id:view.getIds()){
				this.service.deleteUser(id);
			}
			this.writeJsonToResponse(model, true, DataDict.NORMAL, "删除成功", 1);
		}else{
			this.writeJsonToResponse(model, false, DataDict.NORMAL, "操作失败", 0);
		}
	}

}
