package com.smzj.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.smzj.annotation.Authorization;
import com.smzj.common.BaseController;
import com.smzj.common.BasePage;
import com.smzj.common.ConfSystemConstants;
import com.smzj.model.SysOrg;
import com.smzj.model.SysUser;
import com.smzj.model.SysUserOrg;
import com.smzj.service.SysOrgService;
import com.smzj.service.SysUserOrgService;
import com.smzj.util.DateUtils;

@Controller
@Scope("prototype")
public class IndexController extends BaseController {

	@Autowired
	private SysOrgService sysOrgService;

	@Autowired
	private SysUserOrgService sysUserOrgService;

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
	
	
	@Authorization(name="User", value={"0,Sys","0,Work","0,Subject","0,Finance","0,Info","0,Index"})
	@RequestMapping(value = "")
	public ModelAndView LoadIndex(@RequestParam(required = false, defaultValue = "-1") String source,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView result = new ModelAndView("/admin");
		/**
		 * pk验证
		 */
		String userid = getLoginUserId(request);
		SysUser user = getLoginUserInfo(request, response).getUser();
		SysUserOrg sysUserOrg=sysUserOrgService.findOrgByUserId2(userid);
		if(user!=null){

			String orgcode=null;
			SysOrg sysOrg = null;
			if(sysUserOrg!=null){
				orgcode=sysUserOrg.getOrgCode();
				sysOrg = sysOrgService.selectByOrgCode(orgcode);
			}

		}
		/*首页内部公告*/
		Map<String,Object> rolemap= new HashMap<String,Object>();
		rolemap.put("userid", userid);
		//List<Map<String,Object>> noticeList=bbsNoticeUserService.selectbbsnoticelist(rolemap);
		//result.addObject("noticeList", noticeList);
		result.addObject("nowDate",DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		result.addObject("infoSite",ConfSystemConstants.INFOSITE);
		load(result, request, response);
		return result;
	}


	@Authorization(name="User", value={"0,Sys","0,Work","0,Subject","0,Finance","0,Info","0,Index"})
	@RequestMapping(value = "/index.html")
	public ModelAndView LoadIndexIndex(@RequestParam(required = false, defaultValue = "") String source,HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.LoadIndex(source, request, response);
	}

	@Authorization(name="User", value={"0,Sys","0,Work","0,Subject","0,Finance","0,Info"})
	@RequestMapping(value = "/admin.html")
	public ModelAndView LoadAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView result = new ModelAndView("/admin");
		load(result, request, response);
		return result;
	}

}