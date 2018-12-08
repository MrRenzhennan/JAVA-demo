package com.smzj.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.smzj.mapper.WorkOrgMapper;
import com.smzj.model.WorkOrg;
import com.smzj.service.WorkOrgService;

@Service("workOrgService")
public class WorkOrgServiceImpl extends BaseService<WorkOrg> implements WorkOrgService{
	private Logger logger=Logger.getLogger(WorkOrgServiceImpl.class);
	@Override
	public List<WorkOrg> selectAll() {
		try{
			return ((WorkOrgMapper)mapper).selectAllByIsWork();
		}catch(Exception e){
			logger.error("获取WorkOrg列表异常",e);
		}
		return new ArrayList<WorkOrg>();
	}

}
