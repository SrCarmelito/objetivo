package com.objetivo.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class ExcempionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> argumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = "Requisição contém dados inválidos, verifique os erros e tente novamente!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Set<String> errors = new HashSet<>();
        e.getFieldErrors()
                .stream()
                .map(f -> errors.add(String.format("Campo %s %s ", f.getField(), f.getDefaultMessage())))
                .toList();

        StandardError err = new StandardError(Instant.now(), status.value(), message, request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(err);
    }


}
