package com.grupo.casas.bahia.validator;

import com.grupo.casas.bahia.utils.CnpjUtils;
import com.grupo.casas.bahia.validator.annotation.ValidCnpj;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CnpjValidator implements ConstraintValidator<ValidCnpj, String> {

    @Override
    public void initialize(ValidCnpj constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        return cnpj != null && CnpjUtils.isValidCnpj(cnpj);
    }
}