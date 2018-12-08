package com.smzj.service;

import java.util.List;
import java.util.Map;

import com.smzj.model.SysDict;

public interface SysDictService extends IService<SysDict>{
	
	List<SysDict> sysDicts(Map<String, Object> map);

}
