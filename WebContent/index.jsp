<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base  target="myIframe" href="${contextPath}/">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<style type="text/css">
.header {
	height: 10%;
	text-align: center;
	background: blueviolet;
	    padding-top: 20px;
}
#userInfo {
	background: pink;
	position: absolute;
	top: 10px;
	right: 20px;
}
html,body,.container{
height: 100%;
margin: 0;
padding: 0;
}
#body {
	position: absolute;
    top: 10%;
    left: 15%;
    width: 85%;
    height: 90%;
}
#menu{
	height: 90%;
	width: 15%;
    background: yellowgreen;
    position: absolute;
        top: 10%;
}
#menu li{
    margin: 10px;
    border: 1px solid azure;
    text-align: center;
    background: deepskyblue;
    color: white;
}
</style>
</head>
<body style="margin-top: -21px;">	
	<div class="container">
		<div class="header">
			<span>
				<h1>工作流后台管理平台</h1>
			</span>
			<span>
				<table id="userInfo">
					<tr>
						<td style="text-align: right;">ID :</td>
						<th>${SESSION_USER.id}</th>
					</tr>
					<tr>
						<td>用户名 :</td>
						<th>${SESSION_USER.username}</th>
					</tr>
					<tr>
						<th colspan="2"><a href="login.jsp" target="_top">退出</a></th>
					</tr>
				</table>
			</span>
		</div>
		<div class="main_container">
			<div id="menu">
				<ul style="padding-top: 10px;">
					<li><a href="page/leaveApply.html">请假申请</a></li>
					<li><a href="page/buyApply.html">采购申请</a></li>
					<li><a href="page/addProcess.html">添加流程</a></li>
					<li><a href="page/myApply.html">我的申请</a></li>
					<li><a href="page/allApply.html">正在执行的申请</a></li>
					<li><a href="page/myTask.html">待处理流程</a></li>
				</ul>
			</div>

			<div id="body">
				<iframe name="myIframe" height="100%" width="100%" style="border: 0" 
				src="http://www.baidu.com"></iframe>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var contextPath = '${contextPath}/';
</script>
</html>