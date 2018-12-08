package com.smzj.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name = "SYS_MEMBER_COLMATTER")
public class SysMemberColmatter {

    @Column(name = "MEMBERID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String memberid;

    @Column(name = "MATTERID")
    private String matterid;

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
     * @return MATTERID
     */
    public String getMatterid() {
        return matterid;
    }

    /**
     * @param matterid
     */
    public void setMatterid(String matterid) {
        this.matterid = matterid;
    }
}