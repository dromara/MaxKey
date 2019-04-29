package net.minidev.asm;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.BIPUSH;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.F_SAME;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.ICONST_2;
import static org.objectweb.asm.Opcodes.ICONST_3;
import static org.objectweb.asm.Opcodes.ICONST_4;
import static org.objectweb.asm.Opcodes.ICONST_5;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.IFNULL;
import static org.objectweb.asm.Opcodes.IF_ICMPNE;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.RETURN;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class BeansAccessBuilder {
	static private String METHOD_ACCESS_NAME = Type.getInternalName(BeansAccess.class);

	final Class<?> type;
	final Accessor[] accs;
	final DynamicClassLoader loader;
	final String className;
	final String accessClassName;
	final String accessClassNameInternal;
	final String classNameInternal;
	final HashMap<Class<?>, Method> convMtds = new HashMap<Class<?>, Method>();
//	Class<? extends Exception> exeptionClass = net.minidev.asm.ex.NoSuchFieldException.class;
	Class<? extends Exception> exeptionClass = NoSuchFieldException.class;

	/**
	 * Build reflect bytecode from accessor list.
	 * 
	 * @param type
	 *            type to be access
	 * @param accs
	 *            used accessor
	 * @param loader
	 *            Loader used to store the generated class
	 */	
	public BeansAccessBuilder(Class<?> type, Accessor[] accs, DynamicClassLoader loader) {
		this.type = type;
		this.accs = accs;
		this.loader = loader;

		this.className = type.getName();
		if (className.startsWith("java."))
			this.accessClassName = "net.minidev.asm." + className + "AccAccess";
		else
			this.accessClassName = className.concat("AccAccess");

		this.accessClassNameInternal = accessClassName.replace('.', '/');
		this.classNameInternal = className.replace('.', '/');
	}

	public void addConversion(Iterable<Class<?>> conv) {
		if (conv == null)
			return;
		for (Class<?> c : conv)
			addConversion(c);
	}

	public void addConversion(Class<?> conv) {
		if (conv == null)
			return;
		for (Method mtd : conv.getMethods()) {
			if ((mtd.getModifiers() & Modifier.STATIC) == 0)
				continue;
			Class<?>[] param = mtd.getParameterTypes();
			if (param.length != 1)
				continue;
			if (!param[0].equals(Object.class))
				continue;
			Class<?> rType = mtd.getReturnType();
			if (rType.equals(Void.TYPE))
				continue;
			convMtds.put(rType, mtd);
		}
	}

	public Class<?> bulid() {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		MethodVisitor mv;

		boolean USE_HASH = accs.length > 10;
		int HASH_LIMIT = 14;

		String signature = "Lnet/minidev/asm/BeansAccess<L" + classNameInternal + ";>;";

		cw.visit(Opcodes.V1_6, ACC_PUBLIC + Opcodes.ACC_SUPER, accessClassNameInternal, signature, METHOD_ACCESS_NAME, null);
		// init
		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, METHOD_ACCESS_NAME, "<init>", "()V");
			mv.visitInsn(RETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}

		// set(Object object, int methodIndex, Object value)
		mv = cw.visitMethod(ACC_PUBLIC, "set", "(Ljava/lang/Object;ILjava/lang/Object;)V", null, null);
		mv.visitCode();
		// if no Field simply return
		if (accs.length == 0) {
			//
			// mv.visitInsn(RETURN);
		} else if (accs.length > HASH_LIMIT) {
			// lots of field Use Switch Statement
			mv.visitVarInsn(ILOAD, 2);
			Label[] labels = ASMUtil.newLabels(accs.length);
			Label defaultLabel = new Label();

			mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
			int i = 0;
			for (Accessor acc : accs) {
				mv.visitLabel(labels[i++]);
				if (!acc.isWritable()) {
					mv.visitInsn(RETURN);
					continue;
				}
				internalSetFiled(mv, acc);
			}
			mv.visitLabel(defaultLabel);
		} else {
			Label[] labels = ASMUtil.newLabels(accs.length);
			int i = 0;
			for (Accessor acc : accs) {
				ifNotEqJmp(mv, 2, i, labels[i]);
				internalSetFiled(mv, acc);
				mv.visitLabel(labels[i]);
				mv.visitFrame(F_SAME, 0, null, 0, null);
				i++;
			}
		}
		if (exeptionClass != null)
			throwExIntParam(mv, exeptionClass);
		else
			mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		// public Object get(Object object, int fieldId)
		mv = cw.visitMethod(ACC_PUBLIC, "get", "(Ljava/lang/Object;I)Ljava/lang/Object;", null, null);
		mv.visitCode();
		// if (USE_HASH)
		if (accs.length == 0) {
			mv.visitFrame(F_SAME, 0, null, 0, null);
		} else if (accs.length > HASH_LIMIT) {
			mv.visitVarInsn(ILOAD, 2);
			Label[] labels = ASMUtil.newLabels(accs.length);
			Label defaultLabel = new Label();
			mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
			int i = 0;
			for (Accessor acc : accs) {
				mv.visitLabel(labels[i++]);
				mv.visitFrame(F_SAME, 0, null, 0, null);
				if (!acc.isReadable()) {
					mv.visitInsn(ACONST_NULL);
					mv.visitInsn(ARETURN);
					continue;
				}
				mv.visitVarInsn(ALOAD, 1);
				mv.visitTypeInsn(CHECKCAST, classNameInternal);
				Type fieldType = Type.getType(acc.getType());
				if (acc.isPublic()) {
					mv.visitFieldInsn(GETFIELD, classNameInternal, acc.getName(), fieldType.getDescriptor());
				} else {
					String sig = Type.getMethodDescriptor(acc.getter);
					mv.visitMethodInsn(INVOKEVIRTUAL, classNameInternal, acc.getter.getName(), sig);
				}
				ASMUtil.autoBoxing(mv, fieldType);
				mv.visitInsn(ARETURN);
			}
			mv.visitLabel(defaultLabel);
			mv.visitFrame(F_SAME, 0, null, 0, null);
		} else {
			Label[] labels = ASMUtil.newLabels(accs.length);
			int i = 0;
			for (Accessor acc : accs) {
				ifNotEqJmp(mv, 2, i, labels[i]);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitTypeInsn(CHECKCAST, classNameInternal);
				Type fieldType = Type.getType(acc.getType());
				if (acc.isPublic()) {
					mv.visitFieldInsn(GETFIELD, classNameInternal, acc.getName(), fieldType.getDescriptor());
				} else {
					if (acc.getter == null)
						throw new RuntimeException("no Getter for field " + acc.getName() + " in class " + this.className);
					String sig = Type.getMethodDescriptor(acc.getter);
					mv.visitMethodInsn(INVOKEVIRTUAL, classNameInternal, acc.getter.getName(), sig);
				}
				ASMUtil.autoBoxing(mv, fieldType);
				mv.visitInsn(ARETURN);

				mv.visitLabel(labels[i]);
				mv.visitFrame(F_SAME, 0, null, 0, null);
				i++;
			}
		}

		if (exeptionClass != null)
			throwExIntParam(mv, exeptionClass);
		else {
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
		}
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		if (!USE_HASH) {
			// Object get(Object object, String methodName)
			mv = cw.visitMethod(ACC_PUBLIC, "set", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V", null, null);
			mv.visitCode();

			Label[] labels = ASMUtil.newLabels(accs.length);

			int i = 0;
			for (Accessor acc : accs) {
				mv.visitVarInsn(ALOAD, 2);
				mv.visitLdcInsn(acc.fieldName);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z");
				mv.visitJumpInsn(IFEQ, labels[i]);
				internalSetFiled(mv, acc);
				mv.visitLabel(labels[i]);
				mv.visitFrame(F_SAME, 0, null, 0, null);
				i++;
			}
			if (exeptionClass != null)
				throwExStrParam(mv, exeptionClass);
			else
				mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0); // 2,4
			mv.visitEnd();
		}

		if (!USE_HASH) {
			// get(Object object, String methodName)
			mv = cw.visitMethod(ACC_PUBLIC, "get", "(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;", null, null);
			mv.visitCode();

			Label[] labels = ASMUtil.newLabels(accs.length);

			int i = 0;
			for (Accessor acc : accs) {
				mv.visitVarInsn(ALOAD, 2); // methodName
				mv.visitLdcInsn(acc.fieldName);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z");
				mv.visitJumpInsn(IFEQ, labels[i]);
				mv.visitVarInsn(ALOAD, 1); // object
				mv.visitTypeInsn(CHECKCAST, classNameInternal);
				Type fieldType = Type.getType(acc.getType());
				if (acc.isPublic()) {
					mv.visitFieldInsn(GETFIELD, classNameInternal, acc.getName(), fieldType.getDescriptor());
				} else {
					String sig = Type.getMethodDescriptor(acc.getter);
					mv.visitMethodInsn(INVOKEVIRTUAL, classNameInternal, acc.getter.getName(), sig);
				}
				ASMUtil.autoBoxing(mv, fieldType);
				mv.visitInsn(ARETURN);
				mv.visitLabel(labels[i]);
				mv.visitFrame(F_SAME, 0, null, 0, null);
				i++;
			}
			if (exeptionClass != null)
				throwExStrParam(mv, exeptionClass);
			else {
				mv.visitInsn(ACONST_NULL);
				mv.visitInsn(ARETURN);
			}
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		{
			mv = cw.visitMethod(ACC_PUBLIC, "newInstance", "()Ljava/lang/Object;", null, null);
			mv.visitCode();
			mv.visitTypeInsn(NEW, classNameInternal);
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, classNameInternal, "<init>", "()V");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(2, 1);
			mv.visitEnd();
		}
		cw.visitEnd();
		byte[] data = cw.toByteArray();
		// dumpDebug(data, "/tmp/debug-" + accessClassName + ".txt");
		return loader.defineClass(accessClassName, data);
	}

	/**
	 * Dump Generate Code
	 */
	@SuppressWarnings("unused")
	private void dumpDebug(byte[] data, String destFile) {
		// try {
		// File debug = new File(destFile);
		// int flags = ClassReader.SKIP_DEBUG;
		// ClassReader cr = new ClassReader(new ByteArrayInputStream(data));
		// cr.accept(new ASMifierClassVisitor(new PrintWriter(debug)),
		// ASMifierClassVisitor.getDefaultAttributes(),
		// flags);
		// } catch (Exception e) {
		// }
	}

	/**
	 * Dump Set Field Code
	 * 
	 * @param mv
	 * @param acc
	 */
	private void internalSetFiled(MethodVisitor mv, Accessor acc) {
		/**
		 * FNC params
		 * 
		 * 1 -> object to alter
		 * 
		 * 2 -> id of field
		 * 
		 * 3 -> new value
		 */
		mv.visitVarInsn(ALOAD, 1);
		mv.visitTypeInsn(CHECKCAST, classNameInternal);
		// get VELUE
		mv.visitVarInsn(ALOAD, 3);
		Type fieldType = Type.getType(acc.getType());
		Class<?> type = acc.getType();
		String destClsName = Type.getInternalName(type);

		Method conMtd = convMtds.get(type);
		if (conMtd != null) {
			// external converion
			String clsSig = Type.getInternalName(conMtd.getDeclaringClass());
			String mtdName = conMtd.getName();
			String mtdSig = Type.getMethodDescriptor(conMtd);
			mv.visitMethodInsn(INVOKESTATIC, clsSig, mtdName, mtdSig);
		} else if (acc.isEnum()) {
			// builtIn Enum Conversion
			Label isNull = new Label();
			mv.visitJumpInsn(IFNULL, isNull);
			mv.visitVarInsn(ALOAD, 3);
			// mv.visitTypeInsn(CHECKCAST, "java/lang/String");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "toString", "()Ljava/lang/String;");
			mv.visitMethodInsn(INVOKESTATIC, destClsName, "valueOf", "(Ljava/lang/String;)L" + destClsName + ";");
			mv.visitVarInsn(ASTORE, 3);
			mv.visitLabel(isNull);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, this.classNameInternal); // "net/minidev/asm/bean/BEnumPriv"
			mv.visitVarInsn(ALOAD, 3);
			mv.visitTypeInsn(CHECKCAST, destClsName);
		} else if (type.equals(String.class)) {
			// built In String Conversion
			Label isNull = new Label();
			mv.visitJumpInsn(IFNULL, isNull);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "toString", "()Ljava/lang/String;");
			mv.visitVarInsn(ASTORE, 3);
			mv.visitLabel(isNull);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, this.classNameInternal);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitTypeInsn(CHECKCAST, destClsName);
		} else {
			// just check Cast
			mv.visitTypeInsn(CHECKCAST, destClsName);
		}
		if (acc.isPublic()) {
			mv.visitFieldInsn(PUTFIELD, classNameInternal, acc.getName(), fieldType.getDescriptor());
		} else {
			String sig = Type.getMethodDescriptor(acc.setter);
			mv.visitMethodInsn(INVOKEVIRTUAL, classNameInternal, acc.setter.getName(), sig);
		}
		mv.visitInsn(RETURN);
	}

	/**
	 * add Throws statement with int param 2
	 */
	private void throwExIntParam(MethodVisitor mv, Class<?> exCls) {
		String exSig = Type.getInternalName(exCls);
		mv.visitTypeInsn(NEW, exSig);
		mv.visitInsn(DUP);
		mv.visitLdcInsn("mapping " + this.className + " failed to map field:");
		mv.visitVarInsn(ILOAD, 2);
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "toString", "(I)Ljava/lang/String;");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "concat", "(Ljava/lang/String;)Ljava/lang/String;");
		mv.visitMethodInsn(INVOKESPECIAL, exSig, "<init>", "(Ljava/lang/String;)V");
		mv.visitInsn(ATHROW);
	}

	/**
	 * add Throws statement with String param 2
	 */
	private void throwExStrParam(MethodVisitor mv, Class<?> exCls) {
		String exSig = Type.getInternalName(exCls);
		mv.visitTypeInsn(NEW, exSig);
		mv.visitInsn(DUP);
		mv.visitLdcInsn("mapping " + this.className + " failed to map field:");
		mv.visitVarInsn(ALOAD, 2);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "concat", "(Ljava/lang/String;)Ljava/lang/String;");
		mv.visitMethodInsn(INVOKESPECIAL, exSig, "<init>", "(Ljava/lang/String;)V");
		mv.visitInsn(ATHROW);
	}

	/**
	 * dump a Jump if not EQ
	 */
	private void ifNotEqJmp(MethodVisitor mv, int param, int value, Label label) {
		mv.visitVarInsn(ILOAD, param);
		if (value == 0) {
			/* notest forvalue 0 */
			mv.visitJumpInsn(IFNE, label);
		} else if (value == 1) {
			mv.visitInsn(ICONST_1);
			mv.visitJumpInsn(IF_ICMPNE, label);
		} else if (value == 2) {
			mv.visitInsn(ICONST_2);
			mv.visitJumpInsn(IF_ICMPNE, label);
		} else if (value == 3) {
			mv.visitInsn(ICONST_3);
			mv.visitJumpInsn(IF_ICMPNE, label);
		} else if (value == 4) {
			mv.visitInsn(ICONST_4);
			mv.visitJumpInsn(IF_ICMPNE, label);
		} else if (value == 5) {
			mv.visitInsn(ICONST_5);
			mv.visitJumpInsn(IF_ICMPNE, label);
		} else if (value >= 6) {
			mv.visitIntInsn(BIPUSH, value);
			mv.visitJumpInsn(IF_ICMPNE, label);
		} else {
			throw new RuntimeException("non supported negative values");
		}
	}
}
