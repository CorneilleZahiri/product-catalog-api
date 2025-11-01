package com.corneille.product.config;

import com.corneille.product.exception.AttributeAlreadyExistException;
import com.corneille.product.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AttributeAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateEntry(AttributeAlreadyExistException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erreur", exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erreur", exception.getMessage()));

    }

//    @ExceptionHandler(InvalideArgumentException.class)
//    public ResponseEntity<Map<String, String>> handleInvalideArgument(InvalideArgumentException exception) {
//        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("erreur", exception.getMessage()));
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<Void> handlerBadCredentialsException() {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }
}
