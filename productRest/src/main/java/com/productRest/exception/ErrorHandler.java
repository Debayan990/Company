package com.productRest.exception;

import com.productRest.dtos.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseDto> handleProductNotFound(ProductNotFoundException ex){
        ResponseDto error = new ResponseDto(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
