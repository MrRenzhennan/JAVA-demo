package com.smzj.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SYS_MSG_SET")
public class SysMsgSet {

	@Id
	@Column(name = "ID")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue(generator = "UUID")
	private Integer id;

	@Column(name = "MSGFLAG")
	private String msgflag;

	@Column(name = "MSGTITLE")
	private String msgtitle;

	@Column(name = "MSGTEMPLATE")
	private String msgtemplate;

	@Column(name = "ISDISABLE")
	private int isdisable;

	@Column(name = "UPDAYS")
	private int updays;

	@Column(name = "UPMINS")
	private int upmins;

	@Column(name = "UPHOURS")
	private int uphours;

	@Column(name = "SENDTIME")
	private Date sendtime;

	@Column(name = "SENDTIMEFLAG")
	private int sendtimeflag;

	@Column(name = "MSGWAY")
	private int msgway;

	@Column(name = "LINKURL")
	private String linkurl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMsgflag() {
		return msgflag;
	}

	public void setMsgflag(String msgflag) {
		this.msgflag = msgflag;
	}

	public String getMsgtitle() {
		return msgtitle;
	}

	public void setMsgtitle(String msgtitle) {
		this.msgtitle = msgtitle;
	}

	public String getMsgtemplate() {
		return msgtemplate;
	}

	public void setMsgtemplate(String msgtemplate) {
		this.msgtemplate = msgtemplate;
	}

	public int getIsdisable() {
		return isdisable;
	}

	public void setIsdisable(int isdisable) {
		this.isdisable = isdisable;
	}

	public int getUpdays() {
		return updays;
	}

	public void setUpdays(int updays) {
		this.updays = updays;
	}

	public int getUpmins() {
		return upmins;
	}

	public void setUpmins(int upmins) {
		this.upmins = upmins;
	}

	public int getUphours() {
		return uphours;
	}

	public void setUphours(int uphours) {
		this.uphours = uphours;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public int getSendtimeflag() {
		return sendtimeflag;
	}

	public void setSendtimeflag(int sendtimeflag) {
		this.sendtimeflag = sendtimeflag;
	}

	public int getMsgway() {
		return msgway;
	}

	public void setMsgway(int msgway) {
		this.msgway = msgway;
	}

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

}
