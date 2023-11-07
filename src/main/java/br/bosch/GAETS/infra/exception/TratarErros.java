package br.bosch.GAETS.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratarErros {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity tratarRegraDeNegocio(RuntimeException e) {

        // SE É ERRO DE PERMISSÃO, RETORNA ERRO 403
        if (e.getMessage().contains("permissão")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

        return ResponseEntity.badRequest().body(e.getMessage());
    }
}