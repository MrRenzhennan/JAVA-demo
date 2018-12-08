package com.smzj.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "KQ_USER_REL")
public class KqUserRel {
    @Id
    @Column(name = "USERID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userid;

    @Column(name = "KQNO")
    private String kqno;

    @Column(name = "IS_LEADER")
    private Short isLeader;

    @Column(name = "IS_ADMIN")
    private Short isAdmin;

    /**
     * @return USERID
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return KQNO
     */
    public String getKqno() {
        return kqno;
    }

    /**
     * @param kqno
     */
    public void setKqno(String kqno) {
        this.kqno = kqno;
    }

    /**
     * @return IS_LEADER
     */
    public Short getIsLeader() {
        return isLeader;
    }

    /**
     * @param isLeader
     */
    public void setIsLeader(Short isLeader) {
        this.isLeader = isLeader;
    }

    /**
     * @return IS_ADMIN
     */
    public Short getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin
     */
    public void setIsAdmin(Short isAdmin) {
        this.isAdmin = isAdmin;
    }
}