package com.grupo.casas.bahia.controller.dto.request;

import com.grupo.casas.bahia.controller.dto.ContractTypeDTO;
import com.grupo.casas.bahia.validator.annotation.ValidCnpj;
import com.grupo.casas.bahia.validator.annotation.ValidContractType;
import com.grupo.casas.bahia.validator.annotation.ValidCpf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidContractType
public class SellerRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "BirthDate cannot be null")
    private LocalDate birthDate;

    @NotBlank(message = "Document cannot be blank")
    @ValidCpf
    @ValidCnpj
    private String document;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "ContractType cannot be null")
    private ContractTypeDTO contractType;

    @NotBlank(message = "BranchDocument cannot be blank")
    private String branchDocument;
}