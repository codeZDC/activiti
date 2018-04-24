$('#submit_btn').click(function(){
	//提交流程
	$.ajax({
		type : 'POST',
		url : contextPath + 'process/deploy.html',
		cache: false,
		processData: false,
		contentType: false,
		dataType:'text', 
		data : new FormData($('form')[0]),
		success : function(res){
			alert(res);
			parent.location.reload();
		},
		error : function(e){
		}
	});
});