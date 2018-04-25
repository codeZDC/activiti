var taskId = $('#taskId').val();
var processInstanceId = $('#processInstanceId').val();
//根据taskId 获取下一个处理人的信息(一个或多个)
//根据taskId获取表单信息


getFormMsg();
getHistory();

	
function getFormMsg(){
	$.getJSON(contextPath+'process/formMsg.html',{taskId:taskId,processInstanceId:processInstanceId},function(res){
		//返回的就是表单数据
		for ( var key in res) {
			$(':input[name='+key+']').val(res[key]).attr('readonly','readonly');
		}
	})
}
function getHistory(){
	$.getJSON(contextPath+'process/history.html',{taskId:taskId,processInstanceId:processInstanceId},function(res){
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

