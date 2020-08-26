<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<script type="text/javascript">
			function beforeAction() {
				$("label[for='maxLength']").html("");
				$("label[for='specialChar']").html("");
				var minLength = $("#minLength").val();
				var maxLength = $("#maxLength").val();
				var lowerCase = $("#lowerCase").val();
				var upperCase = $("#upperCase").val();
				var digits = $("#digits").val();
				var specialChar = $("#specialChar").val();
				if(parseInt(minLength) > parseInt(maxLength)) {
					$("label[for='maxLength']").html("");
					return false;
				}
				if(parseInt(lowerCase)+parseInt(upperCase)+parseInt(digits)+parseInt(specialChar) > parseInt(maxLength)) {
					$("label[for='specialChar']").html("");
					return false;
				}
				if(parseInt(lowerCase)+parseInt(upperCase)+parseInt(digits)+parseInt(specialChar) < parseInt(minLength)) {
					$("label[for='specialChar']").html("");
					return false;
				}
				return true;
			}
		</script>
</head>
<body> 
<div class="app header-default side-nav-dark">
<div class="layout">
	<div class="header navbar">
		<#include  "../../layout/top.ftl"/>
	</div>
	
	<div class="col-md-3 sidebar-nav side-nav" >
 		<#include  "../../layout/sidenav.ftl"/>
	</div>
	<div class="page-container">
	
	<div class="main-content">
		<div class="container-fluid">
			<div class="breadcrumb-wrapper row">
				<div class="col-12 col-lg-3 col-md-6">
					<h4 class="page-title"><@locale code="navs.conf.passwordpolicy"/></h4>
				</div>
				<div class="col-12 col-lg-9 col-md-6">
					<ol class="breadcrumb float-right">
						<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
						<li class="inactive">/ <@locale code="navs.conf"/></li>
						<li class="active">/ <@locale code="navs.conf.passwordpolicy"/></li>
					</ol>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-12 grid-margin">
				<div class="card">
					<div class="card-header border-bottom">
						<h4 class="card-title"><@locale code="login.passwordpolicy"/></h4>
					</div>
					<div class="card-body">
								<form  method="post" type="label" validate="true" action="<@base/>/config/passwordpolicy/update" id="actionForm"   class="needs-validation" novalidate>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.minlength" />：</label>
												<div class="col-sm-9">
													<input id="id" name="id" type="hidden" value="${model.id!}"/>
						   							<input  required="" class="form-control" type="text" id="minLength" name="minLength"  value="${model.minLength!}" />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.maxlength" />：</label>
												<div class="col-sm-9">
													<input required=""  class="form-control" type="text" id="maxLength" name="maxLength" value="${model.maxLength!}" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.lowercase" />：</label>
												<div class="col-sm-9">
													<input  required="" class="form-control" type="text" id="lowerCase" name="lowerCase"  value="${model.lowerCase!}" />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.uppercase" />：</label>
												<div class="col-sm-9">
													<input  required="" class="form-control" type="text" id="upperCase" name="upperCase" value="${model.upperCase!}" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.digits" />：</label>
												<div class="col-sm-9">
													<input  required="" class="form-control" type="text" id="digits" name="digits" value="${model.digits!}"/>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3"><@locale code="login.passwordpolicy.specialchar" />：</label>
												<div class="col-sm-9">
													<input  required=""  class="form-control" type="text" id="specialChar" name="specialChar"  value="${model.specialChar!}" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.occurances" />：</label>
												<div class="col-sm-9">
													<input  required="" class="form-control" type="text" id="occurances" name="occurances" value="${model.occurances}" />
												</div>
											</div>
										</div>
										
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.username" />：</label>
												<div class="col-sm-9">
													<select  class="form-control"   id="username" name="username"  >
														<option  <#if 1==model.username>selected</#if>  value="1"><@locale code="common.text.status.enabled"/></option>
														<option  <#if 0==model.username>selected</#if>  value="0"><@locale code="common.text.status.disabled"/></option>
													</select>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.alphabetical" />：</label>
												<div class="col-sm-9">
													<select  class="form-control"   id="alphabetical" name="alphabetical"  >
														<option  <#if 1==model.alphabetical>selected</#if>  value="1"><@locale code="common.text.status.enabled"/></option>
														<option  <#if 0==model.alphabetical>selected</#if>  value="0"><@locale code="common.text.status.disabled"/></option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.numerical" />：</label>
												<div class="col-sm-9">
													<select  class="form-control"   id="numerical" name="numerical"  >
														<option  <#if 1==model.numerical>selected</#if>  value="1"><@locale code="common.text.status.enabled"/></option>
														<option  <#if 0==model.numerical>selected</#if>  value="0"><@locale code="common.text.status.disabled"/></option>
													</select>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.qwerty" />：</label>
												<div class="col-sm-9">
													<select  class="form-control"   id="qwerty" name="qwerty"  >
														<option  <#if 1==model.qwerty>selected</#if>  value="1"><@locale code="common.text.status.enabled"/></option>
														<option  <#if 0==model.qwerty>selected</#if>  value="0"><@locale code="common.text.status.disabled"/></option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.dictionary" />：</label>
												<div class="col-sm-9">
													<select  class="form-control"   id="dictionary" name="dictionary"  >
														<option  <#if 1==model.dictionary>selected</#if>  value="1"><@locale code="common.text.status.enabled"/></option>
														<option  <#if 0==model.dictionary>selected</#if>  value="0"><@locale code="common.text.status.disabled"/></option>
													</select>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.attempts" />：</label>
												<div class="col-sm-9">
													<input  required="" class="form-control" type="text" id="attempts" name="attempts" value="${model.attempts}" />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.duration" />(Unit:Hour)：</label>
												<div class="col-sm-9">
													<input  required="" class="form-control" type="text" id="duration" name="duration" value="${model.duration!}"/>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.expiration" />(Unit:Day)：</label>
												<div class="col-sm-9">
													<input  required="" class="form-control" type="text" id="expiration" name="expiration"  value="${model.expiration!}" />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="login.passwordpolicy.history" />：</label>
												<div class="col-sm-9">
													<input  required="" class="form-control" type="text" id="history" name="history"  value="${model.history!}" />
												</div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-4"></div>
										<div class="col-md-4">
											<button type="submit" class="button btn-primary btn btn-common btn-block mr-3"    id="submitBtn" ><@locale code="button.text.save" /></button>
										</div>
										<div class="col-md-4"></div>
									</div>
									
								</form>
							</div>
						</div>
					</div>
					<footer class="content-footer">
		<#include  "../../layout/footer.ftl"/>
	</footer>

	</div>
	
	</div>
</div>

<div id="preloader">
<div class="loader" id="loader-1"></div>
</div>

</body>
</html>