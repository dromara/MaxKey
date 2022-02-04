<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
</head>
<body> 
<div class="card">
    <div class="card-body">
	<form  method="post" type="label" validate="true" action="<@base/>/localization/update" id="actionForm"   class="needs-validation" novalidate>
		<div class="row mb-3">
			<div class="col-md-6" style="display:none">
				<div class="form-group row">
					<label class="col-sm-3 col-form-label"><@locale code="localization.property" /></label>
					<div class="col-sm-9">
						<input id="id" name="id" type="hidden" value="${model.id!}"/>
						<input  required="" class="form-control" type="text" id="property" name="property"  value="${model.property!}" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group row">
					<label class="col-sm-3 col-form-label"><@locale code="localization.langZh" /></label>
					<div class="col-sm-9">
						<input required=""  class="form-control" type="text" id="langZh" name="langZh" value="${model.langZh!}" />
					</div>
					
				</div>
			</div>
			<div class="col-md-6">
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label"><@locale code="localization.langEn" /></label>
                    <div class="col-sm-9">
                        <input  required="" class="form-control" type="text" id="langEn" name="langEn"  value="${model.langEn!}" />
                        
                    </div>
                </div>
            </div>
		</div>
		
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
			      <input   class="form-control" type="hidden" id="status" name="status"  value="1" />
				<button type="submit" class="button btn-primary btn btn-common btn-block mr-3"    id="submitBtn" ><@locale code="button.text.save" /></button>
			</div>
			<div class="col-md-4"></div>
		</div>
		
	</form>
    </div>
    </div>							
</body>
</html>