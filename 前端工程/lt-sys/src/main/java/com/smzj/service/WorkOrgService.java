package com.smzj.service;

import java.util.List;

import com.smzj.model.WorkOrg;

public interface WorkOrgService extends IService<WorkOrg>{
	List<WorkOrg> selectAll();
}
