package com.smzj.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smzj.mapper.SysMemberTempMapper;
import com.smzj.model.SysMemberTemp;
import com.smzj.service.SysMemberTempService;

@Service("sysMemberTempService")
public class SysMemberTempServiceImpl extends BaseService<SysMemberTemp> implements SysMemberTempService {

	@Override
	public void reSetSysMemberMessage(SysMemberTemp sysMember) {
		((SysMemberTempMapper)mapper).reSetSysMemberMessage(sysMember);
	}

	@Override
	public SysMemberTemp findBySysMemberId(String memberId) {
		return ((SysMemberTempMapper)mapper).findBySysMemberId(memberId);
	}

	@Override
	public void saveMember(SysMemberTemp sysMember) {
		((SysMemberTempMapper)mapper).saveMember(sysMember);
	}

	@Override
	public SysMemberTemp findBySysMemberByPerUserId(String preUserId) {
		return ((SysMemberTempMapper)mapper).findBySysMemberByPerUserId(preUserId);
	}

	@Override
	public void delSysMember(String memberId) {
		((SysMemberTempMapper)mapper).delSysMember(memberId);
	}

	@Override
	public int updateMember(SysMemberTemp member) {
		return ((SysMemberTempMapper)mapper).updateMember(member);
	}

	@Override
	public void updateMemberByPreUserId(SysMemberTemp member) {
		((SysMemberTempMapper)mapper).updateMemberByPreUserId(member);
	}

}
