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
<jsp:include page="common/viewFlowModal.jsp"></jsp:include>
</head>
<body>
	<div style="font-size: 30px; color: cornflowerblue; text-align: center; padding-top: 20px;">请假申请
		<a href="${contextPath }/processDefineImage.html?processKey=leaveKey" target="_blank"
		style="font-size: 10px;color: red;" id="a_view">查看流程图</a></div>
	
	<div style="margin-top: 100px;margin-left: 100px;">
		<form style="margin-left: 25%;" class="form-horizontal" action="${contextPath}/process/startLeaveProcess.html" method="post">
			<div class="form-group">
				<label class="col-sm-2 control-label">请假人</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" name="leavePerson"
						placeholder="请输入请假人姓名">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="ss">请假天数</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" name="leaveDays" id=""
						placeholder="请输入请假人天数">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">请假日期</label>
				<div class="col-sm-6">
					<input type="date" class="form-control" name="leaveDate"
						placeholder="请输入请假天数">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">请假原因</label>
				<div class="col-sm-6">
					<textarea class="form-control" name="remarks" rows="3"></textarea>
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
<script type="text/javascript">
$('#submit_btn').click(function(){
	$('form').submit();
});
</script>
</html>