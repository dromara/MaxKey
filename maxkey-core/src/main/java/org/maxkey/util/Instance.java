/**
 * 
 */
package org.maxkey.util;

import java.lang.reflect.Constructor;

/**
 * @author Crystal
 *
 */
public class Instance {

	/**
	 * 
	 */
	public Instance() {
	}


	public static Object newInstance(String className) {
		Class<?> cls;
		try {
			cls = Class.forName(className);
			Constructor<?> constructor = cls.getConstructor();
			return constructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Object newInstance(Class<?> cls) {
		try {
			Constructor<?> constructor = cls.getConstructor();
			return constructor.newInstance();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object newInstance(String className, Object[] args) {
		Class<?> newClass;
		try {
			newClass = Class.forName(className);
			Class[] argsClass = new Class[args.length];

			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}

			Constructor<?> cons = newClass.getConstructor(argsClass);
			return cons.newInstance(args);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;

	}

	public static <T> Object newInstance(Class<T> cls, Object[] args) {
		try {
			Class[] argsClass = new Class[args.length];

			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}

			Constructor<T> cons = cls.getConstructor(argsClass);
			return cons.newInstance(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}
