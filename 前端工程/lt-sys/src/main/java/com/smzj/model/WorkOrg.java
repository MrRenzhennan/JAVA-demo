package com.smzj.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "WORK_ORG")
public class WorkOrg {
    @Id
    @Column(name = "ORGID")
    @GeneratedValue(generator = "UUID")
    private String orgid;

    @Column(name = "ORGNAME")
    private String orgname;

    @Column(name = "ORGURL")
    private String orgurl;

    @Column(name = "ORGADDRESS")
    private String orgaddress;

    @Column(name = "ORGTEL")
    private String orgtel;

    @Column(name = "ORGEMAIL")
    private String orgemail;

    @Column(name = "ORGSCORE")
    private Long orgscore;

    @Column(name = "ISDISABLE")
    private Short isdisable;

    @Column(name = "ISDEL")
    private Short isdel;

    @Column(name = "ORGSCORENUM")
    private Integer orgscorenum;

    @Column(name = "SORTCODE")
    private Long sortcode;

    @Column(name = "ORGCODE")
    private String orgcode;

    @Column(name = "IMGURL")
    private String imgurl;

    @Column(name = "ORGINFO")
    private String orginfo;

    /**
     * @return ORGID
     */
    public String getOrgid() {
        return orgid;
    }

    /**
     * @param orgid
     */
    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    /**
     * @return ORGNAME
     */
    public String getOrgname() {
        return orgname;
    }

    /**
     * @param orgname
     */
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    /**
     * @return ORGURL
     */
    public String getOrgurl() {
        return orgurl;
    }

    /**
     * @param orgurl
     */
    public void setOrgurl(String orgurl) {
        this.orgurl = orgurl;
    }

    /**
     * @return ORGADDRESS
     */
    public String getOrgaddress() {
        return orgaddress;
    }

    /**
     * @param orgaddress
     */
    public void setOrgaddress(String orgaddress) {
        this.orgaddress = orgaddress;
    }

    /**
     * @return ORGTEL
     */
    public String getOrgtel() {
        return orgtel;
    }

    /**
     * @param orgtel
     */
    public void setOrgtel(String orgtel) {
        this.orgtel = orgtel;
    }

    /**
     * @return ORGEMAIL
     */
    public String getOrgemail() {
        return orgemail;
    }

    /**
     * @param orgemail
     */
    public void setOrgemail(String orgemail) {
        this.orgemail = orgemail;
    }

    /**
     * @return ORGSCORE
     */
    public Long getOrgscore() {
        return orgscore;
    }

    /**
     * @param orgscore
     */
    public void setOrgscore(Long orgscore) {
        this.orgscore = orgscore;
    }

    /**
     * @return ISDISABLE
     */
    public Short getIsdisable() {
        return isdisable;
    }

    /**
     * @param isdisable
     */
    public void setIsdisable(Short isdisable) {
        this.isdisable = isdisable;
    }

    /**
     * @return ISDEL
     */
    public Short getIsdel() {
        return isdel;
    }

    /**
     * @param isdel
     */
    public void setIsdel(Short isdel) {
        this.isdel = isdel;
    }

    /**
     * @return ORGSCORENUM
     */
    public Integer getOrgscorenum() {
        return orgscorenum;
    }

    /**
     * @param orgscorenum
     */
    public void setOrgscorenum(Integer orgscorenum) {
        this.orgscorenum = orgscorenum;
    }

    /**
     * @return SORTCODE
     */
    public Long getSortcode() {
        return sortcode;
    }

    /**
     * @param sortcode
     */
    public void setSortcode(Long sortcode) {
        this.sortcode = sortcode;
    }

    /**
     * @return ORGCODE
     */
    public String getOrgcode() {
        return orgcode;
    }

    /**
     * @param orgcode
     */
    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    /**
     * @return IMGURL
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * @param imgurl
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    /**
     * @return ORGINFO
     */
    public String getOrginfo() {
        return orginfo;
    }

    /**
     * @param orginfo
     */
    public void setOrginfo(String orginfo) {
        this.orginfo = orginfo;
    }
}