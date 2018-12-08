package com.smzj.mapper;

import java.util.List;
import java.util.Map;

import com.smzj.model.SysUserRole;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

	void insertSysUserRole(SysUserRole sysUserRole);

	List<SysUserRole> selectRoleByUserid(String userid);

	void delRoleByUserid(String userid);

	List<SysUserRole> selectByOrgTypeAndUserId(Map<String,String> map);
}