package net.minidev.asm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * allow to control read/write access to field
 * 
 */
public interface FieldFilter {
	/**
	 * NOT Implemented YET
	 */
	public boolean canUse(Field field);

	public boolean canUse(Field field, Method method);

	/**
	 * NOT Implemented YET
	 */
	public boolean canRead(Field field);

	/**
	 * NOT Implemented YET
	 */
	public boolean canWrite(Field field);
}
