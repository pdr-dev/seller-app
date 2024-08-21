package com.grupo.casas.bahia.mapper;

import com.grupo.casas.bahia.controller.dto.response.BranchResponseDTO;
import com.grupo.casas.bahia.domain.Branch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    Branch convertToEntity(BranchResponseDTO branchResponseDTO);
}