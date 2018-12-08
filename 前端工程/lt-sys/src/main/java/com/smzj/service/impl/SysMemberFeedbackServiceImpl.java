package com.smzj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.util.StringUtil;

import com.smzj.common.ConfSystemConstants;
import com.smzj.mapper.SysMemberFeedbackMapper;
import com.smzj.mapper.SysMemberFeedbackPicMapper;
import com.smzj.model.SysMemberFeedback;
import com.smzj.model.SysMemberFeedbackPic;
import com.smzj.service.SysMemberFeedbackService;

@Service("sysMemberFeedbackService")
public class SysMemberFeedbackServiceImpl extends BaseService<SysMemberFeedback> implements SysMemberFeedbackService{
	@Autowired
	private Mapper<SysMemberFeedbackPic> feedbackPicMapper;
	@Override
	public Map<String,String> insertModel(SysMemberFeedback memberFeedback,String[] filepaths) {
		Map<String,String> map=check(memberFeedback);
		if(!"1".equals(map.get("result"))){
			return map;
		}
		((SysMemberFeedbackMapper)mapper).insertModel(memberFeedback);
		List<SysMemberFeedbackPic> memberFeedbackPicList = new ArrayList<SysMemberFeedbackPic>();
		if(filepaths!=null){
			for(String filepath : filepaths){
				if(StringUtil.isNotEmpty(filepath)){
					SysMemberFeedbackPic memberFeedbackPic=new SysMemberFeedbackPic();
					memberFeedbackPic.setFeedbackid(memberFeedback.getFeedbackid());
					memberFeedbackPic.setFilepath(filepath);
					memberFeedbackPic.setFiletype(filepath.replaceAll("^[^.]{0,}/.", ""));
					memberFeedbackPic.setMemberid(memberFeedback.getMemberid());
					memberFeedbackPicList.add(memberFeedbackPic);
				}
			}
		}
		if(memberFeedbackPicList!=null && !memberFeedbackPicList.isEmpty()){
			((SysMemberFeedbackPicMapper)feedbackPicMapper).insertModel(memberFeedbackPicList);
		}
		map.put("msg", "您的反馈我们已经收到，我们会尽快为您就解决问题");
		map.put("url", ConfSystemConstants.MAINSITE);
		return map;
	}

	private Map<String,String> check(SysMemberFeedback memberFeedback){
		Map<String,String> map=new HashMap<String,String>();
		if(StringUtil.isEmpty(memberFeedback.getMemberid())){
			map.put("result", "-1");
			map.put("msg", "请您先登陆");
			map.put("url", ConfSystemConstants.MEMBERSITE+"login.html");
			return map;
		}
		if(StringUtil.isEmpty(memberFeedback.getOpinion())){
			map.put("result", "-1");
			map.put("msg", "请您填写问题和意见");
			return map;
		}
		if(memberFeedback.getOpinion().length()>500){
			map.put("result", "-1");
			map.put("msg", "请您填写问题和意见不能超过500个字符");
			return map;
		}
		if(StringUtil.isEmpty(memberFeedback.getTel())){
			map.put("result", "-1");
			map.put("msg", "请您填写联系方式，以方便我们与您取得联系");
			return map;
		}
		if(memberFeedback.getTel().length()>60){
			map.put("result", "-1");
			map.put("msg", "请您填写的联系方式不能超过60个字符");
			return map;
		}
		map.put("result", "1");
		return map;
	}
}
