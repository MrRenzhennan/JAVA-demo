package com.smzj.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SYS_MEMBER_FEEDBACK")
public class SysMemberFeedback {
    @Id
    @Column(name = "FEEDBACKID")
    private String feedbackid;

    @Column(name = "MEMBERID")
    private String memberid;

    @Column(name = "OPINION")
    private String opinion;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "ADDTIME")
    private Date addtime;

    @Column(name = "IMGURL")
    private String imgurl;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "UNIT")
    private String unit;

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
     * @return OPINION
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * @param opinion
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    /**
     * @return TEL
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}