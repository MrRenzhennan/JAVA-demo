package com.smzj.service.impl;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.util.StringUtil;

import com.smzj.mapper.SysMemberMapper;
import com.smzj.mapper.SysMemberTruenameMapper;
import com.smzj.model.SysMember;
import com.smzj.model.SysMemberTruename;
import com.smzj.service.SysMemberTruenameService;

@Service("sysMemberTruenameService")
public class SysMemberTruenameServiceImpl extends BaseService<SysMemberTruename> implements SysMemberTruenameService{

	@Autowired
	private Mapper<SysMember> memeberMapper;

	@Override
	public String insertTruename(SysMemberTruename truename) {
		if(StringUtil.isEmpty(truename.getMemberid())){
			return "请您先登录";
		}
		SysMemberTruename oldTruename=((SysMemberTruenameMapper)mapper).selectByMember(truename.getMemberid());
		truename.setAddtime(new Date());
		if(oldTruename==null){//增加
			truename.setStatus((short)0);
			String result=CheckTruename(truename);
			if(StringUtil.isNotEmpty(result)){
				return result;
			}
			/*if(((SysMemberTruenameMapper)mapper).selectOverByIdcard(truename.getIdcard())>0){
				return "此身份证号已经通过了实名认证";
			}*/

			SysMember membert = ((SysMemberMapper)memeberMapper).selectByRankIdcard(truename.getIdcard(),"MemTrueName");
			if(membert!=null && !membert.getMemberid().equals(truename.getMemberid())){
				return "该身份证号已经通过了实名认证，不能重复身份证认证";
			}

			SysMember memberp = ((SysMemberMapper)memeberMapper).selectByRankIdcard(truename.getIdcard(),"MemPSocialSecurity");
			if(memberp!=null && !memberp.getMemberid().equals(truename.getMemberid())){
				return "该身份证号已经通过了个人社保认证，不能重复身份证认证";
			}


			((SysMemberTruenameMapper)mapper).insertTruename(truename);
		}else{//修改
			if(oldTruename.getStatus()==null ||oldTruename.getStatus()==0){
				return "您已申请实名认证，请您耐心等待...";
			}else if(oldTruename.getStatus()==1){
				return "您已通过实名认证，不必重复申请";
			}else if(oldTruename.getStatus()==-1){
				if(((SysMemberTruenameMapper)mapper).selectOverByIdcard(truename.getIdcard())>0){
					return "此身份证号已经通过了实名认证";
				}
				truename.setStatus((short)0);
				((SysMemberTruenameMapper)mapper).updateByMember(truename);
			}
		}
		return "";
	}

	@Override
	public SysMemberTruename selectByMember(String memberid) {
		if(StringUtil.isNotEmpty(memberid)){
			return ((SysMemberTruenameMapper)mapper).selectByMember(memberid);
		}
		return null;
	}



	private String CheckTruename(SysMemberTruename truename){
		if(StringUtil.isEmpty(truename.getMemberid())){
			return "请您先登录会员系统";
		}
		if(StringUtil.isEmpty(truename.getTruename())){
			return "请您填写真实姓名";
		}
		if(truename.getTruename().length()>50){
			return "您填写的真实姓名过长";
		}
		if(StringUtil.isEmpty(truename.getIdcard())){
			return "请您填写身份证号";
		}
		//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        Pattern pattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        //通过Pattern获得Matcher
        Matcher matcher = pattern.matcher(truename.getIdcard());
        if(!matcher.matches()){
        	return "请您输入正确的身份证号";
        }
		if(StringUtil.isEmpty(truename.getFrontcard())){
			return "请您上传身份证正面照";
		}
		if(truename.getFrontcard().length()>500){
			return "您上传的身份证正面照的路径过长";
		}
		if(StringUtil.isEmpty(truename.getBehindcard())){
			return "请您上传身份证背面照";
		}
		if(truename.getBehindcard().length()>500){
			return "您上传的身份证背面照的路径过长";
		}
		if(StringUtil.isEmpty(truename.getPeoplecard())){
			return "请您上传您与身份证的合照";
		}
		if(truename.getBehindcard().length()>500){
			return "您上传的您与身份证的合照的路径过长";
		}
		return "";
	}

