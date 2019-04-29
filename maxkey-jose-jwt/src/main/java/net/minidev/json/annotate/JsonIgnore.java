package net.minidev.json.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * block access to a field or to a getter or to a setter.
 * 
 * If field and getter are annotate with @JsonIgnore the field will be Writable
 * only
 * 
 * 
 * If field and setter are annotate with @JsonIgnore the field will be Readable
 * only
 * 
 * 
 * If getter and setter are annotate with @JsonIgnore the field will be
 * Read/Write using field if the field is public (default )
 * 
 * 
 * @author uriel
 * 
 */
@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@JsonSmartAnnotation
public @interface JsonIgnore {
	boolean value() default true;
}
