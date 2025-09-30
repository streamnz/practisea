package com.streamnz.practisea.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Author cheng hao
 * @Date 26/09/2025 20:31
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PractiseException.class)
    public ResponseEntity<String> handlePractiseException(PractiseException e){
        // response with 40+ and e.message in body
        return ResponseEntity.badRequest().body(e.getMessage());
    }

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException e){
        // response with 40+ and e.message in body
        return ResponseEntity.badRequest().body(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleException(SQLIntegrityConstraintViolationException e){
        // response with 40+ and e.message in body
        return ResponseEntity.badRequest().body("Database error: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e){
        // response with 500 and e.message in body
        return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
    }
}
