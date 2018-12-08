package com.smzj.controller;

import java.util.ArrayList;
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

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.abel533.sql.SqlMapper;
import com.github.pagehelper.Page;
import com.smzj.annotation.Authorization;
import com.smzj.common.BaseController;
import com.smzj.common.BasePage;
import com.smzj.common.LogTypeConstat;
import com.smzj.common.LoginUserInfo;
import com.smzj.mapper.SysOrgMapper;
import com.smzj.model.SysOrg;
import com.smzj.model.SysUser;
import com.smzj.model.SysUserOrg;
import com.smzj.model.SysUserRole;
import com.smzj.service.SysOrgService;
import com.smzj.service.SysUserOrgService;
import com.smzj.service.SysUserRoleService;
import com.smzj.service.SysUserService;
import com.smzj.util.CookieUtil;
import com.smzj.util.FormCheckUtil;
import com.smzj.util.IPUtil;
import com.smzj.util.LawlessUtil;
import com.smzj.util.MD5Util;
import com.smzj.util.PageUtil;
import com.smzj.util.PassWordCreate;
import com.smzj.util.SplitUserId;
import com.smzj.util.StringUtil;
import com.smzj.util.TextUtil;
import com.smzj.util.TreeHtmlUtil;

@Controller
@Scope("prototype")
public class SysSystemUserController extends BaseController {
	private Logger logger = Logger.getLogger(SysSystemUserController.class);
	@Autowired
	private SysUserService sysUserService;
	private final String IMPERMISSABLE_STRING ="包含不允许的字符";
	private final int STRING_LENGTH=64;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private SysUserOrgService sysUserOrgService;
	@Autowired
	private SysUserService sysService;
	@Autowired
	private SqlMapper sqlMapper;
	@Autowired
	private SysOrgMapper sysorgMapper;

	
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
	
	private void load1(ModelAndView result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BasePage basePage = new BasePage();
		basePage.setPageTitle("系统管理");
		basePage.setPageId("Sys");
		basePage.setAppFunCode("Sys", "");
		result.addObject("basePage", basePage);
		SysUser sysUser1 = getLoginUserInfo(request, response).getUser();
		getMenuHtml(result,  sysUser1, "Sys");
		loadProperties(result, request, response);
	}



//	@Authorization(name="User",value={"0,SysSystemUser"})
	@RequestMapping(value = "/system/user.html")
	public ModelAndView LoadIndex(@RequestParam(required = false) String pageNum,
	@RequestParam(required = false) String pageSize,
	@RequestParam(required = false) String orgcode,
	@RequestParam(required = false) String truename,
	@RequestParam(required = false) String orgtype,
	@RequestParam(required = false) String search, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String _orgtype = orgtype;
		String _orgcode = orgcode;
		ModelAndView result = new ModelAndView("/system/user");
		Map<String, String> map = new HashMap<String, String>();
		_orgtype = StringUtils.isEmpty(_orgtype)?"0":_orgtype;
		String userid =getLoginUserId(request);
		if(com.smzj.util.StringUtil.isNotNull(userid)){//判断非空解密 
			userid =SplitUserId.getUserId(userid);
		}
		SysUser sysUser=sysService.getSysUser(userid);
		map.put("orgtype", _orgtype);//需要传递的参数
		if(StringUtils.isEmpty(search)){
			if(StringUtils.isEmpty(_orgcode)){
				if("0".equals(_orgtype)){
					_orgcode = "1";
				}else if("1".equals(_orgtype)){
					_orgcode = "2";
				}else{
					_orgcode = "3";
				}
			}
		}else{
			_orgcode = "";
			result.addObject("search", search);
			map.put("search", search);
		}
		if(StringUtils.isNotEmpty(_orgcode)){
			map.put("orgcode", _orgcode);
		}

		if(StringUtils.isNotEmpty(truename)){
			map.put("truename", truename.trim());
			result.addObject("truename", truename);
		}else{
			result.addObject("truename", "");
		}
		String orgList = sysOrgService.selectOrgForTree(map);
		PageUtil pageUtil = new PageUtil(pageNum,pageSize);

		//--------------add by wrj 2016-6-2---------------------
		String isSA="0"; //是否是系统管理员 0否 1是
		List<SysUserRole> list=sysUserRoleService.selectRoleByUserid1(userid);
		for(SysUserRole sysUserRole:list){
			if("502089D7B1434E25BDAD0D3700D92CDF".equals(sysUserRole.getRolecode())||"329B61E5CE0E0D8BE053DD0010AC28FF".equals(sysUserRole.getRolecode())){
				isSA="1";
				break;
			}
		}
		if("0".equals(isSA)){
			Example example = new Example(SysUserOrg.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("userId", userid);
			List<SysUserOrg> userOrgList = sysUserOrgService.selectByExample(example);
			if(userOrgList!=null && userOrgList.size()>0){
				_orgtype=userOrgList.get(0).getOrgType();
				_orgcode=userOrgList.get(0).getOrgCode();
				map.put("orgtype", _orgtype);
				map.put("orgcode", _orgcode);
			}
		}
		map.put("isSA", isSA);
		result.addObject("isSA", isSA);
		//-------------------------------------------------------

		Page<SysUser> pageList = sysUserService.selectUser4Page(map,pageUtil);
		String isshow="";
		List<SysOrg> solist = sysOrgService.selectSysOrgByParentCode(_orgcode);
		if(solist.size()>0){
			isshow = "no";
		}else{
			isshow = "is";
		}

		result.addObject("orgcode", _orgcode);
		result.addObject("orgtype1",sysUser.getOrgtype());
		result.addObject("isshow", isshow);
		result.addObject("pageList",pageList);
		result.addObject("orgtype", _orgtype);
		result.addObject("pageNum", pageUtil.getPageNum());
		result.addObject("pageSize", pageUtil.getPageSize());
		result.addObject("truename", truename);
		//分页设值-start-
		setPageInfo(result,pageList,"/system/user.html",map);
		//分页-end-
		if("1".equals(isSA)) {
			//setTreeHtml();
			String treeScript = TreeHtmlUtil.getOrgTreeScript("/system/user.html",orgList, _orgcode);
			result.addObject("TreeScript", treeScript);
		}
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE);
	    load(result, request, response);
		return result;
	}

	@Authorization(name="User",value={"0,SysSystemUser"})
	@RequestMapping(value = "/system/orguser.html")
	public ModelAndView LoadIndexOrg(@RequestParam(required = false) String pageNum,
	@RequestParam(required = false) String pageSize,
	@RequestParam(required = false) String orgcode,
	@RequestParam(required = false) String truename,
	@RequestParam(required = false) String orgtype,
	@RequestParam(required = false) String search, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String _orgtype = "1";
		String _orgcode = orgcode;
		ModelAndView result = new ModelAndView("/system/orguser");
		Map<String, String> map = new HashMap<String, String>();
		_orgtype = StringUtils.isEmpty(_orgtype)?"0":_orgtype;
		/**
		 * pk验证
		 */
		String userid = getLoginUserId(request);
		if(com.smzj.util.StringUtil.isNotNull(userid)){//解密
			userid =SplitUserId.getUserId(userid);
		}
		SysUser sysUser=sysService.getSysUser( userid);
		map.put("orgtype", _orgtype);//需要传递的参数
		if(StringUtils.isEmpty(search)){
			if(StringUtils.isEmpty(_orgcode)){
				if("0".equals(_orgtype)){
					_orgcode = "1";
				}else if("1".equals(_orgtype)){
					_orgcode = "2";
				}else{
					_orgcode = "3";
				}
			}
		}else{
			_orgcode = "";
			result.addObject("search", search);
			map.put("search", search);
		}
		if(StringUtils.isNotEmpty(_orgcode)){
			map.put("orgcode", _orgcode);
		}

		if(StringUtils.isNotEmpty(truename)){
			map.put("truename", truename.trim());
			result.addObject("truename", truename);
		}else{
			result.addObject("truename", "");
		}
		//createPage();
		String orgList = sysOrgService.selectOrgForTree(map);
		PageUtil pageUtil = new PageUtil(pageNum,pageSize);

		//--------------add by wrj 2016-6-2---------------------
		String isSA="0"; //是否是系统管理员 0否 1是
//		String userid = CookieUtil.getCookieValue(request.getCookies(), "LoginUserId");
		List<SysUserRole> list=sysUserRoleService.selectRoleByUserid1(userid);
		for(SysUserRole sysUserRole:list){
			if("502089D7B1434E25BDAD0D3700D92CDF".equals(sysUserRole.getRolecode())){
				isSA="1";
				break;
			}
		}
		isSA="1";
		if("0".equals(isSA)){
			Example example = new Example(SysUserOrg.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("userId", userid);
			List<SysUserOrg> userOrgList = sysUserOrgService.selectByExample(example);
			if(userOrgList!=null && userOrgList.size()>0){
				_orgtype=userOrgList.get(0).getOrgType();
				_orgcode=userOrgList.get(0).getOrgCode();
				map.put("orgtype", _orgtype);
				map.put("orgcode", _orgcode);
			}
		}
		map.put("isSA", isSA);
		result.addObject("isSA", isSA);
		//-------------------------------------------------------

		Page<SysUser> pageList = sysUserService.selectUser4Page(map,pageUtil);
		String isshow="";
		List<SysOrg> solist = sysOrgService.selectSysOrgByParentCode(_orgcode);
		if(solist.size()>0){
			isshow = "no";
		}else{
			isshow = "is";
		}

		result.addObject("orgcode", _orgcode);
		result.addObject("orgtype1",sysUser.getOrgtype());
		result.addObject("isshow", isshow);
		result.addObject("pageList",pageList);
		result.addObject("orgtype", _orgtype);
		result.addObject("pageNum", pageUtil.getPageNum());
		result.addObject("pageSize", pageUtil.getPageSize());
		result.addObject("truename", truename);
		//分页设值-start-
		setPageInfo(result,pageList,"/system/user.html",map);
		//分页-end-
		//setTreeHtml();
		String treeScript = TreeHtmlUtil.getOrgTreeScript("/system/orguser.html",orgList, _orgcode);
		result.addObject("TreeScript", treeScript);
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE);
		load(result, request, response);
		return result;
	}

