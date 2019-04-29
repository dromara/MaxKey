package net.minidev.asm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BasicFiledFilter implements FieldFilter {
	public final static BasicFiledFilter SINGLETON = new BasicFiledFilter();

	@Override
	public boolean canUse(Field field) {
		return true;
	}

	@Override
	public boolean canUse(Field field, Method method) {
		return true;
	}

	@Override
	public boolean canRead(Field field) {
		return true;
	}

	@Override
	public boolean canWrite(Field field) {
		return true;
	}

}
