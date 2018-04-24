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
<link
	href="${contextPath }/static/modules/common.css"
	rel="stylesheet">
<style type="text/css">
	table {
	text-align: center;
}
</style>
</head>
<body>
	<div class="container">
		<div
			style="font-size: 30px; color: cornflowerblue; text-align: center; padding-top: 20px;">我的待处理文件</div>
		<table class="table table-bordered" id="tasks_table">
			<thead>
				<tr>
					<th>序号</th>
					<th>申请人</th>
					<th>申请类型</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	
	<div class="modal" id="accept_modal" tabindex="-1" role="dialog">
		<div class="modal-dialog w-250" role="document">
			<div class="modal-content">
				<div class="modal-header" style="padding: 3px;">
					<button type="button" class="close" style="font-size: x-large;" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					请输入意见:<br><textarea type="text"  name="comment"></textarea>
				</div>
				<div class="modal-footer" style="padding: 5px;">
					<button type="button" class="btn btn-xs btn-primary" id="accept_btn">确认</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="view_modal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content" style="width: 180%;margin-left: -20%; height: 800px;">
				<div class="modal-header" style="padding: 3px;">
					<button type="button" class="close" style="font-size: x-large;" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body" style="height: 80%">
					<iframe src="" width="100%" height="100%" style="border: 0"></iframe>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script src="${contextPath }/static/modules/common.js"></script>
<script src="${contextPath }/static/modules/myTask.js"></script>
</html>