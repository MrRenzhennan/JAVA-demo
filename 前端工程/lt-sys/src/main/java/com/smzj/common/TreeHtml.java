package com.smzj.common;

public class TreeHtml {
	public static String getOrgTreeScript(String url,String orgList,String orgcode){
		StringBuilder sb = new StringBuilder();
		sb.append("<script type=\"text/javascript\">").append("\n");
		sb.append("var zNodes = "+orgList+";").append("\n");
		sb.append("var setting = {data: {simpleData: {enable: true}},callback: {onClick: zTreeOnClick}};").append("\n");
		sb.append("function setCheck() {").append("\n");
		sb.append("  var zTree = $.fn.zTree.getZTreeObj(\"treeDemo\");").append("\n");
		sb.append("  type = { \"Y\":\"ps\", \"N\":\"ps\"};").append("\n");
		sb.append("\n");
		sb.append("  var node = zTree.getNodeByParam(\"id\",'"+orgcode+"');").append("\n");
		sb.append("  zTree.selectNode(node);").append("\n");
		sb.append("}").append("\n");
		sb.append("function zTreeOnClick(event, treeId, treeNode) {").append("\n");
		sb.append(" var layerid = $(\"#layerid\").val();");
		sb.append("  window.location.href=\""+url+"?orgtype=\" + treeNode.orgType + \"&orgcode=\" + treeNode.id + \"&layerid=\" + layerid;").append("\n");
		sb.append("};").append("\n");
		sb.append("$(document).ready(function(){").append("\n");
		sb.append("	$.fn.zTree.init($(\"#treeDemo\"), setting, zNodes);").append("\n");
		sb.append("	setCheck();").append("\n");
		sb.append("});").append("\n");
		sb.append("</script>").append("\n");
		return sb.toString();
	}
	
	public static String getOrgTreeScript(String url,String orgList,String orgcode,String tid){
		StringBuilder sb = new StringBuilder();
		sb.append("<script type=\"text/javascript\">").append("\n");
		sb.append("var zNodes = "+orgList+";").append("\n");
		sb.append("var setting = {data: {simpleData: {enable: true}},callback: {onClick: zTreeOnClick}};").append("\n");
		sb.append("function setCheck() {").append("\n");
		sb.append("  var zTree = $.fn.zTree.getZTreeObj(\"treeDemo\");").append("\n");
		sb.append("  type = { \"Y\":\"ps\", \"N\":\"ps\"};").append("\n");
		sb.append("\n");
		sb.append("  var node = zTree.getNodeByParam(\"id\",'"+orgcode+"');").append("\n");
		sb.append("  zTree.selectNode(node);").append("\n");
		sb.append("}").append("\n");
		sb.append("function zTreeOnClick(event, treeId, treeNode) {").append("\n");
		sb.append(" var layerid = $(\"#layerid\").val();");
		sb.append("  window.location.href=\""+url+"?tid="+tid+"&orgtype=\" + treeNode.orgType + \"&orgcode=\" + treeNode.id + \"&layerid=\" + layerid;").append("\n");
		sb.append("};").append("\n");
		sb.append("$(document).ready(function(){").append("\n");
		sb.append("	$.fn.zTree.init($(\"#treeDemo\"), setting, zNodes);").append("\n");
		sb.append("	setCheck();").append("\n");
		sb.append("});").append("\n");
		sb.append("</script>").append("\n");
		return sb.toString();
	}

	public static String getOrgTreeSelectScript(String url,String orgList,String orgcode){
		StringBuilder sb = new StringBuilder();
		sb.append("<script type=\"text/javascript\">").append("\n");
		sb.append("var zNodes = "+orgList+";").append("\n");
		sb.append("var setting = {data: {simpleData: {enable: true}},callback: {onClick: zTreeOnClick}};").append("\n");
		sb.append("function setCheck() {").append("\n");
		sb.append("  var zTree = $.fn.zTree.getZTreeObj(\"treeDemo\");").append("\n");
		sb.append("  type = { \"Y\":\"ps\", \"N\":\"ps\"};").append("\n");
		sb.append("\n");
		sb.append("  var node = zTree.getNodeByParam(\"id\",'"+orgcode+"');").append("\n");
		sb.append("  zTree.selectNode(node);").append("\n");
		sb.append("}").append("\n");
		sb.append("function zTreeOnClick(event, treeId, treeNode) {").append("\n");
		sb.append("  	if(setOrg){ setOrg(treeNode);} ").append("\n");
		sb.append("};").append("\n");
		sb.append("$(document).ready(function(){").append("\n");
		sb.append("	$.fn.zTree.init($(\"#treeDemo\"), setting, zNodes);").append("\n");
		sb.append("	setCheck();").append("\n");
		sb.append("});").append("\n");
		sb.append("</script>").append("\n");
		return sb.toString();
	}
}
