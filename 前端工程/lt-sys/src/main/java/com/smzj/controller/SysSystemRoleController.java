package com.smzj.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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

import tk.mybatis.mapper.util.StringUtil;

import com.github.pagehelper.Page;
import com.smzj.annotation.Authorization;
import com.smzj.common.BaseController;
import com.smzj.common.BasePage;
import com.smzj.common.LogTypeConstat;
import com.smzj.model.SysApp;
import com.smzj.model.SysRole;
import com.smzj.model.SysRolePermit;
import com.smzj.model.SysUser;
import com.smzj.model.ViewModAct;
import com.smzj.service.SysAppService;
import com.smzj.service.SysRolePermitService;
import com.smzj.service.SysRoleService;
import com.smzj.service.ViewModActService;
import com.smzj.util.FormCheckUtil;
import com.smzj.util.IPUtil;
import com.smzj.util.PageUtil;

@Controller
@Scope("prototype")
public class SysSystemRoleController extends BaseController {
	private Logger logger = Logger.getLogger(SysSystemRoleController.class);

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysAppService sysAppService;
	@Autowired
	private SysRolePermitService sysRolePermitService;
	@Autowired
	private ViewModActService viewModActService;

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

	@Authorization(name="User", value={"0,SysSystemRole_View"})
	@RequestMapping(value = "/system/role.html", produces = "text/html;charset=UTF-8")
	public ModelAndView LoadIndex(@RequestParam(required = false) String pageNum,
			@RequestParam(required = false) String pageSize,
			@RequestParam(required = false) String rolename,
			@RequestParam(required = false) String orgtype,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String _orgtype = orgtype;
		ModelAndView result = new ModelAndView("/system/role");
		_orgtype = _orgtype == null ? "0" : _orgtype;
		Page<SysRole> list = sysRoleService.selectRoleByOrgtype(_orgtype,rolename,new PageUtil(pageNum, pageSize));
		result.addObject("roleList", list);
		result.addObject("pageNum", pageNum);
		result.addObject("pageSize", pageSize);
		//分页设值-start-
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgtype", _orgtype);//需要传递的参数
		setPageInfo(result,list,"/system/role.html",map);
		//分页-end-
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE);
		result.addObject("orgtype", _orgtype);
		result.addObject("rolename", rolename);
		load(result, request, response);
		return result;
	}

	@Authorization(name="User", value={"0,SysSystemRole_Add"})
	@RequestMapping(value = "/system/addHtml.html")
	public ModelAndView addHtml(@RequestParam(required = true) Integer orgtype,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView result = new ModelAndView("/system/roleedit");
		///createPage();
		result.addObject("subflag", 1);
		SysRole sysRole=new SysRole();
		sysRole.setOrgType(orgtype);
		result.addObject("sysRole", sysRole);
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE);
		load(result, request, response);
		return result;
	}

	@ResponseBody
	@Authorization(name="User", value={"1,SysSystemRole_Add","2,SysSystemRole_Edit"})
	@RequestMapping(value = "/system/sysRole_add.html", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public Map<String,Object> sysRoleAdd(SysRole sysRole,@RequestParam(required = false) String pageNum,
			@RequestParam(required = false) String pageSize,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> rmap = new HashMap<String, Object>();
		//createPage();
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[] { "角色名称", sysRole.getRoleName(), "isNull|isContainIllegalChar" });
		list.add(new String[] { "描述", sysRole.getRoleMemo()+"", "isContainIllegalChar" });
		list.add(new String[] { "启用", sysRole.getIsEel()+"", "isNull|isInteger" });
		String isok = FormCheckUtil.checkForm(list);
		if (!"".equals(isok.trim())) {
			rmap.put("code", -1);
			/*map.put("url", "/login.html");
			map.put("title", "登录提示");*/
			rmap.put("msg", isok.trim());
			//redirect.openMessage(-1, isok.trim());
			return rmap;
		}
		int subFlag = Integer.parseInt(request.getParameter("subflag"));
		if (subFlag == 1) {
			Calendar c = Calendar.getInstance();
			sysRole.setAddTime(new Timestamp(c.getTimeInMillis()));
			try {
				sysRoleService.sysRoleAdd(sysRole);
				rmap.put("code", 1);
				rmap.put("url", "/system/role.html?orgtype="+sysRole.getOrgType()+"&pageNum="+pageNum+"&pageSize="+pageSize);
				rmap.put("title", "添加角色");
				rmap.put("msg", "已经成功添加了该角色！");
				//redirect.openMessageToUrl(1, "添加角色", "已经成功添加了该角色！", "/system/role.html?orgtype="+sysRole.getOrgType()+"&pageNum="+pageNum+"&pageSize="+pageSize);
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，添加角色成功！登录IP为：" + IPUtil.getIpAddr(request) + "，角色rolecode为：" + sysRole.getRoleCode(), getLoginUserInfo(request, response).getUser());
			} catch (Exception e) {
				logger.error("添加角色失败！", e);
				//redirect.openMessage(-1, "添加角色失败！");
				rmap.put("code", -1);
				/*map.put("url", "/login.html");
				map.put("title", "登录提示");*/
				rmap.put("msg", "添加角色失败！");
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，添加角色失败！登录IP为：" + IPUtil.getIpAddr(request), getLoginUserInfo(request, response).getUser());
				return rmap;
			}
		} else if (subFlag == 2) {
			try {
				sysRoleService.sysRoleUpdate(sysRole);
				rmap.put("code", 1);
				rmap.put("url", "/system/role.html?orgtype="+sysRole.getOrgType()+"&pageNum="+pageNum+"&pageSize="+pageSize);
				rmap.put("title", "修改角色");
				rmap.put("msg", "已经成功修改了该角色！");
				
				
				//redirect.openMessageToUrl(1, "修改角色", "已经成功修改了该角色！", "/system/role.html?orgtype="+sysRole.getOrgType()+"&pageNum="+pageNum+"&pageSize="+pageSize);
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，修改角色成功！登录IP为：" + IPUtil.getIpAddr(request) + "，角色rolecode为：" + sysRole.getRoleCode(), getLoginUserInfo(request, response).getUser());
			} catch (Exception e) {
				logger.error("修改角色失败", e);
				//redirect.openMessage(-1, "修改角色失败！");
				rmap.put("code", -1);
				/*map.put("url", "/login.html");
				map.put("title", "登录提示");*/
				rmap.put("msg", "修改角色失败！");
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，修改角色失败！登录IP为：" + IPUtil.getIpAddr(request) + "，角色rolecode为：" + sysRole.getRoleCode(), getLoginUserInfo(request, response).getUser());
				return rmap;
			}
		} else {
			rmap.put("code", -1);
			/*map.put("url", "/login.html");
			map.put("title", "登录提示");*/
			rmap.put("msg", "参数传递错误！");
			//redirect.openMessage(-1, "参数传递错误！");
		}
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE);
		return rmap;
	}

	@Authorization(name="User", value={"0,SysSystemRole_Edit"})
	@RequestMapping(value = "/system/roleedit.html")
	public ModelAndView LoadEdit(SysRole sysRole,@RequestParam("tagName")String tagName,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView result = new ModelAndView("/system/roleedit");
		SysRole _sysRole = sysRole;
		//createPage();
		if(StringUtils.isNotEmpty(_sysRole.getRoleCode())){
			_sysRole = sysRoleService.sysRoleById(_sysRole.getRoleCode());
			result.addObject("tagName", tagName);
			result.addObject("subflag", 2);
			result.addObject("sysRole",_sysRole);
		}
		load(result, request, response);
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE);
		return result;
	}

	@Authorization(name="User", value={"0,SysSystemRole_Permit"})
	@RequestMapping(value = "/system/rolepermit.html",method = RequestMethod.GET)
	public ModelAndView LoadPermit(String appcode,String orgtype,String rolecode,HttpServletRequest request, HttpServletResponse response) throws Exception {
		//baseWebPage.addTopJsFile(request.getContextPath() + "/static/js/jquery-1.8.3.min.js");
		//baseWebPage.addTopJsFile(request.getContextPath() + "/static/js/system/rolepermit.js");
		ModelAndView result = new ModelAndView("/system/rolepermit");
		//createPage();
		List<SysApp> appList=sysAppService.selectBySysApp(new SysApp());

		List<ViewModAct> modActList=new ArrayList<ViewModAct>();
		if(StringUtil.isNotEmpty(appcode)){
			modActList=viewModActService.selectByAppcode(appcode);
		}
		else if(!appList.isEmpty()){
			modActList=viewModActService.selectByAppcode(appList.get(0).getAppcode());
		}
		SysRole sysRole = sysRoleService.sysRoleById((rolecode == null ? "" : rolecode));
		result.addObject("appList",appList);
		result.addObject("modActList",modActList);
		result.addObject("appcode",appcode==null?"":appcode);
		result.addObject("orgtype",orgtype==null?"":orgtype);
		result.addObject("rolecode",rolecode==null?"":rolecode);
		if(sysRole != null) {
			result.addObject("rolename", sysRole.getRoleName());
		}
		load(result, request, response);
		return result;
	}

	@ResponseBody
	@Authorization(name="User", value={"1,SysSystemRole_Permit"})
	@RequestMapping(value = "/system/delRole.html", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public Map<String,Object> delRole(@RequestParam(required = false) String pageNum,
			@RequestParam(required = false) String pageSize,
			@RequestParam String rolecode,
			@RequestParam String orgtype,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> rmap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(rolecode)){
			rmap.put("code", -1);
			/*map.put("url", "/login.html");
			map.put("title", "登录提示");*/
			rmap.put("msg", "rolecode参数不能为空!");
			//redirect.openMessage(-1, "rolecode参数不能为空!");
			return rmap;
		}else{
			int num = sysRoleService.selectCountRoleUser(rolecode);
			if(num>0){
				rmap.put("code", -1);
				rmap.put("msg", "该角色下有用户,不能删除!");
				//redirect.openMessage(-1, "该角色下有用户,不能删除!");
				return rmap;
			}else{
				try {
					sysRoleService.delRole(rolecode);
					
					rmap.put("code", 1);
					rmap.put("url", "/system/role.html?orgtype="+orgtype+"&pageNum="+pageNum+"&pageSize="+pageSize);
					rmap.put("title", "删除角色");
					rmap.put("msg", "已经成功删除了该角色！");
					
					//redirect.openMessageToUrl(1, "删除角色", "已经成功删除了该角色！", "/system/role.html?orgtype="+orgtype+"&pageNum="+pageNum+"&pageSize="+pageSize);
					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，删除角色成功！登录IP为：" + IPUtil.getIpAddr(request) + "，角色rolecode为：" + rolecode, getLoginUserInfo(request, response).getUser());
				} catch (Exception e) {
					logger.error("删除角色失败！", e);
					rmap.put("code", -1);
					rmap.put("msg", "删除角色失败！");
					//redirect.openMessage(-1, "删除角色失败！");
					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，删除角色失败！登录IP为：" + IPUtil.getIpAddr(request) + "，角色rolecode为：" + rolecode, getLoginUserInfo(request, response).getUser());
					return rmap;
				}
			}
		}
		return rmap;
	}

	@ResponseBody
	@Authorization(name="User", value={"1,SysSystemRole_Disable"})
	@RequestMapping(value = "/system/updateRoleIsDel.html", produces = "text/html;charset=UTF-8")
	public Map<String,Object> updateRoleIsDel(@RequestParam(required = false) String pageNum,
			@RequestParam(required = false) String pageSize,
			@RequestParam String isdel,
			@RequestParam String orgtype,
			@RequestParam String rolecode,
			HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> rmap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(rolecode)||StringUtils.isEmpty(isdel)){
			rmap.put("code", -1);
			/*map.put("url", "/login.html");
			map.put("title", "登录提示");*/
			rmap.put("msg", "isDel或rolecode参数不能为空!");
			//redirect.openMessage(-1, "isDel或rolecode参数不能为空!");
			return rmap;
		}else{
			int isDel = 0;
			try {
				isDel = sysRoleService.updateRoleIsDel(isdel, rolecode);
				if(isDel==1){
					rmap.put("code", 1);
					rmap.put("url", "/system/role.html?orgtype="+orgtype+"&pageNum="+pageNum+"&pageSize="+pageSize);
					rmap.put("title", "禁用角色");
					rmap.put("msg", "已经成功禁用了该角色！");
					
					//redirect.openMessageToUrl(1, "禁用角色", "已经成功禁用了该角色！", "/system/role.html?orgtype="+orgtype+"&pageNum="+pageNum+"&pageSize="+pageSize);
					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，禁用角色成功！登录IP为：" + IPUtil.getIpAddr(request) + "，角色rolecode为：" + rolecode, getLoginUserInfo(request, response).getUser());
				} else {
					rmap.put("code", 1);
					rmap.put("url", "/system/role.html?orgtype="+orgtype+"&pageNum="+pageNum+"&pageSize="+pageSize);
					rmap.put("title", "启用角色");
					rmap.put("msg", "已经成功启用了该角色！");
					
					//redirect.openMessageToUrl(1, "启用角色", "已经成功启用了该角色！", "/system/role.html?orgtype="+orgtype+"&pageNum="+pageNum+"&pageSize="+pageSize);
					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，启用角色成功！登录IP为：" + IPUtil.getIpAddr(request) + "，角色rolecode为：" + rolecode, getLoginUserInfo(request, response).getUser());
				}
			} catch (Exception e) {
				String tip = isDel == 1 ? "禁用" : "启用";
				logger.error(tip + "该角色失败！", e);
				
				rmap.put("code", -1);
				/*map.put("url", "/login.html");
				map.put("title", "登录提示");*/
				rmap.put("msg", tip+"该角色失败！");
				//redirect.openMessage(-1, tip+"该角色失败！");
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，" + tip + "角色失败！登录IP为：" + IPUtil.getIpAddr(request) + "，角色rolecode为：" + rolecode, getLoginUserInfo(request, response).getUser());
				return rmap;
			}
		}
		return rmap;
	}

	@ResponseBody
	@RequestMapping(value = "system/getRolePermit", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getRolePermit(Short orgtype,String rolecode,String appcode)
	{
		List<SysRolePermit> rolePermitList=sysRolePermitService.selectByOrgAndRole(orgtype, rolecode,appcode);
		JSONArray rolePermitListJson=JSONArray.fromObject(rolePermitList);
		return rolePermitListJson.toString();
	}

	@ResponseBody
	@RequestMapping(value = "system/saveRolePermit.html", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String saveRolePermit(Short orgtype,String rolecode,String appcode,String json,HttpServletRequest request, HttpServletResponse response)
	{
		try {
			JSONArray rolePermitListJson=JSONArray.fromObject(json);
			List<SysRolePermit> rolePermitList=(List<SysRolePermit>)JSONArray.toCollection(rolePermitListJson, SysRolePermit.class);
			sysRolePermitService.del(new SysRolePermit(orgtype,rolecode,null,appcode,null,null,null));
			sysRolePermitService.saveList(rolePermitList);
			saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，角色授权成功！登录IP为：" + IPUtil.getIpAddr(request) + "，角色rolecode为：" + rolecode, getLoginUserInfo(request, response).getUser());
			return "{\"result\":\"1\"}";
		} catch (Exception e) {
			logger.error("角色授权失败！", e);
			saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，角色授权失败！登录IP为：" + IPUtil.getIpAddr(request) + "，角色rolecode为：" + rolecode, getLoginUserInfo(request, response).getUser());
		}
		return "{\"result\":\"0\"}";
	}

	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}
}
