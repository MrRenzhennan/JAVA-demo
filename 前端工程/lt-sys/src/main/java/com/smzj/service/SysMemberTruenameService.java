package com.smzj.service;

import com.smzj.model.SysMemberTruename;

public interface SysMemberTruenameService extends IService<SysMemberTruename>{
	String insertTruename(SysMemberTruename truename);

	String insertTruename1(SysMemberTruename truename,int status);

	SysMemberTruename selectByMember(String memberid);

	String check(SysMemberTruename truename);
}
