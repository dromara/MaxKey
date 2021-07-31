$(function () {
	$.platform = $.platform || {};
	$.platform.messages = $.platform.messages || {};
	$.extend($.platform.messages, {
		window:	{
			title		:	'窗口'
		},
		alert:	{
			title		:	'提示信息',
			closeText	:	'关闭'
		},
		conform:{
			title		:	'确认提示',
			yes			:	'确定',
			no			:	'关闭'
		},
		select:{
			alertText	: 	'请选择您要操作的数据'
		},
		del:{
			conformText	: 	'您确定要删除吗?'
		},
		grid:{
			loadtext	:	'正在加载...',
			loadnodata	:	'没有查询到符合数据'
		},
		submit:{
			conformText	:	'您确定要进行此项操作吗?',
			errorText	:	'执行操作失败'
		}
	});
});