	/**
	 * orgtype=3的查询操作
	 * @param pageNum
	 * @param pageSize
	 * @param orgcode
	 * @param truename
	 * @param orgtype
	 * @param search
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/system/userlist.html")
	public ModelAndView LoadAddUser(@RequestParam(required = false) String pageNum,
	@RequestParam(required = false) String pageSize,
	@RequestParam(required = false) String orgcode,
	@RequestParam(required = false) String truename,
	@RequestParam(required = false) String orgtype,
	@RequestParam(required = false) String area,
	@RequestParam(required = false) String flag,
	@RequestParam(required = false) String search,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String _orgtype = orgtype;
		String _orgcode = orgcode;
		String userid = getLoginUserId(request);
		if(com.smzj.util.StringUtil.isNotNull(userid)){
			userid =SplitUserId.getUserId(userid);
		}
		ModelAndView result = null;
		SysUserOrg sysUserOrg = null;
		String URL=null;
		if(StringUtils.isNotEmpty(flag) && "2".equals(flag)){
			 result = new ModelAndView("/system/userlist2");
			 URL="/system/userlist.html?flag="+flag+"&";
		}else{
			 result = new ModelAndView("/system/userlist");
			 URL="/system/userlist.html?flag="+""+"&";
		}
		SysUser sysUser=sysService.getSysUser( userid);
		Map<String, String> map = new HashMap<String, String>();
		_orgtype = StringUtils.isEmpty(_orgtype)?"3":_orgtype;
		map.put("orgtype", _orgtype);//需要传递的参数
		if(StringUtils.isEmpty(search)){
			if(StringUtils.isEmpty(_orgcode)){
				if("0".equals(_orgtype)){
					_orgcode = "1";
				}else if("1".equals(_orgtype)){
					_orgcode = "2";
				}else if("3".equals(_orgtype)){
					if(StringUtils.isEmpty(_orgcode)){
						sysUserOrg=sysUserOrgService.findOrgByUserId2(userid);
						if(sysUserOrg!=null){
							if("0".equals(sysUserOrg.getOrgType())){
								_orgcode = "4";
							}else{
								_orgcode=sysUserOrg.getOrgCode();
							}
						}
					}
					if(StringUtils.isEmpty(_orgcode)){
						_orgcode = "4";
					}
				}
			}
		}else{
			if(StringUtils.isEmpty(_orgcode)){
				sysUserOrg=sysUserOrgService.findOrgByUserId2(userid);
				if(sysUserOrg!=null){
					if("0".equals(sysUserOrg.getOrgType())){
						_orgcode = "4";
					}else{
						_orgcode=sysUserOrg.getOrgCode();
					}
				}
			}
			if(StringUtils.isEmpty(_orgcode)){
				_orgcode = "4";
			}
			result.addObject("search", search);
			map.put("search", search);
		}
		if(StringUtils.isNotEmpty(_orgcode)){
			map.put("orgcode", _orgcode);
		}
		if (StringUtils.isNotEmpty(area)) {
			map.put("area", area);
		}

		if(StringUtils.isNotEmpty(truename)){
			map.put("truename", truename.trim());
			result.addObject("truename", truename);
		}else{
			result.addObject("truename", "");
		}
		String orgCodeTree = null;
		SysUserOrg sysUserOrg2=sysUserOrgService.findOrgByUserId2(userid);
		if(sysUserOrg2!=null){
			if(StringUtils.isNotEmpty(sysUserOrg2.getOrgType())){
				if("0".equals(sysUserOrg2.getOrgType())){
					orgCodeTree="4";//查询全部
				}else{
					orgCodeTree=StringUtils.isEmpty(sysUserOrg2.getOrgCode())?"4":sysUserOrg2.getOrgCode();//查询树结构是始终用当前登录用户的orgcode
				}
			}else{
				orgCodeTree="4";//查询全部
			}
		}else{
			orgCodeTree="4";//admin查询全部
		}
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("orgtype", _orgtype);//需要传递的参数
		map1.put("orgcode", orgCodeTree);//需要传递的参数
		map1.put("area", map.get("area"));
		String orgList = sysOrgService.selectInteractionOrgForTree2(map1);

		PageUtil pageUtil = new PageUtil(pageNum,pageSize);//查询列表默认使用当前用户的orgcode  当点击相应的org时则用相应的orgcode去查询
		Page<SysUser> pageList = sysUserService.selectUser5Page(map,pageUtil);

		String isshow="";
		List<SysOrg> solist = sysOrgService.selectSysOrgByParentCode(_orgcode);
		if(solist.size()>0){
			isshow = "no";
		}else{
			isshow = "is";
		}
		map.put("flag", flag);
		result.addObject("orgcode", _orgcode);
		result.addObject("isshow", isshow);
		result.addObject("pageList",pageList);
		result.addObject("orgtype", _orgtype);
		result.addObject("orgtype1", sysUser.getOrgtype());
		result.addObject("pageNum", pageUtil.getPageNum());
		result.addObject("pageSize", pageUtil.getPageSize());
		result.addObject("truename", truename);
		result.addObject("flag", flag);
		result.addObject("area", StringUtils.isNotEmpty(area)?area:"");
		//分页设值-start-
		setPageInfo(result,pageList,URL,map);
		//分页-end-
		//setTreeHtml();
		String treeScript = TreeHtmlUtil.getOrgTreeScript(URL,orgList, _orgcode);
		result.addObject("TreeScript", treeScript);
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE);
		load(result, request, response);
		return result;
	}


	
	@RequestMapping(value = "/system/selectorg.html")
	public ModelAndView selectOrg(@RequestParam(required = false) String pageNum,
			@RequestParam(required = false) String pageSize,
			@RequestParam(required = false) String orgcode,
			@RequestParam(required = false) String key,
			@RequestParam(required = false) String area,
			@RequestParam(required = false) String orgtype,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String _orgcode = orgcode;
		String _orgtype = orgtype;
		ModelAndView result = new ModelAndView("/system/selectorg");
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
			map.put("key", key);
			result.addObject("key", key);
		} else {
			result.addObject("key", "");
		}
		//createLayerPage();
		PageUtil pageUtil = new PageUtil(pageNum, pageSize);
		Page<SysOrg> pageList = sysOrgService.selectOrg4PageIncludeMy(map, pageUtil);
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
		setPageInfo(result, pageList, "/system/selectorg.html", map);
		// 分页-end-
		//setTreeHtml();
		String treeScript = TreeHtmlUtil.getOrgTreeScript("/system/selectorg.html", orgList, _orgcode);
		result.addObject("TreeScript", treeScript);
		result.addObject("subflag", 1);
		result.addObject("area", area == null ? "" : area);
		//loadBaseProperties(result, PageTopTypeEnum.PAGE_LAYER);// 这句话必须放到最后
		load(result, request, response);
		return result;
	}

	@Authorization(name="User",value={"0,SysSystemUser_Add","0,SysSystemUser_Edit"})
	@RequestMapping(value = "/system/useredit.html")
	public ModelAndView LoadEdit(
			@RequestParam(required = false, defaultValue="0") Integer orgtype,
			@RequestParam(required = false) String orgcode,
			@RequestParam(required = false) String flag,
			@RequestParam(required = false) String userid,HttpServletRequest request, HttpServletResponse response) throws Exception {
		int _orgtype = orgtype;
		String _orgcode = orgcode;
		ModelAndView result = new ModelAndView("/system/useredit");
		//createPage();
		SysUser sysUser1;
		if(TextUtil.isNotNull(userid)){//编辑
			sysUser1=sysUserService.findSysUserByID(userid);
			result.addObject("subflag", 2);
			result.addObject("sysUser1", sysUser1);
			result.addObject("isAdd", false);
			if(sysUser1!=null&&StringUtil.isNotNull(sysUser1.getOrgcode())){
				String sql="select orgname from sys_org so where so.orgcode='"+sysUser1.getOrgcode()+"'";
				List<Map<String,Object>> listmap=sqlMapper.selectList(sql);
				if(listmap!=null&&listmap.size()>0){
					String orgname=listmap.get(0).get("ORGNAME").toString();
					result.addObject("orgname", orgname);
				}
			}
		}else {//新增
			if(TextUtil.isNull(_orgcode)){
				_orgcode="1";
			}
			sysUser1=new SysUser();
			sysUser1.setOrgtype(_orgtype);
			sysUser1.setOrgcode(_orgcode);
			sysUser1.setIsDel(0);
			sysUser1.setIsDrop(0);
			result.addObject("subflag", 1);
			result.addObject("flag", flag);
			result.addObject("sysUser1", sysUser1);
			result.addObject("isAdd", true);
		}
		load1(result, request, response);
		return result;
	}


	/**
	 * orgtype=3时添加和编辑操作员
	 * @param orgtype
	 * @param orgcode
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	@Authorization(name="User",value={"0,SysSystemUser_Add","0,SysSystemUser_Edit"})
	@RequestMapping(value = "/system/useroperation.html")
	public ModelAndView LoadAddOrEdit(
			@RequestParam(required = false, defaultValue="0") Integer orgtype,
			@RequestParam(required = false) String orgcode,
			@RequestParam(required = false) String flag,
			@RequestParam(required = false) String userid,HttpServletRequest request, HttpServletResponse response) throws Exception {
		int _orgtype = orgtype;
		String _orgcode = orgcode;
		ModelAndView result = new ModelAndView("/system/useroperation");
		SysUser sysUser1;
		if(TextUtil.isNotNull(userid)){//编辑
			sysUser1=sysUserService.findSysUserByID(userid);
			result.addObject("subflag", 2);
			result.addObject("sysUser1", sysUser1);
			result.addObject("isAdd", false);
			result.addObject("flag", flag);
			if(sysUser1!=null&&StringUtil.isNotNull(sysUser1.getOrgcode())){
				String sql="select orgname from sys_org so where so.orgcode='"+sysUser1.getOrgcode()+"'";
				List<Map<String,Object>> listmap=sqlMapper.selectList(sql);
				if(listmap!=null&&listmap.size()>0){
					String orgname=listmap.get(0).get("ORGNAME").toString();
					result.addObject("orgname", orgname);
				}
			}
		}else {//新增
			if(TextUtil.isNull(_orgcode)){
				_orgcode="4";
			}
			sysUser1=new SysUser();
			sysUser1.setOrgtype(_orgtype);
			sysUser1.setOrgcode(_orgcode);
			sysUser1.setIsDel(0);
			sysUser1.setIsDrop(0);
			result.addObject("subflag", 1);
			result.addObject("flag", flag);
			result.addObject("sysUser1", sysUser1);
			result.addObject("isAdd", true);
		}
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE);
		load(result, request, response);
		return result;
	}

	

	/**
	 * orgtype=3时保存操作员
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */

