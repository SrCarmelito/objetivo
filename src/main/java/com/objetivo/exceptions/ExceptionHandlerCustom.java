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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> argumentNotValid(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        Set<String> errors = new HashSet<>();
        errors.add(e.getLocalizedMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<StandardError> argumentNotValid(HttpClientErrorException e, HttpServletRequest request) {
        Set<String> errors = new HashSet<>();
        errors.add(e.getLocalizedMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> general(Exception e, HttpServletRequest request) {
        Set<String> errors = new HashSet<>();
        errors.add(e.getLocalizedMessage());
        String message = "Ops, ocorreu um erro inesperado!";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), message, request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<StandardError> noResourceFound(NoResourceFoundException e, HttpServletRequest request) {
        Set<String> errors = new HashSet<>();
        errors.add(e.getResourcePath() + " Não corresponde a nenhum End Point!!!");
        String message = "Método inválido, verifique e tente novamente";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), message, request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(err);
    }

}
