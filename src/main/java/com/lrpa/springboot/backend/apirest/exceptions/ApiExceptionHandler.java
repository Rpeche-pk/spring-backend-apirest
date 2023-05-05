package com.lrpa.springboot.backend.apirest.exceptions;

import org.apache.el.util.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = FileUnknownException.class)
    public ResponseEntity<ErrorMessage> handlerFileUnknownException(HttpServletRequest request, Exception e){
        //ErrorMessage errorMessage= new ErrorMessage(e, request.getRequestURI());

        HttpStatus bad = HttpStatus.BAD_REQUEST;

        ErrorMessage errorMessage= ErrorMessage.builder()
                .status(String.valueOf(bad.value()))
                .message(e.getMessage())
                .error(e.getClass().getSimpleName())
                .date(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
