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
					<h4 class="page-title"><@locale code="navs.institutions"/></h4>
				</div>
				<div class="col-12 col-lg-9 col-md-6">
					<ol class="breadcrumb float-right">
						<li><a href="<@base/>/main"><@locale code="navs.home"/></a></li>
						<li class="inactive">/ <@locale code="navs.conf"/></li>
						<li class="active">/ <@locale code="navs.institutions"/></li>
					</ol>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div class="content-wrapper row">
				<div class="col-12 grid-margin">
					<div class="card">
						<div class="card-header border-bottom">
							<h4 class="card-title"><@locale code="navs.institutions"/></h4>
						</div>
						<form  method="post" type="label" validate="true" action="<@base/>/institutions/update" id="actionForm"   class="needs-validation" novalidate>
                                        
						<div class="card-body">
									<div class="row mb-3">
											<div class="col-md-6">
												<div class="form-group row">
													<label class="col-sm-3 col-form-label"><@locale code="institutions.name" /></label>
													<div class="col-sm-9">
														<input id="id" name="id" type="hidden" value="${model.id!}"/>
							   							<input  required="" class="form-control" type="text" id="name" name="name"  value="${model.name!}" />
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group row">
													<label class="col-sm-3 col-form-label"><@locale code="institutions.fullName" /></label>
													<div class="col-sm-9">
														<input required=""  class="form-control" type="text" id="fullName" name="fullName" value="${model.fullName!}" />
													</div>
												</div>
											</div>
										</div>
										<div class="row mb-3">
											<div class="col-md-6">
												<div class="form-group row">
													<label class="col-sm-3 col-form-label"><@locale code="institutions.division" /></label>
													<div class="col-sm-9">
														<input   class="form-control" type="text" id="division" name="division" value="${model.division!}"/>
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group row">
													<label class="col-sm-3"><@locale code="institutions.contact" /></label>
													<div class="col-sm-9">
														<input    class="form-control" type="text" id="contact" name="contact"  value="${model.contact!}" />
													</div>
												</div>
											</div>
										</div>
										<div class="row mb-3">
											<div class="col-md-6">
												<div class="form-group row">
													<label class="col-sm-3 col-form-label"><@locale code="institutions.phone" /></label>
													<div class="col-sm-9">
														<input   class="form-control" type="text" id="phone" name="phone" value="${model.phone!}" />
													</div>
												</div>
											</div>
											
											<div class="col-md-6">
												<div class="form-group row">
	                                                <label class="col-sm-3 col-form-label"><@locale code="institutions.email" /></label>
	                                                <div class="col-sm-9">
	                                                    <input   class="form-control" type="text" id="email" name="email" value="${model.email!}" />
	                                                </div>
	                                            </div>
											</div>
										</div>
										<div class="row mb-3">
											<div class="col-md-6">
												<div class="form-group row">
	                                                
	                                                <label class="col-sm-3 col-form-label"><@locale code="institutions.fax" /></label>
	                                                <div class="col-sm-9">
	                                                    <input   class="form-control" type="text" id="fax" name="fax" value="${model.fax!}" />
	                                                </div>
	                                            </div>
											</div>
											<div class="col-md-6">
												<div class="form-group row">
	                                                <label class="col-sm-3 col-form-label"><@locale code="institutions.postalcode" /></label>
	                                                <div class="col-sm-9">
	                                                    <input   class="form-control" type="text" id="postalCode" name="postalCode"  value="${model.postalCode!}" />
	                                                </div>
	                                            </div>
											</div>
										</div>
										<div class="row mb-3">
											<div class="col-md-6">
												<div class="form-group row">
	                                                <label class="col-sm-3 col-form-label"><@locale code="institutions.country" /></label>
	                                                <div class="col-sm-9">
	                                                    <input   class="form-control" type="text" id="country" name="country" value="${model.country!}" />
	                                                </div>
	                                            </div>
											</div>
											<div class="col-md-6">
												<div class="form-group row">
	                                                
	                                                <label class="col-sm-3 col-form-label"><@locale code="institutions.region" /></label>
	                                                <div class="col-sm-9">
	                                                    <input   class="form-control" type="text" id="region" name="region" value="${model.region!}" />
	                                                </div>
	                                            </div>
											</div>
										</div>
										<div class="row mb-3">
											<div class="col-md-6">
												<div class="form-group row">
	                                                
	                                                <label class="col-sm-3 col-form-label"><@locale code="institutions.locality" /></label>
	                                                <div class="col-sm-9">
	                                                    <input  class="form-control" type="text" id="locality" name="locality" value="${model.locality!}" />
	                                                </div>
	                                            </div>
											</div>
											<div class="col-md-6">
												<div class="form-group row">
	                                                <label class="col-sm-3 col-form-label"><@locale code="institutions.street" /></label>
	                                                <div class="col-sm-9">
	                                                    <input   class="form-control" type="text" id="street" name="street" value="${model.street!}" />
	                                                </div>
	                                            </div>
											</div>
										</div>
										<div class="row mb-3">
											<div class="col-md-12">
	                                            <div class="form-group row">
	                                                <div class="col-sm-2">
													    <label class="col-form-label"><@locale code="institutions.address" /></label>
													</div>
													<div class="col-sm-10">
														<input   class="form-control" type="text" id="address" name="address"  value="${model.address!}" />
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
										
								</div>
						<div class="card-header border-bottom">
	                        <h4 class="card-title"><@locale code="navs.institutions.system"/></h4>
	                    </div>
	                   <div class="card-body">
                                   
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-group row">
                                                    <label class="col-sm-3 col-form-label"><@locale code="institutions.title" /></label>
                                                    <div class="col-sm-7">
                                                        <input  required="" class="form-control" type="text" id="title" name="title"  value="${model.title!}" />
                                                        
                                                    </div>
                                                    <div class="col-sm-2">
                                                        <input class="button btn btn-primary mr-3 window" type="button" value="<@locale code="common.text.locale"/>" 
                                                            wurl="<@base/>/localization/forward/global.title"
                                                            wwidth="650"
                                                            wheight="200"
                                                            target="window"> 
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group row">
                                                    <label class="col-sm-3 col-form-label"><@locale code="institutions.consoleTitle" /></label>
                                                    <div class="col-sm-7">
                                                        <input  required="" class="form-control" type="text" id="consoleTitle" name="consoleTitle" value="${model.consoleTitle!}" />
                                                    </div>
                                                    <div class="col-sm-2">
                                                        <input class="button btn btn-primary mr-3 window" type="button" value="<@locale code="common.text.locale"/>" 
                                                            wurl="<@base/>/localization/forward/global.consoleTitle"
                                                            wwidth="650"
                                                            wheight="200"
                                                            target="window"> 
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-group row">
                                                    <label class="col-sm-3 col-form-label"><@locale code="institutions.logo" /></label>
                                                    <div class="col-sm-9">
                                                        <input  required="" class="form-control" type="text" id="logo" name="logo"  value="${model.logo!}" />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group row">
                                                    <label class="col-sm-3 col-form-label"><@locale code="institutions.domain" /></label>
                                                    <div class="col-sm-9">
                                                        <input  required="" class="form-control" type="text" id="domain" name="domain" value="${model.domain!}" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <div class="form-group row">
                                                    <label class="col-sm-3 col-form-label"><@locale code="institutions.captchaSupport" /></label>
                                                    <div class="col-sm-9">
                                                        <select  id="captchaSupport" name="captchaSupport" class="form-control  form-select" >
								                            <option value="YES" <#if 'YES' == model.captchaSupport!>selected</#if>><@locale code="common.text.yes"/></option>
								                            <option value="NO" <#if 'NO' == model.captchaSupport!>selected</#if>><@locale code="common.text.no"/></option>
								                          </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group row">
                                                    <label class="col-sm-3"><@locale code="institutions.captcha.type" /></label>
                                                    <div class="col-sm-9">
                                                        <select  id="captcha" name="captcha" class="form-control  form-select" >
                                                            <option value="TEXT" <#if 'none'==model.captcha!>selected</#if>><@locale code="institutions.captcha.type.text"/></option>
                                                            <option value="ARITHMETIC" <#if 'https'==model.captcha!>selected</#if>><@locale code="institutions.captcha.type.arithmetic"/></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="row mb-3">
                                            <div class="col-md-12">
                                                <div class="form-group row">
                                                    <div class="col-sm-2">
                                                        <label class="col-form-label"><@locale code="institutions.default.uri" /></label>
                                                    </div>
                                                    <div class="col-sm-10">
                                                        <input   class="form-control" type="text" id="defaultUri" name="defaultUri"  value="${model.description!}" />
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
                                </div>
                            </form>
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