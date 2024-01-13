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

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.LogFactory;

/**
 * @author Crystal
 *
 */
public class BeanConvert {

	/**
	 * 
	 */
	public BeanConvert() {
		
	}
	
	public static <T> Map<String, Object> bean2Map( T  bean){
		Map <String,Object> mapBean=new HashMap<String,Object>();
		Field[] flds = bean.getClass().getDeclaredFields();
		LogFactory.getLog(BeanConvert.class).debug("bean2Map() *******************************************");
		LogFactory.getLog(BeanConvert.class).debug("bean2Map() "+bean.getClass().getName());
		for (int i = 0; i < flds.length; i++) {
			String fieldName = flds[i].getName();
			if(BeanUtil.isGetProperty(bean.getClass(),fieldName)){
				Object value=BeanUtil.get(bean, fieldName);
				mapBean.put(fieldName,value );
				LogFactory.getLog(BeanConvert.class).debug("bean2Map() field "+(i+1)+" : "+fieldName+" = "+value+" type : "+flds[i].getType());
			}
		}
		LogFactory.getLog(BeanConvert.class).debug("bean2Map() *******************************************");
		return mapBean;
	}
	
	
	public static <T> Object map2Bean(T bean,HashMap<?, ?> valueMap){
		Map<?, ?> beanFiledMap=null;
		try {
			beanFiledMap = BeanUtil.getFields(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(beanFiledMap==null) {
			return bean;
		}
		Iterator<?> fieldit = beanFiledMap.entrySet().iterator(); 
		LogFactory.getLog(BeanConvert.class).debug("map2Bean() *******************************************");
		LogFactory.getLog(BeanConvert.class).debug("map2Bean() "+bean.getClass().getName());
		int i=1;
        while (fieldit.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry entry = (Map.Entry) fieldit.next();
            String fieldName = entry.getKey().toString();
            Object value = null;
            String fieldType=(String)beanFiledMap.get(fieldName);
            if(valueMap.get(fieldName)==null) {
            	continue;
            }
            String fillValue=valueMap.get(fieldName).toString();
            LogFactory.getLog(BeanConvert.class).debug("map2Bean() field "+(i++)+" : "+fieldName+" = "+fillValue+" type : "+fieldType);  
            if(fieldType.equals("java.lang.String")){
            	value=String.valueOf(fillValue);
            }else if(fieldType.equals("int")){
            	value=Integer.parseInt(fillValue);
            }else if(fieldType.equals("java.lang.Integer")){
            	value=Integer.parseInt(fillValue);
            }else if(fieldType.equals("long")){
            	value=Long.parseLong(fillValue);
            }else if(fieldType.equals("java.lang.Long")){
            	value= Long.parseLong(fillValue);
            }else if(fieldType.equals("double")){
            	value=(double)Double.valueOf(fillValue);
            }else if(fieldType.equals("java.lang.Double")){
            	value=Double.valueOf(fillValue);
            }else if(fieldType.equals("float")){
            	value=Float.parseFloat(fillValue);
            }else if(fieldType.equals("java.lang.Float")){
            	value=Float.parseFloat(fillValue);
            }else if(fieldType.equals("java.util.Date")){ 
            	try {
            		if(fillValue.length()==10){
            			fillValue+=" 00:00:00";
            			value=(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse(fillValue);
            		}else{
            			continue;
            		}
				} catch (ParseException e) {
					e.printStackTrace();
				}
            }else if(fieldType.equals("java.lang.Object")){
            	value=valueMap.get(fieldName);
            }else if(fieldType.equals("char")){
            	value=Character.valueOf(fillValue.charAt(0));
            }else if(fieldType.equals("boolean")){
            	value=Boolean.parseBoolean(fillValue);
            }else if(fieldType.equals("short")){
            	value=Short.parseShort(fillValue);
            }else if(fieldType.equals("byte")){
            	value=Byte.parseByte(fillValue);
            }

           BeanUtil.set(bean, fieldName, value);   
        }
		LogFactory.getLog(BeanConvert.class).debug("map2Bean() *******************************************");
		return bean;
	}
}