	@Override
	public String insertTruename1(SysMemberTruename truename,int status) {
		if(StringUtil.isEmpty(truename.getMemberid())){
			return "请您先登录";
		}
		SysMemberTruename oldTruename=((SysMemberTruenameMapper)mapper).selectByMember(truename.getMemberid());
		truename.setAddtime(new Date());
		if(oldTruename==null){//增加
			truename.setStatus((short)status);
			String result=CheckTruename1(truename);
			if(StringUtil.isNotEmpty(result)){
				return result;
			}

			SysMember membert = ((SysMemberMapper)memeberMapper).selectByRankIdcard(truename.getIdcard(),"MemTrueName");
			if(membert!=null && !membert.getMemberid().equals(truename.getMemberid())){
				return "该身份证号已经通过了实名认证，不能重复身份证认证";
			}

			SysMember memberp = ((SysMemberMapper)memeberMapper).selectByRankIdcard(truename.getIdcard(),"MemPSocialSecurity");
			if(memberp!=null && !memberp.getMemberid().equals(truename.getMemberid())){
				return "该身份证号已经通过了个人社保认证，不能重复身份证认证";
			}


			((SysMemberTruenameMapper)mapper).insertTruename1(truename);
		}else{//修改
			if(oldTruename.getStatus()==null ||oldTruename.getStatus()==0){
				return "您已申请实名认证，请您耐心等待...";
			}else if(oldTruename.getStatus()==1){
				return "您已通过实名认证，不必重复申请";
			}else if(oldTruename.getStatus()==-1){
				if(((SysMemberTruenameMapper)mapper).selectOverByIdcard(truename.getIdcard())>0){
					return "此身份证号已经通过了实名认证";
				}
				truename.setStatus((short)status);
				((SysMemberTruenameMapper)mapper).updateByMember1(truename);
			}
		}
		return "100";
	}

	private String CheckTruename1(SysMemberTruename truename){
		if(StringUtil.isEmpty(truename.getMemberid())){
			return "请您先登录会员系统";
		}
		if(StringUtil.isEmpty(truename.getTruename())){
			return "请您填写真实姓名";
		}
		if(truename.getTruename().length()>50){
			return "您填写的真实姓名过长";
		}
		if(StringUtil.isEmpty(truename.getIdcard())){
			return "请您填写身份证号";
		}
		//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
       /* Pattern pattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        //通过Pattern获得Matcher
        Matcher matcher = pattern.matcher(truename.getIdcard());
        if(!matcher.matches()){
        	return "请您输入正确的身份证号";
        }
   */
		return "";
	}

	@Override
	public String check(SysMemberTruename truename){
		if(StringUtil.isEmpty(truename.getMemberid())){
			return "请您先登录";
		}
		SysMemberTruename oldTruename=((SysMemberTruenameMapper)mapper).selectByMember(truename.getMemberid());
		truename.setAddtime(new Date());
		if(oldTruename==null){
			String result=CheckTruename1(truename);
			if(StringUtil.isNotEmpty(result)){
				return result;
			}
			SysMember membert = ((SysMemberMapper)memeberMapper).selectByRankIdcard(truename.getIdcard(),"MemTrueName");
			if(membert!=null && !membert.getMemberid().equals(truename.getMemberid())){
				return "该身份证号已经通过了实名认证，不能重复身份证认证";
			}

			SysMember memberp = ((SysMemberMapper)memeberMapper).selectByRankIdcard(truename.getIdcard(),"MemPSocialSecurity");
			if(memberp!=null && !memberp.getMemberid().equals(truename.getMemberid())){
				return "该身份证号已经通过了个人社保认证，不能重复身份证认证";
			}

		}else{
			if(oldTruename.getStatus()==null ||oldTruename.getStatus()==0){
				return "您已申请实名认证，请您耐心等待...";
			}else if(oldTruename.getStatus()==1){
				return "您已通过实名认证，不必重复申请";
			}else if(oldTruename.getStatus()==-1){
				if(((SysMemberTruenameMapper)mapper).selectOverByIdcard(truename.getIdcard())>0){
					return "此身份证号已经通过了实名认证";
				}
			}
		}
		return "100";
	}
}
