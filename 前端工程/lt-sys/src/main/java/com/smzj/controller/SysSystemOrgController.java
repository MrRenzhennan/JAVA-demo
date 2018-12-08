package com.smzj.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.github.pagehelper.Page;
import com.smzj.annotation.Authorization;
import com.smzj.common.BaseController;
import com.smzj.common.BasePage;
import com.smzj.common.LogTypeConstat;
import com.smzj.model.SysDict;
import com.smzj.model.SysOrg;
import com.smzj.model.SysUser;
import com.smzj.model.SysUserOrg;
import com.smzj.model.WorkOrg;
import com.smzj.service.SysAppService;
import com.smzj.service.SysDictService;
import com.smzj.service.SysOrgService;
import com.smzj.service.SysUserOrgService;
import com.smzj.service.WorkOrgService;
import com.smzj.util.CookieUtil;
import com.smzj.util.IPUtil;
import com.smzj.util.LawlessUtil;
import com.smzj.util.PageUtil;
import com.smzj.util.SplitUserId;
import com.smzj.util.StringUtil;
import com.smzj.util.TextUtil;
import com.smzj.util.TreeHtmlUtil;

@Controller
@Scope("prototype")
public class SysSystemOrgController extends BaseController {
	private Logger logger = Logger.getLogger(SysSystemOrgController.class);

	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private SysUserOrgService sysUserOrgService;
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private SysAppService sysAppService;
	@Autowired
	private WorkOrgService workOrgService;

	private final String IMPERMISSABLE_STRING = "不允许的字符";
	private final String IMPERMISSABLE_NUM = "不是正整数";
	private final int STRING_LENGTH = 256;
	private final String STRING_ERROR = "字符长度超多最大限度";
	 public static final String USER_LOGIN = "LoginUserId";
	    public static final String USERINFO = "LoginUserInfo";

	
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


//	@Authorization(name = "User", value = { "0,SysSystemOrg" })
	@RequestMapping(value = "/system/org.html")
	public ModelAndView LoadIndex(@RequestParam(required = false) String pageNum, @RequestParam(required = false) String pageSize, @RequestParam(required = false) String orgcode, @RequestParam(required = false) String key, @RequestParam(required = false) String area, @RequestParam(required = false) String orgtype,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String _orgcode = orgcode;
		String _orgtype = orgtype;
		ModelAndView result = new ModelAndView("/system/org");
		Map<String, String> map = new HashMap<String, String>();
		_orgtype = StringUtils.isEmpty(_orgtype) ? "0" : _orgtype;
		String userid = getLoginUserId(request);
		SysUserOrg sysUserOrg = sysUserOrgService.findOrgByUserId2(userid);
		map.put("orgtype", _orgtype);// 需要传递的参数
		if (StringUtils.isEmpty(_orgcode)) {
			if ("0".equals(_orgtype)) {
				_orgcode = "1";
			} else if ("1".equals(_orgtype)) {
				_orgcode = "2";
			} else if ("2".equals(_orgtype)) {
				_orgcode = "3";
			} else if ("3".equals(_orgtype)) {
				_orgcode = "4";
			}
		}
		if (StringUtils.isNotEmpty(_orgcode)) {
			map.put("orgcode", _orgcode);
		}
		if (StringUtils.isNotEmpty(area)) {
			map.put("area", area);
		}
		result.addObject("orgcode", _orgcode);
		if (StringUtils.isNotEmpty(key)) {
			map.put("key", key.trim());
			result.addObject("key", key);
		} else {
			result.addObject("key", "");
		}
		//createPage();
		PageUtil pageUtil = new PageUtil(pageNum, pageSize);
		Page<SysOrg> pageList = sysOrgService.selectOrg4Page(map, pageUtil);
		if ("0".equals(_orgtype)) {
			map.put("parentcode", sysUserOrg == null ? "1" : sysUserOrg.getOrgCode());
		} else if ("1".equals(_orgtype)) {
			map.put("parentcode", sysUserOrg == null ? "2" : sysUserOrg.getOrgCode());
		} else if ("2".equals(_orgtype)) {
			map.put("parentcode", sysUserOrg == null ? "3" : sysUserOrg.getOrgCode());
		} else if ("3".equals(_orgtype)) {
			map.put("parentcode", sysUserOrg == null ? "4" : sysUserOrg.getOrgCode());
		}
		String orgList = sysOrgService.selectOrgForTree(map);
		result.addObject("pageList", pageList);
		result.addObject("orgtype", _orgtype);
		result.addObject("pageNum", pageUtil.getPageNum());
		result.addObject("pageSize", pageUtil.getPageSize());
		// 分页设值-start-
		setPageInfo(result, pageList, "/system/org.html", map);
		// 分页-end-
		//setTreeHtml();
		String treeScript = TreeHtmlUtil.getOrgTreeScript("/system/org.html", orgList, _orgcode);
		result.addObject("TreeScript", treeScript);
		result.addObject("subflag", 1);
		result.addObject("area", area == null ? "" : area);
		load(result, request, response);
		//loadBaseProperties(result, PageTopTypeEnum.B_PAGE);// 这句话必须放到最后
		return result;
	}
	
	

