package com.smzj.mapper;

import java.util.Map;

import com.smzj.model.SysMemberColmatter;

public interface SysMemberColmatterMapper extends BaseMapper<SysMemberColmatter> {
	/**
	 * 查询会员收藏的办事项
	 * @param memberid 会员ID
	 * @return
	 */
	SysMemberColmatter selectByMemberid(Map<String, String> map);
	/**
	 * 添加收藏
	 */
	int saveSMC(SysMemberColmatter smc);
}