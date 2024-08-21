package com.grupo.casas.bahia.controller.dto;

import lombok.Getter;

@Getter
public enum ContractTypeDTO {
    CLT("CLT"),
    OUT("Outsourcing (terceirizado)"),
    PJ("Pessoa Jurídica");

    private final String value;

    ContractTypeDTO(String value) {
        this.value = value;
    }
}