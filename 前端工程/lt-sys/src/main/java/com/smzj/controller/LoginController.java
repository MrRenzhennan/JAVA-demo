package com.smzj.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smzj.common.BaseController;
import com.smzj.common.ConfSystemConstants;
import com.smzj.common.LogTypeConstat;
import com.smzj.model.SysOrg;
import com.smzj.model.SysUser;
import com.smzj.model.SysUserOrg;
import com.smzj.model.SysUserRole;
import com.smzj.service.SysOrgService;
import com.smzj.service.SysUserOrgService;
import com.smzj.service.SysUserRoleService;
import com.smzj.service.SysUserService;
import com.smzj.util.CookieUtil;
import com.smzj.util.IPUtil;
import com.smzj.util.MD5Util;
import com.smzj.util.SplitUserId;
import com.smzj.util.VerifyCodeUtils;

@Controller
@Scope("prototype")
public class LoginController extends BaseController {
	private Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserOrgService sysUserOrgService;
	@Autowired
	private SysOrgService sysOrgService;


	private final String ISCANLOGIN_ERROR = "登录次数超过50次,请稍后登录！";
	private final int PIC_H = 100;
	private final int PIC_W = 40;
	private final boolean flag = true;
    public static final String USER_LOGIN = "LoginUserId";
    public static final String USERINFO = "LoginUserInfo";
    

    private void load(ModelAndView result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		loadProperties(result, request, response);
	}
	
	@RequestMapping(value = "/login.html")
	public ModelAndView LoadIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ModelAndView result = new ModelAndView("/login");
		
		if (sysUserService.getCanOrNotLogin(session,request,response,"2")) {
			result.addObject("loginError", ISCANLOGIN_ERROR);
			result.addObject("button_disable", flag);
			load(result,request,response);
			return result;
		} else {
			result.addObject("loginError", "请输入用户账号、密码和验证码！");
			result.addObject("button_disable", !flag);
		}
		load(result,request,response);
		return result;
	}

	@RequestMapping(value = "/userLogin/code.html", method = RequestMethod.GET)
	public void getCode(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		HttpSession session = request.getSession();
		session.setAttribute("code_rand", verifyCode.toLowerCase());

		try {
			VerifyCodeUtils.outputImage(PIC_H, PIC_W, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			logger.error("获取验证码出现异常！", e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/userLogin/checkLogin.html", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public  Map<String,Object> checkLogin(@RequestParam(required = flag) String code, SysUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<Integer, String> info = sysUserService.checkLogin(session, request, response, user, flag, code);
		SysUser sysUser = sysUserService.getUserByNameAndPwd(user.getUsername(), user.getUserpassword());
		List<SysUserRole> roles = new ArrayList<SysUserRole>();
		if(sysUser!=null){
			roles = sysUserRoleService.selectRoleByUserid1(sysUser.getUserid());
		}
		if (info.containsKey(-1)) {
			map.put("code", -1);
			map.put("url", "/login.html");
			map.put("title", "登录提示");
			map.put("msg", info.get(-1));
			//redirect.openMessageToUrl(-1, "登录提示", info.get(-1), "/login.html");
			saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + user.getUsername() + "，登录系统后台失败！登录IP为：" + IPUtil.getIpAddr(request) + "," + info.get(-1), user);
		} else if (info.containsKey(-2)) {
			map.put("code", -100);
			map.put("url", "RootElement.document.getElementById('checkCode').value=''");
			map.put("title", "登录提示");
			map.put("msg", info.get(-2));
			//redirect.openMessage(-1, "登录提示", info.get(-2), "RootElement.document.getElementById('checkCode').value=''");
			saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + user.getUsername() + "，登录系统后台失败！登录IP为：" + IPUtil.getIpAddr(request) + "," + info.get(-2), user);
		} else {
			//登录系统后，如果用户的真实名称和机构名称相等或联系电话为空或主管领导为空，那么跳转到个人信息页面  add by wrj 2016-10-10
			String orgname="";
			SysUserOrg sysUserOrg=sysUserOrgService.findOrgByUserId2(sysUser.getUserid());
			if(sysUserOrg!=null){
				SysOrg sysOrg=sysOrgService.selectByOrgCode(sysUserOrg.getOrgCode());
				if(sysOrg!=null){
					orgname=sysOrg.getOrgname();
				}
			}
			if(sysUser.getOrgtype()==3 && (orgname.equals(sysUser.getTruename()) || StringUtils.isEmpty(sysUser.getMobile()) || StringUtils.isEmpty(sysUser.getLeader()))){
				//redirect.toUrl("/information.html?flag=1");
			}else{
				map.put("code", 1);
			}

			saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + sysUser.getUsername() + "，登录系统后台！登录IP为：" + IPUtil.getIpAddr(request), sysUser);
		}
		return map;
	}

	private boolean judgeRole(String code,List<SysUserRole> roles){
		for (SysUserRole sysUserRole : roles) {
			if(code.equals(sysUserRole.getRolecode())){
				return true;
			}
		}
		return false;
	}
	
	
	@RequestMapping(value = "/logout.html")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String userid = CookieUtil.getCookieValue(request.getCookies(), USER_LOGIN);
		Cookie cookie = new Cookie(USER_LOGIN, null);
		if(StringUtils.isNotEmpty(ConfSystemConstants.SYSTEM_HOST_URL)&&(ConfSystemConstants.SYSTEM_HOST_URL.contains(".com")||ConfSystemConstants.SYSTEM_HOST_URL.contains(".cn"))){
			cookie.setDomain(ConfSystemConstants.SYSTEM_HOST_URL);
		}
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		request.getSession().removeAttribute(USERINFO+userid);
		request.getSession().removeAttribute(USERINFO);
		Cookie cookie2 = new Cookie(MD5Util.calcMD5("check_user_session_id"+session.getId()), null);
		cookie2.setPath("/");
		cookie2.setMaxAge(0);
		response.addCookie(cookie2);
		
		if(com.smzj.util.StringUtil.isNotNull(userid)){//解密id
			userid =SplitUserId.getUserId(userid);
		}
		if(request.getSession().getAttribute("LoginUserInfo" + userid) != null) {
			saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，登出系统后台！登录IP为：" + IPUtil.getIpAddr(request), getLoginUserInfo(request, response).getUser());
		}
		return this.LoadIndex(request, response);
	}


}
