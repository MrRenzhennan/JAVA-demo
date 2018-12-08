package com.smzj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smzj.model.SysMemberRank;

public interface SysMemberRankMapper extends BaseMapper<SysMemberRank> {
	public void addMemberRank(SysMemberRank memberRank);

	public List<SysMemberRank> selectByMemberAndRank(@Param("memberid")String memberid,@Param("rankid")String rankid);

	public List<SysMemberRank> queryAllSysMemberRank(@Param("memberid")String memberid);

	public SysMemberRank selectByIdAndRankId(@Param("memberid")String memberid,@Param("rankid")String rankid);

	int saveMemberRank(SysMemberRank memberRank);

	int deleteMemberRank(SysMemberRank memberRank);

	int selectisExistRank(@Param("rankid")String rankid,@Param("value")String value);

	int selectPSocialCountByIdcard(@Param("idcard")String idcard);

	int selectCsocialCountByCompanycode(@Param("companycode")String companycode);
	
	void deleteByMemberId(String memberid);

	void deleteSocialByMemberId(String memberid);
}