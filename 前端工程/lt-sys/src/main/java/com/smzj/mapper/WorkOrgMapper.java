package com.smzj.mapper;

import java.util.List;

import com.smzj.model.WorkOrg;

public interface WorkOrgMapper extends BaseMapper<WorkOrg> {
	List<WorkOrg> selectAll();
	/**
	 * 过滤掉iswork！=1的
	 */
	List<WorkOrg> selectAllByIsWork();
}