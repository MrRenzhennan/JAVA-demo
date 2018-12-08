package com.smzj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smzj.mapper.SysMemberMapper;
import com.smzj.mapper.SysMemberRankMapper;
import com.smzj.model.SysMember;
import com.smzj.model.SysMemberRank;
import com.smzj.model.SysMemberTruename;
import com.smzj.model.SysRank;
import com.smzj.service.SysMemberRankService;
import com.smzj.service.SysMemberService;
import com.smzj.service.SysMemberTruenameService;
import com.smzj.service.SysRankService;

@Service("sysMemberRankService")
public class SysMemberRankServiceImpl extends BaseService<SysMemberRank> implements SysMemberRankService{

	@Autowired
	private SysRankService sysRankService;
	@Autowired
	private Mapper<SysMember> memberMapper;
	@Autowired
	private SysMemberService sysMemberService;
	@Autowired
	private SysMemberTruenameService sysMemberTruenameService;

	@Override
	public void addMemberRank(SysMemberRank memberRank) {
		((SysMemberRankMapper)mapper).addMemberRank(memberRank);
	}

	@Override
	public void addMemberRank(String memberid,Boolean isAndroid) {
		String rankid="";
		if(isAndroid){
			rankid="MemAndroid";
		}else{
			rankid="MemIphone";
		}
		if(StringUtil.isNotEmpty(memberid)&&StringUtil.isNotEmpty(rankid)){
			List<SysMemberRank> memberRankList=((SysMemberRankMapper)mapper).selectByMemberAndRank(memberid,rankid);
			if(memberRankList.size()==0){
				SysMemberRank memberRank=new SysMemberRank();
				memberRank.setMemberid(memberid);
				memberRank.setRankid(rankid);
				((SysMemberRankMapper)mapper).addMemberRank(memberRank);
			}
		}
	}

	@Override
	public List<SysMemberRank> queryAllSysMemberRank(String memberid) {
		return ((SysMemberRankMapper) getMapper()).queryAllSysMemberRank(memberid);
	}

	@Override
	public SysMemberRank selectByIdAndRankid(String memberid, String rankid) {
		return ((SysMemberRankMapper) getMapper()).selectByIdAndRankId(memberid,rankid);
	}


	@Override
	public int saveMemberRank(String memberId,String rankId) {
		SysMemberRank sysMemberRank = selectByIdAndRankid(memberId, rankId);
		if(sysMemberRank!=null){
			return 1;
		}
		SysMemberRank memberRank=new SysMemberRank();
		memberRank.setMemberid(memberId);
		memberRank.setRankid(rankId);
		return ((SysMemberRankMapper)mapper).saveMemberRank(memberRank);
	}

	@Override
	public Map<String, String> querySysMemberRank(String memberid) {
		Map<String, List<SysRank>> memberRankMap = sysRankService.querySysRank(memberid);
		if(memberRankMap!=null&&!memberRankMap.isEmpty()){
			Map<String, String> resultMap = new HashMap<String, String>();
			List<SysRank> sysRankList1 = memberRankMap.get("1");
			if(sysRankList1!=null&&!sysRankList1.isEmpty()){
				for (SysRank sysRank : sysRankList1) {
					//已开通的，设置为1
					resultMap.put(sysRank.getRankid(), "1");
				}
			}
			List<SysRank> sysRankList2 = memberRankMap.get("2");
			if(sysRankList2!=null&&!sysRankList2.isEmpty()){
				for (SysRank sysRank : sysRankList2) {
					//未开通的，设置为1
					resultMap.put(sysRank.getRankid(), "0");
				}
			}
			return resultMap;
		}else{
			return null;
		}
	}

	@Override
	public int deleteMemberRank(String memberId, String rankId) {
		SysMemberRank sysMemberRank=new SysMemberRank();
		sysMemberRank.setMemberid(memberId);
		sysMemberRank.setRankid(rankId);
		return ((SysMemberRankMapper)mapper).deleteMemberRank(sysMemberRank);
	}

	@Override
	public boolean selectisExistRank(String rankid, String value) {
		if(StringUtil.isNotEmpty(rankid)&&StringUtil.isNotEmpty(value)){
			return ((SysMemberRankMapper)mapper).selectisExistRank(rankid, value)>0?true:false;
		}
		return false;
	}


	@Override
	public List<SysMemberRank> selectByMember(String memberid) {
		if(StringUtil.isNotEmpty(memberid)){
			return  ((SysMemberRankMapper)mapper).selectByMemberAndRank(memberid,null);
		}
		return new ArrayList<SysMemberRank>();
	}

	@Override
	public int selectPSocialCountByIdcard(String idcard) {
		return ((SysMemberRankMapper)mapper).selectPSocialCountByIdcard(idcard);
	}

	@Override
	public int selectCsocialCountByCompanycode(String companycode) {
		return  ((SysMemberRankMapper)mapper).selectCsocialCountByCompanycode(companycode);
	}

	@Override
	public void addPSocialRank(SysMemberRank memberRank, SysMember member) {
		((SysMemberRankMapper)mapper).addMemberRank(memberRank);
		((SysMemberMapper)memberMapper).updateByPrimaryKeySelective(member);
	}

	@Override
	public List<Map<String, Object>> selectByRankidAndMobile(String rankid,String mobile) {
		return ((SysMemberMapper)memberMapper).selectByRankidAndMobile(rankid,mobile);
	}
	

	@Override
	public int addidAndmemberrank(SysMember sysMember,int status) {
		int i=sysMemberService.updateNotNull(sysMember);
		if(i==0){
			return 0;
		}
		SysMemberTruename sysMemberTrueName=new SysMemberTruename();
		sysMemberTrueName.setId(UUID.randomUUID().toString().replace("-", ""));
		sysMemberTrueName.setTruename(sysMember.getTruename());
		sysMemberTrueName.setIdcard(sysMember.getIdcard());
		sysMemberTrueName.setMemberid(sysMember.getMemberid());
		sysMemberTrueName.setStatus((short)status);
		if(sysMemberTruenameService.insertTruename1(sysMemberTrueName,status).equals("100")){
			i+=1;
		}
		if(i==2&&status==1){
			i+=this.saveMemberRank(sysMember.getMemberid(),"MemTrueName");
		}
		return i;
	}

	@Override
	public void deleteByMemberId(String memberid) {
		((SysMemberRankMapper)mapper).deleteByMemberId(memberid);
	}

	@Override
	public void deleteSocialByMemberId(String memberid) {
		((SysMemberRankMapper)mapper).deleteSocialByMemberId(memberid);
	}

}
