package net.minidev.asm;

/*
 *    Copyright 2011 JSON-SMART authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/**
 * ASM Utils used to simplify class generation
 * 
 * @author uriel Chemouni
 */
public class ASMUtil {
	/**
	 * Append the call of proper autoboxing method for the given primitif type.
	 */
	public static void autoBoxing(MethodVisitor mv, Class<?> clz) {
		autoBoxing(mv, Type.getType(clz));
	}

	/**
	 * Extract all Accessor for the field of the given class.
	 * 
	 * @param type
	 * @return all Accessor available
	 */
	static public Accessor[] getAccessors(Class<?> type, FieldFilter filter) {
		Class<?> nextClass = type;
		HashMap<String, Accessor> map = new HashMap<String, Accessor>();
		if (filter == null)
			filter = BasicFiledFilter.SINGLETON;
		while (nextClass != Object.class) {
			Field[] declaredFields = nextClass.getDeclaredFields();

			for (Field field : declaredFields) {
				String fn = field.getName();
				if (map.containsKey(fn))
					continue;
				Accessor acc = new Accessor(nextClass, field, filter);
				if (!acc.isUsable())
					continue;
				map.put(fn, acc);
			}
			nextClass = nextClass.getSuperclass();
		}
		return map.values().toArray(new Accessor[map.size()]);
	}

	/**
	 * Append the call of proper autoboxing method for the given primitif type.
	 */
	protected static void autoBoxing(MethodVisitor mv, Type fieldType) {
		switch (fieldType.getSort()) {
		case Type.BOOLEAN:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
			break;
		case Type.BYTE:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
			break;
		case Type.CHAR:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
			break;
		case Type.SHORT:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
			break;
		case Type.INT:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
			break;
		case Type.FLOAT:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
			break;
		case Type.LONG:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
			break;
		case Type.DOUBLE:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
			break;
		}
	}

	/**
	 * Append the call of proper extract primitive type of an boxed object.
	 */
	protected static void autoUnBoxing1(MethodVisitor mv, Type fieldType) {
		switch (fieldType.getSort()) {
		case Type.BOOLEAN:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z");
			break;
		case Type.BYTE:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Byte");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B");
			break;
		case Type.CHAR:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Character");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C");
			break;
		case Type.SHORT:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Short");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S");
			break;
		case Type.INT:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
			break;
		case Type.FLOAT:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Float");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F");
			break;
		case Type.LONG:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Long");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J");
			break;
		case Type.DOUBLE:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Double");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D");
			break;
		case Type.ARRAY:
			mv.visitTypeInsn(CHECKCAST, fieldType.getInternalName());
			break;
		default:
			mv.visitTypeInsn(CHECKCAST, fieldType.getInternalName());
		}
	}

	/**
	 * Append the call of proper extract primitive type of an boxed object. this
	 * methode use Number interface to unbox object
	 */
	protected static void autoUnBoxing2(MethodVisitor mv, Type fieldType) {
		switch (fieldType.getSort()) {
		case Type.BOOLEAN:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z");
			break;
		case Type.BYTE:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Number");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Number", "byteValue", "()B");
			break;
		case Type.CHAR:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Character");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C");
			break;
		case Type.SHORT:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Number");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Number", "shortValue", "()S");
			break;
		case Type.INT:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Number");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Number", "intValue", "()I");
			break;
		case Type.FLOAT:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Number");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F");
			break;
		case Type.LONG:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Number");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Number", "longValue", "()J");
			break;
		case Type.DOUBLE:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Number");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Number", "doubleValue", "()D");
			break;
		case Type.ARRAY:
			mv.visitTypeInsn(CHECKCAST, fieldType.getInternalName());
			break;
		default:
			mv.visitTypeInsn(CHECKCAST, fieldType.getInternalName());
		}
	}

	/**
	 * return a array of new Label (used for switch/case generation)
	 * 
	 * @param cnt
	 *            number of label to return
	 */
	public static Label[] newLabels(int cnt) {
		Label[] r = new Label[cnt];
		for (int i = 0; i < cnt; i++)
			r[i] = new Label();
		return r;
	}

	public static String getSetterName(String key) {
		int len = key.length();
		char[] b = new char[len + 3];
		b[0] = 's';
		b[1] = 'e';
		b[2] = 't';
		char c = key.charAt(0);
		if (c >= 'a' && c <= 'z')
			c += 'A' - 'a';
		b[3] = c;
		for (int i = 1; i < len; i++) {
			b[i + 3] = key.charAt(i);
		}
		return new String(b);
	}

	public static String getGetterName(String key) {
		int len = key.length();
		char[] b = new char[len + 3];
		b[0] = 'g';
		b[1] = 'e';
		b[2] = 't';
		char c = key.charAt(0);
		if (c >= 'a' && c <= 'z')
			c += 'A' - 'a';
		b[3] = c;
		for (int i = 1; i < len; i++) {
			b[i + 3] = key.charAt(i);
		}
		return new String(b);
	}

	public static String getIsName(String key) {
		int len = key.length();
		char[] b = new char[len + 2];
		b[0] = 'i';
		b[1] = 's';
		char c = key.charAt(0);
		if (c >= 'a' && c <= 'z')
			c += 'A' - 'a';
		b[2] = c;
		for (int i = 1; i < len; i++) {
			b[i + 2] = key.charAt(i);
		}
		return new String(b);
	}

}
