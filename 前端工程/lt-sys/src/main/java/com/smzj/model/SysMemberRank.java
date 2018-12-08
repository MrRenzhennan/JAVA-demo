package com.smzj.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SYS_MEMBER_RANK")
public class SysMemberRank {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MEMBERID")
    private String memberid;

    @Column(name = "RANKID")
    private String rankid;

    @Column(name = "ADDTIME")
    private Date addtime;

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
     * @return MEMBERID
     */
    public String getMemberid() {
        return memberid;
    }

    /**
     * @param memberid
     */
    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    /**
     * @return RANKID
     */
    public String getRankid() {
        return rankid;
    }

    /**
     * @param rankid
     */
    public void setRankid(String rankid) {
        this.rankid = rankid;
    }

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
}