<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
  	.ck-content {
   		min-height: 300px;
	}
</style>
<script type="text/javascript">

</script>
</head>
<body>
<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/notices/update"  class="needs-validation" novalidate>
	 <table  border="0" cellpadding="0" cellspacing="0" class="table table-bordered">
		<tbody>
		<tr style="display:none">
			<th><@locale code="common.text.id" /></th>
			<td nowrap>
				<input id="id" type="text" readonly name="id"  class="form-control"   value="${model.id}"/>
			</td>
		</tr>
		<tr>
			<th><@locale code="notices.title" /></th>
			<td nowrap>
				<input type="text" id="title" name="title" class="form-control" title="" value="${model.title!}"  required="" />
			</td>
		</tr>
		<tr>
				<th><@locale code="notices.content" /></th>
				<td nowrap>
					<textarea name="content" class="editor">${model.content!}</textarea>
				</td>
		</tr>
		<tr>
                <th><@locale code="common.text.description" /></th>
                <td nowrap>
                	<textarea id="description" name="description" class="form-control"  rows="2" cols="20">${model.description!}</textarea>
                </td>
            </tr>
		<tr>
			<td nowrap colspan="2"  class="center">
				<input id="_method" type="hidden" name="_method"  value="post"/>
				<input id="status" type="hidden" name="status"  value="1"/>
	    		<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />">
  				<input class="button btn btn-secondary mr-3"  id="closeBtn"   type="button" value="<@locale code="button.text.cancel" />">	 
			</td>
		</tr>
		</tbody>
	  </table>
</form>
	<script src="<@base />/static/ckeditor5/build/ckeditor.js"></script>
	<script>ClassicEditor
			.create( document.querySelector( '.editor' ), {
				
				toolbar: {
					items: [
						'heading',
						'|',
						'bold',
						'italic',
						'link',
						'bulletedList',
						'numberedList',
						'|',
						'outdent',
						'indent',
						'|',
						'blockQuote',
						'insertTable',
						'mediaEmbed',
						'undo',
						'redo'
					]
				},
				language: 'zh-cn',
				image: {
					toolbar: [
						'imageTextAlternative',
						'imageStyle:full',
						'imageStyle:side'
					]
				},
				table: {
					contentToolbar: [
						'tableColumn',
						'tableRow',
						'mergeTableCells'
					]
				},
				licenseKey: '',
				
				
			} )
			.then( editor => {
				window.editor = editor;
		
				
				
				
		
				
				
				
			} )
			.catch( error => {
				console.error( 'Oops, something went wrong!' );
				console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
				console.warn( 'Build id: ejuf0r2j7w54-jfha1cexgplv' );
				console.error( error );
			} );
	</script>
<div id="orgContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="orgsTree" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
</div>
</body>
</html>