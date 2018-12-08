package com.smzj.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.util.StringUtil;

import com.github.pagehelper.Page;
import com.smzj.annotation.Authorization;
import com.smzj.common.BaseController;
import com.smzj.common.BasePage;
import com.smzj.model.SysMember;
import com.smzj.model.SysMemberRank;
import com.smzj.model.SysUser;
import com.smzj.service.SysMemberRankService;
import com.smzj.service.SysMemberService;
import com.smzj.util.MD5Util;
import com.smzj.util.PageUtil;

@Controller
@Scope("prototype")
public class SysMemberListController extends BaseController {
	@Autowired
	private SysMemberService sysMemberService;
	@Autowired
	private SysMemberRankService sysMemberRankService;

	
	private void load(ModelAndView result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BasePage basePage = new BasePage();
		basePage.setPageTitle("系统管理");
		basePage.setPageId("Sys");
		basePage.setAppFunCode("Sys", "");
		result.addObject("basePage", basePage);
		SysUser sysUser = getLoginUserInfo(request, response).getUser();
		getMenuHtml(result, sysUser, "Sys");
		loadProperties(result, request, response);
	}

	@Authorization(name="User", value={"0,SysMemberList_View"})
	@RequestMapping(value = "/member/list.html")
	public ModelAndView LoadIndex(@RequestParam(required = false) String pageNum,
			@RequestParam(required = false) String pageSize,
			@RequestParam(required = false) String membername,
			@RequestParam(required = false) String ty, HttpServletRequest request, HttpServletResponse response
			) throws Exception {
		ModelAndView result = new ModelAndView("/member/list");
		//createPage();
		Page<SysMember> list = sysMemberService.selectSysMember(membername,ty,new PageUtil(pageNum, pageSize));
		for (SysMember sysMember : list) {
			if(StringUtils.isEmpty(sysMember.getNickname())){
				sysMember.setNickname("");
			}
		}
		result.addObject("memberList", list);
		//分页设值-start-
		Map<String, String> map = new HashMap<String, String>();
		map.put("membername", membername==null?"":membername);
		setPageInfo(result,list,"/member/list.html",map);
		//分页-end-
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE);
		result.addObject("membername", membername);
		result.addObject("ty", ty);
		load(result, request, response);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/member/resetpwd",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public Map<String,String> resetPwd(String memberid){
		Map<String,String> map=new HashMap<String, String>();
		if(StringUtil.isEmpty(memberid)){
			map.put("result", "-1");
			map.put("msg", "请选择会员后再重置密码");
			return map;
		}
		SysMember member =new SysMember();
		member.setMemberid(memberid);
		member.setPassword(MD5Util.calcMD5("88888888"));
		sysMemberService.resetPassword(member);
		map.put("result", "1");
		map.put("msg", "密码重置成功，重置密码为88888888");
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/member/del",method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public Map<String,String> del(String memberid){
		Map<String,String> map=new HashMap<String, String>();
		if(StringUtil.isEmpty(memberid)){
			map.put("result", "-1");
			map.put("msg", "请选择会员后再删除");
			return map;
		}
		SysMember member =new SysMember();
		member.setMemberid(memberid);
		sysMemberService.delete(member);

		sysMemberRankService.deleteByMemberId(memberid);

		sysMemberRankService.deleteSocialByMemberId(memberid);

		map.put("result", "1");
		map.put("msg", "会员删除成功");
		return map;
	}
}
