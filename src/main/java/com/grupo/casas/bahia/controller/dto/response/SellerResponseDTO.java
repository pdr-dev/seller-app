package com.grupo.casas.bahia.controller.dto.response;

import com.grupo.casas.bahia.controller.dto.ContractTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerResponseDTO {
    private Long id;
    private String registrationNumber;
    private String name;
    private LocalDate birthDate;
    private String document;
    private String email;
    private ContractTypeDTO contractType;
    private BranchResponseDTO branch;
}