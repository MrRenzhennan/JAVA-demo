package com.smzj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.util.StringUtil;

import com.smzj.mapper.SysRankMapper;
import com.smzj.model.SysMemberRank;
import com.smzj.model.SysRank;
import com.smzj.service.SysMemberRankService;
import com.smzj.service.SysRankService;

@Service("sysRankService")
public class SysRankServiceImpl extends BaseService<SysRank> implements SysRankService{

	@Autowired
	private SysMemberRankService sysMemberRankService;
	@Override
	public List<SysRank> queryAllSysRank() {
		return ((SysRankMapper) getMapper()).queryAllSysRank();
	}
	@Override
	public Map<String,List<SysRank>> querySysRank(String memberid) {
		Map<String,List<SysRank>> map =new HashMap<String,List<SysRank>>();
		List<SysRank> sysRankList =queryAllSysRank();
		List<SysMemberRank>  list =sysMemberRankService.queryAllSysMemberRank(memberid);
		//已开通业务
		List<SysRank> list1=new ArrayList<SysRank>();
		//未开通业务
		List<SysRank> list2=new ArrayList<SysRank>();
		list2.addAll(sysRankList);

		if(!list.isEmpty()){
			if(!sysRankList.isEmpty()){
				for(SysMemberRank memberRank :list){
					for(SysRank sysRank :sysRankList){
						if(memberRank.getRankid().equalsIgnoreCase(sysRank.getRankid())){
							list1.add(sysRank);
							list2.remove(sysRank);
						}
					}
				}
			}

		}
		map.put( "1",list1);
		map.put("2",list2);
		return map;
	}

	@Override
	public Map<String, Object> selectByRankidAndMemberid(String rankid,String memberid) {
		if(StringUtil.isNotEmpty(rankid)){
			return ((SysRankMapper)mapper).selectByRankidAndMemberid(rankid,memberid);
		}
		return null;
	}
}
