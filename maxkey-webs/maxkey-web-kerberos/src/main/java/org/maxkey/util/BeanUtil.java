package org.maxkey.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtil {
	
	public static void copyBean(Object  origin,Object target) {
		if( origin == null || target == null) return;
		try {				
			BeanUtils.copyProperties( origin, target);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
		
	public static Object cloneSupper(Object origin) {			
		Object target = null;
		if(origin == null) return target;
		try {				
			target = origin.getClass().getSuperclass().newInstance();
			BeanUtils.copyProperties(target,origin);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return target;
	}
		
	public static String getValue(Object bean,String  field ) {
		if(bean == null) return null;
		String retVal = "";
		try {
			retVal = BeanUtils.getProperty(bean, field);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isNotNull(Collection collection) {
		if(collection != null && collection.size() > 0) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isNotNull(Map map) {
		if(map != null && map.size() > 0) {
			return true;
		}
		return false;
	}
}
