package com.example.resumeScreening.Be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExistException(ItemAlreadyExistException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.CONFLICT.value());
        errorResponse.put("error", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(ItemNotFoundException ex){
        Map<String,Object> errorResponse = new HashMap<>();
       errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InValidOtpException.class)
    public ResponseEntity<Object> handleInValidOtpException(InValidOtpException ex){
        Map<String,Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.NOT_ACCEPTABLE.value());
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
