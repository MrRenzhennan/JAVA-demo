package com.smzj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.smzj.mapper.SysDictMapper;
import com.smzj.model.SysDict;
import com.smzj.service.SysDictService;

@Service("sysDictService")
public class SysDictServiceImpl extends BaseService<SysDict> implements SysDictService{

	@Override
	public List<SysDict> sysDicts(Map<String, Object> map) {
		List<SysDict> sysDicts=((SysDictMapper)getMapper()).selectsysdict(map);
		return sysDicts;
	}
}
