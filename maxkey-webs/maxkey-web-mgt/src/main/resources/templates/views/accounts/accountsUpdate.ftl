<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <#include  "../layout/header.ftl"/>
    <#include  "../layout/common.cssjs.ftl"/>
    <link type="text/css" rel="stylesheet"  href="<@base />/static/css/minitable.css"/>
    <script type="text/javascript">
    $(function(){
        $("#relatedUsername").focus(function(){
          if($("#relatedUsername").val()==""){
            $("#relatedUsername").val($("#username").val());
          }
        });
        
        $("#generateSecret").on("click",function(){
            $.post("<@base/>/userinfo/randomPassword/", {_method:"post",currTime:(new Date()).getTime()}, function(data) {
                $("#relatedPassword").val(data+"");
            }); 
        });
        $("#view").on("click",function(){
            if($("#relatedPassword").attr("type")=="text"){
                $("#relatedPassword").attr("type","password");
            }else{
                $("#relatedPassword").attr("type","text");
            }
        });
    });
    </script>
</head>
<body>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/accounts/update"  class="needs-validation" novalidate>
    <table border="0" cellpadding="0" cellspacing="0" class="table table-bordered" >
        <tbody>
            <tr style="display:none">
                <th><@locale code="userinfo.id" /></th>
                <td nowrap>
                    <input  required="" type="text" id="id" name="id" readonly  class="form-control" title="" value="${model.id!}"/>
    
                </td>
            </tr>
            <tr>
                <th style="width: 150px;"><@locale code="userinfo.username" /></th>
                <td nowrap  style="width:400px">
                    <input  required="" readonly type="text" id="username" name="username"  class="form-control username" title="" value="${model.username!}"   required="" />
                    
                </td>
            </tr>
            <tr>
                <th><@locale code="userinfo.displayName" /></th>
                <td nowrap>
                    <input  required="" readonly  type="text" id="displayName" name="displayName"  class="form-control displayName"  title="" value="${model.displayName!}"   required="" />
            
                </td>
            </tr>
            <tr>
                <th><@locale code="apps.name" /></th>
                <td nowrap>
                    <input  required="" readonly  type="text" id="appName" name="appName"  class="form-control appName" title="" value="${model.appName!}"   required="" />
                    
                    
                </td>
            </tr>
            <tr>
                <th><@locale code="account.relatedUsername" /></th>
                <td nowrap>
                    <input type="text" id="relatedUsername" name="relatedUsername"  class="form-control" title="" value="${model.relatedUsername!}"    required="" />

                </td>
            </tr>
            <tr>
                <th><@locale code="account.relatedPassword" /></th>
                <td nowrap>
                    <div class="input-group mb-3" style="vertical-align: middle;">
                        <input type="password" id="relatedPassword" name="relatedPassword"  class="form-control" title="" value="${model.relatedPassword!}"  required="" />
                        <input id="generateSecret" type="button" class="button btn btn-primary mr-3" style="width:75px"  value="<@locale code="button.text.generate"/>"/>
                        <input id="view" type="button" class="button btn btn-primary mr-3" style="width:75px"  value="<@locale code="button.text.view"/>"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th><@locale code="common.text.status" /></th>
                <td nowrap>
                    <select  id="status" name="status" class="form-control  form-select" >
                        <option value="1" <#if 1==model.status!>selected</#if>><@locale code="common.text.status.activate"/></option>
                        <option value="2" <#if 2==model.status!>selected</#if>><@locale code="common.text.status.inactive"/></option>
                        <option value="5" <#if 5==model.status!>selected</#if>><@locale code="common.text.status.lock"/></option>
                        <option value="9" <#if 9==model.status!>selected</#if>><@locale code="common.text.status.delete"/></option>
                    
                    </select>
                </td>
             </tr>
            <tr>
                <td colspan="2"  class="center">
                    <input id="_method" type="hidden" name="_method"  value="post"/>
                    <input type="hidden" id="userId" name="userId" class="userId" title="" value="${model.userId!}"/>
                    <input type="hidden" id="appId" name="appId" class="appId" title="" value="${model.appId!}"/>
                    <input class="button btn btn-primary mr-3"  type="submit"    id="submitBtn" value="<@locale code="button.text.save" />"/>
                    <input class="button btn btn-secondary mr-3"  type="button"    id="closeBtn" value="<@locale code="button.text.cancel" /> "/>   
                    
                </td>
            </tr>
        </tbody>
    </table>
</form>
</body>
</html>