package com.smzj.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smzj.model.SysMemberTemp;

public interface SysMemberTempService extends IService<SysMemberTemp> {
	
	void reSetSysMemberMessage(SysMemberTemp sysMember);


	SysMemberTemp findBySysMemberId(String memberId);


	void saveMember(SysMemberTemp sysMember);


	int updateMember(SysMemberTemp member);

	/**
	 * 根据省政务平台编号查询会员信息
	 * @param perUserId
	 * @return
	 */
	SysMemberTemp findBySysMemberByPerUserId(String preUserId);

	void delSysMember(String memberId);
	
	void updateMemberByPreUserId(SysMemberTemp member);
}
