package com.smzj.service;

import java.util.List;
import java.util.Map;

import com.smzj.model.SysApp;

public interface SysAppService extends IService<SysApp> {
	List<SysApp> selectBySysApp(SysApp sysApp);

	Map<String, Object> selectSysOrgByMap(Map<String, Object> map);
}
