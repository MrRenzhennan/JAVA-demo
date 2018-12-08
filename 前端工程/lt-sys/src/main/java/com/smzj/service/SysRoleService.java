
package com.smzj.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.smzj.model.SysRole;
import com.smzj.util.PageUtil;

public interface SysRoleService extends IService<SysRole> {
	/**
	 * 查询所有的角色根据体系编码
	 * @param sysRole
	 * @return
	 */
	Page<SysRole> selectRoleByOrgtype(SysRole sysRole,PageUtil pageUtil);

	/**
	 * 修改“启用或者禁用”
	 * @param isDel
	 * @param roleCode
	 * @return
	 */
	int updateRoleIsDel(String isDel,String roleCode);

	/**
	 * 查询角色下用户
	 * @param roleCode
	 * @return
	 */
	int selectCountRoleUser(String roleCode);

    /**
     * 删除角色
     * @param roleCode
     */
	void delRole(String roleCode);
	/**
	 * 角色信息保存
	 * @param SysRole:角色对象
	 * @return
	 */
	int sysRoleAdd(SysRole sysRole);

	/**
	 * 角色信息修改
	 * @param SysRole
	 * @return
	 */
	SysRole sysRoleById(String roleCode);
	/**
	 * 修改角色信息
	 * @param sysRole
	 * @return
	 */
	int sysRoleUpdate(SysRole sysRole);

	/**
	 * 查询指定orgtype的角色
	 * @param orgtype
	 * @return
	 */
	List<SysRole> selectNotDelRoleByOrgtype(String orgtype);

	/**
	 * 查询指定orgtype的角色
	 * @param sysRole
	 * @return
	 */
	Page<SysRole> selectRoleByOrgtype(String orgtype,String rolename, PageUtil pageUtil);

}
