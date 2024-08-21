package com.grupo.casas.bahia.validator;

import com.grupo.casas.bahia.controller.dto.request.SellerRequestDTO;
import com.grupo.casas.bahia.utils.CnpjUtils;
import com.grupo.casas.bahia.utils.CpfUtils;
import com.grupo.casas.bahia.validator.annotation.ValidContractType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContractTypeValidator implements ConstraintValidator<ValidContractType, SellerRequestDTO> {

    @Override
    public void initialize(ValidContractType constraintAnnotation) {
    }

    @Override
    public boolean isValid(SellerRequestDTO requestDTO, ConstraintValidatorContext context) {
        if (requestDTO.getContractType() == null) {
            return true;
        }

        return switch (requestDTO.getContractType()) {
            case CLT -> CpfUtils.isValidCpf(requestDTO.getDocument());
            case PJ -> CnpjUtils.isValidCnpj(requestDTO.getDocument());
            default -> true;
        };
    }
}