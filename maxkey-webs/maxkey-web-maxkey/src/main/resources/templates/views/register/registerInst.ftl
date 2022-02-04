<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<#include  "../layout/header.ftl">
<#include  "../layout/common.cssjs.ftl">
</head>
<body  >
<div id="top">
	<#include "../layout/nologintop.ftl">
</div>
<div  id="main"  class="container">	
<div class="row">
<div class="col-md-2"></div>
<div class="col-md-8">
				<form id="actionForm" 
					action="<@base/>/signup/register"
					forward="<@base/>/login"
					method="post"   class="needs-validation" novalidate>
					<table  class="table table-bordered">
						<tr>
							<td><@locale code="register.displayName"/></td>
							<td><input required="" class="form-control" type='text' id='displayName'  name='displayName' tabindex="1"/></td>
						</tr>
						<tr>
							<td><@locale code="register.workEmail"/></td>
							<td><input  required="" type="text" id="workEmail" name="workEmail" class="form-control"  title="" value="" tabindex="2"/></td>
						</tr>
						<tr>
                            <td><@locale code="register.workPhone"/></td>
                            <td><input  required="" type="text" id="workPhone" name="workPhone" class="form-control"  title="" value="" tabindex="3"/></td>
                        </tr>
						<tr>
							<td><@locale code="register.instName"/></td>
							<td><input required="" class="form-control" type='text' id='instName'  name='instName' tabindex="4"/></td>
						</tr>
						<tr>
                            <td><@locale code="register.employees"/></td>
                            <td>
                                <select class="form-control" name="employees" >
                                    <option value="20"  selected ><@locale code="register.employees.20" /></option>
                                    <option value="100"  ><@locale code="register.employees.100" /></option>
                                    <option value="500"  ><@locale code="register.employees.500" /></option>
                                    <option value="1000" ><@locale code="register.employees.1000" /></option>
                                    <option value="2000" ><@locale code="register.employees.2000" /></option>
                                </select>
                            </td>
                        </tr>
						<tr>
							<td><@locale code="login.text.captcha"/></td>
							<td><input  required="" class="form-control"  type='text' id="j_captcha" name="captcha"  tabindex="6"  value="" style="float: left;"/><img id="j_captchaimg" class="captcha-image" src="<@base/>/captcha"/></td>
						</tr>
						<tr>
							<td  colspan="2"><input   class="button btn btn-lg btn-primary btn-block" type="submit" value="<@locale code="login.text.register" />"/></td>
						</tr>					
					</table>
				</form>
</div>
<div class="col-md-2"></div>
</div >
</div>
<div id="footer">
	<#include "../layout/footer.ftl">
</div>
</body>
</html>