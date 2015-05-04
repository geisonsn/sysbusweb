package br.com.gsn.sysbusweb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.gsn.sysbusweb.validator.EnumValidator;

@Documented
@Constraint(validatedBy = EnumValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
	
	String message() default "Invalid value. This is not permitted.";
    
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
    
    Class<? extends java.lang.Enum<?>> enumClass();
    
    boolean ignoreCase() default false;
}
