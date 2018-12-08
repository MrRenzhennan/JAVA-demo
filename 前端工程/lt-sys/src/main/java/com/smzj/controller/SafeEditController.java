package com.smzj.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smzj.annotation.Authorization;
import com.smzj.common.BaseController;
import com.smzj.common.BasePage;
import com.smzj.common.ConfSystemConstants;
import com.smzj.common.LogTypeConstat;
import com.smzj.model.SysMember;
import com.smzj.service.SysMemberService;
import com.smzj.service.SysRankService;
import com.smzj.util.FormCheckUtil;
import com.smzj.util.IPUtil;
import com.smzj.util.MD5Util;

@Controller
@Scope("prototype")
public class SafeEditController extends BaseController {
	@Autowired
	private SysMemberService sysMemberService;
	private static final Logger logger=Logger.getLogger(SafeEditController.class);

	private void load(ModelAndView result,HttpServletRequest request, HttpServletResponse response) throws Exception {
		BasePage basePage = new BasePage();
		basePage.setPageTitle("用户中心");
		basePage.setPageId("Member");
		basePage.setAppFunCode("Member", "");
		result.addObject("basePage", basePage);
		loadProperties(result, request, response);
	}

	@Authorization(name="Member", value={"0,Member"})
	@RequestMapping(value = "/safe/edit.html")
	public ModelAndView LoadView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView result = new ModelAndView("/member/edit");
		SysMember sm=getLoginMemberInfo(request, response).getSysMember();
		result.addObject("SM", sm);
		getMenuHtml(result, null, "Member");
		load(result, request, response);
		return result;
	}
	/**
	 * 重置密码
	 * @param id	用户ID
	 * @param oldpwd	原密码
	 * @param newpwd	新密码
	 * @param secpwd	确认密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/safe/resetPassword.html", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public Map<String,Object> resetPwd(
			@RequestParam("MemberId")String memberid,
			@RequestParam("OldPwd") String oldpwd,
			@RequestParam("NewPwd") String newpwd,
			@RequestParam("SecPwd") String secpwd,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rmap = new HashMap<String, Object>();
 		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[] { "原密码",oldpwd, "isNull"});
		list.add(new String[] { "新密码",newpwd, "isNull|isContainIllegalChar|isContainsChinese"});
		list.add(new String[] { "确认密码",secpwd, "isNull|isContainIllegalChar|isContainsChinese"});
		String isok = FormCheckUtil.checkForm(list);
		if (!"".equals(isok.trim())) {
			rmap.put("code", -1);
			rmap.put("msg", isok.trim());
			//redirect.openMessage(-1, isok.trim());
			return rmap;
		}
		 //新密码长度需在6-20字符之间
		if (newpwd.length() > 20||newpwd.length()<6) {
			rmap.put("code", -1);
			rmap.put("msg", "密码长度为6-20个字符！");
			//redirect.openMessage(-1, "密码长度为6-20个字符！");
			return rmap;
		}
		 //新密码不能与旧密码相同
		if (newpwd.equals(oldpwd)) {
			rmap.put("code", -1);
			rmap.put("msg", "新密码不能与原始密码相同！");
			//redirect.openMessage(-1, "新密码不能与原始密码相同！");
			return rmap;
		}
		//新密码两次输入不一致
		if (!newpwd.equals(secpwd)) {
			rmap.put("code", -1);
			rmap.put("msg", "重设密码两次输入不一致！");
			//redirect.openMessage(-1, "重设密码两次输入不一致！");
			return rmap;
		}
		 //MD5Util加密
		oldpwd = MD5Util.calcMD5(oldpwd);
		secpwd = MD5Util.calcMD5(secpwd);
		try {
			// TODO 传入id值找到sysUser
			SysMember sysMember = sysMemberService.getSysMember(memberid);
			// 验证原密码
			if (sysMember.getPassword().equals(oldpwd)) {
				sysMember.setPassword(secpwd);
				sysMemberService.resetPassword(sysMember);
				String login =ConfSystemConstants.MEMBERSITE+"logout.html";
				saveLog(LogTypeConstat.LOG_TYPE_0, LogTypeConstat.OPATYPE_PW_UPDATE, IPUtil.getIpAddr(request), "账号："+sysMember.getMembername()+"，修改密码",sysMember);
				
				rmap.put("code", 1);
				rmap.put("url", "/memberLogin.html");
				rmap.put("title", "密码修改成功！");
				rmap.put("msg",  "您的密码已修改成功！请重新登录！");
				//redirect.openMessageToUrl(1,"密码修改成功！", "您的密码已修改成功！请重新登录！", login);
				//return redirect.script();
			} else {
				rmap.put("code", -1);
				rmap.put("msg", "原密码验证错误！");
				//redirect.openMessage(-1, "原密码验证错误！");
				return rmap;
			}
		} catch (Exception e) {
			logger.error("密码修改失败",e);
			rmap.put("code", -1);
			rmap.put("msg",  "您的密码修改失败！");
			//redirect.openMessage(-1, "您的密码修改失败！");
			return rmap;
		}
		return rmap;
	}


}
