package com.smzj.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SYS_MEMBER_FEEDBACK_PIC")
public class SysMemberFeedbackPic {
    @Id
    @Column(name = "PICID")
    private String picid;

    @Column(name = "FEEDBACKID")
    private String feedbackid;

    @Column(name = "FILEPATH")
    private String filepath;

    @Column(name = "FILETYPE")
    private String filetype;

    @Column(name = "MEMBERID")
    private String memberid;

    @Column(name = "ADDTIME")
    private Date addtime;

    /**
     * @return PICID
     */
    public String getPicid() {
        return picid;
    }

    /**
     * @param picid
     */
    public void setPicid(String picid) {
        this.picid = picid;
    }

    /**
     * @return FEEDBACKID
     */
    public String getFeedbackid() {
        return feedbackid;
    }

    /**
     * @param feedbackid
     */
    public void setFeedbackid(String feedbackid) {
        this.feedbackid = feedbackid;
    }

    /**
     * @return FILEPATH
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * @param filepath
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /**
     * @return FILETYPE
     */
    public String getFiletype() {
        return filetype;
    }

    /**
     * @param filetype
     */
    public void setFiletype(String filetype) {
        this.filetype = filetype;
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
}