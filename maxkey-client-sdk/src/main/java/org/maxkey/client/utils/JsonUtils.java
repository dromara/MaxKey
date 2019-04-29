package org.maxkey.client.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {


	/**
	 * Transform json string to java bean object use Gson
	 * @param <T>
	 * @param json
	 * @param Class
	 * @return Object
	 */

	public static <T>  T gson2Object(String json,Class<T> cls){
		T newBean  = (new Gson()).fromJson(json, cls);
		return newBean;
	}
	
	
	/**
	 * Transform  java bean object to json string use Gson
	 * @param bean
	 * @return string
	 */
	public static String gson2Json(Object bean){
		String json="";
		// convert java object to JSON format,
		// and returned as JSON formatted string
		json = (new Gson()).toJson(bean);
		
		return json;
	}
	
	/**
	 * prettyJson use Gson
	 * @param bean
	 * @return String
	 */
	public static String gsonPretty(Object bean){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(bean);
		return json;
	}
	
}
