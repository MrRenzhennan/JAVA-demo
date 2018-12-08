package com.smzj.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.smzj.mapper.SysMemberColmatterMapper;
import com.smzj.model.SysMemberColmatter;
import com.smzj.service.SysMemberColmatterService;
@Service("SysMemberColmatterService")
public class SysMemberColmatterServiceImpl extends BaseService<SysMemberColmatter>
		implements SysMemberColmatterService{

	@Override
	public SysMemberColmatter selectByMemberid(Map<String, String> map) {
		return ((SysMemberColmatterMapper) getMapper()).selectByMemberid(map);
	}

	@Override
	public int saveSMC(SysMemberColmatter smc) {
		return ((SysMemberColmatterMapper) getMapper()).saveSMC(smc);
	}

}
