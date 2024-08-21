package com.grupo.casas.bahia.validator.annotation;

import com.grupo.casas.bahia.validator.CnpjValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CnpjValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCnpj {
    String message() default "Invalid CNPJ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}