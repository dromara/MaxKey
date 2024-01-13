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
 

package org.dromara.maxkey.pretty.impl;

import java.io.IOException;

import org.dromara.maxkey.pretty.Pretty;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class JsonPretty  implements Pretty{

	static JsonPretty instance ;
	
	public JsonPretty() {

	}

	public static JsonPretty getInstance() {
		if (null == instance) {
			synchronized (JsonPretty.class) {
				if (instance == null) {
					instance = new JsonPretty();
				}
			}
		}
		return instance;
	}
	
	/**
	 * prettyJson use jackson
	 * @param bean
	 * @return String
	 */
	public  String jacksonFormat(Object bean){
		String prettyJson = "";
		try {
			prettyJson = (new ObjectMapper())
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(bean);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prettyJson;
	}
	
	/**
	 * prettyJson use Gson
	 * @param bean
	 * @return String
	 */
	public  String format(Object bean){
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		String json = gson.toJson(bean);
		return json;
	}
	
	/**
	 * prettyJson use Gson , htmlEscaping 
	 * @param bean
	 * @return String
	 */
	public  String format(Object bean,boolean htmlEscaping){
		if(!htmlEscaping) {
			return format(bean);
		}
		
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.disableHtmlEscaping()
				.create();
		String json = gson.toJson(bean);
		return json;
	}
	
	/**
	 * prettyJson use Gson
	 * @param bean
	 * @return String
	 */
	public  String formatln(Object bean){
		return LINE_BREAK + format(bean);
	}
	
	/**
	 * prettyJson use Gson
	 * @param JSON String
	 * @return String
	 */
	@Override
	public  String format(String  jsonString){
		return format(JsonParser.parseString(jsonString));
	}

	@Override
	public String formatln(String source) {
		return LINE_BREAK + format(source);
	}
	
}
