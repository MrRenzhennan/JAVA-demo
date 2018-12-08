package com.smzj.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.util.StringUtil;

import com.smzj.mapper.ViewModActMapper;
import com.smzj.model.ViewModAct;
import com.smzj.service.ViewModActService;


@Service("viewModActService")
public class ViewModActServiceImpl extends BaseService<ViewModAct> implements ViewModActService{
	@Override
	public List<ViewModAct> selectByAppcode(String appcode) {
		if(StringUtil.isNotEmpty(appcode)){
			return ((ViewModActMapper)mapper).selectByappcode(appcode);
		}else{
			return new ArrayList<ViewModAct>();
		}
	}
}