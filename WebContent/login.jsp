<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="ie=edge" />
	<title>Document</title>
	<style type="text/css">
		html,body{  
		    height: 100%;  
		} 
		body {
		 margin :0;
		}
		.container {
			height: 100%;
			background: url('${contextPath}/static/images/login_bg2.jpg')  no-repeat;
			position: relative;
		}
		.window {
			width:340px;
			height: 346px;
			background: #F0F0F4;
			margin : 0 auto;
			position: absolute;
			top: 50%;
			left: 50%;
			margin-top : -168px;
			margin-left: -162px;
		}
		#login_panel {
			width: 300px;
			height: 286px;
			position: absolute;
			top: 50%;
			left: 50%;
			margin-top: -146px;
			margin-left: -150px;
			background: white;
		}
		#login_panel>div{
			margin-left: 15px;
		}
		
		#login_panel .body div{
			width: 268px;
			height: 32px;
			border: 1px solid #C6C6C6;
		}
		.input {
			border: navajowhite;
		    font-size: 14px;
		    width: 232px;
		    height: 30px;
		    margin-left: 36px;
		    text-decoration: none;
		    outline: none;
			padding-left: 5px;
		}
		.imgDiv {
			float: left;	
		}
		.mt {
			margin-top: 12px;
		}
		#login_panel .body .nb {
			border: 0;
		}
		.left{
			    display: inline-block;
			    margin-left: -5px;
			    font-size: xx-small;
			    line-height: 32px;
		}
		.right {
			display: inline-block;
		    float: right;
		    line-height: 32px;
		    
		}
		a {
			text-decoration: none;
		}
		#loginBtn  {
			background-color: #F45D27;
			line-height: 32px;
			color: white;
			text-align: center;
			border-radius: 2px;
			box-shadow:0px 0px 1px #000;
		}
		#login_panel #bodyFooter {
			font-size: 12px;
			line-height: 32px;
			border-bottom: 1 solid #C6C6C6;
		}
		#login_panel .header{
			width: 268px;
			height: 50px;
			text-align: center;
			line-height: 46px;
			font-size: 20px;
			border-bottom: 1px solid #DBDBDB;
		}
		.header div{
			float: left;
    		width: 134px;
		}
		#hl{
			border-bottom: 4px solid #F45D27;
		}
		.bgimgT {
			background: url('${contextPath}/static/images/bgimg.jpg') 0px -2px;
		}
		.bgimgB {
			background: url('${contextPath}/static/images/bgimg.jpg') 0px -52px;
		}
	</style>
</head>
<body>

	<div class="container">
			<div class="window">
				<div id="login_panel">
						<div class="header">
							<div id="hl">账号登录</div>
							<div id="hr">用户注册</div>
						</div>
						
						<div  class="body" id="login_body">
							<form action="${contextPath }/login.html" method="post" name='form'>
								<div id="" class="mt bgimgT">
								<input class="input" type="text" name="username"  value="zdc" placeholder="邮箱/会员帐号/手机"/>
							</div>
							<div id="" class="mt bgimgB">
								<input class="input" type="password" name="password"  value="1"  placeholder="请输入密码"/>
							</div>
							<div id="" class="nb">
								<label class="left"><input type="checkbox" name="" id="" value="" /><span>记住我</span></label>
								<span class="right">
									<a href="javascript:alert('看来是真的忘记了!');">忘记密码</a>
								</span>
							</div>
							<div id="loginBtn" class="nb">
								登录
							</div>							
							</form>
							<div id="bodyFooter" style="border-bottom: 1px solid #DBDBDB;" class="nb bodyFooter">
								还没有微博？<a href="javascript:alert('注册成功!');">立即注册</a>!
							</div>
						</div>
						<div id="register_body" style="display: none;">
							<h2 style="text-align: center;">欢迎注册</h2><h6>功能还没做</h6>
						</div>
						
						<div class="footer">
							
						</div>
				</div>		
			</div>
	</div>
</body>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.slim.min.js"></script>
<script type="text/javascript">	
	$(function(){
		$(document).keydown(function(event){ 
			if(event.keyCode==13){ 
				document.form.submit();
			} 
		});		
	}) 
	document.getElementById("loginBtn").onclick=function(){
		document.form.submit();
	}
	document.getElementById("hr").onclick=function(){
		document.getElementById("login_body").style.display = 'none';
		document.getElementById("register_body").style.display = 'block';
	}
</script>
</html>