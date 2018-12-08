package com.smzj.service;

import java.util.List;
import java.util.Map;

import com.smzj.model.SysRank;

public interface SysRankService extends IService<SysRank>{

	/**
	 * 拿到所有的rank
	 * @return
	 */
	public List<SysRank> queryAllSysRank();
	/**
	 * 拿到已开通和未开通rank
	 * @param memberid
	 * @return
	 */
	public Map<String,List<SysRank>> querySysRank(String memberid);

	public Map<String, Object> selectByRankidAndMemberid(String rankid,String memberid);
}
