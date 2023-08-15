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
 

/**
 * 
 */
package org.dromara.maxkey.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.logging.LogFactory;

/**
 * @author Crystal
 *
 */
public class DynaBean {
	
	HashMap<String,Object> beanMap;
	/**
	 * 
	 */
	public DynaBean() {
		beanMap=new HashMap<String,Object>();
	}
	
	/**
	 * 
	 */
	public DynaBean(Map<String, Object> map) {
		beanMap=new HashMap<String,Object>();
		mapToDynaBean(map);
	}
	
	public void set(String name,Object value){
		beanMap.put(name, value);
	}
	
	public Object get(String name){
		return beanMap.get(name);
	}
	
	public void remove(String name){
		beanMap.remove(name);
	}
	
	@SuppressWarnings("rawtypes")
    public void displayValues(){
		Iterator<?> beanMapit = beanMap.entrySet().iterator(); 
		int i=1;
		LogFactory.getLog(DynaBean.class).debug("displayValues() *******************************************");
		while (beanMapit.hasNext()) {
	        Map.Entry entry = (Map.Entry) beanMapit.next();
	        String fieldName = entry.getKey().toString();
	        LogFactory.getLog(DynaBean.class).debug("displayValues() Field "+(i++)+" "+fieldName+" : "+beanMap.get(fieldName));
		}
		LogFactory.getLog(DynaBean.class).debug("displayValues() *******************************************");
	}
	
	public <T> Object convertToBean(T bean){
		return BeanConvert.map2Bean((T)bean, beanMap);
	}
	@SuppressWarnings("unchecked")
	public <T> Object createBean(T cls){
		T bean=(T)Instance.newInstance((Class<?>)cls);
		return BeanConvert.map2Bean(bean, beanMap);
	}
	
	public Map<String, Object> toMap(){
		return beanMap;
	}
	
	@SuppressWarnings("rawtypes")
    public DynaBean mapToDynaBean(Map<String, Object> map){
		
		if(map.getClass().getName()=="java.util.HashMap"){
			beanMap=(HashMap<String, Object>)map;
		}else{
			Iterator<?> mapIt = map.entrySet().iterator(); 
			int i=1;
			while (mapIt.hasNext()) {
		        Map.Entry entry = (Map.Entry) mapIt.next();
		        String fieldName = entry.getKey().toString();
		        beanMap.put(fieldName, map.get(fieldName));
		        LogFactory.getLog(DynaBean.class).debug("mapToDynaBean() Field "+(i++)+" "+fieldName+" : "+beanMap.get(fieldName));
			}
		}
		return this;
	}

}
