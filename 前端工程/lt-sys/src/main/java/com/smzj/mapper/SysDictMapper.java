package com.smzj.mapper;

import java.util.List;
import java.util.Map;

import com.smzj.model.SysDict;

public interface SysDictMapper extends BaseMapper<SysDict> {

	List<SysDict> selectsysdict(Map<String, Object> map);
}