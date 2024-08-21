package com.grupo.casas.bahia.service.impl;

import com.grupo.casas.bahia.controller.dto.response.BranchResponseDTO;
import com.grupo.casas.bahia.domain.Branch;
import com.grupo.casas.bahia.domain.repository.BranchRepository;
import com.grupo.casas.bahia.exception.InternalServerErrorException;
import com.grupo.casas.bahia.exception.NotFoundException;
import com.grupo.casas.bahia.mapper.BranchMapper;
import com.grupo.casas.bahia.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class BranchServiceImpl implements BranchService {

    @Value("${host.branches}")
    String hostBranches;

    private final BranchRepository repository;
    private final BranchMapper mapper;
    private final RestTemplate restTemplate;

    @Autowired
    public BranchServiceImpl(BranchRepository repository, BranchMapper mapper, RestTemplate restTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public Branch getBranchByDocument(String document) {
        try {
            return repository.findByDocument(document)
                    .map(branch -> {
                        BranchResponseDTO branchResponseDTO = callClientGetBranchByDocument(document);
                        if (updateBranchIfDifferent(branch, branchResponseDTO)) {
                            return repository.save(branch);
                        }
                        return branch;
                    })
                    .orElseGet(() -> repository.save(mapper.convertToEntity(callClientGetBranchByDocument(document))));
        } catch (Exception ex) {
            log.error("An error occurred when trying to fetch branch information for document: {}", document, ex);
            throw new InternalServerErrorException("An error occurred when trying to fetch branch information.", ex);
        }
    }

    @Override
    public BranchResponseDTO getMockedBranchByDocument(String document) {
        return createMockBranchResponseDTOs()
                .stream()
                .filter(it -> it.getDocument().equals(document))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Branch of document " + document + " not found."));
    }

    private BranchResponseDTO callClientGetBranchByDocument(String document) {
        ResponseEntity<BranchResponseDTO> response = restTemplate.getForEntity(hostBranches + document, BranchResponseDTO.class);
        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new NotFoundException("Branch data not found for document: " + document));
    }

    private boolean updateBranchIfDifferent(Branch branch, BranchResponseDTO dto) {
        if (branch == null || dto == null) {
            throw new IllegalArgumentException("Branch and BranchResponseDTO cannot be null.");
        }

        boolean updated = false;

        if (!Objects.equals(branch.getName(), dto.getName())) {
            branch.setName(dto.getName());
            updated = true;
        }
        if (!Objects.equals(branch.getDocument(), dto.getDocument())) {
            branch.setDocument(dto.getDocument());
            updated = true;
        }
        if (!Objects.equals(branch.getCity(), dto.getCity())) {
            branch.setCity(dto.getCity());
            updated = true;
        }
        if (!Objects.equals(branch.getState(), dto.getState())) {
            branch.setState(dto.getState());
            updated = true;
        }
        if (!Objects.equals(branch.getType(), dto.getType())) {
            branch.setType(dto.getType());
            updated = true;
        }
        if (branch.isActive() != dto.isActive()) {
            branch.setActive(dto.isActive());
            updated = true;
        }
        if (!Objects.equals(branch.getRegistrationDate(), dto.getRegistrationDate())) {
            branch.setRegistrationDate(dto.getRegistrationDate());
            updated = true;
        }
        if (!Objects.equals(branch.getLastUpdate(), dto.getLastUpdate())) {
            branch.setLastUpdate(dto.getLastUpdate());
            updated = true;
        }

        return updated;
    }

    private List<BranchResponseDTO> createMockBranchResponseDTOs() {
        BranchResponseDTO dto1 = new BranchResponseDTO();
        dto1.setId(1L);
        dto1.setName("Branch A");
        dto1.setDocument("12345678000101");
        dto1.setCity("São Paulo");
        dto1.setState("SP");
        dto1.setType("Logistico");
        dto1.setActive(true);
        dto1.setRegistrationDate(LocalDate.of(2023, 1, 10));
        dto1.setLastUpdate(LocalDateTime.of(2024, 8, 19, 15, 45));

        BranchResponseDTO dto2 = new BranchResponseDTO();
        dto2.setId(2L);
        dto2.setName("Branch B");
        dto2.setDocument("98765432000109");
        dto2.setCity("Rio de Janeiro");
        dto2.setState("RJ");
        dto2.setType("Administrativo");
        dto2.setActive(false);
        dto2.setRegistrationDate(LocalDate.of(2022, 5, 15));
        dto2.setLastUpdate(LocalDateTime.of(2024, 8, 20, 11, 30));

        BranchResponseDTO dto3 = new BranchResponseDTO();
        dto3.setId(3L);
        dto3.setName("Branch C");
        dto3.setDocument("19283746000101");
        dto3.setCity("Belo Horizonte");
        dto3.setState("MG");
        dto3.setType("Financeiro");
        dto3.setActive(true);
        dto3.setRegistrationDate(LocalDate.of(2021, 3, 20));
        dto3.setLastUpdate(LocalDateTime.of(2024, 8, 18, 14, 0));

        return Arrays.asList(dto1, dto2, dto3);
    }
}
