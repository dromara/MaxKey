package com.connsec.desktop.login;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


/**
 * @author Crystal.Sea
 *
 */
public class JsonResolve {

	public static  ExeParam resolve(String json) {
		
		ExeParam exeParam=new ExeParam();
		if(null!=json){
			Object parseObj=JSONValue.parse(json);
			JSONObject jsonObj=(JSONObject)parseObj;
			//program Execute physical  Path
			if(null!=jsonObj.get("programPath")){
				exeParam.setProgramPath(jsonObj.get("programPath").toString());
				System.out.println("programPath :  "+jsonObj.get("programPath").toString());
			}
			
			//parameter
			if(null!=jsonObj.get("parameter")){
				exeParam.setParameter(jsonObj.get("parameter").toString());
				System.out.println("parameter :  "+jsonObj.get("parameter").toString());
			}
			
			if(null!=jsonObj.get("preUsername")){
				exeParam.setPreUsername("submitKey :  "+jsonObj.get("preUsername").toString());
			}
			if(null!=jsonObj.get("usernameType")){
				exeParam.setUsernameType(jsonObj.get("usernameType").toString());
				System.out.println("usernameType :  "+jsonObj.get("usernameType").toString());
			}
			if(null!=jsonObj.get("usernameParameter")){
				exeParam.setUsernameParameter(jsonObj.get("usernameParameter").toString());
				System.out.println("usernameParameter :  "+jsonObj.get("usernameParameter").toString());
			}
			//username
			if(null!=jsonObj.get("username")){
				exeParam.setUsername(jsonObj.get("username").toString());
				System.out.println("username :  "+jsonObj.get("username").toString());
			}
			
			//password
			if(null!=jsonObj.get("prePassword")){
				exeParam.setPrePassword("submitKey :  "+jsonObj.get("prePassword").toString());
			}
			if(null!=jsonObj.get("passwordType")){
				exeParam.setPasswordType(jsonObj.get("passwordType").toString());
				System.out.println("passwordType :  "+jsonObj.get("passwordType").toString());
			}
			if(null!=jsonObj.get("passwordParameter")){
				exeParam.setPasswordParameter(jsonObj.get("passwordParameter").toString());
				System.out.println("passwordParameter :  "+jsonObj.get("passwordParameter").toString());
			}
			if(null!=jsonObj.get("password")){
				exeParam.setPassword(jsonObj.get("password").toString());
				System.out.println("password :  ******");
			}
			//submit
			if(null!=jsonObj.get("preSubmit")){
				exeParam.setPreSubmit(jsonObj.get("preSubmit").toString());
				System.out.println("preSubmit :  "+jsonObj.get("preSubmit").toString());
			}
			if(null!=jsonObj.get("submitType")){
				exeParam.setSubmitType(jsonObj.get("submitType").toString());
				System.out.println("submitType :  "+jsonObj.get("submitType").toString());
			}
			if(null!=jsonObj.get("submitKey")){
				exeParam.setSubmitKey(jsonObj.get("submitKey").toString());
				System.out.println("submitKey :  "+jsonObj.get("submitKey").toString());
			}
		}
		
		return exeParam;
		
	}
}
