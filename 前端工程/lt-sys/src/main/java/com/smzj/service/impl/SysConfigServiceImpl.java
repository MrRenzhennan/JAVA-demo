package com.smzj.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smzj.mapper.SysConfigMapper;
import com.smzj.model.SysConfig;
import com.smzj.service.SysConfigService;
@Service("sysConfigService")
public class SysConfigServiceImpl extends BaseService<SysConfig> implements SysConfigService{
	@Override
	public List<SysConfig> selectSysConfig() {
		return  ((SysConfigMapper)getMapper()).selectSysConfig();
	}

}
