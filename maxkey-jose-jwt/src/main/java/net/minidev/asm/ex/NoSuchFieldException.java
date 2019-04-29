package net.minidev.asm.ex;

/**
 * Same exception as java.lang.NoSuchFieldException but extends RuntimException
 * 
 * @author uriel
 *
 */
public class NoSuchFieldException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoSuchFieldException() {
		super();
	}

	public NoSuchFieldException(String message) {
		super(message);
	}
}
