package com.grupo.casas.bahia.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.grupo.casas.bahia.controller.dto.ContractTypeDTO;
import com.grupo.casas.bahia.controller.dto.request.SellerRequestDTO;
import com.grupo.casas.bahia.controller.dto.response.SellerResponseDTO;
import com.grupo.casas.bahia.domain.Branch;
import com.grupo.casas.bahia.domain.Seller;
import com.grupo.casas.bahia.domain.repository.SellerRepository;
import com.grupo.casas.bahia.exception.DuplicatedException;
import com.grupo.casas.bahia.exception.InternalServerErrorException;
import com.grupo.casas.bahia.exception.NotFoundException;
import com.grupo.casas.bahia.mapper.SellerMapper;
import com.grupo.casas.bahia.service.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

class SellerServiceImplTest {

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private SellerMapper sellerMapper;

    @Mock
    private BranchService branchService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSeller_success() {
        SellerRequestDTO requestDTO = new SellerRequestDTO();
        requestDTO.setDocument("12345678901");
        requestDTO.setBranchDocument("branch-doc");
        requestDTO.setContractType(ContractTypeDTO.PJ);

        Seller seller = new Seller();
        SellerResponseDTO responseDTO = new SellerResponseDTO();
        Branch branch = new Branch();

        when(sellerRepository.findByDocument(anyString())).thenReturn(Optional.empty());
        when(sellerMapper.requestToEntity(any(SellerRequestDTO.class))).thenReturn(seller);
        when(branchService.getBranchByDocument(anyString())).thenReturn(branch);
        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);
        when(sellerMapper.entityToResponse(any(Seller.class))).thenReturn(responseDTO);

        SellerResponseDTO result = sellerService.create(requestDTO);

        assertNotNull(result);
        verify(sellerRepository).save(seller);
    }

    @Test
    public void testCreateSeller_duplicated() {
        SellerRequestDTO requestDTO = new SellerRequestDTO();
        requestDTO.setDocument("12345678901");

        when(sellerRepository.findByDocument(anyString())).thenReturn(Optional.of(new Seller()));

        DuplicatedException thrown = assertThrows(DuplicatedException.class, () -> {
            sellerService.create(requestDTO);
        });

        assertEquals("Seller for document 12345678901 already exists.", thrown.getMessage());
    }

    @Test
    public void testFindById_success() {
        Long sellerId = 1L;
        Seller seller = new Seller();
        SellerResponseDTO responseDTO = new SellerResponseDTO();

        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(seller));
        when(sellerMapper.entityToResponse(any(Seller.class))).thenReturn(responseDTO);

        SellerResponseDTO result = sellerService.findById(sellerId);

        assertNotNull(result);
        verify(sellerRepository).findById(sellerId);
    }

    @Test
    public void testFindById_notFound() {
        Long sellerId = 1L;

        when(sellerRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            sellerService.findById(sellerId);
        });

        assertEquals("Seller with id 1 not found", thrown.getMessage());
    }

    @Test
    public void testFindAll_success() {
        List<Seller> sellers = Arrays.asList(new Seller(), new Seller());
        List<SellerResponseDTO> responseDTOs = Arrays.asList(new SellerResponseDTO(), new SellerResponseDTO());

        when(sellerRepository.findAll()).thenReturn(sellers);
        when(sellerMapper.entityToResponse(any(Seller.class))).thenReturn(new SellerResponseDTO());

        List<SellerResponseDTO> result = sellerService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testUpdate_success() {
        Long sellerId = 1L;
        SellerRequestDTO requestDTO = new SellerRequestDTO();
        requestDTO.setName("Updated Name");
        requestDTO.setBranchDocument("branch-doc");

        Seller seller = new Seller();
        SellerResponseDTO responseDTO = new SellerResponseDTO();
        Branch branch = new Branch();

        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(seller));
        when(branchService.getBranchByDocument(anyString())).thenReturn(branch);
        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);
        when(sellerMapper.entityToResponse(any(Seller.class))).thenReturn(responseDTO);

        SellerResponseDTO result = sellerService.update(sellerId, requestDTO);

        assertNotNull(result);
        verify(sellerRepository).save(seller);
    }

    @Test
    public void testUpdate_notFound() {
        Long sellerId = 1L;
        SellerRequestDTO requestDTO = new SellerRequestDTO();

        when(sellerRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            sellerService.update(sellerId, requestDTO);
        });

        assertEquals("Seller with id 1 not found", thrown.getMessage());
    }

    @Test
    public void testDelete_success() {
        Long sellerId = 1L;

        sellerService.delete(sellerId);

        verify(sellerRepository).deleteById(sellerId);
    }

    @Test
    public void testDelete_error() {
        Long sellerId = 1L;

        doThrow(new RuntimeException()).when(sellerRepository).deleteById(anyLong());

        InternalServerErrorException thrown = assertThrows(InternalServerErrorException.class, () -> {
            sellerService.delete(sellerId);
        });

        assertEquals("An error occurred when trying to delete seller with id 1", thrown.getMessage());
    }
}