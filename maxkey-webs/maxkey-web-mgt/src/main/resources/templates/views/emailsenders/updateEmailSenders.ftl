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
					<h4 class="page-title"><@locale code="navs.emailsenders"/></h4>
				</div>
				<div class="col-12 col-lg-9 col-md-6">
					<ol class="breadcrumb float-right">
						<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
						<li class="inactive">/ <@locale code="navs.conf"/></li>
						<li class="active">/ <@locale code="navs.emailsenders"/></li>
					</ol>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div class="content-wrapper row">
			<div class="col-12 grid-margin">
				<div class="card">
					<div class="card-header border-bottom">
						<h4 class="card-title"><@locale code="navs.emailsenders"/></h4>
					</div>
					<div class="card-body">
								<form  method="post" type="label" validate="true" action="<@base/>/emailsenders/update" id="actionForm"   class="needs-validation" novalidate>
									<div class="row mb-3">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="common.text.id" /></label>
												<div class="col-sm-9">
													<input class="form-control" readonly id="id" name="id" type="text" value="${model.id!}"/>
						   							
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
                                        <div class="col-md-6">
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label"><@locale code="emailsenders.smtpHost" /></label>
                                                <div class="col-sm-9">
                                                    <input  required="" class="form-control" type="text" id="smtpHost" name="smtpHost"  value="${model.smtpHost!}" />
                                                    
                                                </div>
                                                
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label"><@locale code="emailsenders.port" /></label>
                                                <div class="col-sm-9">
                                                    <input  required="" class="form-control" type="text" id="port" name="port" value="${model.port!}" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
									<div class="row mb-3">
                                        <div class="col-md-6">
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label"><@locale code="emailsenders.account" /></label>
                                                <div class="col-sm-9">
                                                    <input  required="" class="form-control" type="text" id="account" name="account"  value="${model.account!}" />
                                                    
                                                </div>
                                                
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label"><@locale code="emailsenders.credentials" /></label>
                                                <div class="col-sm-9">
                                                    <input  required="" class="form-control" type="password" id="credentials" name="credentials" value="${model.credentials!}" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-md-6">
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label"><@locale code="emailsenders.protocol" /></label>
                                                <div class="col-sm-9">
                                                    <input  required="" class="form-control" type="text" id="protocol" name="protocol"  value="${model.protocol!}" />
                                                    
                                                </div>
                                                
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label"><@locale code="emailsenders.encoding" /></label>
                                                <div class="col-sm-9">
                                                    <input  required="" class="form-control" type="text" id="encoding" name="encoding" value="${model.encoding!}" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
									<div class="row mb-3">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><@locale code="emailsenders.sender" /></label>
												<div class="col-sm-9">
													<input   class="form-control" type="text" id="sender" name="sender" value="${model.sender!}"/>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3"><@locale code="emailsenders.sslSwitch" /></label>
												<div class="col-sm-9">
												    <select id="sslSwitch" name="sslSwitch"  class="form-control  form-select">
                                                        <option value="0" <#if 0==model.sslSwitch>selected</#if> ><@locale code="common.text.no" /></option>
                                                        <option value="1" <#if 1==model.sslSwitch>selected</#if> ><@locale code="common.text.yes" /></option>
                                                    </select>
												</div>
											</div>
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