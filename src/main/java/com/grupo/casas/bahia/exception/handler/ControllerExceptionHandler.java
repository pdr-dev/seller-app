package com.grupo.casas.bahia.exception.handler;

import com.grupo.casas.bahia.controller.dto.response.ResponseErrorDTO;
import com.grupo.casas.bahia.exception.DuplicatedException;
import com.grupo.casas.bahia.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        return new ResponseEntity<>(new ResponseErrorDTO("Request parameters are invalid."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<Object> handleNotFoundException(DuplicatedException ex) {
        return new ResponseEntity<>(new ResponseErrorDTO(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new ResponseErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerErrorException(Exception ex) {
        return new ResponseEntity<>(new ResponseErrorDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}