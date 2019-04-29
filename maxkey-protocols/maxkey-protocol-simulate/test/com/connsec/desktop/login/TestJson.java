package com.connsec.desktop.login;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class TestJson {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String json = "{";
		json += "\"name\":\"some name\",";
		json += " \"description\":\"some description\",";
		json += " \"doOnMonth\":11,";
		json += " \"doOnDay\":13,";
		json += " \"doOnYear\":2007";
		json += "}";
		Object obj=JSONValue.parse(json);
		JSONObject obj2=(JSONObject)obj;
		System.out.println(obj2.get("description"));
		
		String key="key";
		System.out.println(key.charAt(0));
		
	}

}
