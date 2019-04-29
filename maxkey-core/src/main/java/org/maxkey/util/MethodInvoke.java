/**
 * 
 */
package org.maxkey.util;

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
		Class[] argsClass = new Class[args.length];
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
		Class[] argsClass = new Class[args.length];
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
