package com.smzj.service;

import java.util.List;

import com.smzj.model.SysRoleQueryModel;
import com.smzj.model.SysUserRole;

public interface SysUserRoleService extends IService<SysUserRole>{

	void saveUserRole(String userid,String[] rolecheck);

	List<SysRoleQueryModel> selectRoleByUserid(String orgtype,String userid);

	void delRoleByUserid(String userid);

	List<SysUserRole> selectRoleByUserid1(String userid);

}
