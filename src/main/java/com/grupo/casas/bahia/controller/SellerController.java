package com.grupo.casas.bahia.controller;

import com.grupo.casas.bahia.controller.dto.request.SellerRequestDTO;
import com.grupo.casas.bahia.controller.dto.response.SellerResponseDTO;
import com.grupo.casas.bahia.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    private final SellerService service;

    @Autowired
    public SellerController(SellerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SellerResponseDTO> create(@RequestBody SellerRequestDTO sellerRequest) {
        return ResponseEntity.ok(service.create(sellerRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<SellerResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerResponseDTO> update(@PathVariable Long id, @RequestBody SellerRequestDTO sellerRequest) {
        return ResponseEntity.ok(service.update(id, sellerRequest));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}