package com.smzj.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SYS_RANK")
public class SysRank {
    @Id
    @Column(name = "RANKID")
    private String rankid;

    @Column(name = "RANKNAME")
    private String rankname;

    @Column(name = "OBJECT")
    private String object;

    @Column(name = "CONDITION")
    private String condition;

    @Column(name = "PIC")
    private String pic;

    @Column(name = "CONHTML")
    private String conhtml;
    @Column(name = "OPENURL")
    private String openurl;
    
    @Column(name = "SORTBY")
    private int sortby;

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

    /**
     * @return RANKNAME
     */
    public String getRankname() {
        return rankname;
    }

    /**
     * @param rankname
     */
    public void setRankname(String rankname) {
        this.rankname = rankname;
    }

    /**
     * @return OBJECT
     */
    public String getObject() {
        return object;
    }

    /**
     * @param object
     */
    public void setObject(String object) {
        this.object = object;
    }

    /**
     * @return CONDITION
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return PIC
     */
    public String getPic() {
        return pic;
    }

    /**
     * @param pic
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * @return CONHTML
     */
    public String getConhtml() {
        return conhtml;
    }

    /**
     * @param conhtml
     */
    public void setConhtml(String conhtml) {
        this.conhtml = conhtml;
    }

	public String getOpenurl() {
		return openurl;
	}

	public void setOpenurl(String openurl) {
		this.openurl = openurl;
	}

	public int getSortby() {
		return sortby;
	}

	public void setSortby(int sortby) {
		this.sortby = sortby;
	}
}