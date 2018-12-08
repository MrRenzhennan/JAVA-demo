package com.smzj.service;


import java.util.Map;

import com.smzj.model.SysMemberFeedback;

public interface SysMemberFeedbackService extends IService<SysMemberFeedback>{
	Map<String,String> insertModel(SysMemberFeedback memberFeedback,String[] filepaths);
}
