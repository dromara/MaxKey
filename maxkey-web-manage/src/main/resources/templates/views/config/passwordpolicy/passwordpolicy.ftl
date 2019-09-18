<%@ page   language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="s" 			uri="http://sso.maxkey.org/tags" %> 
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<jsp:include page="../../layout/header.jsp"></jsp:include>
	<jsp:include page="../../layout/common.cssjs.jsp"></jsp:include>
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
		<jsp:include page="../../layout/top.jsp"></jsp:include>
	</div>
	
	<div class="col-md-3 sidebar-nav side-nav" >
 		<jsp:include page="../../layout/sidenav.jsp"></jsp:include>
	</div>
	<div class="page-container">
	
	<div class="main-content">
					<div class="container-fluid">

						<div class="breadcrumb-wrapper row">
							<div class="col-12 col-lg-3 col-md-6">
								<h4 class="page-title">Dashboard 2</h4>
							</div>
							<div class="col-12 col-lg-9 col-md-6">
								<ol class="breadcrumb float-right">
									<li><a href="index.html">Dashboard</a></li>
									<li class="active">/ Dashboard 2</li>
								</ol>
							</div>
						</div>

					</div>


					<div class="col-12 grid-margin">
						<div class="card">
							<div class="card-header border-bottom">
								<h4 class="card-title">Horizontal Two column</h4>
							</div>
							<div class="card-body">
								<form  method="post" type="label" validate="true" action="<s:Base/>/config/passwordpolicy/update" id="actionForm" >
									<p class="card-description">Personal info</p>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><s:Locale code="passwordpolicy.minlength" />：</label>
												<div class="col-sm-9">
													<input id="id" name="id" type="hidden" value="${model.id}"/>
						   							<input  class="form-control" type="text" id="minLength" name="minLength"  value="${model.minLength}" />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><s:Locale code="passwordpolicy.maxlength" />：</label>
												<div class="col-sm-9">
													<input  class="form-control" type="text" id="maxLength" name="maxLength" value="${model.maxLength}" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><s:Locale code="passwordpolicy.lowercase" />：</label>
												<div class="col-sm-9">
													<input  class="form-control" type="text" id="lowerCase" name="lowerCase"  value="${model.lowerCase}" />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><s:Locale code="passwordpolicy.uppercase" />：</label>
												<div class="col-sm-9">
													<input  class="form-control" type="text" id="upperCase" name="upperCase" value="${model.upperCase}" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><s:Locale code="passwordpolicy.digits" />：</label>
												<div class="col-sm-9">
													<input  class="form-control" type="text" id="digits" name="digits" value="${model.digits}"/>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3"><s:Locale code="passwordpolicy.specialchar" />：</label>
												<div class="col-sm-9">
													<input   class="form-control" type="text" id="specialChar" name="specialChar"  value="${model.specialChar}" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><s:Locale code="passwordpolicy.attempts" />：</label>
												<div class="col-sm-9">
													<input  class="form-control" type="text" id="attempts" name="attempts" value="${model.attempts}" />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><s:Locale code="passwordpolicy.duration" />(Unit:Hour)：</label>
												<div class="col-sm-9">
													<input  class="form-control" type="text" id="duration" name="duration" value="${model.duration}"/>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><s:Locale code="passwordpolicy.expiration" />(Unit:Day)：</label>
												<div class="col-sm-9">
													<input  class="form-control" type="text" id="expiration" name="expiration"  value="${model.expiration}" />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group row">
												<label class="col-sm-3 col-form-label"><s:Locale code="passwordpolicy.username" />：</label>
												<div class="col-sm-9">
													<select  class="form-control"   id="username" name="username"  >
														<option  <c:if test="${1==model.username}">selected</c:if>  value="1"><s:Locale code="common.text.status.3"/></option>
														<option  <c:if test="${0==model.username}">selected</c:if>  value="0"><s:Locale code="common.text.status.4"/></option>
													</select>
												</div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-12">
											<div class="form-group m-b-20">
												<label   style="float: left;" for="simplePasswords"><s:Locale code="passwordpolicy.simplepasswords" />：</label>
												<textarea id="simplePasswords" name="simplePasswords"  class="form-control" >${model.simplePasswords}</textarea>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-3">
											<button type="submit" class="button btn-primary btn btn-common btn-block mr-3"    id="submitBtn" ><s:Locale code="button.text.save" /></button>
										</div>
									</div>
									
								</form>
							</div>
						</div>
					</div>
					<footer class="content-footer">
		<jsp:include page="../../layout/footer.jsp"></jsp:include>
	</footer>

	</div>
	
	</div>
</div>

<div id="preloader">
<div class="loader" id="loader-1"></div>
</div>

</body>
</html>