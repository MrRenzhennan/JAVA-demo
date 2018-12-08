package com.smzj.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SYS_APP")
public class SysApp {
    @Id
    @Column(name = "APPCODE")
    private String appcode;

    @Column(name = "APPNAME")
    private String appname;

    @Column(name = "SORTCODE")
    private Short sortcode;

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
     * @return APPNAME
     */
    public String getAppname() {
        return appname;
    }

    /**
     * @param appname
     */
    public void setAppname(String appname) {
        this.appname = appname;
    }

    /**
     * @return SORTCODE
     */
    public Short getSortcode() {
        return sortcode;
    }

    /**
     * @param sortcode
     */
    public void setSortcode(Short sortcode) {
        this.sortcode = sortcode;
    }
}