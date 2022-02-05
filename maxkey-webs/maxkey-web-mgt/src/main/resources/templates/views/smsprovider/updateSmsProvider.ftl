<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
</head>
<body> 
<div class="app header-default side-nav-dark">
<div class="layout">
	<div class="header navbar">
		<#include  "../layout/top.ftl"/>
	</div>
	
	<div class="col-md-3 sidebar-nav side-nav" >
 		<#include  "../layout/sidenav.ftl"/>
	</div>
	<div class="page-container">
	
	<div class="main-content">
		<div class="container-fluid">
			<div class="breadcrumb-wrapper row">
				<div class="col-12 col-lg-3 col-md-6">
					<h4 class="page-title"><@locale code="navs.smsprovider"/></h4>
				</div>
				<div class="col-12 col-lg-9 col-md-6">
					<ol class="breadcrumb float-right">
						<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
						<li class="inactive">/ <@locale code="navs.conf"/></li>
						<li class="active">/ <@locale code="navs.smsprovider"/></li>
					</ol>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div class="content-wrapper row">
			<div class="col-12 grid-margin">
				<div class="card">
					<div class="card-header border-bottom">
						<h4 class="card-title"><@locale code="navs.smsprovider"/></h4>
					</div>
					<div class="card-body">
								<form  method="post" type="label" validate="true" action="<@base/>/smsprovider/update" id="actionForm"   class="needs-validation" novalidate>
									<div class="row mb-3">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="smsprovider.providerName" /></label>
												<div class="col-sm-9">
													<input id="id" name="id" type="hidden" value="${model.id!}"/>
						   							<select id="provider" name="provider"  class="form-control form-select">
                                                        <option value="aliyun" <#if 'aliyun'==model.provider>selected</#if> ><@locale code="smsprovider.providerName.aliyun" /></option>
                                                        <option value="tencentcloud"        <#if 'tencentcloud'==model.provider       >selected</#if> ><@locale code="smsprovider.providerName.tencentcloud" /></option>
                                                        <option value="neteasesms"    <#if 'neteasesms'==model.provider   >selected</#if> ><@locale code="smsprovider.providerName.neteasesms" /></option>
                                                        <option value="email"    <#if 'email'==model.provider   >selected</#if> ><@locale code="smsprovider.providerName.email" /></option>
                                                    </select>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="common.text.status" /></label>
												<div class="col-sm-9">
													<select id="status" name="status"  class="form-control  form-select">
                                                        <option value="0" <#if 0==model.status>selected</#if> ><@locale code="common.text.status.disabled" /></option>
                                                        <option value="1" <#if 1==model.status>selected</#if> ><@locale code="common.text.status.enabled" /></option>
                                                    </select>
												</div>
											</div>
										</div>
									</div>
									<div class="row mb-3">
                                            <label class="col-sm-2 col-form-label"><@locale code="smsprovider.message" /></label>
                                            <div class="col-sm-10">
                                                <input required=""  class="form-control" type="text" id="message" name="message" value="${model.message!}" />
                                            </div>
                                    </div>
									<div class="row mb-3">
                                        <div class="col-md-6">
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label"><@locale code="smsprovider.appkey" /></label>
                                                <div class="col-sm-9">
                                                    <input  required="" class="form-control" type="text" id="appKey" name="appKey"  value="${model.appKey!}" />
                                                    
                                                </div>
                                                
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label"><@locale code="smsprovider.appsecret" /></label>
                                                <div class="col-sm-9">
                                                    <input  required="" class="form-control" type="password" id="appSecret" name="appSecret" value="${model.appSecret!}" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
									<div class="row mb-3">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="smsprovider.templateid" /></label>
												<div class="col-sm-9">
													<input  required="" class="form-control" type="text" id="templateId" name="templateId"  value="${model.templateId!}" />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="smsprovider.signname" /></label>
												<div class="col-sm-9">
													<input  required="" class="form-control" type="text" id="signName" name="signName" value="${model.signName!}" />
												</div>
											</div>
										</div>
									</div>
									<div class="row mb-3">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="smsprovider.tencentcloud.smssdkappid" /></label>
												<div class="col-sm-9">
													<input   class="form-control" type="text" id="smsSdkAppId" name="smsSdkAppId" value="${model.smsSdkAppId!}"/>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											
										</div>
									</div>
                                    <div class="row mb-3">
                                        <div class="col-md-12">
                                            <div class="form-group row">
                                                <div class="col-sm-2">
                                                    <label class="col-form-label"><@locale code="common.text.description" /></label>
                                                </div>
                                                <div class="col-sm-10">
                                                    <input   class="form-control" type="text" id="description" name="description"  value="${model.description!}" />
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
					</div>
				</div>
					<footer class="content-footer">
		<#include  "../layout/footer.ftl"/>
	</footer>

	</div>
	
	</div>
</div>

<div id="preloader">
<div class="loader" id="loader-1"></div>
</div>

</body>
</html>