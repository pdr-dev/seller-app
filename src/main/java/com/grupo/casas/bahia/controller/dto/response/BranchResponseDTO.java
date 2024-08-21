package com.grupo.casas.bahia.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponseDTO {
    private Long id;
    private String name;
    private String document;
    private String city;
    private String state;
    private String type;
    private boolean active;
    private LocalDate registrationDate;
    private LocalDateTime lastUpdate;
}