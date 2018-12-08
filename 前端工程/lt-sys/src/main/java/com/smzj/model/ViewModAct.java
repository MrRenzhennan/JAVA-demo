package com.smzj.model;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "VIEW_MOD_ACT")
public class ViewModAct {
    @Column(name = "APPCODE")
    private String appcode;

    @Column(name = "MODCODE")
    private String modcode;

    @Column(name="PARENTCODE")
    private String parentcode;

    @Column(name = "MODNAME")
    private String modname;

    @Column(name = "MODID")
    private String modid;

    @Column(name = "ISSHOW")
    private Short isshow;

    @Column(name = "ISMENU")
    private Short ismenu;

    @Column(name = "SORTCODE")
    private Integer sortcode;

    @Column(name = "ACTNAME")
    private String actname;

    @Column(name="ACTNAME1")
    private String actname1;

    /**
     * @return APPCODE
     */
    public String getAppcode() {
        return appcode;
    }

    /**
     * @param appcode
     */
    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    /**
     * @return MODCODE
     */
    public String getModcode() {
    	return modcode;
    }

    /**
     * @param modcode
     */
    public void setModcode(String modcode) {
        this.modcode = modcode;
    }

    public String getParentcode() {
		return parentcode==null?"":parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	/**
     * @return MODNAME
     */
    public String getModname() {
        return modname;
    }

    /**
     * @param modname
     */
    public void setModname(String modname) {
        this.modname = modname;
    }

    /**
     * @return MODID
     */
    public String getModid() {
        return modid;
    }

    /**
     * @param modid
     */
    public void setModid(String modid) {
        this.modid = modid;
    }

    /**
     * @return ISSHOW
     */
    public Short getIsshow() {
        return isshow;
    }

    /**
     * @param isshow
     */
    public void setIsshow(Short isshow) {
        this.isshow = isshow;
    }

    /**
     * @return ISMENU
     */
    public Short getIsmenu() {
        return ismenu;
    }

    /**
     * @param ismenu
     */
    public void setIsmenu(Short ismenu) {
        this.ismenu = ismenu;
    }

    /**
     * @return SORTCODE
     */
    public Integer getSortcode() {
        return sortcode;
    }

    /**
     * @param sortcode
     */
    public void setSortcode(Integer sortcode) {
        this.sortcode = sortcode;
    }

    /**
     * @return ACTNAME
     */
    public String getActname() {
        return actname==null?"":actname;
    }

    /**
     * @param actname
     */
    public void setActname(String actname) {
        this.actname = actname;
    }

	public String getActname1() {
		return actname1==null?"":actname1;
	}

	public void setActname1(String actname1) {
		this.actname1 = actname1;
	}
}