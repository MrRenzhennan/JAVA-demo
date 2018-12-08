package com.smzj.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SYS_MEMBER_TRUENAME")
public class SysMemberTruename {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "TRUENAME")
    private String truename;

    @Column(name = "IDCARD")
    private String idcard;

    @Column(name = "FRONTCARD")
    private String frontcard;

    @Column(name = "BEHINDCARD")
    private String behindcard;

    @Column(name = "PEOPLECARD")
    private String peoplecard;

    @Column(name = "MEMBERID")
    private String memberid;

    @Column(name = "ADDTIME")
    private Date addtime;

    @Column(name = "STATUS")
    private Short status;
    
    @Column(name = "NOTBYREASON")
    private String notbyreason;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return TURENAME
     */
    public String getTruename() {
        return truename;
    }

    /**
     * @param turename
     */
    public void setTruename(String turename) {
        this.truename = turename;
    }

    /**
     * @return IDCARD
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * @param idcard
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * @return FRONTCARD
     */
    public String getFrontcard() {
        return frontcard;
    }

    /**
     * @param frontcard
     */
    public void setFrontcard(String frontcard) {
        this.frontcard = frontcard;
    }

    /**
     * @return BEHINDCARD
     */
    public String getBehindcard() {
        return behindcard;
    }

    /**
     * @param behindcard
     */
    public void setBehindcard(String behindcard) {
        this.behindcard = behindcard;
    }

    /**
     * @return PEOPLECARD
     */
    public String getPeoplecard() {
        return peoplecard;
    }

    /**
     * @param peoplecard
     */
    public void setPeoplecard(String peoplecard) {
        this.peoplecard = peoplecard;
    }

    public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	/**
     * @return ADDTIME
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getNotbyreason() {
		return notbyreason;
	}

	public void setNotbyreason(String notbyreason) {
		this.notbyreason = notbyreason;
	}
}