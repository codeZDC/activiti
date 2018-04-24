var taskId = $('#taskId').val();
//根据taskId 获取下一个处理人的信息(一个或多个)
//根据taskId获取表单信息


getNextProcess();
getFormMsg();
getHistory();

function getNextProcess(){
	$.getJSON(contextPath + 'process/nextProcess.html',{taskId:taskId},function(res){
		if(res.nextCount!=1){
			buildSelect(res.roles);
		}
	});
}
function getFormMsg(){
	$.getJSON(contextPath+'process/formMsg.html',{taskId:taskId},function(res){
		//返回的就是表单数据
		for ( var key in res) {
			$(':input[name='+key+']').val(res[key]).attr('readonly','readonly');
		}
	})
}
function getHistory(){
	$.getJSON(contextPath+'process/history.html',{taskId:taskId},function(res){
		console.log(res);
		//historyTable
		var container = $('#historyTable tbody').empty();
		$.each(res,function(ind){
			var index =$('<td>').text( ind + 1);
			var assignee =$('<td>').text(this.assignee);
			var assigneeName =$('<td>').text(this.assigneeName);
			var comment =$('<td>').text(this.comment);
			$('<tr>').append(index).append(assignee)
			.append(assigneeName).append(comment).appendTo(container);
		});
	})
}

function buildSelect(roles){
	//list(map) 键id,username
	$('#roleSelect option:not(:first-child)').remove();
	$('#roleSelect').parents('.form-group').show();
	$.each(roles,function(){
		$('#roleSelect').append($('<option value='+this.id+'>').text(this.username));
	})
}
$('#submit_btn').click(function(){
	var param = {};
	param.taskId = taskId;
	param.comment = $('[name=comment]').val();
	if($('#roleSelect').is(':visible'))param.next = $('#roleSelect').val(); 
	$.post(contextPath+'process/accept.html',param,function(res){
		alert(res);
		parent.location.reload();
	},'json');
});

