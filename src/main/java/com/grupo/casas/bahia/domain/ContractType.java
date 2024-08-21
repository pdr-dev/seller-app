package com.grupo.casas.bahia.domain;

import lombok.Getter;

@Getter
public enum ContractType {
    CLT("CLT"),
    OUT("Outsourcing (terceirizado)"),
    PJ("Pessoa Jurídica");

    private final String value;

    ContractType(String value) {
        this.value = value;
    }
}