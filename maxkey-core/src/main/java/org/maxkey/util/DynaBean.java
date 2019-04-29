/**
 * 
 */
package org.maxkey.util;

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
