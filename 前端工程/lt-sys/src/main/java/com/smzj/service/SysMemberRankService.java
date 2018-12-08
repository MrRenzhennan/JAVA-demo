package com.smzj.service;

import java.util.List;
import java.util.Map;

import com.smzj.model.SysMember;
import com.smzj.model.SysMemberRank;

public interface SysMemberRankService extends IService<SysMemberRank>{
	void addMemberRank(SysMemberRank memberRank);

	void addMemberRank(String memberid,Boolean isAndroid);
	/**
	 *  通过会员id拿到会员开通的SysMemberRank
	 * @param memberid
	 * @return
	 */
	List<SysMemberRank> queryAllSysMemberRank(String memberid);

	SysMemberRank selectByIdAndRankid(String memberid,String rankid);

	/**
	 * 添加
	 * @param memberId  会员ID
	 * @return
	 */
	int saveMemberRank(String memberId,String rankId);
	/**
	 * 删除
	 * @param memberId
	 * @param rankId
	 * @return
	 */
	int deleteMemberRank(String memberId,String rankId);

	/**
	 * 查询会员所有的认证，已开通的为1，未开通的为0
	 * @return
	 */
	Map<String,String> querySysMemberRank(String memberid);

	/**
	 * 查询会员是否认证了某个功能
	 * @param rankid MemEmail：email认证   MemMobile：手机认证
	 * @param value
	 * @return	true：有认证  false：没有认证
	 */
	boolean selectisExistRank(String rankid,String value);

	List<SysMemberRank> selectByMember(String memberid);

	int selectPSocialCountByIdcard(String idcard);

	int selectCsocialCountByCompanycode(String companycode);

	void addPSocialRank(SysMemberRank memberRank,SysMember member);

	List<Map<String,Object>> selectByRankidAndMobile(String rankid,String mobile);
	
	/**
	 * 会员实名验证
	 * @param member
	 * @return
	 */
	int addidAndmemberrank(SysMember member,int status);
	
	public void deleteByMemberId(String memberid);

	public void deleteSocialByMemberId(String memberid);

}
