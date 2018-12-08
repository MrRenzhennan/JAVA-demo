package com.smzj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.smzj.model.SysRank;

public interface SysRankMapper extends BaseMapper<SysRank> {

	public List<SysRank> queryAllSysRank();

	public Map<String,Object> selectByRankidAndMemberid(@Param("rankid")String rankid,@Param("memberid")String memberid);
}