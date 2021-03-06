<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link
	href="https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<!-- 请假流程申请流程相关记录页面(审批查看) -->
	<!-- jsp名称格式为 :  流程类型+View.jsp -->
	<input type="hidden" id="taskId" value="${taskId }"> 
	<input type="hidden" id="processInstanceId" value="${processInstanceId }"> 
	<div style="margin-left: 100px;">
		<form class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">请假人</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" name="leavePerson">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="ss">请假天数</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" name="leaveDays" id="">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">请假日期</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" name="leaveDate" readonly="readonly">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">请假原因</label>
				<div class="col-sm-6">
					<textarea class="form-control" name="remarks" rows="3"></textarea>
				</div>
			</div>
		</form>
	</div>
	<div class="container historyDiv">
		<div
			style="font-size: 20px; color: cornflowerblue; text-align: center; padding-top: 20px;">处理流程图</div>
		<table class="table table-bordered" id="historyTable">
			<thead>
				<tr>
					<th>序号</th>
					<th>处理人id</th>
					<th>处理人</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</body>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script src="${contextPath }/static/modules/common.js"></script>
<script src="${contextPath }/static/modules/leaveViewReadonly.js"></script>
</html>