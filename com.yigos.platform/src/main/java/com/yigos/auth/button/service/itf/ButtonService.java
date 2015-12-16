package com.yigos.auth.button.service.itf;

import com.yigos.auth.button.view.SysButtonQueryView;
import com.yigos.auth.button.view.SysButtonView;
import com.yigos.auth.entity.SysButton;

public interface ButtonService {
	void list(SysButtonQueryView view);
	SysButton findByPK(String id);
	SysButton save(SysButtonView view);
	SysButton delete(String id);
	SysButton update(SysButtonView view);
}
