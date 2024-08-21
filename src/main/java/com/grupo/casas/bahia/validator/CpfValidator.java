package com.grupo.casas.bahia.validator;

import com.grupo.casas.bahia.utils.CpfUtils;
import com.grupo.casas.bahia.validator.annotation.ValidCpf;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<ValidCpf, String> {

    @Override
    public void initialize(ValidCpf constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        return cpf != null && CpfUtils.isValidCpf(cpf);
    }
}