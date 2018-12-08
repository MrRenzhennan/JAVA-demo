package com.smzj.model;

public class SysRoleQueryModel {

    private SysRole sysRole;

    /**
     * 是否被选中
     */
    private boolean isChecked = false;

    public SysRoleQueryModel() {
        this(new SysRole());
    }

    public SysRoleQueryModel(SysRole sysRole) {
        this.sysRole = sysRole;
    }

	public SysRole getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	public boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}