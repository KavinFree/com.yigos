package com.yigos.auth.config.service.itf;

import com.yigos.auth.config.view.SysConfigParamQueryView;
import com.yigos.auth.config.view.SysConfigParamView;
import com.yigos.auth.entity.config.SysConfigParam;

public interface ParamService {
	void list(SysConfigParamQueryView view);
	SysConfigParam findByPK(String id);
	SysConfigParam save(SysConfigParamView view);
	SysConfigParam delete(String id);
	SysConfigParam update(SysConfigParamView view);
}
