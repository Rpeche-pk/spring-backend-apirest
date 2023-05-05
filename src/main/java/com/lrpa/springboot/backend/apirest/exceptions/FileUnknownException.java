package com.lrpa.springboot.backend.apirest.exceptions;

import org.springframework.beans.factory.annotation.Value;

public class FileUnknownException extends RuntimeException{


    public FileUnknownException(String message) {
        super(message);
    }

    public FileUnknownException(String message, Throwable cause) {
        super(message, cause);
    }
}
