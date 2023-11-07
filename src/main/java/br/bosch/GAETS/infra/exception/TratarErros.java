package br.bosch.GAETS.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratarErros {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity tratarRegraDeNegocio(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}