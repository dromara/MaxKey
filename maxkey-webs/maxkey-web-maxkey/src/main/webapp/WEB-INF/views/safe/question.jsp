<%@ page    language="java"  	import="java.util.*"   pageEncoding="UTF-8"%>
<%@ taglib  prefix="spring"  	uri="http://www.springframework.org/tags" %>
<%@ taglib  prefix="fmt"     	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib 	prefix="s" 			uri="http://sso.maxkey.org/tags" %>
<%@ taglib  prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<table width="100%">
  <tr>
    <td>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<s:Base/>/safe/question"> 
	<input type="hidden" id="id" name="id"  title="" value="${model.id}"/>
	  <table   class="datatable">
			<tbody>
			<tr>
				<th  colspan="2"><s:Locale code="access.security.question.setting" /></th>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.displayName" /> :</th>
				<td>
					<input readonly type="text" id="displayName" name="displayName" class="required" title="" value="${model.displayName}"/>
					
				</td>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.username" /> :</th>
				<td>
					<input readonly type="text" id="username" name="username" class="required" title="" value="${model.username}"/>
					
				</td>
			</tr>
			<tr>
				<th><s:Locale code="access.security.question.passwordQuestion" /> :</th>
				<td>
					<select name="passwordQuestion" id="passwordQuestion">
						<option value="0"  <c:if test="${0==model.passwordQuestion}">selected</c:if>  >请选择密保问题</option>
						<option value="1"  <c:if test="${1==model.passwordQuestion}">selected</c:if>  >我第一份工作做什么？</option>
						<option value="2"  <c:if test="${2==model.passwordQuestion}">selected</c:if>  >我妈妈的姓名是？</option>
						<option value="3"  <c:if test="${3==model.passwordQuestion}">selected</c:if>  >我妈妈的生日？</option>
						<option value="4"  <c:if test="${4==model.passwordQuestion}">selected</c:if>  >我父亲的姓名是？</option>
						<option value="5"  <c:if test="${5==model.passwordQuestion}">selected</c:if>  >我父亲的生日？</option>
						<option value="6"  <c:if test="${6==model.passwordQuestion}">selected</c:if>  >我最爱的人的名字？</option>
						<option value="7"  <c:if test="${7==model.passwordQuestion}">selected</c:if>  >我最爱的人的生日是？</option>
						<option value="8"  <c:if test="${8==model.passwordQuestion}">selected</c:if>  >我最爱的电影？</option>
						<option value="9"  <c:if test="${9==model.passwordQuestion}">selected</c:if>  >我最喜欢的歌曲？</option>
						<option value="10" <c:if test="${10==model.passwordQuestion}">selected</c:if> >我最喜欢的食物？</option>	
						<option value="11" <c:if test="${11==model.passwordQuestion}">selected</c:if> >我最喜欢的休闲运动是什么？</option>
						<option value="12" <c:if test="${12==model.passwordQuestion}">selected</c:if> >我最喜欢的运动员是谁？</option>
						<option value="13" <c:if test="${13==model.passwordQuestion}">selected</c:if> >我第一所学校是？</option>
						<option value="14" <c:if test="${14==model.passwordQuestion}">selected</c:if> >我的学号（或工号）是？</option>
						<option value="15" <c:if test="${15==model.passwordQuestion}">selected</c:if> >我小学班主任的名字是？</option>
						<option value="16" <c:if test="${16==model.passwordQuestion}">selected</c:if> >我初中班主任的名字是？</option>
						<option value="17" <c:if test="${17==model.passwordQuestion}">selected</c:if> >我高中班主任的名字是？</option>
						<option value="18" <c:if test="${18==model.passwordQuestion}">selected</c:if> >我最熟悉的童年好友名字是？</option>
						<option value="19" <c:if test="${19==model.passwordQuestion}">selected</c:if> >我最熟悉的学校宿舍室友名字是？</option>
						<option value="20" <c:if test="${20==model.passwordQuestion}">selected</c:if> >对我影响最大的人名字是？</option>
					</select>
				</td>
			</tr>
			<tr>
				<th><s:Locale code="access.security.question.passwordAnswer" />:</th>
				<td>
					<input type="text" id="passwordAnswer" name="passwordAnswer" class=" required" title="" value="${model.passwordAnswer}"/>
					<b class="orange">*</b>
					<label for="newPassword"></label>
				</td>
			</tr>
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
		    		<input class="button" style="width:100px"  type="button"    id="submitBtn" value="<s:Locale code="button.text.save" />"/>
					
				</td>
			</tr>
		</tbody>
	  </table>
</form>
</td>
  </tr>
</table>