package com.smzj.mapper;

import java.util.Map;

import com.smzj.model.SysApp;

public interface SysAppMapper extends BaseMapper<SysApp> {
	Map<String, Object> selectSysOrgByMap(Map<String, Object> map);
}