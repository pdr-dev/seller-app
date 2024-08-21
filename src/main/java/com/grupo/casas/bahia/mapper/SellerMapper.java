package com.grupo.casas.bahia.mapper;

import com.grupo.casas.bahia.controller.dto.request.SellerRequestDTO;
import com.grupo.casas.bahia.controller.dto.response.SellerResponseDTO;
import com.grupo.casas.bahia.domain.Seller;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SellerMapper {
    Seller requestToEntity(SellerRequestDTO sellerRequest);
    SellerResponseDTO entityToResponse(Seller seller);
}
