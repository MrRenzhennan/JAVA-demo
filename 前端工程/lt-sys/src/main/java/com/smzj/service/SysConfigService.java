package com.smzj.service;

import java.util.List;

import com.smzj.model.SysConfig;

public interface SysConfigService extends IService<SysConfig>{
	List<SysConfig> selectSysConfig();
}
