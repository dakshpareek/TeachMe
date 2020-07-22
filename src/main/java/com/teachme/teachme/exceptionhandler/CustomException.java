package com.teachme.teachme.exceptionhandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private String message;
    private HttpStatus status;
    private String path;

    protected CustomException() {
    }

    public CustomException(String message, HttpStatus status, String path) {
        this.message = message;
        this.status = status;
        this.path = path;
    }

}