package net.minidev.asm;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class BeansAccessConfig {
	/**
	 * Field type convertor for all classes
	 * 
	 * Convertor classes should contains mapping method Prototyped as:
	 * 
	 * public static DestinationType Method(Object data);
	 * 
	 * @see DefaultConverter
	 */
	//static protected LinkedHashSet<Class<?>> globalMapper = new LinkedHashSet<Class<?>>();

	/**
	 * Field type convertor for custom Class
	 * 
	 * Convertor classes should contains mapping method Prototyped as:
	 * 
	 * public static DestinationType Method(Object data);
	 * 
	 * @see DefaultConverter
	 */
	static protected HashMap<Class<?>, LinkedHashSet<Class<?>>> classMapper = new HashMap<Class<?>, LinkedHashSet<Class<?>>>();

	/**
	 * FiledName remapper for a specific class or interface
	 */
	static protected HashMap<Class<?>, HashMap<String, String>> classFiledNameMapper = new HashMap<Class<?>, HashMap<String, String>>();

	static {
		addTypeMapper(Object.class, DefaultConverter.class);
		addTypeMapper(Object.class, ConvertDate.class);
	}

//	/**
//	 * Field type convertor for all classes
//	 * 
//	 * Convertor classes should contains mapping method Prototyped as:
//	 * 
//	 * public static DestinationType Method(Object data);
//	 * 
//	 * @see DefaultConverter
//	 */
//	public static void addGlobalTypeMapper(Class<?> mapper) {
//		synchronized (globalMapper) {
//			globalMapper.add(mapper);
//		}
//	}

	/**
	 * Field type convertor for all classes
	 * 
	 * Convertor classes should contains mapping method Prototyped as:
	 * 
	 * public static DestinationType Method(Object data);
	 * 
	 * @see DefaultConverter
	 */
	public static void addTypeMapper(Class<?> clz, Class<?> mapper) {
		synchronized (classMapper) {
			LinkedHashSet<Class<?>> h = classMapper.get(clz);
			if (h == null) {
				h = new LinkedHashSet<Class<?>>();
				classMapper.put(clz, h);
			}
			
			h.add(mapper);
		}
	}
}
