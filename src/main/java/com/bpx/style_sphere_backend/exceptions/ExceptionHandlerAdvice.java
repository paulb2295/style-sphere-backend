package com.bpx.style_sphere_backend.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    public ExceptionHandlerAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            String message = violation.getMessage();
            errors.put(fieldName, message);
        }
        return new ResponseEntity<>(objectToString(errors), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String defaultMessage = Objects.requireNonNull(error.getDefaultMessage());
            errors.put(error.getField(), defaultMessage);
        });
        return new ResponseEntity<>(objectToString(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsedEmailException.class)
    public ResponseEntity<String> usedEmailException(UsedEmailException usedEmailException) {
        return new ResponseEntity<>(objectToString(Map.of("message", usedEmailException.getMessage())), BAD_REQUEST);
    }

    @ExceptionHandler(WrongEmailPasswordException.class)
    public ResponseEntity<String> wrongEmailPasswordException(WrongEmailPasswordException wrongEmailPasswordException) {
        return new ResponseEntity<>(objectToString(Map.of("message", wrongEmailPasswordException.getMessage())), BAD_REQUEST);
    }

    @ExceptionHandler(RefreshTokenGeneratingException.class)
    public ResponseEntity<String> refreshTokenGenerationException(RefreshTokenGeneratingException refreshTokenGeneratingException) {
        return new ResponseEntity<>(objectToString(Map.of("message", refreshTokenGeneratingException.getMessage())), NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<String> invalidRefreshTokenException(InvalidRefreshTokenException invalidRefreshTokenException) {
        return new ResponseEntity<>(objectToString(Map.of("message", invalidRefreshTokenException.getMessage())), BAD_REQUEST);
    }

    @ExceptionHandler(InvalidStoreItemException.class)
    public ResponseEntity<String> stringStoreItemException(InvalidStoreItemException invalidStoreItemException) {
        return new ResponseEntity<>(objectToString(Map.of("message", invalidStoreItemException.getMessage())), BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> itemNotFoundException(ItemNotFoundException itemNotFoundException) {
        return new ResponseEntity<>(objectToString(Map.of("message", itemNotFoundException.getMessage())), NOT_FOUND);
    }

    private String objectToString(Object response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException exception) {
            return "Internal error";
        }
    }
}
