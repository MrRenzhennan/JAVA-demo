package com.smzj.mapper;

import org.apache.ibatis.annotations.Param;

import com.smzj.model.SysMemberTruename;

public interface SysMemberTruenameMapper extends BaseMapper<SysMemberTruename> {
	public void insertTruename(SysMemberTruename truename);

	public void insertTruename1(SysMemberTruename truename);

	SysMemberTruename selectByMember(@Param("memberid")String memberid);

	int selectOverByIdcard(@Param("idcard")String memberid);

	int updateByMember(SysMemberTruename truename);

	int updateByMember1(SysMemberTruename truename);

	int updatestatusByMember(SysMemberTruename truename);
}