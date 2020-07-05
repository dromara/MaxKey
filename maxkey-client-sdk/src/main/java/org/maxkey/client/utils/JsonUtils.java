/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
