package com.grupo.casas.bahia.service.impl;

import com.grupo.casas.bahia.controller.dto.ContractTypeDTO;
import com.grupo.casas.bahia.controller.dto.request.SellerRequestDTO;
import com.grupo.casas.bahia.controller.dto.response.SellerResponseDTO;
import com.grupo.casas.bahia.domain.Seller;
import com.grupo.casas.bahia.domain.repository.SellerRepository;
import com.grupo.casas.bahia.exception.DuplicatedException;
import com.grupo.casas.bahia.exception.InternalServerErrorException;
import com.grupo.casas.bahia.exception.NotFoundException;
import com.grupo.casas.bahia.mapper.SellerMapper;
import com.grupo.casas.bahia.service.BranchService;
import com.grupo.casas.bahia.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository repository;
    private final SellerMapper sellerMapper;
    private final BranchService branchService;

    @Autowired
    public SellerServiceImpl(SellerRepository repository, SellerMapper sellerMapper, BranchService branchService) {
        this.repository = repository;
        this.sellerMapper = sellerMapper;
        this.branchService = branchService;
    }

    @Override
    public SellerResponseDTO create(SellerRequestDTO sellerRequest) {
        try {
            log.info("Initializing creation of seller: {}", sellerRequest);

            repository.findByDocument(sellerRequest.getDocument()).ifPresent(it -> {
                throw new DuplicatedException("Seller for document " + sellerRequest.getDocument() + " already exists.");
            });

            Seller seller = sellerMapper.requestToEntity(sellerRequest);
            seller.setRegistrationNumber(generateRegistrationNumber(sellerRequest.getContractType()));
            seller.setBranch(branchService.getBranchByDocument(sellerRequest.getBranchDocument()));

            Seller savedSeller = repository.save(seller);
            return sellerMapper.entityToResponse(savedSeller);
        } catch (DuplicatedException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error occurred when saving new seller: {}", sellerRequest, ex);
            throw new InternalServerErrorException("An error occurred when trying to save new seller", ex);
        }
    }

    @Override
    public SellerResponseDTO findById(Long id) {
        try {
            log.info("Finding seller by id: {}", id);

            Seller seller = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Seller with id " + id + " not found"));

            return sellerMapper.entityToResponse(seller);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error occurred when searching for seller by id: {}", id, ex);
            throw new InternalServerErrorException("An error occurred when trying to search for seller with id " + id, ex);
        }
    }

    @Override
    public List<SellerResponseDTO> findAll() {
        try {
            return repository.findAll().stream()
                    .map(sellerMapper::entityToResponse)
                    .toList();
        } catch (Exception ex) {
            log.error("Error occurred when retrieving all sellers", ex);
            throw new InternalServerErrorException("An error occurred when trying to retrieve all sellers", ex);
        }
    }

    @Override
    public SellerResponseDTO update(Long id, SellerRequestDTO sellerRequest) {
        try {
            log.info("Updating seller with id {}: {}", id, sellerRequest);

            Seller seller = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Seller with id " + id + " not found"));

            boolean isUpdated = false;

            if (!Objects.equals(seller.getName(), sellerRequest.getName())) {
                seller.setName(sellerRequest.getName());
                isUpdated = true;
                log.info("Updated name for seller id {}: {}", id, sellerRequest.getName());
            }

            if (!Objects.equals(seller.getEmail(), sellerRequest.getEmail())) {
                seller.setEmail(sellerRequest.getEmail());
                isUpdated = true;
                log.info("Updated email for seller id {}: {}", id, sellerRequest.getEmail());
            }

            if (Objects.equals(seller.getBranch(), null) || !Objects.equals(seller.getBranch().getDocument(), sellerRequest.getBranchDocument())) {
                seller.setBranch(branchService.getBranchByDocument(sellerRequest.getBranchDocument()));
                isUpdated = true;
                log.info("Updated branch for seller id {}: {}", id, sellerRequest.getBranchDocument());
            }

            if (!isUpdated) {
                log.info("No changes detected for seller id {}", id);
                return sellerMapper.entityToResponse(seller);
            }

            Seller updatedSeller = repository.save(seller);
            return sellerMapper.entityToResponse(updatedSeller);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error occurred when updating seller with id {}: {}", id, sellerRequest, ex);
            throw new InternalServerErrorException("An error occurred when trying to update seller with id " + id, ex);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            log.info("Deleting seller with id: {}", id);
            repository.deleteById(id);
        } catch (Exception ex) {
            log.error("Error occurred when deleting seller with id: {}", id, ex);
            throw new InternalServerErrorException("An error occurred when trying to delete seller with id " + id, ex);
        }
    }

    private String generateRegistrationNumber(ContractTypeDTO contractType) {
        return repository.findNextRegistrationNumber().orElse("1") + contractType.name();
    }
}