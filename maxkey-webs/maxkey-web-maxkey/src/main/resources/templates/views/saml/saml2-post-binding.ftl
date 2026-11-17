<!--
##
## Velocity Template for SAML 2 HTTP-POST binding
##
## Velocity context may contain the following properties
## action - String - the action URL for the form
## binding - String - the SAML binding type in use
## RelayState - String - the relay state for the message
## SAMLRequest - String - the Base64 encoded SAML Request
## SAMLResponse - String - the Base64 encoded SAML Response
-->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
    	<#include   "add-html-head-content.ftl" >
    </head>
    <body onload="document.forms[0].submit()">
        <noscript>
            <p>
                <strong>Note:</strong> Since your browser does not support JavaScript,
                you must press the Continue button once to proceed.
            </p>
        </noscript>
        
        <form action="${action}" method="post">
            <div>
                <#if RelayState??><input type="hidden" name="RelayState" value="${RelayState}"/></#if>
                
                <#if SAMLRequest??><input type="hidden" name="SAMLRequest" value="${SAMLRequest}"/></#if>
                
                <#if SAMLResponse??><input type="hidden" name="SAMLResponse" value="${SAMLResponse}"/></#if>
                
            </div>
            <noscript>
                <div>
                    <input type="submit" value="Continue"/>
                </div>
            </noscript>
        </form>
        	<#include   "add-html-body-content.ftl" >
    </body>
</html>