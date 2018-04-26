///获取我的待处理任务
getMyTask();

function getMyTask(){
	$.getJSON(contextPath+'process/allApply.html',function(res){
		console.log(res);
		var container = $('#tasks_table tbody').empty();
		
		$.each(res,function(ind){
			var index =$('<td>').text( ind + 1);
			var user =$('<td>').text(this.user);
			var applyPerson =$('<td>').text(this.applyPerson);
			var applyTypeName =$('<td>').text(this.applyTypeName);
			var handler = $('<td>').append($('<a href="javascript:void(0);"  class="view_btn">查看详情</a>\
					&nbsp;&nbsp;<a href="javascript:void(0);"  class="viewImage_btn">查看流程图</a>'));
			$('<tr>').attr('taskId',this.taskId).attr('type',this.type)
			.append(index).append(user).append(applyPerson)
			.append(applyTypeName).append(handler).appendTo(container);
		});
	});
}
function getNexts(taskId){
	/*$.getJSON(contextPath + 'process/nextProcess.html',{taskId:taskId},function(res){
		nextCount = res.nextCount;
		roles = res.roles;
	});*/
	$.ajax({
		url:contextPath + 'process/nextProcess.html',
		type:'get',
		dataType:'json',
		data:{taskId:taskId},
		async:false,
		success:function(res){
			nextCount = res.nextCount;
		},
		error:function(){alert('网络错误,请联系管理员!');}
	});
}

//查看详情更多
$(document).on('click','.view_btn',function(){
	var type = $(this).parents('tr').attr('type');
	var taskId = $(this).parents('tr').attr('taskId');
	$('#view_modal').modal();
	$('#view_modal iframe').attr('src',contextPath+'page/'+type+'ViewReadonly/1/'+taskId+'.html');
});
//查看流程图
$(document).on('click','.viewImage_btn',function(){
	var taskId = $(this).parents('tr').attr('taskId');
	//location = contextPath + "processImage.html?temp=1&taskId="+taskId;
	$('#viewFlowImage_modal iframe')
	.attr('src',contextPath + "processImage.html?temp=1&taskId="+taskId);
	$('#viewFlowImage_modal').modal();
});