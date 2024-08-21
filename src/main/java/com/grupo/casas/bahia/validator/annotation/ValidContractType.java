package com.grupo.casas.bahia.validator.annotation;

import com.grupo.casas.bahia.validator.ContractTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ContractTypeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidContractType {
    String message() default "Invalid document for contract type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}