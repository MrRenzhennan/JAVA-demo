package com.smzj.mapper;

import java.util.List;

import com.smzj.model.SysMemberFeedbackPic;

public interface SysMemberFeedbackPicMapper extends BaseMapper<SysMemberFeedbackPic> {
	void insertModel(List<SysMemberFeedbackPic> memberFeedbackPicList);
}