	@Authorization(name = "User", value = { "0,SysSystemOrg_Add", "0,SysSystemOrg_Edit" })
	@RequestMapping(value = "/system/orgedit.html")
	public ModelAndView LoadEdit(@RequestParam(required = false) String orgtype, @RequestParam(required = false) String orgcode, @RequestParam(required = false) String ocode, @RequestParam(required = false) String parentcode, String tag,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView result = new ModelAndView("/system/orgedit");
		if (TextUtil.isNotNull(orgcode) && TextUtil.isNull(ocode)) {
			SysOrg sysOrg = sysOrgService.findSysOrgByID(orgtype, orgcode);
			SysOrg sysOrg2 = sysOrgService.findSysOrgByID(orgtype, parentcode);
			if (sysOrg2 != null) {
				sysOrg.setParentname(sysOrg2.getOrgname());
			} else {
				if ("0".equals(orgtype)) {
					sysOrg.setParentname("市民之家");
				} else if ("1".equals(orgtype)) {
					sysOrg.setParentname("局委机构");
				} else if ("2".equals(orgtype)) {
					sysOrg.setParentname("银行部门");
				} else if ("3".equals(orgtype)) {
					sysOrg.setParentname("政民互动");
				}
			}
			result.addObject("subflag", 2);
			result.addObject("sysOrg", sysOrg);
			sysOrg.setParentcode(parentcode);
		} else {
			SysOrg sysOrg2 = sysOrgService.findSysOrgByID(orgtype, parentcode);
			SysOrg sysOrg = new SysOrg();
			sysOrg.setOrgtype(Short.parseShort(orgtype));
			sysOrg.setParentcode(parentcode);
			if (sysOrg2 != null) {
				sysOrg.setParentname(sysOrg2.getOrgname());
			} else {
				if (TextUtil.isNotNull(ocode) && ocode.length() > 1) {
					SysOrg org = sysOrgService.selectByOrgCode(ocode);
					if (org != null) {
						sysOrg.setParentcode(ocode);
						sysOrg.setParentname(org.getOrgname());
					}
				} else {
					if ("0".equals(orgtype)) {
						sysOrg.setParentname("市民之家");
					} else if ("1".equals(orgtype)) {
						sysOrg.setParentname("局委机构");
					} else if ("2".equals(orgtype)) {
						sysOrg.setParentname("银行部门");
					} else if ("3".equals(orgtype)) {
						sysOrg.setParentname("政民互动");
					}
				}
			}
			result.addObject("subflag", 1);
			sysOrg.setOrgcode("");
			result.addObject("sysOrg", sysOrg);
		}

		Map<String, Object> map=new HashMap<String, Object>();//机构分类的查询
		map.put("flag", "SYS_ORG_TYPE");
		map.put("isdisable", 0);
		List<SysDict> sysDicts = sysDictService.sysDicts(map);

		result.addObject("orgtype", orgtype);
		result.addObject("sysDicts", sysDicts);
		load(result, request, response);
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/system/saveOrg.html", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public Map<String,Object> saveOrg(SysOrg sysOrg, @RequestParam(required = false) String area,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> rmap = new HashMap<String, Object>();
		if (LawlessUtil.isLawless(sysOrg.getParentcode())) {
			
			rmap.put("code", -1);
			/*map.put("url", "/login.html");
			map.put("title", "登录提示");*/
			rmap.put("msg", IMPERMISSABLE_STRING);
			
			//redirect.openMessage(-1, IMPERMISSABLE_STRING);
			return rmap;
		} else if (TextUtil.isNull(sysOrg.getOrgname())) {
			rmap.put("code", -1);
			rmap.put("msg", "部门名称不能为空");
			//redirect.openMessage(-1, "部门名称不能为空");
			return rmap;
		} else if (LawlessUtil.isLawless(sysOrg.getOrgname())) {
			rmap.put("code", -1);
			rmap.put("msg", IMPERMISSABLE_STRING);
			//redirect.openMessage(-1, IMPERMISSABLE_STRING);
			return rmap;
		} else if (TextUtil.isNotNull(sysOrg.getOrgmemo()) && sysOrg.getOrgmemo().length() > STRING_LENGTH) {
			rmap.put("code", -1);
			rmap.put("msg", STRING_ERROR);
			//redirect.openMessage(-1, STRING_ERROR);
			return rmap;
		} else if (LawlessUtil.isLawless(sysOrg.getOrgmemo())) {
			rmap.put("code", -1);
			rmap.put("msg", IMPERMISSABLE_STRING);
			//redirect.openMessage(-1, IMPERMISSABLE_STRING);
			return rmap;
		}
		String userid = getLoginUserId(request);
		SysUser sysUser = getLoginUserInfo(request, response).getUser();
		if (TextUtil.isNull(sysOrg.getParentcode())) {
			sysOrg.setParentcode("0");
		}
		if (sysOrg.getSortcode() == null) {
			sysOrg.setSortcode((short) 0);
		}
		if (area != null && area.length() > 0) {
			String[] areas = area.split(",");
			if (areas.length > 0) {
				String areaname = "";
				for (int i = 0; i < areas.length; i++) {
					areaname = areas[i];
					if ("istel".equals(areaname)) {
						sysOrg.setIstel(1);
					} else if ("isemail".equals(areaname)) {
						sysOrg.setIsemail(1);
					} else if ("islink".equals(areaname)) {
						sysOrg.setIslink(1);
					} else if("ismsg".equals(areaname)) {
						sysOrg.setIsmsg(1);
					}
				}
			}
		}
		int subFlag = Integer.parseInt(request.getParameter("subflag"));
		if (subFlag == 1) {
			try {
				String orgname = sysOrg.getOrgname();
				orgname = (orgname == null ? "" : orgname.trim());
				Map<String, Object> map = new HashMap<String, Object> ();
				map.put("orgname", orgname);
				map.put("orgtype", sysOrg.getOrgtype());
				Map<String, Object> _sysOrg = sysAppService.selectSysOrgByMap(map);
				if(_sysOrg != null) {
					if (sysUser.getOrgtype() != 3) {
						rmap.put("code", -2);
						rmap.put("url", "/system/org.html?orgtype=" + sysOrg.getOrgtype());
						rmap.put("title", "添加组织机构");
						rmap.put("msg", "当前组织机构名称已存在，请重新添加！");
						//redirect.openMessageToUrl(-1, "添加组织机构", "当前组织机构名称已存在，请重新添加！", "/system/org.html?orgtype=" + sysOrg.getOrgtype());
					} else {
						rmap.put("code", -2);
						rmap.put("url", "/system/interaction.html");
						rmap.put("title", "添加组织机构");
						rmap.put("msg", "当前组织机构名称已存在，请重新添加！");
						//redirect.openMessageToUrl(-1, "添加组织机构", "当前组织机构名称已存在，请重新添加！", "/system/interaction.html");
					}
					return rmap;
				}
				sysOrg.setAddtime(new Timestamp(new Date().getTime()));
				sysOrgService.save(sysOrg);
				if ((short) 1 == sysOrg.getOrgtype() && sysOrg.getParentcode().length() > 2) {
					WorkOrg workOrg = new WorkOrg();
					workOrg.setOrgname(sysOrg.getOrgname());
					workOrg.setOrgcode(sysOrg.getOrgcode());
					workOrg.setOrgscore(0l);
					workOrg.setIsdisable((short) 0);
					workOrg.setIsdel((short) 0);
					workOrg.setOrgscorenum(0);
					workOrg.setSortcode(0l);
					workOrg.setOrginfo("");
					workOrg.setOrgurl("");
					workOrg.setOrgaddress("");
					workOrg.setOrgtel("");
					workOrg.setOrgemail("");
					workOrg.setImgurl("");
					workOrgService.save(workOrg);
				}
				if (sysUser.getOrgtype() != 3) {
					rmap.put("code", 1);
					rmap.put("url", "/system/org.html?orgtype=" + sysOrg.getOrgtype());
					rmap.put("title", "添加组织机构");
					rmap.put("msg", "已经成功添加了该组织机构部门！");
					
					//redirect.openMessageToUrl(1, "添加组织机构", "已经成功添加了该组织机构部门！", "/system/org.html?orgtype=" + sysOrg.getOrgtype());
				} else {
					rmap.put("code", 1);
					rmap.put("url", "/system/interaction.html");
					rmap.put("title", "添加组织机构");
					rmap.put("msg", "已经成功添加了该组织机构部门！");
					
					//redirect.openMessageToUrl(1, "添加组织机构", "已经成功添加了该组织机构部门！", "/system/interaction.html");
				}
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，添加组织机构部门成功！登录IP为：" + IPUtil.getIpAddr(request) + "，部门orgcode为：" + sysOrg.getOrgcode(), getLoginUserInfo(request, response).getUser());
			} catch (Exception e) {
				logger.error("添加组织结构失败！", e);
				rmap.put("code", -1);
				/*rmap.put("url", "/system/interaction.html");
				rmap.put("title", "添加组织机构");*/
				rmap.put("msg", "添加该组织机构失败！");
				//redirect.openMessage(-1, "添加该组织机构失败！");
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，添加组织机构部门失败！登录IP为：" + IPUtil.getIpAddr(request), getLoginUserInfo(request, response).getUser());
				return rmap;
			}
		} else if (subFlag == 2) {
			try {
				rmap.put("code", -1);
				/*rmap.put("url", "/system/interaction.html");
				rmap.put("title", "添加组织机构");*/
				rmap.put("msg", "部门修改成功");
				//redirect.openMessage(-1, "部门修改成功");
				sysOrgService.update(sysOrg);
				if (sysUser.getOrgtype() != 3) {
					rmap.put("code", 1);
					rmap.put("url", "/system/org.html?orgtype=" + sysOrg.getOrgtype());
					rmap.put("title", "更新");
					rmap.put("msg", "已经成功修改了该组织机构部门！");
					//redirect.openMessageToUrl(1, "更新", "已经成功修改了该组织机构部门！", "/system/org.html?orgtype=" + sysOrg.getOrgtype());
				} else {
					rmap.put("code", 1);
					rmap.put("url", "/system/interaction.html");
					rmap.put("title", "更新");
					rmap.put("msg", "已经成功修改了该组织机构部门！");
					//redirect.openMessageToUrl(1, "更新", "已经成功修改了该组织机构部门！", "/system/interaction.html");
				}
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，修改组织机构部门成功！登录IP为：" + IPUtil.getIpAddr(request) + "，部门orgcode为：" + sysOrg.getOrgcode(), getLoginUserInfo(request, response).getUser());
			} catch (Exception e) {
				logger.error("修改组织机构失败！", e);
				rmap.put("code", -1);
				/*rmap.put("url", "/system/interaction.html");
				rmap.put("title", "添加组织机构");*/
				rmap.put("msg", "修改该组织机构部门失败！");
				//redirect.openMessage(-1, "修改该组织机构部门失败！");
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，修改组织机构部门失败！登录IP为：" + IPUtil.getIpAddr(request) + "，部门orgcode为：" + sysOrg.getOrgcode(), getLoginUserInfo(request, response).getUser());
				return rmap;
			}
		} else {
			rmap.put("code", -1);
			/*rmap.put("url", "/system/interaction.html");
			rmap.put("title", "添加组织机构");*/
			rmap.put("msg", "参数传递错误！");
			//redirect.openMessage(-1, "参数传递错误！");
		}
		return rmap;
	}

