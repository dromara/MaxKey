/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.dromara.maxkey.web;

/**
 * Web Application Constants define.
 * 
 * @author Crystal.Sea
 *
 */
public class WebConstants {

    public static final  String CURRENT_USER_PASSWORD_SET_TYPE 
                                    = "current_user_password_set_type";

    public static final  String CURRENT_MESSAGE 	= "current_message";
    
    public static final  String CURRENT_INST 		= "current_inst";
    
    public static final   String INST_COOKIE_NAME 	= "mxk_inst";

    // SPRING_SECURITY_SAVED_REQUEST
    public static final  String FIRST_SAVED_REQUEST_PARAMETER 
                                    = "SPRING_SECURITY_SAVED_REQUEST";

    public static final  String KAPTCHA_SESSION_KEY = "kaptcha_session_key";

    public static final  String SINGLE_SIGN_ON_APP_ID = "single_sign_on_app_id";
    
    public static final  String AUTHORIZE_SIGN_ON_APP = "authorize_sign_on_app";
    
    public static final  String AUTHORIZE_SIGN_ON_APP_SAMLV20_ADAPTER 
                                    = "authorize_sign_on_app_samlv20_adapter";
    
    public static final  String KERBEROS_TOKEN_PARAMETER = "kerberosToken";

    public static final  String CAS_SERVICE_PARAMETER = "service";

    public static final  String KERBEROS_USERDOMAIN_PARAMETER = "kerberosUserDomain";

    public static final  String REMEBER_ME_COOKIE = "sign_remeber_me";

    public static final  String JWT_TOKEN_PARAMETER = "jwt";
    
    public static final  String CAS_TICKET_PARAMETER = "ticket";

    public static final  String CURRENT_SINGLESIGNON_URI = "current_singlesignon_uri";

    public static final  String AUTHENTICATION = "current_authentication";
    
    public static final  String SESSION = "current_session";
    
    public static final  String THEME_COOKIE_NAME = "mxk_theme_value";
    
    public static final  String LOGIN_ERROR_SESSION_MESSAGE 
                                    = "login_error_session_message_key";
    
    public static final  String ONLINE_TICKET_PREFIX = "OT";
    
    public static final  String ONLINE_TICKET_NAME = "online_ticket";
    
	public static final  String MXK_METADATA_PREFIX = "mxk_metadata_";
	
    public static final class LOGIN_RESULT{
    	public static final  String SUCCESS 		= "success";
    	public static final  String FAIL 			= "fail";
    	public static final  String PASSWORD_ERROE 	= "password error";
    	public static final  String USER_NOT_EXIST 	= "user not exist";
    	public static final  String USER_LOCKED 	= "locked";
    	public static final  String USER_INACTIVE 	= "inactive";	
    }
    
    public static final String DELIMITER = "-----------------------------------------------------------";

}
