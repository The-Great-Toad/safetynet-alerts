package com.openclassrooms.safetynetalerts.configuration.exceptions;

import org.springframework.http.HttpStatus;
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

//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(PersonNotFoundException.class)
//    public Map<String, String> handlePersonNotFoundException(PersonNotFoundException ex) {
//        return getExceptionErrorMessageMap(ex);
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return getExceptionErrorMessageMap(ex);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
        return getExceptionErrorMessageMap(ex);
    }

    private Map<String, String> getExceptionErrorMessageMap(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
    }
}
