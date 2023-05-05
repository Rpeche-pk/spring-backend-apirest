package com.lrpa.springboot.backend.apirest.exceptions;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorMessage {

    private String message;
    private String error;
    private String path;

    private LocalDateTime date;
    private String status;


    /*
    Creando los mensajes de excepcion
    public ErrorMessage(Exception exception, String path) {
        this.message = exception.getMessage();
        this.error = exception.getClass().getSimpleName()+"->"+exception.getClass().getName();
        this.path = path;
        this.date= LocalDateTime.now();
    }

    public ErrorMessage(String status,String message, String error, String path, LocalDateTime date) {
        this.status=status;
        this.message = message;
        this.error = error;
        this.path = path;
        this.date = date;

    }*/
}
