package com.smzj.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SYS_MSG_SEND")
public class SysMsgSend {

	@Id
	@Column(name = "MSGID")
	@GeneratedValue(generator = "UUID")
	private String msgid;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "MSGCONTENT")
	private String msgcontent;

	@Column(name = "TRUENAME")
	private String truename;

	@Column(name = "MEMBERID")
	private String memberid;

	@Column(name = "USERID")
	private String userid;

	@Column(name = "SENDTIME")
	private Date sendtime;

	@Column(name = "ISSEND")
	private int issend;

	@Column(name = "RETIME")
	private Date retime;

	@Column(name = "REINFO")
	private String reinfo;

	@Column(name = "MSGFLAG")
	private String msgflag;

	@Column(name = "ADDTIME")
	private Date addtime;

	@Column(name = "MSGWAY")
	private int msgway;

	@Column(name = "SRCID")
	private String srcid;

	@Column(name = "SRCTAB")
	private String srctab;

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMsgcontent() {
		return msgcontent;
	}

	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public int getIssend() {
		return issend;
	}

	public void setIssend(int issend) {
		this.issend = issend;
	}

	public Date getRetime() {
		return retime;
	}

	public void setRetime(Date retime) {
		this.retime = retime;
	}

	public String getReinfo() {
		return reinfo;
	}

	public void setReinfo(String reinfo) {
		this.reinfo = reinfo;
	}

	public String getMsgflag() {
		return msgflag;
	}

	public void setMsgflag(String msgflag) {
		this.msgflag = msgflag;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public int getMsgway() {
		return msgway;
	}

	public void setMsgway(int msgway) {
		this.msgway = msgway;
	}

	public String getSrcid() {
		return srcid;
	}

	public void setSrcid(String srcid) {
		this.srcid = srcid;
	}

	public String getSrctab() {
		return srctab;
	}

	public void setSrctab(String srctab) {
		this.srctab = srctab;
	}

}
