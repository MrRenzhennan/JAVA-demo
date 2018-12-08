package com.smzj.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smzj.mapper.SysUserRoleMapper;
import com.smzj.model.SysRole;
import com.smzj.model.SysRoleQueryModel;
import com.smzj.model.SysUserRole;
import com.smzj.service.SysRoleService;
import com.smzj.service.SysUserRoleService;


@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends BaseService<SysUserRole> implements SysUserRoleService {
	@Autowired
	private SysRoleService sysRoleService;

	@Override
	public void saveUserRole(String userid,String[] rolecheck) {
		delRoleByUserid(userid);
		if(rolecheck==null){
			return;
		}
		for (int i = 0; i < rolecheck.length; i++) {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserid(userid);
			sysUserRole.setRolecode(rolecheck[i]);
			((SysUserRoleMapper)getMapper()).insertSysUserRole(sysUserRole);
		}
	}

	@Override
	public void delRoleByUserid(String userid) {
		((SysUserRoleMapper)getMapper()).delRoleByUserid(userid);
	}


	private SysRoleQueryModel setRoleChecked(SysRole sysRole,List<SysUserRole> urList){
		SysRoleQueryModel model = new SysRoleQueryModel(sysRole);
		if(urList!=null && !urList.isEmpty()){
			for (SysUserRole sysUserRole : urList) {
				if(sysRole.getRoleCode().equals(sysUserRole.getRolecode())){
					model.setIsChecked(true);
					break;
				}
			}
		}
		return model;
	}

	@Override
	public List<SysRoleQueryModel> selectRoleByUserid(String orgtype,
			String userid) {
		List<SysRole> roleList = sysRoleService.selectNotDelRoleByOrgtype(orgtype);
		List<SysUserRole> urList = ((SysUserRoleMapper)getMapper()).selectRoleByUserid(userid);
		List<SysRoleQueryModel> roleModel = new ArrayList<SysRoleQueryModel>();
		if(roleList!=null && !roleList.isEmpty()){
			for (SysRole sysRole : roleList) {
				roleModel.add(setRoleChecked(sysRole, urList));
			}
		}
		return roleModel;
	}

	@Override
	public List<SysUserRole> selectRoleByUserid1(String userid) {
		List<SysUserRole> list = ((SysUserRoleMapper)getMapper()).selectRoleByUserid(userid);
		return list;
	}

}
