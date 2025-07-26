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
			Class<?>[] argsClass = new Class[args.length];

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
			Class<?>[] argsClass = new Class[args.length];

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
