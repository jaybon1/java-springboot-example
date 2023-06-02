package com.example.my.common;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpEntity<?> handleException(MethodArgumentNotValidException exception){
        Map<String,String> errorMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(
                ResDTO.builder()
                .code(-1)
                .message("요청 데이터가 유효하지 않습니다.")
                .data(errorMap)
                .build(),
                HttpStatus.BAD_REQUEST
        );

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public HttpEntity<?> handleException(){
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(-1)
                        .message("요청 데이터가 유효하지 않습니다.")
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
