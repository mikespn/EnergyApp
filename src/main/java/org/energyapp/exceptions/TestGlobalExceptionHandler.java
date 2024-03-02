package org.energyapp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.energyapp.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

//@Slf4j
//@RestControllerAdvice
//public class TestGlobalExceptionHandler {
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidEx(MethodArgumentTypeMismatchException ex) {
//        ErrorResponse errorResponse = new ErrorResponse(400, ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//}
