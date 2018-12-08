package com.smzj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smzj.model.ViewModAct;

public interface ViewModActMapper extends BaseMapper<ViewModAct> {
	List<ViewModAct> selectByappcode(@Param("appcode")String appcode);
}