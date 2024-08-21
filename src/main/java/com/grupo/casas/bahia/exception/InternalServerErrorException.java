package com.grupo.casas.bahia.exception;

public class InternalServerErrorException extends RuntimeException{

    public InternalServerErrorException(String message, Throwable ex) {
        super(message, ex);
    }
}