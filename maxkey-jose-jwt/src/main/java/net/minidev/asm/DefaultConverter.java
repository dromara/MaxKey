package net.minidev.asm;

import net.minidev.asm.ex.ConvertException;

public class DefaultConverter {
	public static int convertToint(Object obj) {
		if (obj == null)
			return 0;
		if (obj instanceof Number)
			return ((Number) obj).intValue();
		if (obj instanceof String)
			return Integer.parseInt((String) obj);
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to int");
	}

	public static Integer convertToInt(Object obj) {
		if (obj == null)
			return null;
		Class<?> c = obj.getClass();
		if (c == Integer.class)
			return (Integer) obj;
		if (obj instanceof Number)
			return Integer.valueOf(((Number) obj).intValue());
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to Integer");
	}

	public static short convertToshort(Object obj) {
		if (obj == null)
			return 0;
		if (obj instanceof Number)
			return ((Number) obj).shortValue();
		if (obj instanceof String)
			return Short.parseShort((String) obj);
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to short");
	}

	public static Short convertToShort(Object obj) {
		if (obj == null)
			return null;
		Class<?> c = obj.getClass();
		if (c == Short.class)
			return (Short) obj;
		if (obj instanceof Number)
			return Short.valueOf(((Number) obj).shortValue());
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to Short");
	}

	public static long convertTolong(Object obj) {
		if (obj == null)
			return 0;
		if (obj instanceof Number)
			return ((Number) obj).longValue();
		if (obj instanceof String)
			return Long.parseLong((String) obj);
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to long");
	}

	public static Long convertToLong(Object obj) {
		if (obj == null)
			return null;
		Class<?> c = obj.getClass();
		if (c == Long.class)
			return (Long) obj;
		if (obj instanceof Number)
			return Long.valueOf(((Number) obj).longValue());
		throw new ConvertException("Primitive: Can not convert value '" + obj+ "' As " + obj.getClass().getName() + " to Long");
	}

	public static byte convertTobyte(Object obj) {
		if (obj == null)
			return 0;
		if (obj instanceof Number)
			return ((Number) obj).byteValue();
		if (obj instanceof String)
			return Byte.parseByte((String) obj);
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to byte");
	}

	public static Byte convertToByte(Object obj) {
		if (obj == null)
			return null;
		Class<?> c = obj.getClass();
		if (c == Byte.class)
			return (Byte) obj;
		if (obj instanceof Number)
			return Byte.valueOf(((Number) obj).byteValue());
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to Byte");
	}

	public static float convertTofloat(Object obj) {
		if (obj == null)
			return 0f;
		if (obj instanceof Number)
			return ((Number) obj).floatValue();
		if (obj instanceof String)
			return Float.parseFloat((String) obj);
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to float");
	}

	public static Float convertToFloat(Object obj) {
		if (obj == null)
			return null;
		Class<?> c = obj.getClass();
		if (c == Float.class)
			return (Float) obj;
		if (obj instanceof Number)
			return Float.valueOf(((Number) obj).floatValue());
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to Float");
	}

	public static double convertTodouble(Object obj) {
		if (obj == null)
			return 0.0;
		if (obj instanceof Number)
			return ((Number) obj).doubleValue();
		if (obj instanceof String)
			return Double.parseDouble((String) obj);
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to float");
	}

	public static Double convertToDouble(Object obj) {
		if (obj == null)
			return null;
		Class<?> c = obj.getClass();
		if (c == Double.class)
			return (Double) obj;
		if (obj instanceof Number)
			return Double.valueOf(((Number) obj).doubleValue());
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to Float");
	}

	public static char convertTochar(Object obj) {
		if (obj == null)
			return ' ';
		if (obj instanceof String)
			if (((String) obj).length() > 0)
				return ((String) obj).charAt(0);
			else
				return ' ';
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to char");
	}

	public static Character convertToChar(Object obj) {
		if (obj == null)
			return null;
		Class<?> c = obj.getClass();
		if (c == Character.class)
			return (Character) obj;
		if (obj instanceof String)
			if (((String) obj).length() > 0)
				return ((String) obj).charAt(0);
			else
				return ' ';
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to Character");
	}

	public static boolean convertTobool(Object obj) {
		if (obj == null)
			return false;
		if (obj.getClass() == Boolean.class)
			return ((Boolean) obj).booleanValue();
		if (obj instanceof String)
			return Boolean.parseBoolean((String) obj);
		if (obj instanceof Number) {
			if (obj.toString().equals("0"))
				return false;
			else
				return true;
		}
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to boolean");
	}

	public static Boolean convertToBool(Object obj) {
		if (obj == null)
			return null;
		Class<?> c = obj.getClass();
		if (c == Boolean.class)
			return (Boolean) obj;
		if (obj instanceof String)
			return Boolean.parseBoolean((String) obj);
		throw new ConvertException("Primitive: Can not convert " + obj.getClass().getName() + " to Boolean");
	}
}
