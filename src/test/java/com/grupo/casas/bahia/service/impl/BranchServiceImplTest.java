package com.grupo.casas.bahia.service.impl;

import com.grupo.casas.bahia.controller.dto.response.BranchResponseDTO;
import com.grupo.casas.bahia.domain.Branch;
import com.grupo.casas.bahia.domain.repository.BranchRepository;
import com.grupo.casas.bahia.exception.NotFoundException;
import com.grupo.casas.bahia.mapper.BranchMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BranchServiceImplTest {

    @Mock
    private BranchRepository repository;

    @Mock
    private BranchMapper mapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BranchServiceImpl branchService;

    private static final String HOST_BRANCHES = "http://example.com/branches/";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        branchService.hostBranches = HOST_BRANCHES;
    }

    @Test
    public void testGetBranchByDocument_existingBranch_updatesAndReturnsBranch() {
        String document = "12345678000101";
        Branch existingBranch = new Branch();
        existingBranch.setDocument(document);
        existingBranch.setName("Old Name");

        BranchResponseDTO responseDTO = new BranchResponseDTO();
        responseDTO.setDocument(document);
        responseDTO.setName("New Name");
        responseDTO.setCity("São Paulo");
        responseDTO.setState("SP");
        responseDTO.setType("Logistico");
        responseDTO.setActive(true);
        responseDTO.setRegistrationDate(LocalDate.of(2023, 1, 10));
        responseDTO.setLastUpdate(LocalDateTime.of(2024, 8, 19, 15, 45));

        when(repository.findByDocument(document)).thenReturn(Optional.of(existingBranch));
        when(restTemplate.getForEntity(HOST_BRANCHES + document, BranchResponseDTO.class)).thenReturn(ResponseEntity.ok(responseDTO));
        when(repository.save(any(Branch.class))).thenReturn(existingBranch);

        Branch result = branchService.getBranchByDocument(document);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(repository).save(existingBranch);
    }

    @Test
    public void testGetBranchByDocument_newBranch_createsAndReturnsBranch() {
        String document = "98765432000109";
        BranchResponseDTO responseDTO = new BranchResponseDTO();
        responseDTO.setDocument(document);
        responseDTO.setName("Branch Name");
        responseDTO.setCity("Rio de Janeiro");
        responseDTO.setState("RJ");
        responseDTO.setType("Administrativo");
        responseDTO.setActive(false);
        responseDTO.setRegistrationDate(LocalDate.of(2022, 5, 15));
        responseDTO.setLastUpdate(LocalDateTime.of(2024, 8, 20, 11, 30));

        Branch newBranch = new Branch();
        newBranch.setDocument(document);
        when(repository.findByDocument(document)).thenReturn(Optional.empty());
        when(restTemplate.getForEntity(HOST_BRANCHES + document, BranchResponseDTO.class)).thenReturn(ResponseEntity.ok(responseDTO));
        when(mapper.convertToEntity(responseDTO)).thenReturn(newBranch);
        when(repository.save(newBranch)).thenReturn(newBranch);

        Branch result = branchService.getBranchByDocument(document);

        assertNotNull(result);
        assertEquals(document, result.getDocument());
        verify(repository).save(newBranch);
    }

    @Test
    public void testGetMockedBranchByDocument_found_returnsBranch() {
        String document = "12345678000101";
        BranchResponseDTO expectedDto = new BranchResponseDTO();
        expectedDto.setDocument(document);

        BranchResponseDTO result = branchService.getMockedBranchByDocument(document);

        assertEquals(expectedDto.getDocument(), result.getDocument());
    }

    @Test
    public void testGetMockedBranchByDocument_notFound_throwsNotFoundException() {
        String document = "nonexistent";
        assertThrows(NotFoundException.class, () -> branchService.getMockedBranchByDocument(document));
    }
}