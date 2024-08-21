package com.grupo.casas.bahia.controller.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class ResponseErrorDTO {
    private LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);
    private String message;

    public ResponseErrorDTO(String message) {
        this.message = message;
    }

}