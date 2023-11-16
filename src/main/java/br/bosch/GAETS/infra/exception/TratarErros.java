package br.bosch.GAETS.infra.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratarErros {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity tratarRegraDeNegocio(RuntimeException e) {

        // ERRO DE PERMISSÃO, RETORNA ERRO 403
        if (e.getMessage().contains("permissão")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

        // ERRO DE NÃO ENCONTRADO, RETORNA ERRO 404
        else if (e.getMessage().contains("não encontrad") || e.getMessage().contains("inexistente")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarDadosInvalidos(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body("Dados inválidos");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity tratarConstraints(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().body("Dados duplicados");
    }
}