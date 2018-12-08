<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--@page language="java" contentType="text/html"--%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<%! int day = 3; %>
<%! int fontSize; %>   
<html>  
  <head>  
    <base href="<%=basePath%>">  
      
    <title>My JSP 'login.jsp' starting page</title>  
      
    <meta http-equiv="pragma" content="no-cache">  
    <meta http-equiv="cache-control" content="no-cache">  
    <meta http-equiv="expires" content="0">      
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
    <meta http-equiv="description" content="This is my page">  
    <!-- 
    <link rel="stylesheet" type="text/css" href="styles.css"> 
    -->  
    <style>
      .box{
      	margin:100px auto 0px auto;
      	width:500px;
      	min-height:500px;
      	border: 1px solid #111;
      	box-shadow:0px 0px 10px #111;
      	padding:20px;
      }
      .date{
      	margin:30px;
      }
    </style>
  </head>  
    
  <body> 
  	<div class="box">
  		<form action="login">  
  			<div class="date">
		    username:<input type="text" name="username">
		    </div>
		    <div class="date">
		    password:<input type="password" name="pwd">
		    </div>
		    <div class="date">
		    	<input type="submit">
		    </div>
	    </form>  
	    <div class="date">
	    	Today's date: <%= (new java.util.Date()).toLocaleString()%>
	    </div>
	    <%-- 输出text --%>
	    <div class="date">
	    	<% out.println("Your IP address is " + request.getRemoteAddr()); %>
	    </div>
	    <p class="date">
	    	<% out.println(day);  %>
	    </p>
	    <%-- for循环 --%>
	    <p class="date">
	    	<%for ( fontSize = 4; fontSize <= 6; fontSize++){ %>   
  				<font color="green" size="<%= fontSize %>">    
  					JSP Tutorial   
  				</font><br />
			<%}%>
	    </p>
	    
	    <%-- 动态生成dom --%>
	    <jsp:element name="div">    
			<jsp:attribute name="key">   
			   Value for the attribute
			</jsp:attribute>
			<jsp:body>
			   Body for XML element
			</jsp:body>
		</jsp:element>
		<%-- <jsp:text>动作元素 --%>
		<jsp:text><![CDATA[<br/><div><jsp:text>动作元素允许在JSP页面和文档中使用写入文本的模板</div><br/>]]></jsp:text>
  	</div> 
  </body>  
</html>  

