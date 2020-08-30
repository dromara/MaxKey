<select id="userPropertys" name="userPropertys" class="form-control multipleselect"  multiple="multiple"  >
	<option value="uid"  <#if model.userPropertys?contains("uid")>selected</#if> >
		<@locale code="userinfo.id" />
	</option>
	<option value="username"  <#if model.userPropertys?contains("username")>selected</#if> >
		<@locale code="userinfo.username" />
	</option>
	<option value="displayName"  <#if model.userPropertys?contains("displayName")>selected</#if> >
		<@locale code="userinfo.displayName" />
	</option>
	<option value="picture"  <#if model.userPropertys?contains("picture")>selected</#if> >
		<@locale code="userinfo.picture" />
	</option>
	<option value="familyName"  <#if model.userPropertys?contains("familyName")>selected</#if> >
		<@locale code="userinfo.familyName" />
	</option>
	<option value="givenName"  <#if model.userPropertys?contains("givenName")>selected</#if> >
		<@locale code="userinfo.givenName" />
	</option>
	<option value="middleName"  <#if model.userPropertys?contains("middleName")>selected</#if> >
		<@locale code="userinfo.middleName" />
	</option>
	<option value="nickName"  <#if model.userPropertys?contains("nickName")>selected</#if> >
		<@locale code="userinfo.nickName" />
	</option>
	<option value="gender"  <#if model.userPropertys?contains("gender")>selected</#if> >
		<@locale code="userinfo.gender" />
	</option>
	<option value="married"  <#if model.userPropertys?contains("married")>selected</#if> >
		<@locale code="userinfo.married" />
	</option>
	<option value="website"  <#if model.userPropertys?contains("website")>selected</#if> >
		<@locale code="userinfo.website" />
	</option>
	<option value="birthDate"  <#if model.userPropertys?contains("birthDate")>selected</#if> >
		<@locale code="userinfo.birthDate" />
	</option>
	<option value="idtype"  <#if model.userPropertys?contains("idtype")>selected</#if> >
		<@locale code="userinfo.idtype" />
	</option>
	<option value="idCardNo"  <#if model.userPropertys?contains("idCardNo")>selected</#if> >
		<@locale code="userinfo.idCardNo" />
	</option>
	<option value="startWorkDate"  <#if model.userPropertys?contains("startWorkDate")>selected</#if> >
		<@locale code="userinfo.startWorkDate" />
	</option>
	<option value="preferredLanguage"  <#if model.userPropertys?contains("preferredLanguage")>selected</#if> >
		<@locale code="userinfo.preferredLanguage" />
	</option>
	<option value="timeZone"  <#if model.userPropertys?contains("timeZone")>selected</#if> >
		<@locale code="userinfo.timeZone" />
	</option>
	<option value="locale"  <#if model.userPropertys?contains("locale")>selected</#if> >
		<@locale code="userinfo.locale" />
	</option>
	<option value="mobile"  <#if model.userPropertys?contains("mobile")>selected</#if> >
		<@locale code="userinfo.mobile" />
	</option>
	<option value="email"  <#if model.userPropertys?contains("email")>selected</#if> >
		<@locale code="userinfo.email" />
	</option>
	
	<option value="userType"  <#if model.userPropertys?contains("userType")>selected</#if> >
		<@locale code="userinfo.userType" />
	</option>
	<option value="employeeNumber"  <#if model.userPropertys?contains("employeeNumber")>selected</#if> >
		<@locale code="userinfo.employeeNumber" />
	</option>
	<option value="division"  <#if model.userPropertys?contains("division")>selected</#if> >
		<@locale code="userinfo.division" />
	</option>
	<option value="organization"  <#if model.userPropertys?contains("organization")>selected</#if> >
		<@locale code="userinfo.organization" />
	</option>
	<option value="costCenter"  <#if model.userPropertys?contains("costCenter")>selected</#if> >
		<@locale code="userinfo.costCenter" />
	</option>
	<option value="jobTitle"  <#if model.userPropertys?contains("jobTitle")>selected</#if> >
		<@locale code="userinfo.jobTitle" />
	</option>
	<option value="manager"  <#if model.userPropertys?contains("manager")>selected</#if> >
		<@locale code="userinfo.manager" />
	</option>
	<option value="assistant"  <#if model.userPropertys?contains("assistant")>selected</#if> >
		<@locale code="userinfo.assistant" />
	</option>
	<option value="entryDate"  <#if model.userPropertys?contains("entryDate")>selected</#if> >
		<@locale code="userinfo.entryDate" />
	</option>
	<option value="departmentId"  <#if model.userPropertys?contains("departmentId")>selected</#if> >
		<@locale code="userinfo.departmentId" />
	</option>
	<option value="department"  <#if model.userPropertys?contains("department")>selected</#if> >
		<@locale code="userinfo.department" />
	</option>
	<option value="windowsAccount"  <#if model.userPropertys?contains("windowsAccount")>selected</#if> >
		<@locale code="userinfo.windowsAccount" />
	</option>
	
	
</select>