	@ResponseBody
	@Authorization(name = "User", value = { "2,SysSystemOrg_Disable" })
	@RequestMapping(value = "/system/enableOrDisableOrg.html", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public Map<String,Object> enableOrDisableOrg(SysOrg sysOrg, String pageNum, String pageSize, String key, String tag,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		String userid = CookieUtil.getCookieValue(request.getCookies(), USER_LOGIN);
		if (StringUtils.isEmpty(sysOrg.getOrgcode())) {
			//redirect.openMessage(-1, "orgcode参数不能为空!");
			map.put("code", -1);
			map.put("msg", "orgcode参数不能为空!");
			return map;
		}
		String isDel = String.valueOf(request.getParameter("isdel"));
		List<SysOrg> sysOrgList = sysOrgService.selectSysOrgByParentCode(sysOrg.getOrgcode());
		if (sysOrgList != null && !sysOrgList.isEmpty()) {
			if ("1".equals(isDel)) {
				//redirect.openMessage(-1, "该组织机构下有子机构，禁用该组织机构失败！");
				map.put("code", -1);
				map.put("msg", "该组织机构下有子机构，禁用该组织机构失败！");
				return map;
			}
		}
		int statusCode = sysOrgService.updateSysOrgIsDel(sysOrg);
		String tip = "1".equals(isDel) ? "禁用" : "启用";
		if (statusCode == 1) {
			if (StringUtils.isNotEmpty(tag) && "1".equals(tag)) {
				map.put("code", 1);
				map.put("url", toListUrl(sysOrg, pageNum, pageSize, key));
				map.put("title", "更新");
				map.put("msg", "已经成功" + tip + "了该组织机构部门!");
				//redirect.openMessageToUrl(1, "更新", "已经成功" + tip + "了该组织机构部门!", toListUrl(sysOrg, pageNum, pageSize, key));
			} else {
				map.put("code", 1);
				map.put("url", toInteractionListUrl(sysOrg, pageNum, pageSize, key));
				map.put("title", "更新");
				map.put("msg", "已经成功" + tip + "了该组织机构部门!");
				//redirect.openMessageToUrl(1, "更新", "已经成功" + tip + "了该组织机构部门!", toInteractionListUrl(sysOrg, pageNum, pageSize, key));
			}
			saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser() + "，" + tip + "组织机构部门成功！登录IP为：" + IPUtil.getIpAddr(request) + "，部门orgcode为：" + getLoginUserInfo(request, response).getUser(),null);
			return map;
		} else {
			map.put("code", -1);
			map.put("msg", tip + "该组织机构部门失败!");
			//redirect.openMessage(-1, tip + "该组织机构部门失败!");
			saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser() + "，" + tip + "组织机构部门失败！登录IP为：" + IPUtil.getIpAddr(request) + "，部门orgcode为：" + getLoginUserInfo(request, response).getUser(), null);
			return map;
		}
	}
	@ResponseBody
	@Authorization(name = "User", value = { "1,SysSystemOrg_Del" })
	@RequestMapping(value = "/system/deleteOrg.html", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public Map<String,Object> deleteOrg(SysOrg sysOrg, String pageNum, String pageSize, String key, String tag,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(sysOrg.getOrgcode())) {
			map.put("code", -1);
			map.put("msg", "orgcode参数不能为空!");
			//redirect.openMessage(-1, "orgcode参数不能为空!");
			return map;
		}
		List<SysUserOrg> sysUserOrgList = sysUserOrgService.findByOrgCode(sysOrg.getOrgcode());
		if (sysUserOrgList != null && !sysUserOrgList.isEmpty()) {
			map.put("code", -1);
			map.put("msg", "提示：该部门下存在用户，不允许删除!");
			//redirect.openMessage(-1, "提示：该部门下存在用户，不允许删除!");
			return map;
		} else {
			List<SysOrg> sysOrgList = sysOrgService.findChildOrgsByTypeAndParentCode(sysOrg);
			if (sysOrgList != null && !sysOrgList.isEmpty()) {
				map.put("code", -1);
				map.put("msg", "提示：该部门下存在子部门，不允许删除!");
				//redirect.openMessage(-1, "提示：该部门下存在子部门，不允许删除!");
				return map;
			} else {
				int statusCode = sysOrgService.deleteByOrgCode(sysOrg);
				if (statusCode == 1) {
					if (StringUtils.isNotEmpty(tag) && "1".equals(tag)) {
						map.put("code", 1);
						map.put("url", toListUrl(sysOrg, pageNum, pageSize, key));
						map.put("title", "更新");
						map.put("msg", "已经成功删除了该组织机构部门!");
						//redirect.openMessageToUrl(1, "更新", "已经成功删除了该组织机构部门!", toListUrl(sysOrg, pageNum, pageSize, key));
					} else {
						map.put("code", 1);
						map.put("url", toInteractionListUrl(sysOrg, pageNum, pageSize, key));
						map.put("title", "更新");
						map.put("msg", "已经成功删除了该组织机构部门!");
						//redirect.openMessageToUrl(1, "更新", "已经成功删除了该组织机构部门!", toInteractionListUrl(sysOrg, pageNum, pageSize, key));
					}
					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，删除组织结构部门成功！登录IP为：" + IPUtil.getIpAddr(request) + "，部门orgcode为：" + sysOrg.getOrgcode(), getLoginUserInfo(request, response).getUser());
					return map;
				} else {
					map.put("code", -1);
					map.put("msg", "删除该组织机构部门失败!");
					//redirect.openMessage(-1, "删除该组织机构部门失败!");
					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，删除组织机构部门失败！登录IP为：" + IPUtil.getIpAddr(request) + "，部门orgcode为：" + sysOrg.getOrgcode(), getLoginUserInfo(request, response).getUser());
					return map;
				}
			}
		}
	}

	private String toListUrl(SysOrg sysOrg, String pageNum, String pageSize, String key) {
		StringBuilder url = new StringBuilder("/system/org.html");
		url.append("?orgtype=" + sysOrg.getOrgtype());
		if (StringUtils.isNotEmpty(pageNum)) {
			url.append("&pageNum=" + pageNum);
		}
		if (StringUtils.isNotEmpty(pageSize)) {
			url.append("&pageSize=" + pageSize);
		}
		if (StringUtils.isNotEmpty(key)) {
			url.append("&key=" + key);
		}
		if (StringUtils.isNotEmpty(sysOrg.getParentcode()) && !"-1".equals(sysOrg.getParentcode())) {
			url.append("&orgcode=" + sysOrg.getParentcode());
		}
		return url.toString();
	}

	private String toInteractionListUrl(SysOrg sysOrg, String pageNum, String pageSize, String key) {
		StringBuilder url = new StringBuilder("/system/interaction.html");
		url.append("?orgtype=" + sysOrg.getOrgtype());
		if (StringUtils.isNotEmpty(pageNum)) {
			url.append("&pageNum=" + pageNum);
		}
		if (StringUtils.isNotEmpty(pageSize)) {
			url.append("&pageSize=" + pageSize);
		}
		if (StringUtils.isNotEmpty(key)) {
			url.append("&key=" + key);
		}
		if (StringUtils.isNotEmpty(sysOrg.getParentcode()) && !"-1".equals(sysOrg.getParentcode())) {
			url.append("&orgcode=" + sysOrg.getParentcode());
		}
		return url.toString();
	}

}
