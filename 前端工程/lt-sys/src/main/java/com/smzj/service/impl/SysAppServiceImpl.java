package com.smzj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import com.smzj.mapper.SysAppMapper;
import com.smzj.model.SysApp;
import com.smzj.service.SysAppService;

@Service("sysAppService")
public class SysAppServiceImpl extends BaseService<SysApp> implements SysAppService {
	@Override
	public List<SysApp> selectBySysApp(SysApp sysApp) {

		Example example = new Example(SysApp.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty((String) sysApp.getAppcode())) {
			criteria.andEqualTo("appcode", sysApp.getAppcode());
		}
		if (StringUtil.isNotEmpty((String) sysApp.getAppname())) {
			criteria.andLike("appname", (String) sysApp.getAppname());
		}
		example.setOrderByClause("sortcode asc");
		return selectByExample(example);

	}

	@Override
	public Map<String, Object> selectSysOrgByMap(Map<String, Object> map) {
		return ((SysAppMapper) getMapper()).selectSysOrgByMap(map);
	}
}