	@ResponseBody
	@Authorization(name="User",value={"1,SysSystemUser_Add","2,SysSystemUser_Edit"})
    @RequestMapping(value = "/system/saveUserOperation.html", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public Map<String,Object> saveUser2(SysUser sysUser,@RequestParam(required = false)String flag,@RequestParam(required = false)String  secpassword,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> rmap = new HashMap<String, Object>();
		String URL = null;
		if(StringUtils.isNotEmpty(flag)){
			if("2".equals(flag)){
				 URL="/system/userlist.html?flag="+flag+"&";
			}
		}else{
			 URL="/system/userlist.html?flag="+flag+"&";
		}
		boolean isAdd=false;
		if(sysUser==null){
			rmap.put("code", -1);
			rmap.put("msg", "系统初始化错误!");
			//redirect.openMessage(-1, "系统初始化错误!");
			return rmap;
		}
		if (TextUtil.isNotNull(sysUser.getUserid())) {// 编辑
		}else{//新增
			isAdd=true;
//			if(TextUtil.isNull(sysUser.getOrgcode())){
//				redirect.openMessage(-1, "请选择要添加的组织机构!");
//				return rmap;
//			}
		}

		if(TextUtil.isNull(sysUser.getOrgcode())){
			rmap.put("code", -1);
			rmap.put("msg", "请选择要添加的组织机构!");
			//redirect.openMessage(-1, "请选择要添加的组织机构!");
			return rmap;
		}

		if (TextUtil.isNull(sysUser.getUsername())) {
			
			rmap.put("code", -1);
			rmap.put("msg", "登录账号不能为空！");
			//redirect.openMessage(-1, "登录账号不能为空！");
			return rmap;
		}else if (LawlessUtil.isLawless(sysUser.getUsername())) {
			rmap.put("code", -1);
			
			rmap.put("msg", "登录账号"+IMPERMISSABLE_STRING);
			//redirect.openMessage(-1, "登录账号"+IMPERMISSABLE_STRING);
			return rmap;
		}else if(sysUser.getUsername().length()>STRING_LENGTH){
			
			rmap.put("code", -1);
			rmap.put("msg", "登录账号长度超过限制范围");
			//redirect.openMessage(-1, "登录账号长度超过限制范围");
			return rmap;
		}else if(TextUtil.isNull(sysUser.getTruename())){
			rmap.put("code", -1);
			rmap.put("msg", "真实姓名不能为空！");
			//redirect.openMessage(-1, "真实姓名不能为空！");
			return rmap;
		}else if(LawlessUtil.isLawless(sysUser.getTruename())){
			rmap.put("code", -1);
			rmap.put("msg", "真实姓名"+IMPERMISSABLE_STRING);
			//redirect.openMessage(-1, "真实姓名"+IMPERMISSABLE_STRING);
			return rmap;
		}else if(sysUser.getTruename().length()>32){
			rmap.put("code", -1);
			rmap.put("msg", "真实姓名长度超过限制范围");
			//redirect.openMessage(-1, "真实姓名长度超过限制范围");
			return rmap;
		}else if(isAdd&&TextUtil.isNull(sysUser.getUserpassword())){
			
			rmap.put("code", -1);
			rmap.put("msg", "登录密码不能为空");
			//redirect.openMessage(-1, "登录密码不能为空");
			return rmap;
		}else if(isAdd&&LawlessUtil.isLawless(sysUser.getUserpassword())){
			
			rmap.put("code", -1);
			rmap.put("msg", "登录密码"+IMPERMISSABLE_STRING);
			//redirect.openMessage(-1,"登录密码"+IMPERMISSABLE_STRING);
			return rmap;
		}else if(isAdd&&(sysUser.getUserpassword().length()>16||sysUser.getUserpassword().length()<6)){
			
			rmap.put("code", -1);
			rmap.put("msg",  "登录密码长度不在限制范围内！");
			//redirect.openMessage(-1, "登录密码长度不在限制范围内！");
			return rmap;
		}else if(isAdd&&TextUtil.isNull(secpassword)){
			
			rmap.put("code", -1);
			rmap.put("msg", "确认密码不能为空");
			//redirect.openMessage(-1, "确认密码不能为空");
			return rmap;
		}else if(isAdd&&!sysUser.getUserpassword().equals(secpassword)){
			
			rmap.put("code", -1);
			rmap.put("msg", "两次密码输入不一致");
			//redirect.openMessage(-1, "两次密码输入不一致");
			return rmap;
		}else if(TextUtil.isNotNull(sysUser.getAddress())&&sysUser.getAddress().length()>128){
			
			rmap.put("code", -1);
			rmap.put("msg","联系地址长度超过限制范围");
		//	redirect.openMessage(-1, "联系地址长度超过限制范围");
			return rmap;
		}
		if(TextUtil.isNotNull(sysUser.getTel())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "电话", sysUser.getTel(), "isPhone" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				rmap.put("code", -1);
				rmap.put("msg", isok.trim());
				//redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		if(TextUtil.isNotNull(sysUser.getFax())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "传真", sysUser.getFax(), "isPhone" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				rmap.put("code", -1);
				rmap.put("msg",isok.trim());
				//redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		if(TextUtil.isNotNull(sysUser.getMobile())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "手机号", sysUser.getMobile(), "isMobile" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				rmap.put("code", -1);
				rmap.put("msg",  isok.trim());
				//redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		if(TextUtil.isNotNull(sysUser.getEmail())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "邮箱", sysUser.getEmail(), "isMail" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				rmap.put("code", -1);
				rmap.put("msg",  isok.trim());
			//	redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		int count=sysUserService.verifyDuplicateUserName(sysUser.getUsername());
		int subFlag = Integer.parseInt(request.getParameter("subflag"));
		if (subFlag == 1) {
			if(count>0){
				rmap.put("code", -1);
				rmap.put("msg",  "当前注册账号已存在！");
				//redirect.openMessage(-1, "当前注册账号已存在！");
				return rmap;
			}else{
				sysUser.setUserpassword(MD5Util.calcMD5(sysUser.getUserpassword()));

				sysUserService.savaUser(sysUser);//添加
				String[] strArray=new String[1];
				if("005".equals(sysUser.getOrgcode())){
					strArray[0] = "mayor";
				}else if("006".equals(sysUser.getOrgcode())){
					strArray[0] = "govzfb";
				}else if("007".equals(sysUser.getOrgcode())){
					strArray[0] = "interviewguest";
				}else if("008".equals(sysUser.getOrgcode())){
					strArray[0] = "interviewleader";
				}else{
					strArray[0] = "gov";
				}
				sysUserRoleService.saveUserRole(sysUser.getUserid(), strArray);
				
				rmap.put("code", 1);
				rmap.put("url",  URL+"orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
				rmap.put("title",  "添加操作员");
				rmap.put("msg","已经成功添加了该操作员！");
				//redirect.openMessageToUrl(1, "添加操作员", "已经成功添加了该操作员！", URL+"orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" +getLoginUserInfo(request, response).getUser().getUsername() + "，添加操作员成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + sysUser.getUserid(),getLoginUserInfo(request, response).getUser());
				/*if("005".equals(sysUser.getOrgcode())){//对市长表的操作
					BbsMayor bbsMayor=new BbsMayor();
					bbsMayor.setUserid(sysUser.getUserid());
					bbsMayor.setSortcode("0");
					bbsMayorService.saveNotNull(bbsMayor);
				}*/
			}
		} else if (subFlag == 2) {
			if(count>=2){
				rmap.put("code", -1);
				rmap.put("msg","当前修改账号重复！");
				//redirect.openMessage(-1, "当前修改账号重复！");
				return rmap;
			}else{
				sysUserService.updateUser(sysUser);//修改
				if(sysUser!=null&&sysUser.getOrgtype()==3){
					
					rmap.put("code", 1);
					rmap.put("url",  URL+"orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
					rmap.put("title", "修改操作员");
					rmap.put("msg","已经成功修改了该操作员！");
					//redirect.openMessageToUrl(1, "修改操作员", "已经成功修改了该操作员！", URL+"orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
				}else{
					rmap.put("code", 1);
					rmap.put("url",  "/system/user.html?orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
					rmap.put("title", "修改操作员");
					rmap.put("msg","已经成功修改了该操作员！");
					//redirect.openMessageToUrl(1, "修改操作员", "已经成功修改了该操作员！", "/system/user.html?orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
				}
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，修改操作员成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + sysUser.getUserid(), getLoginUserInfo(request, response).getUser());
			}
		} else {
			rmap.put("code", -1);
			rmap.put("msg","参数传递错误！");
			//redirect.openMessage(-1, "参数传递错误！");
		}
		return rmap;
	}

	@ResponseBody
	@Authorization(name="User",value={"1,SysSystemUser_Add","2,SysSystemUser_Edit"})
    @RequestMapping(value = "/system/saveUser.html", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public Map<String,Object> saveUser(SysUser sysUser,@RequestParam(required = false)String  secpassword,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> rmap = new HashMap<String, Object>();
		 boolean isAdd=false;
		if(sysUser==null){
			rmap.put("code", -1);
			rmap.put("msg", "系统初始化错误!");
			//redirect.openMessage(-1, "系统初始化错误!");
			return rmap;
		}
		if (TextUtil.isNotNull(sysUser.getUserid())) {// 编辑
		}else{//新增
			isAdd=true;
			if(TextUtil.isNull(sysUser.getOrgcode())){
				rmap.put("code", -1);
				rmap.put("msg", "请选择要添加的组织机构!");
				//redirect.openMessage(-1, "请选择要添加的组织机构!");
				return rmap;
			}
		}
		if (TextUtil.isNull(sysUser.getUsername())) {
			rmap.put("code", -1);
			rmap.put("msg", "登录账号不能为空！");
			//redirect.openMessage(-1, "登录账号不能为空！");
			return rmap;
		}else if (LawlessUtil.isLawless(sysUser.getUsername())) {
			rmap.put("code", -1);
			rmap.put("msg", "登录账号"+IMPERMISSABLE_STRING);
			//redirect.openMessage(-1, "登录账号"+IMPERMISSABLE_STRING);
			return rmap;
		}else if(sysUser.getUsername().length()>STRING_LENGTH){
			rmap.put("code", -1);
			rmap.put("msg", "登录账号长度超过限制范围");
			//redirect.openMessage(-1, "登录账号长度超过限制范围");
			return rmap;
		}else if(TextUtil.isNull(sysUser.getTruename())){
			rmap.put("code", -1);
			rmap.put("msg", "真实姓名不能为空！");
			//redirect.openMessage(-1, "真实姓名不能为空！");
			return rmap;
		}else if(LawlessUtil.isLawless(sysUser.getTruename())){
			rmap.put("code", -1);
			rmap.put("msg", "真实姓名"+IMPERMISSABLE_STRING);
			//redirect.openMessage(-1, "真实姓名"+IMPERMISSABLE_STRING);
			return rmap;
		}else if(sysUser.getTruename().length()>32){
			rmap.put("code", -1);
			rmap.put("msg", "真实姓名长度超过限制范围");
			//redirect.openMessage(-1, "真实姓名长度超过限制范围");
			return rmap;
		}else if(isAdd&&TextUtil.isNull(sysUser.getUserpassword())){
			rmap.put("code", -1);
			rmap.put("msg", "登录密码不能为空");
			//redirect.openMessage(-1, "登录密码不能为空");
			return rmap;
		}else if(isAdd&&LawlessUtil.isLawless(sysUser.getUserpassword())){
			rmap.put("code", -1);
			rmap.put("msg", "登录密码"+IMPERMISSABLE_STRING);
			//redirect.openMessage(-1,"登录密码"+IMPERMISSABLE_STRING);
			return rmap;
		}else if(isAdd&&(sysUser.getUserpassword().length()>16||sysUser.getUserpassword().length()<6)){
			rmap.put("code", -1);
			rmap.put("msg", "登录密码长度不在限制范围内！");
			//redirect.openMessage(-1, "登录密码长度不在限制范围内！");
			return rmap;
		}else if(isAdd&&TextUtil.isNull(secpassword)){
			rmap.put("code", -1);
			rmap.put("msg", "确认密码不能为空");
			//redirect.openMessage(-1, "确认密码不能为空");
			return rmap;
		}else if(isAdd&&!sysUser.getUserpassword().equals(secpassword)){
			rmap.put("code", -1);
			rmap.put("msg", "两次密码输入不一致");
			//redirect.openMessage(-1, "两次密码输入不一致");
			return rmap;
		}else if(TextUtil.isNotNull(sysUser.getAddress())&&sysUser.getAddress().length()>128){
			rmap.put("code", -1);
			rmap.put("msg", "联系地址长度超过限制范围");
			//redirect.openMessage(-1, "联系地址长度超过限制范围");
			return rmap;
		}
		if(TextUtil.isNotNull(sysUser.getTel())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "电话", sysUser.getTel(), "isPhone" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				
				rmap.put("code", -1);
				rmap.put("msg", isok.trim());
				//redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		if(TextUtil.isNotNull(sysUser.getFax())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "传真", sysUser.getFax(), "isPhone" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				rmap.put("code", -1);
				rmap.put("msg", isok.trim());
				//redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		if(TextUtil.isNotNull(sysUser.getMobile())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "手机号", sysUser.getMobile(), "isMobile" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				rmap.put("code", -1);
				rmap.put("msg", isok.trim());
				//redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		if(TextUtil.isNotNull(sysUser.getEmail())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "邮箱", sysUser.getEmail(), "isMail" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				rmap.put("code", -1);
				rmap.put("msg", isok.trim());
				//redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		int count=sysUserService.verifyDuplicateUserName(sysUser.getUsername());
		int subFlag = Integer.parseInt(request.getParameter("subflag"));
		if (subFlag == 1) {
			if(count>0){
				rmap.put("code", -1);
				rmap.put("msg", "当前注册账号已存在！");
				//redirect.openMessage(-1, isok.trim());
				return rmap;
				//redirect.openMessage(-1, "当前注册账号已存在！");
			}else{
				sysUser.setUserpassword(MD5Util.calcMD5(sysUser.getUserpassword()));
				sysUser.setPubkey(new PassWordCreate().createPassWord(256));
				sysUserService.savaUser(sysUser);//添加

				rmap.put("code", 1);
				rmap.put("url", "/system/user.html?orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
				rmap.put("title", "添加操作员");
				rmap.put("msg", "已经成功添加了该操作员！");
				
				//redirect.openMessageToUrl(1, "添加操作员", "已经成功添加了该操作员！", "/system/user.html?orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，添加操作员成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + sysUser.getUserid(), getLoginUserInfo(request, response).getUser());
			}
		} else if (subFlag == 2) {
			if(count>=2){
				rmap.put("code", -1);
				rmap.put("msg", "当前修改账号重复！");
				//redirect.openMessage(-1, "当前修改账号重复！");
				return rmap;
			}else{
				sysUserService.updateUser(sysUser);//修改
				if(sysUser!=null&&sysUser.getOrgtype()==3){
					rmap.put("code", 1);
					rmap.put("url", "/system/userlist.html?orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
					rmap.put("title", "修改操作员");
					rmap.put("msg", "已经成功修改了该操作员！");
					//redirect.openMessageToUrl(1, "修改操作员", "已经成功修改了该操作员！", "/system/userlist.html?orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
				}else{
					rmap.put("code", 1);
					rmap.put("url", "/system/user.html?orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
					rmap.put("title", "修改操作员");
					rmap.put("msg", "已经成功修改了该操作员！");
					//redirect.openMessageToUrl(1, "修改操作员", "已经成功修改了该操作员！", "/system/user.html?orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
				}
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，修改操作员成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + sysUser.getUserid(), getLoginUserInfo(request, response).getUser());
			}
		} else {
			rmap.put("code", -1);
			rmap.put("msg", "参数传递错误！");
			//redirect.openMessage(-1, "参数传递错误！");
		}
		return rmap;
	}

//	@Authorization(name="User",value={"0,SysSystemUser_Pass"})
	@RequestMapping(value = "/system/userpass.html")
	public ModelAndView LoadPassword(
			@RequestParam(value = "userid", required = false) String userid,
			@RequestParam(required = false)String flag,
			@RequestParam(value="orgtype", required = false) String orgtype,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView result = new ModelAndView("/system/userpass");
		/*if(StringUtils.isNotEmpty(flag)){
			createPage2();
		}else{
			createPage();
		}*/
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE);
		SysUser sysUser = sysUserService.findSysUserByID(userid);
		result.addObject("subflag", "");
		result.addObject("flag", flag);
		result.addObject("Suer", sysUser);
		result.addObject("orgtype", orgtype);
        load(result, request, response);
		return result;
	}
	
	/**
	 * 设置权限
	 * @param orgtype
	 * @param userid
	 * @param rolecheck
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/system/setUserRole.html"},produces = "text/html;charset=UTF-8")
	public Map<String,Object> delRole(@RequestParam String orgtype,
			@RequestParam String userid,
			@RequestParam(required=false) String[] rolecheck,
			@RequestParam(required = false) String flag,HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> rmap = new HashMap<String, Object>();
		SysUser sysuser = sysUserService.findUserById(userid);
		if(sysuser==null){
			rmap.put("code", -1);
			/*rmap.put("url", "/system/user.html?orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
			rmap.put("title", "修改操作员");*/
			rmap.put("msg", "该用户不存在,不能设置角色权限!");
			//redirect.openMessage(-1, "该用户不存在,不能设置角色权限!");
			return rmap;
		}else{
			try {
				sysUserRoleService.saveUserRole(userid, rolecheck);
				//redirect.openMessage(1, "设置权限成功！");
				rmap.put("code",2);
				/*rmap.put("url", "/system/user.html?orgtype="+sysUser.getOrgtype()+"&orgcode="+sysUser.getOrgcode());
				rmap.put("title", "修改操作员");*/
				rmap.put("msg", "设置权限成功！");
				if(StringUtils.isNotEmpty(flag)) {
					if(("2".equals(flag))) {
						rmap.put("code", 1);
						rmap.put("url", "/system/userlist2.html?flag=" + flag);
						rmap.put("title", "提示：您的操作已经完成！");
						rmap.put("msg", "设置权限成功！");
						//redirect.openMessageToUrl(1, "提示：您的操作已经完成！", "设置权限成功！", "/system/userlist2.html?flag=" + flag);
					} else {
						rmap.put("code", 1);
						rmap.put("url", "/system/userlist.html?orgtype=" + orgtype);
						rmap.put("title", "提示：您的操作已经完成！");
						rmap.put("msg", "设置权限成功！");
						//redirect.openMessageToUrl(1, "提示：您的操作已经完成！", "设置权限成功！", "/system/userlist.html?orgtype=" + orgtype);
					}
				}else {
					rmap.put("code", 1);
					rmap.put("url", "/system/user.html?orgtype=" + orgtype);
					rmap.put("title", "提示：您的操作已经完成！");
					rmap.put("msg", "设置权限成功！");
					//redirect.openMessageToUrl(1, "提示：您的操作已经完成！", "设置权限成功！", "/system/user.html?orgtype=" + orgtype);
				}
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，操作员授权成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid, getLoginUserInfo(request, response).getUser());
			} catch (Exception e) {
				rmap.put("code",-1);
				rmap.put("msg", "操作失败，请重试！");
				//redirect.openMessage(-1, "操作失败，请重试！");
				logger.error("权限操作出现异常！", e);
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，操作员授权失败！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid, getLoginUserInfo(request, response).getUser());
				return rmap;
			}
		}
		return rmap;
	}

	/**
	 * 角色列表
	 * @param orgtype
	 * @param userid
	 * @return
	 * @throws Exception
	 */
//	@Authorization(name="User",value={"0,SysSystemUser_Permit"})
	@RequestMapping(value = "/system/userpermit.html")
	public ModelAndView LoadPermit(@RequestParam String orgtype,
			@RequestParam String userid,
			@RequestParam(required = false) String flag,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView result = new ModelAndView("/system/userpermit");
		/*if(StringUtils.isNotEmpty(flag) && "2".equals(flag)){
			createPage2();
		}else{
			createPage();
		}*/
		//loadBaseProperties(result,PageTopTypeEnum.B_PAGE);
		SysUser sysuser=sysUserService.findUserById(userid);
		result.addObject("subflag", 1);
		result.addObject("sysuser", sysuser);
		result.addObject("flag", flag);
		result.addObject("orgtype", orgtype);
		result.addObject("roleList", sysUserRoleService.selectRoleByUserid(orgtype, userid));
        load(result, request, response);
		return result;
	}

	/**
	 * 重置密码
	 * @param id	用户ID
	 * @param Ud	原密码
	 * @param Sd	新密码
	 * @return
	 */
	@ResponseBody
	@Authorization(name="User",value={"1,SysSystemUser_Pass"})
	@RequestMapping(value = "/system/resetPassword.html", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public Map<String,Object> resetPwd(
			@RequestParam("UserId")String UserId,
			@RequestParam("orgtype")String orgtype,
			@RequestParam("flag")String flag,
			@RequestParam("UserPassword") String UserPassword,
			@RequestParam("SecPassword") String SecPassword,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> rmap = new HashMap<String, Object>();
 		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[] { "新密码", UserPassword, "isNull|isContainIllegalChar|isContainsChinese"});
		list.add(new String[] { "重复密码", SecPassword, "isNull|isContainIllegalChar|isContainsChinese"});
		String isok = FormCheckUtil.checkForm(list);
		if (!"".equals(isok.trim())) {
			rmap.put("code", -1);
			/*rmap.put("url", "/system/user.html?orgtype=" + orgtype);
			rmap.put("title", "提示：您的操作已经完成！");*/
			rmap.put("msg", isok.trim());
			//redirect.openMessage(-1, isok.trim());
			return rmap;
		}
		 //新密码长度需在6-50字符之间
		if (SecPassword.length() > 50||SecPassword.length()<6) {
			rmap.put("code", -1);
			rmap.put("msg", "密码长度为6-16个字符!");
			//redirect.openMessage(-1, "密码长度为6-16个字符!");
			return rmap;
		}
		 //新密码不能与旧密码相同
		if (!UserPassword.equals(SecPassword)) {
			rmap.put("code", -1);
			rmap.put("msg", "两次密码输入不一致!");
			//redirect.openMessage(-1, "两次密码输入不一致!");
			return rmap;
		}
		 //MD5Util加密
		String secPassword = MD5Util.calcMD5(SecPassword);
		try {
			// TODO 传入id值找到sysUser
			SysUser sysUser = sysUserService.getSysUser(UserId);
			sysUser.setUserpassword(secPassword);
			sysUserService.updatePassword(sysUser);
			//redirect.openMessage(1, "密码修改成功!");
			rmap.put("code", 2);
			rmap.put("msg", "密码修改成功!");
			if("3".equals(orgtype)){
				if(StringUtils.isNotEmpty(flag)){
					if("2".equals(flag)){
						rmap.put("code", 1);
						rmap.put("url", "/system/userlist.html?orgtype="+orgtype+"&flag="+flag);
						rmap.put("title", "提示：您的操作已经完成！");
						rmap.put("msg", "密码修改成功!");
						//redirect.openMessageToUrl(1, "", "密码修改成功!", "/system/userlist.html?orgtype="+orgtype+"&flag="+flag);
					}
				}else{
					rmap.put("code", 1);
					rmap.put("url", "/system/userlist.html?orgtype="+orgtype+"&flag="+flag);
					rmap.put("title", "提示：您的操作已经完成！");
					rmap.put("msg", "密码修改成功!");
					//   redirect.openMessageToUrl(1, "", "密码修改成功!", "/system/userlist.html?orgtype="+orgtype+"&flag="+flag);
				}

			}else{
				rmap.put("code", 1);
				rmap.put("url", "/system/user.html?orgtype="+orgtype);
				rmap.put("title", "提示：您的操作已经完成！");
				rmap.put("msg", "密码修改成功!");
				//redirect.openMessageToUrl(1, "", "密码修改成功!", "/system/user.html?orgtype="+orgtype);
			}

			saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，重置操作员密码成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + UserId,getLoginUserInfo(request, response).getUser());
			return rmap;
		} catch (Exception e) {
			logger.error("密码修改失败！", e);
			
			rmap.put("code", -1);
			rmap.put("msg", "密码修改失败!");
			//redirect.openMessage(-1, "密码修改失败!");
			saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，重置操作员密码失败！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + UserId, getLoginUserInfo(request, response).getUser());
			return rmap;
		}
	}




	/**
     * 禁用或者启用
     * @param isdel
     * @param Usercode
     * @param response
     * @return
     */
    @ResponseBody
	@Authorization(name="User",value={"1,SysSystemUser_Disable"})
    @RequestMapping(value = {"/system/enableOrDisableUser.html"},produces = "text/html;charset=UTF-8")
	public Map<String,Object> enableOrDisableUser(@RequestParam String isdel,
			@RequestParam String userid,
			@RequestParam String orgtype,
			@RequestParam(required = false) String pageNum,
			@RequestParam(required = false) String pageSize,
			@RequestParam(required = false) String orgcode,
			@RequestParam(required = false) String flag,
			@RequestParam(required = false) String truename,HttpServletRequest request, HttpServletResponse response){
    	Map<String,Object> rmap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(userid)||StringUtils.isEmpty(isdel)){
			rmap.put("code", 1);
			/*rmap.put("url", "/system/user.html?orgtype="+orgtype);
			rmap.put("title", "提示：您的操作已经完成！");*/
			rmap.put("msg", "isDel或userid参数不能为空!");
			//redirect.openMessage(-1, "isDel或userid参数不能为空!");
			return rmap;
		}else{
			try {
				int isDel = sysUserService.updateUserIsDel(isdel, userid);
				if(isDel==1) {
					if(StringUtils.isNotEmpty(flag)){
						if("2".equals(flag)){
							rmap.put("code", 1);
							rmap.put("url", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
							rmap.put("title", "禁用操作员");
							rmap.put("msg", "已经成功禁用了该操作员!");
							//redirect.openMessageToUrl(1, "禁用操作员", "已经成功禁用了该操作员!", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
						}
					}else{
						rmap.put("code", 1);
						rmap.put("url", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
						rmap.put("title", "禁用操作员");
						rmap.put("msg", "已经成功禁用了该操作员!");
						//redirect.openMessageToUrl(1, "禁用操作员", "已经成功禁用了该操作员!", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
					}

				 	saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，禁用操作员成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid, getLoginUserInfo(request, response).getUser());
				} else {
					if(StringUtils.isNotEmpty(flag)){
						if("2".equals(flag)){
							rmap.put("code", 1);
							rmap.put("url", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
							rmap.put("title", "启用操作员");
							rmap.put("msg", "已经成功启用了该操作员!");
							//redirect.openMessageToUrl(1, "启用操作员", "已经成功启用了该操作员!", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
						}
					}else{
						rmap.put("code", 1);
						rmap.put("url", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
						rmap.put("title", "启用操作员");
						rmap.put("msg", "已经成功启用了该操作员!");
						//redirect.openMessageToUrl(1, "启用操作员", "已经成功启用了该操作员!", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
					}
					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，启用操作员成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid, getLoginUserInfo(request, response).getUser());
				}
				return rmap;
			} catch (Exception e) {
				logger.error("操作失败", e);
				rmap.put("code", -1);
				/*rmap.put("url", "/system/user.html?orgtype="+orgtype);
				rmap.put("title", "提示：您的操作已经完成！");*/
				rmap.put("msg", "操作失败！");
				//redirect.openMessage(-1, "操作失败！");
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，启用操作员失败！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid, getLoginUserInfo(request, response).getUser());
				return rmap;
			}
		}
	}


    /**
     * 禁用或者启用（单页）
     * @param isdel
     * @param Usercode
     * @param response
     * @return
     */
    @ResponseBody
	@Authorization(name="User",value={"1,CommGovUserManage_Disable"})
    @RequestMapping(value = {"/system/enableOrDisableUser2.html"},produces = "text/html;charset=UTF-8")
	public Map<String,Object> enableOrDisableUser1(@RequestParam String isdel,
			@RequestParam String userid,
			@RequestParam String orgtype,
			@RequestParam(required = false) String pageNum,
			@RequestParam(required = false) String pageSize,
			@RequestParam(required = false) String orgcode,
			@RequestParam(required = false) String flag,
			@RequestParam(required = false) String truename,HttpServletRequest request, HttpServletResponse response){
    	Map<String,Object> rmap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(userid)||StringUtils.isEmpty(isdel)){
			rmap.put("code", -1);
			/*rmap.put("url", "/system/user.html?orgtype="+orgtype);
			rmap.put("title", "提示：您的操作已经完成！");*/
			rmap.put("msg", "isDel或userid参数不能为空!");
			//redirect.openMessage(-1, "isDel或userid参数不能为空!");
			return rmap;
		}else{
			try {
				int isDel = sysUserService.updateUserIsDel(isdel, userid);
				if(isDel==1) {
					if(StringUtils.isNotEmpty(flag)){
						if("2".equals(flag)){
							rmap.put("code", 1);
							rmap.put("url", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
							rmap.put("title", "禁用操作员");
							rmap.put("msg", "已经成功禁用了该操作员!");
							//redirect.openMessageToUrl(1, "禁用操作员", "已经成功禁用了该操作员!", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
						}
					}else{
						rmap.put("code", 1);
						rmap.put("url", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
						rmap.put("title", "禁用操作员");
						rmap.put("msg", "已经成功禁用了该操作员!");
						//redirect.openMessageToUrl(1, "禁用操作员", "已经成功禁用了该操作员!", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
					}

				 	saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，禁用操作员成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid, getLoginUserInfo(request, response).getUser());
				} else {
					if(StringUtils.isNotEmpty(flag)){
						if("2".equals(flag)){
							rmap.put("code", 1);
							rmap.put("url", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
							rmap.put("title", "启用操作员");
							rmap.put("msg", "已经成功启用了该操作员!");
							//redirect.openMessageToUrl(1, "启用操作员", "已经成功启用了该操作员!", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
						}
					}else{
						rmap.put("code", 1);
						rmap.put("url", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
						rmap.put("title", "启用操作员");
						rmap.put("msg", "已经成功启用了该操作员!");
						//redirect.openMessageToUrl(1, "启用操作员", "已经成功启用了该操作员!", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
					}
					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，启用操作员成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid, getLoginUserInfo(request, response).getUser());
				}
				return rmap;
			} catch (Exception e) {
				logger.error("操作失败", e);
				//redirect.openMessage(-1, "操作失败！");
				rmap.put("code", -1);
				/*rmap.put("url", "/system/user.html?orgtype="+orgtype);
				rmap.put("title", "提示：您的操作已经完成！");*/
				rmap.put("msg","操作失败！");
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，启用操作员失败！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid, getLoginUserInfo(request, response).getUser());
				return rmap;
			}
		}
	}

    /**
     * 删除用户
     * @param usercode
     * @param response
     * @return
     */
    @ResponseBody
	@Authorization(name="User",value={"2,SysSystemUser_Del"})
    @RequestMapping(value = {"/system/deleteUser.html"},produces = "text/html;charset=UTF-8")
	public Map<String,Object> deleteUser(@RequestParam String userid,
			@RequestParam String orgtype,
			@RequestParam(required = false) String pageNum,
			@RequestParam(required = false) String pageSize,
			@RequestParam(required = false) String orgcode,
			@RequestParam(required = false) String flag,
			@RequestParam(required = false) String truename,HttpServletRequest request, HttpServletResponse response){
    	Map<String,Object> rmap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(userid)){
			rmap.put("code", -1);
			/*rmap.put("url", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
			rmap.put("title", "禁用操作员");*/
			rmap.put("msg", "usercode参数不能为空!");
			//redirect.openMessage(-1, "usercode参数不能为空!");
			return rmap;
		}else{
				try {
					sysUserService.delUser(userid);
					if(StringUtils.isNotEmpty(flag)){
						if("2".equals(flag)){
							rmap.put("code", 1);
							rmap.put("url", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
							rmap.put("title", "删除操作员");
							rmap.put("msg", "已经成功删除了该操作员！");
							//redirect.openMessageToUrl(1, "删除操作员", "已经成功删除了该操作员！", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
						}
					}else{
						rmap.put("code", 1);
						rmap.put("url", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
						rmap.put("title", "删除操作员");
						rmap.put("msg", "已经成功删除了该操作员！");
						//redirect.openMessageToUrl(1, "删除操作员", "已经成功删除了该操作员！", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
					}

					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，删除操作员成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid, getLoginUserInfo(request, response).getUser());
					return rmap;
				} catch (Exception e) {
					logger.error("删除操作员失败！", e);
					rmap.put("code", -1);
					rmap.put("msg", "删除操作员失败！");
					//redirect.openMessage(-1, "删除操作员失败！");
					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，删除操作员失败！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid, getLoginUserInfo(request, response).getUser());
					return rmap;
				}
		}
	}

