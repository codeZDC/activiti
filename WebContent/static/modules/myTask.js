///获取我的待处理任务
getMyTask();

function getMyTask(){
	$.getJSON(contextPath+'process/myTask.html',function(res){
		console.log(res);
		var container = $('#tasks_table tbody').empty();
		
		$.each(res,function(ind){
			var index =$('<td>').text( ind + 1);
			var applyPerson =$('<td>').text(this.applyPerson);
			var user =$('<td>').text(this.user);
			var applyTypeName =$('<td>').text(this.applyTypeName);
			var handler = $('<td>').append($('<a href="javascript:;" class="accept_btn">通过</a>\
					&nbsp;&nbsp;<a href="javascript:void(0);"  class="view_btn">查看详情</a>\
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

//通过,,需要给出通过建议
$(document).on('click','.accept_btn',function(){
	taskId = $(this).parents('tr').attr('taskId');
	getNexts(taskId);
	if(nextCount!=1)
		alert('请查看详情,选择下一个处理部门!');
	else
		$('#accept_modal').modal();
});
//需要获取下一个部门的信息,,如果是多个部门,,就需要下拉框选择一个对应的部门
$('#accept_btn').click(function(){
	//提交到后台 (taskId,commont)
	$.post(contextPath+'process/accept.html',{taskId:taskId,comment:$('[name=comment]').val()},function(res){
		alert(res);
		location.reload();
	},'json');
});

//查看详情更多
$(document).on('click','.view_btn',function(){
	var type = $(this).parents('tr').attr('type');
	var taskId = $(this).parents('tr').attr('taskId');
	$('#view_modal').modal();
	$('#view_modal iframe').attr('src',contextPath+'page/'+type+'View/1/'+taskId+'.html');
});
//查看流程图
$(document).on('click','.viewImage_btn',function(){
	var taskId = $(this).parents('tr').attr('taskId');
	//location = contextPath + "processImage.html?temp=1&taskId="+taskId;
	$('#viewFlowImage_modal iframe')
	.attr('src',contextPath + "processImage.html?temp=1&taskId="+taskId);
	$('#viewFlowImage_modal').modal();
});