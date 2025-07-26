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

import java.lang.reflect.Method;


/**
 * @author Crystal
 * 
 */
public class MethodInvoke {

	/**
	 * 
	 */
	public MethodInvoke() {
	}

	public static Object invokeMethod(Object bean, String methodName,
			Object[] args) throws Exception {
		Class<? extends Object> beanClass = bean.getClass();
		Class<?>[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
			//LogFactory.getLog(MethodInvoke.class).debug("invokeMethod args : "+args[i]+" argsClass:"+argsClass[i]);
		}

		Method method = beanClass.getMethod(methodName, argsClass);
		//LogFactory.getLog(MethodInvoke.class).debug("invokeMethod methodName:"+methodName);
		return method.invoke(bean, args);
	}

	public static Object invokeMethod(Object bean, String methodName)
			throws Exception {
		Class<? extends Object> beanClass = bean.getClass();
		Method method = beanClass.getMethod(methodName);
		//LogFactory.getLog(MethodInvoke.class).debug("invokeMethod methodName:"+methodName);
		return method.invoke(bean, new Object[] {});
	}

	public static Object invokeStaticMethod(Class<?> beanClass, String methodName,
			Object[] args) throws Exception {
		Class<?>[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
			//LogFactory.getLog(MethodInvoke.class).debug("invokeStaticMethod args : "+args[i]+" argsClass:"+argsClass[i]);
		}

		Method method = beanClass.getMethod(methodName, argsClass);
		//LogFactory.getLog(MethodInvoke.class).debug("invokeStaticMethod methodName:"+methodName);
		return method.invoke(null, args);
	}

	public static Object invokeStaticMethod(Class<?> beanClass, String methodName)
			throws Exception {
		Method method = beanClass.getMethod(methodName);
		//LogFactory.getLog(MethodInvoke.class).debug("invokeStaticMethod methodName:"+methodName);
		return method.invoke(null, new Object[] {});
	}

}
