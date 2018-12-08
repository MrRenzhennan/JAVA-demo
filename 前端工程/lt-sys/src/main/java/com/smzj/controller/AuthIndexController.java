package com.smzj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smzj.annotation.Authorization;
import com.smzj.common.BaseController;
import com.smzj.common.BasePage;
import com.smzj.model.SysRank;
import com.smzj.service.SysRankService;

@Controller
@Scope("prototype")
public class AuthIndexController extends BaseController {
	@Autowired
	private SysRankService sysRankService;
	

	private void load(ModelAndView result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BasePage basePage = new BasePage();
		basePage.setPageTitle("用户中心");
		basePage.setPageId("Member");
		basePage.setAppFunCode("Member", "");
		result.addObject("basePage", basePage);
		loadProperties(result, request, response);
	}

	@Authorization(name="Member", value={"0,Member"})
	@RequestMapping(value = "/auth/index.html")
	public ModelAndView LoadView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView result = new ModelAndView("/member/index");
		String memberid =getLoginMemberId(request);
		Map<String,List<SysRank>> map = sysRankService.querySysRank(memberid);
		result.addObject("list1",map.get("1"));
		result.addObject("list2",map.get("2"));
		getMenuHtml(result, null, "Member");
		load(result, request, response);
		return result;
	}
}
