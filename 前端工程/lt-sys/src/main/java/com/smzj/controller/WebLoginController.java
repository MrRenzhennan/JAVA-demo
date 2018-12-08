package com.smzj.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.smzj.common.MemberRankConstants;
import com.smzj.model.SysMember;
import com.smzj.service.SysMemberRankService;
import com.smzj.service.SysMemberService;
import com.smzj.util.Base64;
import com.smzj.util.CookieUtil;
import com.smzj.util.HttpRequestUtil;
import com.smzj.util.IPUtil;
import com.smzj.util.MD5Util;
import com.smzj.util.StringUtil;
import com.smzj.util.VerifyCodeUtils;

import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class WebLoginController extends BaseController {

	@Autowired
	private SysMemberService sysMemberService;
	@Autowired
	private SysMemberRankService sysMemberRankService;

	private final String ISCANLOGIN_ERROR ="登录次数超过50次,请稍后登录！";
	private final int PIC_H =100;
    private final int PIC_W =40;
    private final boolean flag=true;

    private static final String MEMBER_LOGIN = "LoginMemberId";
    private static final String MEMBERINFO = "LoginMemberInfo";
    

    private Logger logger=Logger.getLogger(WebLoginController.class);

	
    private void load(ModelAndView result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		loadProperties(result, request, response);
	}



	@RequestMapping(value = "/memberLogin.html",produces="text/html;charset=UTF-8")
	public ModelAndView LoadView(
			String urlpara,
			@RequestParam(required = false) String fromUrl,
			HttpServletRequest request,
			@RequestParam(required = false) String loginError,
            HttpServletResponse response) throws Exception {
		urlpara=urlpara==null?"":urlpara;
		ModelAndView result = new ModelAndView("member/memberLogin");
		//createPage();
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
        if(sysMemberService.getCanOrNotLogin(session,request,response,"2")){
        	if(StringUtils.isEmpty(loginError)){
        		result.addObject("loginError",ISCANLOGIN_ERROR);
        	}else{
        		result.addObject("loginError",loginError);
        	}
        	result.addObject("button_disable",flag);
    		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE_PAGE);
        	return result;
        }else{
        	if(StringUtils.isEmpty(loginError)){
        		result.addObject("loginError","请输入正确的用户账号、密码和验证码！");
        	}else{
        		result.addObject("loginError",loginError);
        	}
        	result.addObject("button_disable",!flag);
        }
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE_PAGE);
		fromUrl = StringUtils.isNotEmpty(fromUrl)?fromUrl: request.getHeader("Referer");
		result.addObject("fromUrl",StringUtils.isNotEmpty(fromUrl)?URLEncoder.encode(fromUrl,"UTF-8"):"");
		result.addObject("urlpara",urlpara);
		load(result, request, response);
		return result;
	}

	@RequestMapping(value = "member/logout.html",produces="text/html;charset=UTF-8")
	public ModelAndView logout(HttpServletRequest request,
            HttpServletResponse response,HttpSession session) throws Exception {
		String memberid = CookieUtil.getCookieValue(request.getCookies(), MEMBER_LOGIN);
		Cookie cookie = new Cookie(MEMBER_LOGIN, null);
		if(StringUtils.isNotEmpty(ConfSystemConstants.SYSTEM_HOST_URL)&&(ConfSystemConstants.SYSTEM_HOST_URL.contains(".com")||ConfSystemConstants.SYSTEM_HOST_URL.contains(".cn"))){
			cookie.setDomain(ConfSystemConstants.SYSTEM_HOST_URL);
		}
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		Cookie cookie2 = new Cookie(MD5Util.calcMD5("check_member_session_id"+session.getId()), null);
		cookie2.setPath("/");
		cookie2.setMaxAge(0);
		response.addCookie(cookie2);
		request.getSession().removeAttribute(MEMBERINFO+memberid);
		//setSysMember(null);
		String token = CookieUtil.getCookieValue(request.getCookies(), MD5Util.calcMD5(memberid));
		if(StringUtils.isNotEmpty(token)){
			token = token.substring(1, token.length()-1);
//			HttpRequestUtil.getRequest(ConfSystemConstants.DataSite+"user/userLogout?token="+URLEncoder.encode(token, "UTF-8"));
		}
		return this.LoadView("","",request, "请输入用户账号、密码和验证码！", response);
	}

	@RequestMapping(value = "/memberLogin/code.html", method = RequestMethod.GET)
    public void getCode(HttpServletRequest request,
            HttpServletResponse response) {
    	response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = request.getSession();
        session.setAttribute("code_rand", verifyCode.toLowerCase());
        //生成图片
        try {
			VerifyCodeUtils.outputImage( PIC_H,PIC_W, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			logger.error("获取验证码异常",e);
		}
    }

	@ResponseBody
    @RequestMapping(value = "/memberLogin/checkLogin.html",produces="text/html;charset=UTF-8")
    public Map<String,Object> checkLogin(@RequestParam(required = flag) String code,
    		String urlpara,
    		@RequestParam(required = false) String fromUrl,
    		SysMember member,
    		HttpServletRequest request,
           HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
    	Map<String,Object> map = new HashMap<String, Object>();
		Map<Integer, String> info =sysMemberService.checkLogin(session, request, response, member, flag, code,null);
		if(info.containsKey(-1)){
			if(StringUtils.isNotEmpty(fromUrl)){
				map.put("code", -1);
				map.put("url", "/memberLogin.html?loginError="+info.get(-1)+"&fromUrl="+fromUrl);
				map.put("title", "登录提示");
				map.put("msg", info.get(-1));
				//redirect.toUrl("/login.html?loginError="+info.get(-1)+"&fromUrl="+fromUrl);
			}else{
				map.put("code", -1);
				map.put("url", "/memberLogin.html?loginError="+info.get(-1));
				map.put("title", "登录提示");
				map.put("msg", info.get(-1));
				//redirect.toUrl("/login.html?loginError="+info.get(-1));
			}
		}else if(info.containsKey(-2)){
			map.put("code", -2);
			map.put("url", "RootElement.document.getElementById('checkCode').value=''");
			map.put("title", "登录提示");
			map.put("msg", info.get(-2));
			//redirect.openMessage(-2, "登录提示", info.get(-2), "RootElement.document.getElementById('checkCode').value=''");
		}else{
			SysMember member1 = sysMemberService.getMemberByNameAndPwd(member.getMembername(), MD5Util.calcMD5(member.getPassword()));
			List<SysMember> memList=new ArrayList<SysMember>();
			if(member1==null){
				memList=sysMemberService.getMemberByMobileAndPwd(member.getMembername().toString(), MD5Util.calcMD5(member.getPassword().toString()));
				if(memList!=null && !memList.isEmpty()){
					List<Map<String,Object>> memberMap = sysMemberRankService.selectByRankidAndMobile(MemberRankConstants.MEM_MOBILE, member.getMembername().trim());
					if(memberMap==null || memberMap.isEmpty()){
						map.put("code", -3);
						map.put("url", "/memberLogin.html");
						map.put("title", "登录提示");
						map.put("msg", "请先绑定手机号，再使用手机号登录!");
						
						//redirect.openMessageToUrl(-1, "登录提示", "请先绑定手机号，再使用手机号登录!", "/login.html");
						return map;
					}
					member1=sysMemberService.selectByKey(memberMap.get(0).get("MEMBERID"));
				}else{
					map.put("code", -3);
					map.put("url", "/memberLogin.html");
					map.put("title", "登录提示");
					map.put("msg", "账号或密码不正确!");
					//redirect.openMessageToUrl(-1, "登录提示", "账号或密码不正确!", "/login.html");
					return map;
				}
			}

			member1.setActivetime(new Date());
			sysMemberService.updateActiveTime(member1);


			//登录日志
			saveLog(LogTypeConstat.LOG_TYPE_0, LogTypeConstat.OPATYPE_LOGIN, IPUtil.getIpAddr(request), "账号："+member1.getMembername()+"，登录系统",member1);
			if(StringUtils.isEmpty(urlpara)){
				if (StringUtils.isNotEmpty(fromUrl) && !"1".equals(fromUrl) && fromUrl.indexOf("checkLogin.html") < 0) {
					map.put("code", 1);
					map.put("url", URLDecoder.decode(fromUrl, "UTF-8"));
					//redirect.toUrl(URLDecoder.decode(fromUrl, "UTF-8"));
				} else {
					map.put("code", 1);
					map.put("url", "/auth/index.html");
					//redirect.toUrl("/auth/index.html");
				}
			}else{
				urlpara=Base64.decode(urlpara);
				Pattern pattern = Pattern.compile("^http://");
				Matcher matcher = pattern.matcher(urlpara);
				if(!matcher.matches()){
					urlpara="http://"+urlpara;
				}
				map.put("code", 1);
				map.put("url", urlpara+"?username="+Base64.encode(member1.getMembername()));
				//redirect.toUrl(urlpara+"?username="+Base64.encode(member1.getMembername()));
			}
		}
		return map;
    }

	@RequestMapping(value = "/poplogin.html")
	public ModelAndView PopLoadView() throws Exception {
		ModelAndView result = new ModelAndView("/poplogin");
		result.addObject("RootPath",ConfSystemConstants.ROOTPATH);
		result.addObject("OaSite",ConfSystemConstants.OASITE);
		return result;
	}

}
