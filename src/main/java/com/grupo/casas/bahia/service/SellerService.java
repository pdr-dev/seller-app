package com.grupo.casas.bahia.service;

import com.grupo.casas.bahia.controller.dto.request.SellerRequestDTO;
import com.grupo.casas.bahia.controller.dto.response.SellerResponseDTO;

import java.util.List;

public interface SellerService {

    SellerResponseDTO create(SellerRequestDTO sellerRequest);

    SellerResponseDTO findById(Long id);

    List<SellerResponseDTO> findAll();

    SellerResponseDTO update(Long id, SellerRequestDTO sellerRequest);

    void delete(Long id);

}