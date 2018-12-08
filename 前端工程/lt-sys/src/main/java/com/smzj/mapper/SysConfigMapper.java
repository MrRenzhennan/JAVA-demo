package com.smzj.mapper;

import java.util.List;

import com.smzj.model.SysConfig;

public interface SysConfigMapper extends BaseMapper<SysConfig>{
	List<SysConfig> selectSysConfig();
}