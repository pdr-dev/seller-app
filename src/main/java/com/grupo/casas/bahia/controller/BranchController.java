package com.grupo.casas.bahia.controller;

import com.grupo.casas.bahia.controller.dto.request.SellerRequestDTO;
import com.grupo.casas.bahia.controller.dto.response.BranchResponseDTO;
import com.grupo.casas.bahia.controller.dto.response.SellerResponseDTO;
import com.grupo.casas.bahia.service.BranchService;
import com.grupo.casas.bahia.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/{document}")
    public ResponseEntity<BranchResponseDTO> getBranches(@PathVariable String document) {
        return ResponseEntity.ok(branchService.getMockedBranchByDocument(document));
    }
}