package com.yigos.auth.config.service.itf;

import com.yigos.auth.config.view.SysConfigTypeQueryView;
import com.yigos.auth.config.view.SysConfigTypeView;
import com.yigos.auth.entity.config.SysConfigType;

public interface TypeService {
	void list(SysConfigTypeQueryView view);
	SysConfigType findByPK(String id);
	SysConfigType save(SysConfigTypeView view);
	SysConfigType delete(String id);
	SysConfigType update(SysConfigTypeView view);
}
