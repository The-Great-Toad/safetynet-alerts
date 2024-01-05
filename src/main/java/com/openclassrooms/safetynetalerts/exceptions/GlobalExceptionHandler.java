package com.openclassrooms.safetynetalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // set the HTTP status code.
    @ExceptionHandler(MethodArgumentNotValidException.class) // Handle all provided exception type.
    public Map<String, String> handleValidationErrors (MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        // Retrieve all error messages from caught exception.
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

        allErrors.forEach(error -> {
            // Cast ObjectError to FieldError type to set the field name as key and the error msg as value in the Map.
            FieldError fe = (FieldError) error;
            errors.put(fe.getField(), fe.getDefaultMessage());
        });
        return errors;
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity handlePersonNotFoundException(PersonNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }
}
