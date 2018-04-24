<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link
	href="https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div
		style="font-size: 30px; color: cornflowerblue; text-align: center; padding-top: 20px;">添加流程</div>
	<div style="margin-top: 100px;margin-left: 100px;">
		<form class="form-horizontal" enctype="multipart/form-data" method="post">
			<div class="form-group">
				<label class="col-sm-2 control-label">流程名称</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" name="deployName"
						placeholder="请输入流程名称">
				</div>
			</div>
			<div class="form-group">
			    <label class="col-sm-2 control-label">流程文件</label>
			    <div class="col-sm-6">
				    <input type="file" name="file">
				    <p style="    font-size: 1px;" class="help-block">请上传流程文件,文件格式为Zip压缩包</p>
			    </div>
			</div>			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-6">
					<button type="button" id="submit_btn" class="btn btn-default">提交</button>
				</div>
			</div>
		</form>
	</div>
</body>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script src="${contextPath }/static/modules/common.js"></script>
<script src="${contextPath }/static/modules/addProcess.js"></script>
</html>