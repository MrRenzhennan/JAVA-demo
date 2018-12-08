package com.smzj.service;

import java.util.List;

import com.smzj.model.ViewModAct;


public interface ViewModActService extends IService<ViewModAct>{
	List<ViewModAct> selectByAppcode(String appcode);
}
