package com.smzj.mapper;

import java.util.List;
import java.util.Map;

import com.smzj.model.SysMsgSend;

public interface SysMsgSendMapper extends BaseMapper<SysMsgSend> {

	public List<Map<String, Object>> getMsgByMap(Map<String,String> map);

	public int updateMsgStatus(Map<String, String> map);
}