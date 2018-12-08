package com.smzj.mapper;

import java.util.List;
import java.util.Map;

import com.smzj.model.SysMsgSet;

public interface SysMsgSetMapper extends BaseMapper<SysMsgSet> {
	
	Map<String,Object> selectByMsgFlag(String msgflag);
	
	/***
	 * 查询msgway=4的数据
	 * @return
	 */
	List<SysMsgSet> selectSysMsgSets();
	/***
	 * 根据id查询msgway=4的数据
	 * @return
	 */
	SysMsgSet selectSysMsgSet(String id);
	int selectMaxId();
	int addSysMsgSet(SysMsgSet sms);
	
	/**
	 * 根据传入的参数获取短信模板
	 * @param map
	 * @return
	 */
	SysMsgSet selectByMap(Map<String, Object> map);
	
	SysMsgSet selectByMSGFlag(String msgflag);
}