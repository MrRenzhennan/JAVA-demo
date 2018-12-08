package com.smzj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smzj.mapper.SysRoleMapper;
import com.smzj.model.SysRole;
import com.smzj.service.SysRoleService;
import com.smzj.util.PageUtil;
import com.smzj.util.TextUtil;


@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseService<SysRole> implements SysRoleService {


	@Override
	public Page<SysRole> selectRoleByOrgtype(SysRole sysRole,PageUtil pageUtil) {
		return null;
	}

	@Override
	public int updateRoleIsDel(String isDel, String roleCode) {
		int isEel = 1;
		if(null!=isDel){
			isEel = "1".equals(isDel)?0:1;
		}
		SysRole sysrole = new SysRole();
		sysrole.setIsEel(isEel);
		sysrole.setRoleCode(roleCode);
		if(sysrole!=null){
			((SysRoleMapper)getMapper()).updateRoleIsDel(sysrole);
		}
		return isEel;
	}

	@Override
	public int selectCountRoleUser(String roleCode) {
		SysRole sysrole = ((SysRoleMapper)getMapper()).selectCountRoleUser(roleCode);
		return sysrole.getSortCode();
	}

	@Override
	public void delRole(String roleCode) {
		((SysRoleMapper)getMapper()).delRole(roleCode);
		((SysRoleMapper)getMapper()).delRolePermit(roleCode);
	}

	@Override
	public List<SysRole> selectNotDelRoleByOrgtype(String orgtype) {
		return ((SysRoleMapper)getMapper()).selectNotDelRoleByOrgtype(orgtype);
	}

	@Override
	public int sysRoleAdd(SysRole sysRole) {
		return ((SysRoleMapper)getMapper()).sysRoleAdd(sysRole);
	}

	@Override
	public SysRole sysRoleById(String roleCode) {
		return ((SysRoleMapper)getMapper()).sysRoleById(roleCode);
	}

	@Override
	public int sysRoleUpdate(SysRole sysRole) {
		return ((SysRoleMapper)getMapper()).sysRoleUpdate(sysRole);
	}

	@Override
	public Page<SysRole> selectRoleByOrgtype(String orgtype, String rolename, PageUtil pageUtil) {
		Map<String, String> map = new HashMap<String, String>();
		if (TextUtil.isNotNull(orgtype)) {
			map.put("orgtype", orgtype);
		}
		if (TextUtil.isNotNull(rolename)) {
			map.put("rolename", rolename);
		}
		PageHelper.startPage(pageUtil.getPageNum(), pageUtil.getPageSize());
		return (Page<SysRole>) ((SysRoleMapper) getMapper()).selectRoleByOrgtype(map);
	}
}
