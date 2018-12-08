package com.smzj.model;

import javax.persistence.Column;
import javax.persistence.Table;


@Table(name = "SYS_USER_ROLE")
public class SysUserRole {
	@Column(name = "USERID")
    private String userid;

    @Column(name = "ROLECODE")
    private String rolecode;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

}