    /**
     * 删除用户（单页）
     * @param usercode
     * @param response
     * @return
     */
   @ResponseBody
	@Authorization(name="User",value={"2,CommGovUserManage_Del"})
    @RequestMapping(value = {"/system/deleteUser2.html"},produces = "text/html;charset=UTF-8")
	public Map<String,Object> deleteUser2(@RequestParam String userid,
			@RequestParam String orgtype,
			@RequestParam(required = false) String pageNum,
			@RequestParam(required = false) String pageSize,
			@RequestParam(required = false) String orgcode,
			@RequestParam(required = false) String flag,
			@RequestParam(required = false) String truename,HttpServletRequest request, HttpServletResponse response){
	   Map<String,Object> rmap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(userid)){
			rmap.put("code", -1);
			/*rmap.put("url", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
			rmap.put("title", "删除操作员");*/
			rmap.put("msg", "usercode参数不能为空!");
			//redirect.openMessage(-1, "usercode参数不能为空!");
			return rmap;
		}else{
				try {
					sysUserService.delUser(userid);
					if(StringUtils.isNotEmpty(flag)){
						if("2".equals(flag)){
							rmap.put("code", 1);
							rmap.put("url", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
							rmap.put("title", "删除操作员");
							rmap.put("msg", "已经成功删除了该操作员！");
							
							//redirect.openMessageToUrl(1, "删除操作员", "已经成功删除了该操作员！", toListUrl2(orgtype, orgcode, pageNum, pageSize, truename,flag));
						}
					}else{
						rmap.put("code", 1);
						rmap.put("url", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
						rmap.put("title", "删除操作员");
						rmap.put("msg", "已经成功删除了该操作员！");
						//redirect.openMessageToUrl(1, "删除操作员", "已经成功删除了该操作员！", toListUrl(orgtype, orgcode, pageNum, pageSize, truename));
					}

					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" +  getLoginUserInfo(request, response).getUser().getUsername() + "，删除操作员成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid,  getLoginUserInfo(request, response).getUser());
					return rmap;
				} catch (Exception e) {
					logger.error("删除操作员失败！", e);
					rmap.put("code", -1);
					rmap.put("msg", "删除操作员失败！");
					//redirect.openMessage(-1, "删除操作员失败！");
					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" +  getLoginUserInfo(request, response).getUser().getUsername() + "，删除操作员失败！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + userid,  getLoginUserInfo(request, response).getUser());
					return rmap;
				}
		}
	}

    private String toListUrl(String orgtype,String orgcode,String pageNum,String pageSize,String truename){
    	StringBuilder url = null;
    	if("3".equals(orgtype)){
    		url= new StringBuilder("/system/userlist.html");
    	}else{
    		url= new StringBuilder("/system/user.html");
    	}
		url.append("?orgtype="+orgtype);
		if(StringUtils.isNotEmpty(pageNum)){
			url.append("&pageNum="+pageNum);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			url.append("&pageSize="+pageSize);
		}
		if(StringUtils.isNotEmpty(truename)){
			url.append("&truename="+truename);
		}
		if(StringUtils.isNotEmpty(orgcode)&&!"-1".equals(orgcode)){
			url.append("&orgcode="+orgcode);
		}
		return url.toString();
	}




    private String toListUrl2(String orgtype,String orgcode,String pageNum,String pageSize,String truename,String flag){
    	StringBuilder url = null;
    	if("3".equals(orgtype)){
    		url= new StringBuilder("/system/userlist.html");
    	}else{
    		url= new StringBuilder("/system/user.html");
    	}
		url.append("?orgtype="+orgtype);
		if(StringUtils.isNotEmpty(pageNum)){
			url.append("&pageNum="+pageNum);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			url.append("&pageSize="+pageSize);
		}
		if(StringUtils.isNotEmpty(truename)){
			url.append("&truename="+truename);
		}
		if(StringUtils.isNotEmpty(orgcode)&&!"-1".equals(orgcode)){
			url.append("&orgcode="+orgcode);
		}
		url.append("&flag="+flag);
		return url.toString();
	}

    /**
     * 登录当前session用户修改密码
     * @param session
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/password.html")
	public ModelAndView LoadPass(HttpServletRequest request,
	           HttpServletResponse response) throws Exception {
		ModelAndView result = new ModelAndView("/system/resetpassword");
		SysUser sysuser = getLoginUserInfo(request, response).getUser();
		result.addObject("subflag", 1);
		result.addObject("modType", "password");
		result.addObject("sysuser", sysuser);
		load(result, request, response);
		return result;
	}

    //flag=1时为政民互动承办单位登录时因个人信息不全而跳到修改个人信息页面
    @RequestMapping(value = "/information.html")
    public ModelAndView personalInformation(String flag,HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
    	ModelAndView result = new ModelAndView("/system/resetpassword");
    	SysUser sysuser=getLoginUserInfo(request, response).getUser();
    	result.addObject("modType", "information");
    	result.addObject("sysuser", sysuser);
    	if ("".equals(flag)) {
    		flag="0";
		}
    	result.addObject("flag", flag);
    	load(result, request, response);
    	return result;
    }

    /**
     * 登录当前session用户修改密码
     * @param UserId
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/resetPassword.html", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public Map<String,Object> resetPassword(
			@RequestParam("UserId")String UserId,
			@RequestParam("UserPassword") String UserPassword,
			@RequestParam("SecPassword") String SecPassword,HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> rmap = new HashMap<String, Object>();
 		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[] { "新密码", UserPassword, "isNull|isContainIllegalChar|isContainsChinese"});
		list.add(new String[] { "重复密码", SecPassword, "isNull|isContainIllegalChar|isContainsChinese"});
		String isok = FormCheckUtil.checkForm(list);
		if (!"".equals(isok.trim())) {
			rmap.put("code", -1);
			rmap.put("msg",isok.trim());
			//redirect.openMessage(-1, isok.trim());
			return rmap;
		}
		 //新密码长度需在6-50字符之间
		if (SecPassword.length() > 50||SecPassword.length()<6) {
			rmap.put("code", -1);
			rmap.put("msg","密码长度为6-16个字符!");
			//redirect.openMessage(-1, "密码长度为6-16个字符!");
			return rmap;
		}
		 //新密码不能与旧密码相同
		if (!UserPassword.equals(SecPassword)) {
			rmap.put("code", -1);
			rmap.put("msg","两次密码输入不一致!");
			//redirect.openMessage(-1, "两次密码输入不一致!");
			return rmap;
		}
		 //MD5Util加密
		String secPassword = MD5Util.calcMD5(SecPassword);
		SysUser sysUser = new SysUser();
		try {
			sysUser = sysUserService.getSysUser(UserId);
			sysUser.setUserpassword(secPassword);
			sysUserService.updatePassword(sysUser);
			rmap.put("code", 1);
			rmap.put("url", "/password.html");
			rmap.put("title", "");
			rmap.put("msg", "已经成功修改了密码！");
			//redirect.openMessageToUrl(1, "", "已经成功修改了密码！", "/password.html");
			saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，重置操作员密码成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + UserId, getLoginUserInfo(request, response).getUser());
			return rmap;
		} catch (Exception e) {
			logger.error("密码修改失败！", e);
			//redirect.openMessage(-1, "密码修改失败!");
			rmap.put("code", -1);
			rmap.put("msg","密码修改失败!");
			if (sysUser.getUserid() != null) {
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，重置操作员密码失败！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + UserId, getLoginUserInfo(request, response).getUser());
			}
			return rmap;
		}
	}

	@ResponseBody
    @RequestMapping(value = "/updateUser.html", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public Map<String,Object> updateUser(SysUser sysUser,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> rmap = new HashMap<String, Object>();
		if(sysUser==null){
			rmap.put("code", -1);
			rmap.put("msg", "系统初始化错误!");
			//redirect.openMessage(-1, "系统初始化错误!");
			return rmap;
		}
		if (TextUtil.isNull(sysUser.getUserid())) {// 编辑
			rmap.put("code", -1);
			rmap.put("msg", "登录超时，请重新登录");
			//redirect.openMessage(-1, "登录超时，请重新登录");
			return rmap;
		}
		if (TextUtil.isNull(sysUser.getUsername())) {
			rmap.put("code", -1);
			rmap.put("msg", "登录账号不能为空！");
			//redirect.openMessage(-1, "登录账号不能为空！");
			return rmap;
		}else if (LawlessUtil.isLawless(sysUser.getUsername())) {
			rmap.put("code", -1);
			rmap.put("msg", "登录账号"+IMPERMISSABLE_STRING);
			//redirect.openMessage(-1, "登录账号"+IMPERMISSABLE_STRING);
			return rmap;
		}else if(sysUser.getUsername().length()>STRING_LENGTH){
			rmap.put("code", -1);
			rmap.put("msg", "登录账号长度超过限制范围");
			//redirect.openMessage(-1, "登录账号长度超过限制范围");
			return rmap;
		}else if(TextUtil.isNull(sysUser.getTruename())){
			
			rmap.put("code", -1);
			rmap.put("msg", "真实姓名不能为空！");
			//redirect.openMessage(-1, "真实姓名不能为空！");
			return rmap;
		}else if(LawlessUtil.isLawless(sysUser.getTruename())){
			rmap.put("code", -1);
			rmap.put("msg", "真实姓名"+IMPERMISSABLE_STRING);
			//redirect.openMessage(-1, "真实姓名"+IMPERMISSABLE_STRING);
			return rmap;
		}else if(sysUser.getTruename().length()>32){
			
			rmap.put("code", -1);
			rmap.put("msg", "真实姓名长度超过限制范围");
			//redirect.openMessage(-1, "真实姓名长度超过限制范围");
			return rmap;
		}else if(sysUser.getTruename().equals(sysUser.getUsername())){
			
			rmap.put("code", -1);
			rmap.put("msg", "请填写您的真实姓名！");
			//redirect.openMessage(-1, "请填写您的真实姓名！");
			return rmap;
		}else if(TextUtil.isNotNull(sysUser.getAddress())&&sysUser.getAddress().length()>128){
			
			rmap.put("code", -1);
			rmap.put("msg","联系地址长度超过限制范围");
			//redirect.openMessage(-1, "联系地址长度超过限制范围");
			return rmap;
		}
		if(TextUtil.isNotNull(sysUser.getTel())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "电话", sysUser.getTel(), "isPhone" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				rmap.put("code", -1);
				rmap.put("msg", isok.trim());
				//redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		if(TextUtil.isNotNull(sysUser.getFax())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "传真", sysUser.getFax(), "isPhone" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				rmap.put("code", -1);
				rmap.put("msg", isok.trim());
				//redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		if(TextUtil.isNotNull(sysUser.getMobile())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "手机号", sysUser.getMobile(), "isMobile" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				rmap.put("code", -1);
				rmap.put("msg", isok.trim());
				//redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		if(TextUtil.isNotNull(sysUser.getEmail())){
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { "邮箱", sysUser.getEmail(), "isMail" });
			String isok = FormCheckUtil.checkForm(list);
			if (!"".equals(isok.trim())) {
				rmap.put("code", -1);
				rmap.put("msg",isok.trim());
				//redirect.openMessage(-1, isok.trim());
				return rmap;
			}
		}
		int count=sysUserService.verifyDuplicateUserName(sysUser.getUsername());
		if(count>=2){
			rmap.put("code", -1);
			rmap.put("msg","当前修改账号重复！");
			//redirect.openMessage(-1, "当前修改账号重复！");
			return rmap;
		}else{
			int c=sysUserService.updateNotNull(sysUser);
			if(c==1){
				LoginUserInfo loginUserInfo = (LoginUserInfo) request.getSession().getAttribute("LoginUserInfo"+sysUser.getUserid());
				sysUser=sysUserService.selectByKey(sysUser);
				loginUserInfo.setTrueName(sysUser.getTruename());
				SysUserOrg sysUserOrg=sysUserOrgService.findOrgByUserId2(sysUser.getUserid());
				if(sysUserOrg!=null){
					sysUser.setOrgcode(sysUserOrg.getOrgCode());
					if(StringUtil.isNotNull(sysUserOrg.getOrgType())){
						sysUser.setOrgtype(Integer.valueOf(sysUserOrg.getOrgType()));
					}
				}
				loginUserInfo.setUser(sysUser);
				request.getSession().setAttribute("LoginUserInfo"+sysUser.getUserid(), loginUserInfo);
				rmap.put("code", 1);
				rmap.put("url", "/information.html");
				rmap.put("title", "修改个人信息");
				rmap.put("msg", "修改个人信息成功！");
				//redirect.openMessageToUrl(1, "修改个人信息", "修改个人信息成功！", "/information.html");
				saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，修改个人信息成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + sysUser.getUserid(), getLoginUserInfo(request, response).getUser());
			}else{
				rmap.put("code", -1);
				rmap.put("msg", "修改个人信息!");
				//redirect.openMessage(-1, "修改个人信息!");
				if (sysUser.getUserid() != null) {
					saveSysLog(LogTypeConstat.LOG_TYPE_0, IPUtil.getIpAddr(request), "[Sys站]账号：" + getLoginUserInfo(request, response).getUser().getUsername() + "，修改个人信息成功！登录IP为：" + IPUtil.getIpAddr(request) + "，操作员ID为：" + sysUser.getUserid(), getLoginUserInfo(request, response).getUser());
				}
				return rmap;
			}
		}

		return rmap;
	}


}
