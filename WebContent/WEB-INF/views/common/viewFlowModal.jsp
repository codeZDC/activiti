<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		iframe body{
    		overflow: hidden;
		}
	</style>
</head>
<body>
<div class="modal" id="viewFlowImage_modal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content"
				style="width: 180%; margin-left: -20%; height: 800px;">
				<div class="modal-header" style="padding: 3px;">
					<button type="button" class="close" style="font-size: x-large;"
						data-dismiss="modal" aria-label="Close">
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
<script type="text/javascript">
$(function(){
	$('#a_view').click(function(){
		$('#viewFlowImage_modal iframe').attr('src',this.href);
		$('#viewFlowImage_modal').modal();
		return false;
	});
})
</script>
</html>