package com.objetivo.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class ExceptionHandlerCustom {

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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> illegalArgument(EntityNotFoundException e, HttpServletRequest request) {
        Set<String> errors = new HashSet<>();
        errors.add(e.getLocalizedMessage());
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> constraintValidation(ConstraintViolationException e, HttpServletRequest request) {
        String message = "Requisição contém dados inválidos, verifique os erros e tente novamente!";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Set<String> errors = new HashSet<>(constraintViolations.size());
        errors.addAll(constraintViolations.stream()
                .map(violation -> String.format("%s %s %s", violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage()))
                .toList());

        StandardError err = new StandardError(Instant.now(), status.value() , message, request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(err);
    }

    //TODO TRATAR ESTE ERRO: MethodArgumentTypeMismatchException
    //TODO TRATAR ESTE ERRO: HttpClientErrorException

}
