$(function () {
	$.platform = $.platform || {};
	$.platform.messages = $.platform.messages || {};
	$.extend($.platform.messages, {
		window:	{
			title		:	'Window'
		},
		alert:	{
			title		:	'Information',
			closeText	:	'Close'
		},
		conform:{
			title		:	'Conform',
			yes			:	'YES',
			no			:	'NO'
		},
		select:{
			alertText	: 	'Please select the record'
		},
		del:{
			conformText	: 	'Are you sure you want to delete?'
		},
		grid:{
			loadtext	:	'Data loading ...',
			loadnodata	:	'No matching data found'
		},
		submit:{
			conformText	:	'Are you sure you want to do this?',
			errorText	:	'Failed to perform operation'
		}
	});
});