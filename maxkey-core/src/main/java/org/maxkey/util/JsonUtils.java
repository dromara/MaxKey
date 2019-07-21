package org.maxkey.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonUtils {

	/**
	 * Transform json string to java bean object
	 * @param json
	 * @param bean
	 * @return Object
	 */
	public static Object json2Object(String json,Object bean){
		try {
			bean=(new ObjectMapper()).readValue(json, bean.getClass());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
		return bean;
	}
	
	/**
	 * Transform json string to java bean object
	 * @param json
	 * @param Class
	 * @return Object
	 */
	public static <T>  T json2Object(String json,Class<T> cls){
		T bean =null;
		try {
			bean=(new ObjectMapper()).readValue(json, cls);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
		return bean;
	}
	
	/**
	 * Transform  java bean object to json string
	 * @param bean
	 * @return string
	 */
	public static String object2Json(Object bean){
		String json="";
		try {
			json= (new ObjectMapper()).writeValueAsString(bean);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
		return json;
	}
	

	
	
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
	

	
}
