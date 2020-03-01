package org.maxkey.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.maxkey.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtraAttrs {
	final static Logger _logger = LoggerFactory.getLogger(ExtraAttrs.class);
	
	ArrayList <ExtraAttr> extraAttrs ;

	
	/**
	 * 
	 */
	public ExtraAttrs() {
		super();
	}
	
	/**
	 * 
	 */
	public ExtraAttrs(String arrayJsonString) {
		String extraAttrsJsonString= "{\"extraAttrs\":"+arrayJsonString+"}";
		_logger.debug("Extra Attrs Json String " +extraAttrsJsonString);
		ExtraAttrs extraAttrs=JsonUtils.gson2Object(extraAttrsJsonString, ExtraAttrs.class);
		this.extraAttrs=extraAttrs.getExtraAttrs();
	}
	
	

	public void put(String attr,String value) {
		if(extraAttrs==null){
			extraAttrs=new ArrayList<ExtraAttr>();
		}
		this.extraAttrs.add(new ExtraAttr(attr,value));
	}
	
	public void put(String attr,String type,String value) {
		if(extraAttrs==null){
			extraAttrs=new ArrayList<ExtraAttr>();
		}
		this.extraAttrs.add(new ExtraAttr(attr,type,value));
	}
	
	public String get(String attr) {
		String value=null;
		if(extraAttrs!=null&& extraAttrs.size()!=0){
			for(ExtraAttr extraAttr :extraAttrs){
				if(extraAttr.getAttr().equals(attr)){
					value=extraAttr.getValue();
				}
			}
		}
		return value;
	}
	
	public String toJsonString(){
		String jsonString =JsonUtils.gson2Json(extraAttrs);
		_logger.debug("jsonString " +jsonString);
		return jsonString;
	}
	
	public HashMap<String,String > toJsonHashMap(){
		HashMap<String,String > extraAttrsHashMap=new HashMap<String,String >();
		for(ExtraAttr extraAttr :extraAttrs){
			extraAttrsHashMap.put(extraAttr.getAttr(), extraAttr.getValue());
		}
		_logger.debug("extraAttrs HashMap " +extraAttrsHashMap);
		return extraAttrsHashMap;
	}
	
	public Properties toProperties(){
		Properties properties=new Properties();
		for(ExtraAttr extraAttr :extraAttrs){
			properties.put(extraAttr.getAttr(), extraAttr.getValue());
		}
		_logger.debug("extraAttrs HashMap " +properties);
		return properties;
	}
	
	public ArrayList<ExtraAttr> getExtraAttrs() {
		return extraAttrs;
	}

	public void setExtraAttrs(ArrayList<ExtraAttr> extraAttrs) {
		this.extraAttrs = extraAttrs;
	}
	
	@Override
	public String toString() {
		return "ExtraAttrs [extraAttrs=" + extraAttrs + "]";
	}
	
	
}
