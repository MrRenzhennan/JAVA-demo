package com.smzj.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SYS_DICT")
public class SysDict {
    @Id
    @Column(name = "DICTID")
    private String dictid;

    @Column(name = "DICTNAME")
    private String dictname;

    @Column(name = "ISDISABLE")
    private Integer isdisable;

    @Column(name = "FLAG")
    private String flag;

	public String getDictid() {
		return dictid;
	}

	public void setDictid(String dictid) {
		this.dictid = dictid;
	}

	public String getDictname() {
		return dictname;
	}

	public void setDictname(String dictname) {
		this.dictname = dictname;
	}

	public Integer getIsdisable() {
		return isdisable;
	}

	public void setIsdisable(Integer isdisable) {
		this.isdisable = isdisable;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}



}