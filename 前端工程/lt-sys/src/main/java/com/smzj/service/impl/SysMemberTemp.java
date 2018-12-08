package com.smzj.service.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SYS_MEMBER_TEMP")
public class SysMemberTemp {

	@Id
	@Column(name = "MEMBERID")
	private String memberId;

	@Column(name = "MEMBERNAME")
	private String membername;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "NICKNAME")
	private String nickname;

	@Column(name = "FLAG")
	private Short flag;

	@Column(name = "TRUENAME")
	private String truename;

	@Column(name = "COMPANY")
	private String company;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "TEL")
	private String tel;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "QQ")
	private String qq;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ISDEL")
	private Short isdel;

	@Column(name = "ISDROP")
	private Short isdrop;

	@Column(name = "ACTIVETIME")
	private Date activetime;

	@Column(name = "REGTIME")
	private Date regtime;

	@Column(name = "IDCARD")
	private String idcard;

	@Column(name = "COMPANYCODE")
	private String companycode;
	@Column(name = "YMONEY")
	private String ymoney;
	@Column(name = "CREDITNUM")
	private String creditnum;
	@Column(name = "CENTNUM")
	private String centnum;

	@Column(name = "MEMHEADPIC")
	private String memheadpic;
	@Column(name = "SSCARD")
	private String sscard;
	
	@Column(name = "TYPE")
	private String type;//用户类型（0个人 1企业）
	@Column(name = "PRE_USER_ID")
	private String preUserid;//省政务平台中心账号id
	@Column(name = "PRE_USER_ACCOUNT")
	private String preUserAccount;//省政务平台中心用户账号
	@Column(name = "PRE_OPEN_ID")
	private String preOpenId;//省政务平台中心用户id
	

	@Column(name = "PRE_CAKEY")
	private String preCaKey;//	CA证书KEY
	@Column(name = "PRE_GENDER")
	private String preGender;//性别 0男 1女
	@Column(name = "PRE_NATION")
	private String preNation;//	实名信息 -- 民族(见国标GB3304-91)
	@Column(name = "PRE_BIRTH")
	private String preBirth;//生日
	@Column(name = "PRE_WORKTEL")
	private String preWorkTel;//办公电话
	@Column(name = "PRE_WORKFAX")
	private String preWorkFax;//办公传真
	@Column(name = "PRE_LEGALTYPE")
	private String preLegalType;//法人类型 0：企业法人;1：事业单位;2：行政单位;3：社团;4：民非;5：基金会;6：养老机构;7：律师事务所;8：其他非企业法人
	@Column(name = "PRE_ENTERPRISENAME")
	private String preEnterpriseName;//企业名称
	@Column(name = "PRE_CREDITCODE")
	private String preCreditCode;//统一社会信用代码
	@Column(name = "PRE_LEGALREPRESENTATIVE")
	private String preLegalRepresentative;//	法定代表人
	@Column(name = "PRE_LEGALGENDER")
	private String preLegalGender;//性别 0：男 1：女
	@Column(name = "PRE_LEGALNATION")
	private String preLegalNation;//民族
	@Column(name = "PRE_AUTHLEVEL")
	private String preAuthlevel;//认证级别 1.匿名 2.实名 3.实人 （当认证级别为2或者3的时候实名信息才有效）
	@Column(name = "PRE_SYNC_STATUS")
	private String preSyscStatus;//是否为省平台同步用户，1.本地用户-0 2.省同步用户-1  默认值-0

	/**
	 * @return MEMBERID
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberid
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return MEMBERNAME
	 */
	public String getMembername() {
		return membername;
	}

	/**
	 * @param membername
	 */
	public void setMembername(String membername) {
		this.membername = membername;
	}

	/**
	 * @return PASSWORD
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return NICKNAME
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return FLAG
	 */
	public Short getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 */
	public void setFlag(Short flag) {
		this.flag = flag;
	}

	/**
	 * @return TRUENAME
	 */
	public String getTruename() {
		return truename;
	}

	/**
	 * @param truename
	 */
	public void setTruename(String truename) {
		this.truename = truename;
	}

	/**
	 * @return COMPANY
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return ADDRESS
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return ZIP
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
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
	 * @return FAX
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return MOBILE
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return QQ
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return EMAIL
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return ISDROP
	 */
	public Short getIsdrop() {
		return isdrop;
	}

	/**
	 * @param isdrop
	 */
	public void setIsdrop(Short isdrop) {
		this.isdrop = isdrop;
	}

	/**
	 * @return ACTIVETIME
	 */
	public Date getActivetime() {
		return activetime;
	}

	/**
	 * @param activetime
	 */
	public void setActivetime(Date activetime) {
		this.activetime = activetime;
	}

	/**
	 * @return REGTIME
	 */
	public Date getRegtime() {
		return regtime;
	}

	/**
	 * @param regtime
	 */
	public void setRegtime(Date regtime) {
		this.regtime = regtime;
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
	 * @return COMPANYCODE
	 */
	public String getCompanycode() {
		return companycode;
	}

	/**
	 * @param companycode
	 */
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public String getYmoney() {
		return ymoney;
	}

	public void setYmoney(String ymoney) {
		this.ymoney = ymoney;
	}

	public String getCreditnum() {
		return creditnum;
	}

	public void setCreditnum(String creditnum) {
		this.creditnum = creditnum;
	}

	public String getCentnum() {
		return centnum;
	}

	public void setCentnum(String centnum) {
		this.centnum = centnum;
	}

	public String getMemheadpic() {
		return memheadpic;
	}

	public void setMemheadpic(String memheadpic) {
		this.memheadpic = memheadpic;
	}

	public String getSscard() {
		return sscard;
	}

	public void setSscard(String sscard) {
		this.sscard = sscard;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPreUserid() {
		return preUserid;
	}

	public void setPreUserid(String preUserid) {
		this.preUserid = preUserid;
	}

	public String getPreUserAccount() {
		return preUserAccount;
	}

	public void setPreUserAccount(String preUserAccount) {
		this.preUserAccount = preUserAccount;
	}

	public String getPreOpenId() {
		return preOpenId;
	}

	public void setPreOpenId(String preOpenId) {
		this.preOpenId = preOpenId;
	}

	public String getPreCaKey() {
		return preCaKey;
	}

	public void setPreCaKey(String preCaKey) {
		this.preCaKey = preCaKey;
	}

	public String getPreGender() {
		return preGender;
	}

	public void setPreGender(String preGender) {
		this.preGender = preGender;
	}

	public String getPreNation() {
		return preNation;
	}

	public void setPreNation(String preNation) {
		this.preNation = preNation;
	}

	public String getPreBirth() {
		return preBirth;
	}

	public void setPreBirth(String preBirth) {
		this.preBirth = preBirth;
	}

	public String getPreWorkTel() {
		return preWorkTel;
	}

	public void setPreWorkTel(String preWorkTel) {
		this.preWorkTel = preWorkTel;
	}

	public String getPreWorkFax() {
		return preWorkFax;
	}

	public void setPreWorkFax(String preWorkFax) {
		this.preWorkFax = preWorkFax;
	}

	public String getPreLegalType() {
		return preLegalType;
	}

	public void setPreLegalType(String preLegalType) {
		this.preLegalType = preLegalType;
	}

	public String getPreEnterpriseName() {
		return preEnterpriseName;
	}

	public void setPreEnterpriseName(String preEnterpriseName) {
		this.preEnterpriseName = preEnterpriseName;
	}

	public String getPreCreditCode() {
		return preCreditCode;
	}

	public void setPreCreditCode(String preCreditCode) {
		this.preCreditCode = preCreditCode;
	}

	public String getPreLegalRepresentative() {
		return preLegalRepresentative;
	}

	public void setPreLegalRepresentative(String preLegalRepresentative) {
		this.preLegalRepresentative = preLegalRepresentative;
	}

	public String getPreLegalGender() {
		return preLegalGender;
	}

	public void setPreLegalGender(String preLegalGender) {
		this.preLegalGender = preLegalGender;
	}

	public String getPreLegalNation() {
		return preLegalNation;
	}

	public void setPreLegalNation(String preLegalNation) {
		this.preLegalNation = preLegalNation;
	}

	public String getPreAuthlevel() {
		return preAuthlevel;
	}

	public void setPreAuthlevel(String preAuthlevel) {
		this.preAuthlevel = preAuthlevel;
	}

	public String getPreSyscStatus() {
		return preSyscStatus;
	}

	public void setPreSyscStatus(String preSyscStatus) {
		this.preSyscStatus = preSyscStatus;
	}
	
}