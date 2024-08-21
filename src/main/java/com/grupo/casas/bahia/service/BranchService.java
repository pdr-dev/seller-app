package com.grupo.casas.bahia.service;

import com.grupo.casas.bahia.controller.dto.response.BranchResponseDTO;
import com.grupo.casas.bahia.domain.Branch;

public interface BranchService {

    Branch getBranchByDocument(String document);
    BranchResponseDTO getMockedBranchByDocument(String document);

}
